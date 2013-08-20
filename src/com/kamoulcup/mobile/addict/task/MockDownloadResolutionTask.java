package com.kamoulcup.mobile.addict.task;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;

import com.kamoulcup.mobile.addict.domain.Club;
import com.kamoulcup.mobile.addict.domain.Ekyp;
import com.kamoulcup.mobile.addict.domain.Joueur;
import com.kamoulcup.mobile.addict.domain.Offre;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.task.listener.IDownloadFeedTaskListener;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;

/**
 * Impl mockup du t�l�chargement des r�solutions
 */
public class MockDownloadResolutionTask extends AsyncTask<URL, FeedProgressInfo, List<Resolution>>
{
  private static final Club CLUB_1 = new Club("Le Mans");

  private static final Club CLUB_2 = new Club("Monaco");

  private static final Ekyp EKYP_1 = new Ekyp("Béjon14");

  private static final Ekyp EKYP_2 = new Ekyp("Pan Bagnat");

  private static final Joueur CIBLE_1 = new Joueur("Keita", "Alphousseiny", "A", CLUB_1);

  private static final Joueur CIBLE_2 = new Joueur("Gosso", "Jean-Jacques", "M", CLUB_2);

  private static final Offre[] OFFRES_1 =
  {new Offre(2.2f, false), new Offre(5.1f, false), new Offre(13.7f, false), new Offre(18f, true),
    new Offre(20.8f, true) };

  private static final Offre[] OFFRES_2 =
  {new Offre(4.1f, false), new Offre(5.1f, true), new Offre(7.4f, false) };

  private static final Resolution[] RESOLUTIONS_1 =
  {
    new Resolution(1, CIBLE_1, EKYP_1, new Date(), "PA", 0.1f, EKYP_1, 13.7f, new Date(), false, Arrays
      .asList(OFFRES_1), false),
    new Resolution(2, CIBLE_2, EKYP_2, new Date(), "MV", 4f, EKYP_1, 7.4f, new Date(), false, Arrays
      .asList(OFFRES_2), false) };

  /**
   * Delai d'attente simul� pour avoir la r�ponse.
   */
  private long _lagDelay;

  /**
   * Le listener.
   */
  private IDownloadFeedTaskListener<Resolution> _listener;

  public MockDownloadResolutionTask(IDownloadFeedTaskListener<Resolution> listener, long delay)
  {
    _listener = listener;
    _lagDelay = delay;
  }

  @Override
  protected List<Resolution> doInBackground(URL... pParams)
  {
    try
    {
      publishProgress(FeedProgressInfo.CONTACTING_SERVER);
      publishProgress(FeedProgressInfo.DOWNLOADING_FEED);
      Thread.sleep(_lagDelay);
      publishProgress(FeedProgressInfo.PROCESSING_FEED);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    return Arrays.asList(RESOLUTIONS_1);
  }

  protected void onProgressUpdate(FeedProgressInfo... progress)
  {
    if (progress.length > 0)
    {
      _listener.onTaskProgress(progress[0]);
    }
  }

  @Override
  protected void onPostExecute(List<Resolution> result)
  {
    publishProgress(FeedProgressInfo.DONE);
    _listener.onFeedDownloaded(result);
  }
}
