package com.kamoulcup.mobile.addict.task;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.kamoulcup.mobile.addict.config.Configuration;

/**
 * Builder d'url de téléchargement de données.
 */
public class DownloadURLBuilder
{
  public static final String AUCTIONS_FEED_SUFFIX = "mobilecom/getResolutionsFeed.php";

  /**
   * Contexte Android.
   */
  private Context _context;

  /**
   * Constructeur.
   * 
   * @param ctx
   */
  public DownloadURLBuilder(Context ctx)
  {
    _context = ctx;
  }

  /**
   * @return La liste des URLs (1 seul élément) qui servent à interroger le feed des résolutions d'enchères.
   */
  public URL[] buildURLsAuctions()
  {
    String serverHost = Configuration.getServerHost(_context);
    try
    {
      URL url = new URL("http", serverHost, AUCTIONS_FEED_SUFFIX);
      URL[] output = new URL[1];
      output[0] = url;
      return output;
    }
    catch (MalformedURLException e)
    {
      System.err.println("URL de téléchargement mal formée");
      e.printStackTrace();
      return null;
    }

  }
}
