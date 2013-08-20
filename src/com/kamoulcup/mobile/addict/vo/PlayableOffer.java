package com.kamoulcup.mobile.addict.vo;

import com.kamoulcup.mobile.addict.domain.Offre;

/**
 * Decorateur autour de Offre
 */
public class PlayableOffer {

	private Offre _offre;
	private boolean _showStatus;

	public PlayableOffer(Offre of) {
		this(of, false);
	}

	public PlayableOffer(Offre of, boolean show) {
		_offre = of;
		_showStatus = show;
	}

	public boolean isShowStatus() {
		return _showStatus;
	}

	public void setShowStatus(boolean show) {
		_showStatus = show;
	}

	public Offre getOffre() {
		return _offre;
	}

}
