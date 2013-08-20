package com.kamoulcup.mobile.addict.vo;

/**
 * Indicateur de progression de la mise � jour d'un feed
 */
public enum FeedProgressInfo
{
  CONTACTING_SERVER(0, "contacting_server"), DOWNLOADING_FEED(1, "downloading_feed"), PROCESSING_FEED(2,
    "processing_feed"), DONE(3, "done");

  /**
   * La cl� du label de chaque item de l'enum.
   */
  private final String _key;

  /**
   * Id de l'�tape.
   */
  private final int _stepId;

  private FeedProgressInfo(int sid, String key)
  {
    _stepId = sid;
    _key = key;
  }

  public final String getKey()
  {
    return _key;
  }

  public final int getStepId()
  {
    return _stepId;
  }
}
