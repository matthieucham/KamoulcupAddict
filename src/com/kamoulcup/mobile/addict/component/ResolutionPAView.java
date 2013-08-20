package com.kamoulcup.mobile.addict.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.R;
import com.kamoulcup.mobile.addict.domain.Club;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.utils.ResourcesUtils;

/**
 * Présentation d'une mise en vente (PA / MV / RE) Nom du joueur, club, poste et mise � prix.
 */
public class ResolutionPAView extends LinearLayout
{
  // Déclaration des composants graphiques.
  private TextView _tvNomPrenom;

  private TextView _tvClubPoste;

  // Constructeurs.

  /**
   * Création depuis le code.
   * 
   * @param pContext
   */
  public ResolutionPAView(Context pContext)
  {
    super(pContext);
    this.setOrientation(VERTICAL);
    LayoutInflater inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.resolution_pa_view, this);
  }

  /**
   * Création depuis le parser XML Android.
   * 
   * @param pContext
   * @param pAttrs
   */
  public ResolutionPAView(Context pContext, AttributeSet pAttrs)
  {
    super(pContext, pAttrs);
    this.setOrientation(VERTICAL);
    LayoutInflater inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.resolution_pa_view, this);
  }

  @Override
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    _tvNomPrenom = (TextView) findViewById(R.id.prenomNom);
    _tvClubPoste = (TextView) findViewById(R.id.clubPoste);
    setupComponentProperties();
  }

  // Attributs "métier"

  /**
   * La résolution présentée ici.
   */
  private Resolution _resolution;

  public final Resolution getResolution()
  {
    return _resolution;
  }

  public final void setResolution(Resolution presolution)
  {
    _resolution = presolution;
    setupComponentProperties();
  }

  /**
   * Assigne les propriétés aux éléments graphiques.
   */
  private void setupComponentProperties()
  {
    if (_tvNomPrenom != null && _resolution != null)
    {
      _tvNomPrenom.setText((_resolution.getCible().getPrenom() + " " + _resolution.getCible().getNom()).trim());
    }
    if (_tvClubPoste != null && _resolution != null)
    {
      String poste =
        ResourcesUtils.getStringResourceByName(getContext(), "poste" + _resolution.getCible().getPoste());
      String club;
      if (_resolution.getCible().getClub() == Club.SANS_CLUB)
      {
        club = getResources().getString(R.string.noClub);
      }
      else
      {
        club = _resolution.getCible().getClub().getNom();
      }
      _tvClubPoste.setText((poste + ", " + club).trim());
    }
    invalidate();
  }
}
