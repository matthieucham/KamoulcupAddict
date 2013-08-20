package com.kamoulcup.mobile.addict.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.R;
import com.kamoulcup.mobile.addict.domain.Offre;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.utils.OffreMontantComparator;
import com.kamoulcup.mobile.addict.utils.ResolutionUtils;
import com.kamoulcup.mobile.addict.utils.ResourcesUtils;
import com.kamoulcup.mobile.addict.vo.PlayableOffer;

/**
 * Composant qui permet de "jouer" une résolution.
 */
public class ResolutionPlayer extends LinearLayout {
	// Déclaration d'états comme en Flex:
	private enum ResolutionPlayerState {
		UNRESOLVED, RESOLVING, RESOLVED;
	}

	// Déclaration des composants du layout
	// Constants : déclarés dans le layout
	private ResolutionPAView _paView;

	// Dynamiques : déclarés dans le code et ajoutés et retirés dynamiquement
	private Button _btPlay;

	private ProgressBar _pbResolution;

	private TextView _tvTypeVente;

	private TextView _tvMAP;

	private TextView _tvOffer;

	private LinearLayout _frame;

	/**
	 * Marqueur de la validité de l'offre.
	 */
	// private ResolutionOfferValidityView _roValidity;

	private LinearLayout _offerLayout;

	private ResolutionResultView _resultView;

	/**
	 * Point d'insertion des parties dynamiques.
	 */
	private ScrollView _insertPoint;

	/**
	 * Tâche de résolution en cours.
	 */
	private ResolveAuctionTask _currentTask;

	/**
	 * Etat courant.
	 */
	private ResolutionPlayerState _currentState;

	public ResolutionPlayer(Context pContext) {
		super(pContext);
		this.setOrientation(VERTICAL);
		// Tant que l'ui n'est pas prête, état indéterminé.
		_currentState = null;
	}

	public ResolutionPlayer(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		this.setOrientation(VERTICAL);
		LayoutInflater inflater = (LayoutInflater) pContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.resolution_player, this);
		// Tant que l'ui n'est pas prête, état indéterminé.
		_currentState = null;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		_frame = (LinearLayout) findViewById(R.id.framebg);
		_paView = (ResolutionPAView) findViewById(R.id.resolutionPAView1);
		_tvTypeVente = (TextView) findViewById(R.id.typeVente);
		_tvMAP = (TextView) findViewById(R.id.map);
		_btPlay = (Button) findViewById(R.id.btPlay);
		_btPlay.setOnClickListener(new OnClickListener() {
			// Clic sur le bouton play.
			@Override
			public void onClick(View pV) {
				doPlayAuction();
			}
		});
		_insertPoint = (ScrollView) findViewById(R.id.insert_point);
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
		fadeIn.setDuration(1000);
		setAnimation(fadeIn);
		assignState(ResolutionPlayerState.UNRESOLVED);
	}

	/**
	 * La résolution jouée ici.
	 */
	private Resolution _resolution;

	public final Resolution getResolution() {
		return _resolution;
	}

	public final void setResolution(Resolution presolution) {
		_resolution = presolution;
		assignState(ResolutionPlayerState.UNRESOLVED);
	}

	/**
	 * Vitesse de résolution (paramètre). Par défaut: moyen.
	 */
	private volatile ResolutionSpeedSetting _speedSetting = ResolutionSpeedSetting.MEDIUM;

	public final ResolutionSpeedSetting getSpeedSetting() {
		return _speedSetting;
	}

	public final void setSpeedSetting(ResolutionSpeedSetting p_speedSetting) {
		_speedSetting = p_speedSetting;
	}

	// ====================================================================

	// ====================================================================

	/**
	 * Résoud l'enchère.
	 */
	private void doPlayAuction() {
		assignState(ResolutionPlayerState.RESOLVING);
	}

	/**
	 * Fait basculer le composant dans un nouvel état.
	 * 
	 * @param newState
	 */
	private void assignState(ResolutionPlayerState newState) {
		// On ne réagit que si c'est un vrai changement d'état.
		if (newState != null /* _currentState */) {
			switch (newState) {
			case UNRESOLVED:
				if (_btPlay != null) {
					_btPlay.setEnabled(true);
				}
				if (_offerLayout != null) {
					_insertPoint.removeView(_offerLayout);
				}
				if (_resultView != null) {
					_insertPoint.removeView(_resultView);
				}
				break;
			case RESOLVING:
				if (_resultView != null) {
					_insertPoint.removeView(_resultView);
				}
				if (_btPlay != null) {
					_btPlay.setEnabled(false);
				}
				if (_offerLayout == null) {
					buildOfferLayout();
				} else {
					_tvOffer.setText("");
					_insertPoint.removeView(_offerLayout);
				}
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER_HORIZONTAL;
				_insertPoint.addView(_offerLayout, params);

				break;
			case RESOLVED:
				if (_btPlay != null) {
					_btPlay.setEnabled(true);
				}
				if (_offerLayout != null) {
					_insertPoint.removeView(_offerLayout);
				}
				if (_resultView != null) {
					_insertPoint.removeView(_resultView);
				}
				if (_resultView == null) {
					_resultView = new ResolutionResultView(getContext());
				}
				Animation revealAnim = AnimationUtils.loadAnimation(
						getContext(), R.anim.reveal_winner);
				_resultView.setAnimation(revealAnim);
				_insertPoint.addView(_resultView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				break;
			default:
				// Impossible
				break;
			}
			_currentState = newState;
		}
		setupProperties(newState);
		invalidate();
		requestLayout();
	}

	/**
	 * Partie du composant visible uniquement pendant la résol.
	 */
	private void buildOfferLayout() {
		_offerLayout = new LinearLayout(getContext());
		_offerLayout.setOrientation(VERTICAL);
		_offerLayout.setGravity(Gravity.CENTER);

		LinearLayout innerLayout = new LinearLayout(getContext());
		innerLayout.setGravity(Gravity.CENTER);

		_pbResolution = new ProgressBar(getContext());
		_pbResolution.setIndeterminate(true);
		_tvOffer = new TextView(getContext());
		// _roValidity = new ResolutionOfferValidityView(getContext());
		_offerLayout.addView(_pbResolution, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		innerLayout.addView(_tvOffer, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// innerLayout.addView(_roValidity, new LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		_offerLayout.addView(innerLayout, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	/**
	 * Affecte les propriétés métier aux composant, à l'entrée dans l'état passé
	 * en paramètre.
	 * 
	 * @param p_currentState
	 */
	private void setupProperties(ResolutionPlayerState pState) {
		if (pState != null && _resolution != null && _frame != null
				&& _tvTypeVente != null && _tvMAP != null && _paView != null) {
			if (_currentTask != null) {
				_currentTask.cancel(true);
			}
			// Quel que soit l'état :
			if (_resolution.isPA()) {
				updateBackground(
						_frame,
						getResources().getDrawable(
								R.drawable.resolution_frame_pa));
				_tvTypeVente.setTextColor(getResources()
						.getColor(R.color.fg_PA));
			} else if (_resolution.isMV()) {
				updateBackground(
						_frame,
						getResources().getDrawable(
								R.drawable.resolution_frame_mv));
				_tvTypeVente.setTextColor(getResources()
						.getColor(R.color.fg_MV));
			} else if (_resolution.isRE()) {
				updateBackground(
						_frame,
						getResources().getDrawable(
								R.drawable.resolution_frame_re));
				_tvTypeVente.setTextColor(getResources()
						.getColor(R.color.fg_RE));
			}
			String typeVente = ResourcesUtils.getStringResourceByName(
					getContext(), "vente" + _resolution.getTypeVente());
			_tvTypeVente.setText(typeVente);
			_tvMAP.setText(Html.fromHtml(getResources()
					.getString(
							R.string.map,
							ResolutionUtils.formatAmount(_resolution
									.getMontantVente()),
							_resolution.getAuteur().getNom())));
			switch (pState) {
			case UNRESOLVED:
				_paView.setResolution(_resolution);
				break;
			case RESOLVING:
				_currentTask = new ResolveAuctionTask();
				_currentTask.execute(_resolution);
				break;
			case RESOLVED:
				_resultView.setResolution(_resolution);
				break;
			default:
				// Impossible
				break;
			}
			invalidate();
		}
	}

	/**
	 * Compatibilité pour la fonction setBackground.
	 * 
	 * @param target
	 * @param bg
	 */
	private void updateBackground(View target, Drawable bg) {
		if (target != null) {
			if (Build.VERSION.SDK_INT >= 16) {
				target.setBackground(bg);
			} else {
				target.setBackgroundDrawable(bg);
			}
		}
	}

	/**
	 * Propriétés qui bougent au cours de la progression.
	 */
	private void setProgressProperties(ResolutionPlayerProgress progress) {
		if (_pbResolution != null && _tvOffer != null) {
			_pbResolution.setProgress(progress.getProgress());
			_tvOffer.setText(ResolutionUtils.formatAmount(progress
					.getCurrentOffer().getOffre().getMontant()));
			// _roValidity.setIsValid(!progress.getCurrentOffer().getOffre()
			// .isRejetee());
			// // Pour tester : toujours montrer.
			// _roValidity.setShowMarker(progress.getCurrentOffer().isShowStatus());
			// _roValidity.setAnimDuration(_speedSetting.getDelayAfter());
			invalidate();
		}
	}

	/**
	 * POJO de transport de l'avancement de la tâche de résolution simulée.
	 */
	private class ResolutionPlayerProgress {

		private int _progress;

		private PlayableOffer _currentOffer;

		public ResolutionPlayerProgress(PlayableOffer offre, int progress) {
			_progress = progress;
			_currentOffer = offre;

		}

		public final int getProgress() {
			return _progress;
		}

		public final PlayableOffer getCurrentOffer() {
			return _currentOffer;
		}

	}

	private class ResolveAuctionTask extends
			AsyncTask<Resolution, ResolutionPlayerProgress, Void> {

		/**
		 * Liste des offres qui vont être diffusées au cours de l'avancement de
		 * la résolution.
		 */
		private List<PlayableOffer> _offersToPlayInGoodOrder;

		/**
		 * Prépare la liste des offres à jouer, cad: - la liste des offres en
		 * ordre croissant sans s'occuper de la validité - si la dernière de la
		 * liste est invalide, on rajoute en queue une copie de l'avant-dernière
		 * - ainsi de suite jusqu'à tomber sur une valide.
		 */
		private void setupOffersToPlay(Resolution resol) {
			Collections.sort(resol.getOffres(), new OffreMontantComparator());
			_offersToPlayInGoodOrder = new ArrayList<PlayableOffer>();
			if (ResolutionUtils.isPA(resol.getTypeVente())) {
				// On rajoute en tête la PA qui constitue toujours une offre
				// valide
				_offersToPlayInGoodOrder.add(new PlayableOffer(new Offre(resol
						.getMontantVente(), false)));
			}
			if (resol.getOffres() != null) {
				// Ici les offres sont dans l'ordre croissant.
				for (Offre current : resol.getOffres()) {
					_offersToPlayInGoodOrder.add(new PlayableOffer(current));
				}
			}
			goBackTillValid(_offersToPlayInGoodOrder,
					_offersToPlayInGoodOrder.size() - 1);
		}

		/**
		 * Récursif
		 */
		private void goBackTillValid(List<PlayableOffer> pOffers,
				int indexOfLastOffer) {
			int cursor = indexOfLastOffer;
			if (cursor > 0) {
				PlayableOffer lastOffre = pOffers.get(cursor);
				if (lastOffre.getOffre().isRejetee()) {
					cursor--;
					if (cursor >= 0) {
						lastOffre = pOffers.get(cursor);
						pOffers.add(new PlayableOffer(new Offre(lastOffre
								.getOffre().getMontant(), lastOffre.getOffre()
								.isRejetee()), true));
						goBackTillValid(pOffers, cursor);
					}
				} else {
					lastOffre.setShowStatus(true);
				}
			}
		}

		@Override
		protected Void doInBackground(Resolution... pParams) {
			// Préparation de la liste.
			setupOffersToPlay(pParams[0]);
			int nbOffres = _offersToPlayInGoodOrder.size();
			for (int i = 0; i < nbOffres; i++) {
				if (isCancelled()) {
					break;
				}
				try {
					Thread.sleep(_speedSetting.getDelayBefore());
					int avancement = ((i + 1) / nbOffres) * 100;
					publishProgress(new ResolutionPlayerProgress(
							_offersToPlayInGoodOrder.get(i), avancement));
					Thread.sleep(_speedSetting.getDelayAfter());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(ResolutionPlayerProgress... progress) {
			if (progress.length > 0) {
				setProgressProperties(progress[0]);
			}
		}

		@Override
		protected void onPostExecute(Void v) {
			proceedToResolved();
		}

		private void proceedToResolved() {
			assignState(ResolutionPlayerState.RESOLVED);
		}

	}
}
