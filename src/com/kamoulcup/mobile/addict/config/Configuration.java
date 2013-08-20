package com.kamoulcup.mobile.addict.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;

public final class Configuration
{
  /**
   * Nom du fichier de conf.
   */
  private static final String CONFIG_FILENAME = "kamoulcup.properties";

  /**
   * Instance du singleton.
   */
  private static Configuration INSTANCE;

  /**
   * Propriétés lues dans le fichier de conf.
   */
  private Properties _properties;

  private Configuration(Context ctx)
  {
    loadProperties(ctx);
  }

  /**
   * Charge les propriétés trouvées dans le fichier.
   */
  private void loadProperties(Context ctx)
  {
    AssetManager assetManager = ctx.getResources().getAssets();
    try
    {
      InputStream inputStream = assetManager.open(CONFIG_FILENAME);
      _properties = new Properties();
      _properties.load(inputStream);
    }
    catch (IOException e)
    {
      System.err.println("Failed to open config file '" + CONFIG_FILENAME + "'");
      e.printStackTrace();
    }
  }

  /**
   * Pour obtenir l'adresse du serveur de l'édition du jeu en cours.
   */
  public static synchronized String getServerHost(Context ctx)
  {
    if (INSTANCE == null)
    {
      INSTANCE = new Configuration(ctx);
    }
    return INSTANCE._properties.getProperty("server.host");
  }

  /**
   * Ce terminal se trouve-t-il derrière un proxy pour se connecter au réseau ? Emulator oui.
   */
  public static synchronized boolean connectionUseProxy(Context ctx)
  {
    if (INSTANCE == null)
    {
      INSTANCE = new Configuration(ctx);
    }
    return Boolean.parseBoolean(INSTANCE._properties.getProperty("useProxy"));
  }

  /**
   * Proxy host.
   */
  public static synchronized String getProxyHost(Context ctx)
  {
    if (INSTANCE == null)
    {
      INSTANCE = new Configuration(ctx);
    }
    return INSTANCE._properties.getProperty("proxy.host");
  }

  /**
   * Proxy port.
   */
  public static synchronized String getProxyPort(Context ctx)
  {
    if (INSTANCE == null)
    {
      INSTANCE = new Configuration(ctx);
    }
    return INSTANCE._properties.getProperty("proxy.port");
  }
}
