package com.kamoulcup.mobile.addict.auctions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.R;
import com.kamoulcup.mobile.addict.component.ResolutionPlayer;
import com.kamoulcup.mobile.addict.component.ResolutionSpeedSetting;
import com.kamoulcup.mobile.addict.domain.Resolution;

/**
 * Fragment qui présente le détail d'une résolution
 */
public class AuctionDetailFragment extends Fragment
{
  /**
   * Clé de l'attribut correspondant à la résolution à afficher transportée dans le bundle.
   */
  public static final String DISPLAYED_AUCTION = "com.kamoulcup.mobile.addict.auctions.DisplayedAuction";

  /**
   * La résolution affichée par le fragment.
   */
  private Resolution _currentDisplayed;

  /**
   * Composant perso qui affiche les détails de la vue.
   */
  private ResolutionPlayer _auctionViewer;

  /**
   * Textview affiché à la place du player lorsque l'utilisateur n'a encore sélectionné personne.
   */
  private TextView _txtNoSelection;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {

    // If activity recreated (such as from screen rotate), restore
    // the previous article selection set by onSaveInstanceState().
    // This is primarily necessary when in the two-pane layout.
    if (savedInstanceState != null)
    {
      _currentDisplayed = (Resolution) savedInstanceState.getParcelable(DISPLAYED_AUCTION);
    }

    ScrollView scroller = new ScrollView(getActivity());
    _txtNoSelection = new TextView(getActivity());
    _txtNoSelection.setText(getActivity().getText(R.string.select_auction));
    _txtNoSelection.setGravity(Gravity.CENTER);
    scroller.addView(_txtNoSelection, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    return scroller;

  }

  @Override
  public void onActivityCreated(Bundle bundle)
  {
    super.onActivityCreated(bundle);
  }

  @Override
  public void onStart()
  {
    super.onStart();

    // During startup, check if there are arguments passed to the fragment.
    // onStart is a good place to do this because the layout has already been
    // applied to the fragment at this point so we can safely call the method
    // below that sets the article text.
    Bundle args = getArguments();
    if (args != null)
    {
      // Set article based on argument passed in
      displayAuction((Resolution) args.getParcelable(DISPLAYED_AUCTION));
    }
    else if (_currentDisplayed != null)
    {
      // Set article based on saved instance state defined during onCreateView
      displayAuction(_currentDisplayed);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
    // Save the current article selection in case we need to recreate the fragment
    outState.putParcelable(DISPLAYED_AUCTION, _currentDisplayed);
  }

  private ViewGroup getFragmentView()
  {
    return (ViewGroup) getView();
  }

  /**
   * Affiche la résolution sur le fragment et permet de la dérouler.
   * 
   * @param resolution
   *          la résolution à afficher.
   */
  public void displayAuction(Resolution resolution)
  {
    if (resolution != null)
    {
      if (_auctionViewer == null)
      {
        LayoutInflater inflater =
          (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.resolution_player_container, getFragmentView(), false);
        _auctionViewer = (ResolutionPlayer) v.findViewById(R.id.auction);
        ((ScrollView) _txtNoSelection.getParent()).removeView(_txtNoSelection);
        // getFragmentView().removeView(_txtNoSelection);
        getFragmentView().addView(_auctionViewer,
          new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      }
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
      _auctionViewer.setSpeedSetting(ResolutionSpeedSetting.getBySettingId(prefs.getString(
        ResolutionSpeedSetting.SETTING_KEY, "0")));
      _auctionViewer.setResolution(resolution);
      _currentDisplayed = resolution;
    }
  }
}
