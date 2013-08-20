package com.kamoulcup.mobile.addict.utils;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Manager des erreurs � pr�senter � l'utilisateur. Singleton qui peut �tre appel� par n'importe quelle classe de
 * vue.
 */
public class ErrorManager
{

  /**
   * Singleton instanci� par le classloader.
   */
  private static final ErrorManager INSTANCE = new ErrorManager();

  /**
   * Constructeur.
   */
  private ErrorManager()
  {
  }

  /**
   * @return Instance.
   */
  public static final ErrorManager getInstance()
  {
    return INSTANCE;
  }

  /**
   * Traite le feedback utilisateur d'une erreur survenue dans l'application : ouvre un AlertDialog avec un seul
   * bouton d'acquittement.
   * 
   * @param errorContext
   *          activit� qui a rencontr� l'erreur.
   * @param errorMessage
   *          Message � afficher.
   */
  public void displayErrorMessage(Context errorContext, String errorMessage)
  {
    // 1. Instantiate an AlertDialog.Builder with its constructor
    AlertDialog.Builder builder = new AlertDialog.Builder(errorContext);

    // 2. Chain together various setter methods to set the dialog characteristics
    builder.setMessage(errorMessage).setNeutralButton(R.string.ok, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface pDialog, int pWhich)
      {
        // Rien � faire car Android ferme tout seul les dialogs apr�s click.
      }
    });

    // 3. Get the AlertDialog from create()
    AlertDialog dialog = builder.create();
    dialog.show();
  }

}
