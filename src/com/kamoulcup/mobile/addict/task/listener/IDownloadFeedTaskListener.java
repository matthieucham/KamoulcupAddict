/******************************************************************************
 * Projet   : Octaèdre
 * Société  : Devinlec
 ******************************************************************************/
/**
 * 
 */
package com.kamoulcup.mobile.addict.task.listener;

import java.util.List;

/**
 * Implémentée par les écouteurs des tâches de téléchargement de feed.
 */
public interface IDownloadFeedTaskListener<T> extends ITaskListener
{

  /**
   * Invoquée par la task lorsque le download et le parsing du feed sont terminées : on passe les objets ainsi
   * construits à l'écouteur.
   * 
   * @param entries
   *          Les objets parsés, ou null si rien.
   */
  void onFeedDownloaded(List<T> entries);
}
