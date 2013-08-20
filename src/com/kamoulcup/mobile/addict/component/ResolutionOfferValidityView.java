/**
 * 
 */
package com.kamoulcup.mobile.addict.component;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kamoulcup.mobile.addict.R;

/**
 * 
 */
public class ResolutionOfferValidityView extends FrameLayout {

	// Paramètres du composant.
	/**
	 * Durée de l'animation (param du composant).
	 */
	private long _animDuration;

	/**
	 * Offre valide ou non valide ?
	 */
	private boolean _isValid;

	/**
	 * Marqueur à montrer ou pas ? On ne le montre que pour les dernières offres
	 * d'une enchère.
	 */
	private boolean _showMarker;

	// Setters des paramètres du composant.

	public final void setAnimDuration(long p_animDuration) {
		_animDuration = p_animDuration;
		if (p_animDuration > 0) {
			_invisibleDuration = p_animDuration / 2;
			_visibleDuration = p_animDuration / 2;
		}
	}

	public final void setIsValid(boolean p_isValid) {
		_isValid = p_isValid;
		setupProperties();
	}

	public final void setShowMarker(boolean p_showMarker) {
		_showMarker = p_showMarker;
		setupProperties();
	}

	// attributs.

	/**
	 * Durée pendant laquelle le marqueur reste invisible.
	 */
	private long _invisibleDuration;

	/**
	 * Durée pendant laquelle le marqueur devient (ou est déjà) visible.
	 */
	private long _visibleDuration;

	/**
	 * ImageView qui contient l'indicateur.
	 */
	private ImageView _markerView;

	/**
	 * @param pContext
	 */
	public ResolutionOfferValidityView(Context pContext) {
		super(pContext);
		_markerView = new ImageView(getContext());
		addView(_markerView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		setupProperties();
	}

	//
	private void setupProperties() {
		if (_markerView != null && _animDuration > 0) {
			// Au départ, l'icône est masquée.
			_markerView.setAlpha(0f);
			if (_showMarker) {
				// Sinon, ne rien faire.
				if (_isValid) {
					_markerView.setImageResource(R.drawable.ico_valid);
				} else {
					_markerView.setImageResource(R.drawable.ico_invalid);
				}

				// Démarrage de l'animation après le temps réglementaire
				Animation showMarkerAnim = new AlphaAnimation(0f, 1f);
				showMarkerAnim.setDuration(_visibleDuration);
				showMarkerAnim.setInterpolator(new DecelerateInterpolator());
				// showMarkerAnim.setStartOffset(_invisibleDuration);
				_markerView.startAnimation(showMarkerAnim);
				invalidate();
			}
		}
	}
}
