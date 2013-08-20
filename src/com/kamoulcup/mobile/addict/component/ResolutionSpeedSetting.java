/**
 * 
 */
package com.kamoulcup.mobile.addict.component;

/**
 * Paramètrage de la vitesse de résolution.
 */
public enum ResolutionSpeedSetting
{
  SLOW(1, 1500, 1500), MEDIUM(2, 500, 500), FAST(3, 100, 300);

  private final int _settingId;

  private final long _delayBefore;

  private final long _delayAfter;

  private ResolutionSpeedSetting(int p_settingId, long p_delayBefore, long p_delayAfter)
  {
    _settingId = p_settingId;
    _delayBefore = p_delayBefore;
    _delayAfter = p_delayAfter;
  }

  public final int getSettingId()
  {
    return _settingId;
  }

  public final long getDelayBefore()
  {
    return _delayBefore;
  }

  public final long getDelayAfter()
  {
    return _delayAfter;
  }

  /**
   * Clé du paramètre dans la map SharedPreferences.
   */
  public static final String SETTING_KEY = "pref_resolutionSpeed";

  public static ResolutionSpeedSetting getBySettingId(String id)
  {
    int convertedId = Integer.valueOf(id);
    switch (convertedId)
    {
      case 1:
        return SLOW;
      case 2:
        return MEDIUM;
      case 3:
        return FAST;
      default:
        return MEDIUM;
    }
  }

}
