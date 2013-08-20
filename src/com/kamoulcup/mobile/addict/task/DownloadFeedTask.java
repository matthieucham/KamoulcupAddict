package com.kamoulcup.mobile.addict.task;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.kamoulcup.mobile.addict.task.listener.IDownloadFeedTaskListener;
import com.kamoulcup.mobile.addict.task.parser.FeedParserException;
import com.kamoulcup.mobile.addict.task.parser.IFeedParser;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;

/**
 * @param <T>
 *          Le type d'objet parsé.
 */
public class DownloadFeedTask<T> extends AsyncTask<URL, FeedProgressInfo, List<T>>
{

  /**
   * Timeout de lecture sur la liaison HTTP (ms).
   */
  private static final int READ_TIMEOUT = 10000;

  /**
   * Timeout de connexion sur la liaison HTTP (ms).
   */
  private static final int CONNECT_TIMEOUT = 15000;

  /**
   * Ecouteur de la tâche.
   */
  private IDownloadFeedTaskListener<T> _listener;

  /**
   * Parseur du flux.
   */
  private IFeedParser<T> _parser;

  public DownloadFeedTask(IDownloadFeedTaskListener<T> listener, IFeedParser<T> parser)
  {
    _listener = listener;
    _parser = parser;
  }

  @Override
  protected List<T> doInBackground(URL... pParams)
  {
    int nbUrls = pParams.length;
    // 1 : Aller chercher le Feed sur la ou les URLs indiquées
    InputStream currentInput = null;
    List<T> output = null;
    for (int i = 0; i < nbUrls; i++)
    {
      publishProgress(FeedProgressInfo.CONTACTING_SERVER);
      publishProgress(FeedProgressInfo.DOWNLOADING_FEED);
      try
      {
        HttpURLConnection conn = (HttpURLConnection) pParams[i].openConnection();
        try
        {
          conn.setReadTimeout(READ_TIMEOUT);
          conn.setConnectTimeout(CONNECT_TIMEOUT);
          conn.setRequestMethod("GET");
          conn.setDoInput(true);
          conn.connect();
          currentInput = new BufferedInputStream(conn.getInputStream());
          publishProgress(FeedProgressInfo.PROCESSING_FEED);
          List<T> partialOut = _parser.parseFeed(currentInput);
          if (partialOut != null)
          {
            if (output == null)
            {
              output = new ArrayList<T>();
            }
            output.addAll(partialOut);
          }
        }
        finally
        {
          conn.disconnect();
        }
      }
      catch (IOException e)
      {
        _listener.onTaskError(new DownloadTaskException("Erreur de connexion � l'url '"
          + pParams[i].toExternalForm() + "'", e));
      }
      catch (FeedParserException e)
      {
        _listener.onTaskError(new DownloadTaskException("Erreur de parsing du flux renvoy� par l'url '"
          + pParams[i].getPath() + "'", e));
      }
    }
    publishProgress(FeedProgressInfo.DONE);
    return output;
  }

  @Override
  protected void onProgressUpdate(FeedProgressInfo... progress)
  {
    if (progress.length > 0)
    {
      _listener.onTaskProgress(progress[0]);
    }
  }

  @Override
  protected void onPostExecute(List<T> result)
  {
    _listener.onFeedDownloaded(result);
  }
}
