/******************************************************************************
 * Projet   : Octaèdre
 * Société  : Devinlec
 ******************************************************************************/
package com.kamoulcup.mobile.addict.task.listener;

import com.kamoulcup.mobile.addict.task.DownloadTaskException;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;

/**
 * Listener parent des écouteurs de tâche.
 */
public interface ITaskListener
{

  /**
   * Invoquée au fil de l'avancée de la tâche.
   */
  void onTaskProgress(FeedProgressInfo info);

  /**
   * Invoquée lorsque la tâche a été annulée ou interrompue.
   */
  void onTaskCancelled();

  /**
   * Invoquée lorsque la tâche a été annulée ou interrompue.
   * 
   * @param e
   *          L'exception.
   */
  void onTaskError(DownloadTaskException e);
}
