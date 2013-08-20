package com.kamoulcup.mobile.addict.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.R;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.utils.ResolutionUtils;

/**
 * Pr�sente le r�sultat d'une vente
 */
public class ResolutionResultView extends LinearLayout
{
  // Déclaration des composants graphiques.
  private TextView _tvGagnant;

  private TextView _tvValeur;

  private TextView _tvEcart;

  // Constructeurs.

  /**
   * Cr�ation depuis le code.
   * 
   * @param pContext
   */
  public ResolutionResultView(Context pContext)
  {
    super(pContext);
    this.setOrientation(VERTICAL);
    LayoutInflater inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.resolution_result_view, this);
  }

  /**
   * Création depuis le parser XML Android.
   * 
   * @param pContext
   * @param pAttrs
   */
  public ResolutionResultView(Context pContext, AttributeSet pAttrs)
  {
    super(pContext, pAttrs);
    this.setOrientation(VERTICAL);
    LayoutInflater inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.resolution_result_view, this);
  }

  @Override
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    _tvGagnant = (TextView) findViewById(R.id.gagnant);
    _tvValeur = (TextView) findViewById(R.id.valeur);
    _tvEcart = (TextView) findViewById(R.id.ecart);
    setupComponentProperties();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom)
  {
    super.onLayout(changed, left, top, right, bottom);
    _tvGagnant = (TextView) findViewById(R.id.gagnant);
    _tvValeur = (TextView) findViewById(R.id.valeur);
    _tvEcart = (TextView) findViewById(R.id.ecart);
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
    if (_resolution != null)
    {
      if (_resolution.isNoValidOffer())
      {
        if (_tvGagnant != null)
        {
          _tvGagnant.setText(getResources().getString(R.string.cancelled_no_offer));
        }
      }
      else
      {
        // Resolution réussie standard
        if (_tvGagnant != null)
        {
          _tvGagnant.setText(_resolution.getGagnant().getNom());
        }
        if (_tvValeur != null)
        {
          _tvValeur.setText(ResolutionUtils.formatAmount(_resolution.getMontantGagnant()));
        }
        if (_tvEcart != null)
        {
          // Calculer l'écart
          float ecart = _resolution.computeGap();
          if (ecart >= 0)
          {
            _tvEcart.setText(getResources().getString(R.string.gap) + " " + ResolutionUtils.formatAmount(ecart));
          }
        }
      }
    }
    invalidate();
  }

}
