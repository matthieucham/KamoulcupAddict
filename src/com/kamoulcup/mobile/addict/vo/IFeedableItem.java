package com.kamoulcup.mobile.addict.vo;

/**
 * Contrat rempli par les objets qui peuvent être manipulés par une liste de feed.
 */
public interface IFeedableItem
{
  /**
   * @return true si l'item n'a pas été déjà consulté.
   */
  boolean isUnread();

  /**
   * Indique que l'item est lu (le prochain appel à isUnread donnera "false".
   */
  void markAsRead();

  /**
   * Indique que l'item n'est pas lu (le prochain appel à isUnread donnera "true".
   */
  void markAsUnread();

  /**
   * @return Le libellé de l'entête de l'objet.
   */
  String getHeaderLabel();

}
