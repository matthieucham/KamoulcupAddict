package com.kamoulcup.mobile.addict.vo;

import android.content.Context;

import com.kamoulcup.mobile.addict.domain.Club;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.utils.ResourcesUtils;

/**
 * Représentation d'une "Resolution" dans la liste de manipulation des feeds.
 */
public class ResolutionFeedableItem extends AbstractFeedableItem
{

  /**
   * Context Android de l'appelant.
   */
  private Context _context;

  /**
   * Objet wrappé.
   */
  private Resolution _resolution;

  public ResolutionFeedableItem(Resolution resol, Context ctx)
  {
    _resolution = resol;
    _context = ctx;
  }

  @Override
  public String getHeaderLabel()
  {
    String poste = ResourcesUtils.getStringResourceByName(_context, "poste" + _resolution.getCible().getPoste());
    String prenom = _resolution.getCible().getPrenom();
    if (prenom == null)
    {
      prenom = "";
    }
    if (_resolution.getCible() != null && _resolution.getCible().getClub() != Club.SANS_CLUB)
    {
      return (prenom + " " + _resolution.getCible().getNom() + " (" + poste + ", "
        + _resolution.getCible().getClub().getNom() + ")").trim();
    }
    else
    {
      return (prenom + " " + _resolution.getCible().getNom() + " (" + poste + ")").trim();
    }
  }

  /**
   * @return La résolution wrappée.
   */
  public final Resolution getResolution()
  {
    return _resolution;
  }

  @Override
  public boolean equals(Object r)
  {
    if (!(r instanceof ResolutionFeedableItem))
    {
      return false;
    }
    return ((ResolutionFeedableItem) r).getResolution().equals(getResolution());
  }
}
