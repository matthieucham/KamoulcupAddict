package com.kamoulcup.mobile.addict.utils;

import android.content.Context;

/**
 * Utilitaires statiques de gestion des resources.
 */
public final class ResourcesUtils
{

  /**
   * Nom du package de l'appli.
   */
  public static final String APPLICATION_PACKAGE_NAME = "com.kamoulcup.mobile.addict";

  /**
   * Classe d'utilitaires.
   */
  private ResourcesUtils()
  {
  }

  /**
   * Retourne la valeur d'une chaine dans strings.xml lorsque l'on ne connait que sa "clé".
   */
  public static final String getStringResourceByName(Context context, String stringIdentifier)
  {
    int resId = context.getResources().getIdentifier(stringIdentifier, "string", APPLICATION_PACKAGE_NAME);
    if (resId == 0)
    {
      return stringIdentifier;
    }
    else
    {
      return context.getString(resId);
    }
  }
}
