/******************************************************************************
 * Projet   : Octa�dre
 * Soci�t�  : Devinlec
 ******************************************************************************/
/**
 * 
 */
package com.kamoulcup.mobile.addict.task.listener;

import java.util.List;

/**
 * Impl�ment�e par les �couteurs des t�ches de t�l�chargement de feed.
 */
public interface IDownloadFeedTaskListener<T> extends ITaskListener
{

  /**
   * Invoqu�e par la task lorsque le download et le parsing du feed sont termin�es : on passe les objets ainsi
   * construits � l'�couteur.
   * 
   * @param entries
   *          Les objets pars�s, ou null si rien.
   */
  void onFeedDownloaded(List<T> entries);
}
