package com.kamoulcup.mobile.addict.utils;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Manager des erreurs à présenter à l'utilisateur. Singleton qui peut être appelé par n'importe quelle classe de
 * vue.
 */
public class ErrorManager
{

  /**
   * Singleton instancié par le classloader.
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
   *          activité qui a rencontré l'erreur.
   * @param errorMessage
   *          Message à afficher.
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
        // Rien à faire car Android ferme tout seul les dialogs après click.
      }
    });

    // 3. Get the AlertDialog from create()
    AlertDialog dialog = builder.create();
    dialog.show();
  }

}
