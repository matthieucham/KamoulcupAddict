/******************************************************************************
 * Projet   : Octa�dre
 * Soci�t�  : Devinlec
 ******************************************************************************/
package com.kamoulcup.mobile.addict.task.listener;

import com.kamoulcup.mobile.addict.task.DownloadTaskException;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;

/**
 * Listener parent des �couteurs de t�che.
 */
public interface ITaskListener
{

  /**
   * Invoqu�e au fil de l'avanc�e de la t�che.
   */
  void onTaskProgress(FeedProgressInfo info);

  /**
   * Invoqu�e lorsque la t�che a �t� annul�e ou interrompue.
   */
  void onTaskCancelled();

  /**
   * Invoqu�e lorsque la t�che a �t� annul�e ou interrompue.
   * 
   * @param e
   *          L'exception.
   */
  void onTaskError(DownloadTaskException e);
}
