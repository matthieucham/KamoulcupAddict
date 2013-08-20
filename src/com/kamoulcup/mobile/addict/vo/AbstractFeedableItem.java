package com.kamoulcup.mobile.addict.vo;

public abstract class AbstractFeedableItem implements IFeedableItem
{

  /**
   * Indique que l'item n'est pas encore lu.
   */
  private boolean _isUnread;

  public AbstractFeedableItem()
  {
    _isUnread = true;
  }

  @Override
  public boolean isUnread()
  {
    return _isUnread;
  }

  @Override
  public void markAsRead()
  {
    _isUnread = false;
  }

  @Override
  public void markAsUnread()
  {
    _isUnread = true;
  }

  @Override
  public abstract String getHeaderLabel();

}
