package com.kamoulcup.mobile.addict;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.kamoulcup.mobile.addict.auctions.AuctionDetailFragment;
import com.kamoulcup.mobile.addict.auctions.GetAuctionsFragment;
import com.kamoulcup.mobile.addict.domain.Resolution;

public class GetAuctionResultsActivity extends FragmentActivity implements
  GetAuctionsFragment.OnAuctionSelectedListener
{

  /**
   * Holder du fragment qui liste les enchères résolues.
   */
  private GetAuctionsFragment _getAuctionsFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
    setContentView(R.layout.auctions_list);

    // Check that the activity is using the layout version with
    // the fragment_container FrameLayout
    if (findViewById(R.id.fragment_container) != null)
    {

      // However, if we're being restored from a previous state,
      // then we don't need to do anything and should return or else
      // we could end up with overlapping fragments.
      if (savedInstanceState != null)
      {
        return;
      }

      // Create an instance of GetAuctionsFragment
      GetAuctionsFragment auctionsFrag = new GetAuctionsFragment();

      // In case this activity was started with special instructions from an Intent,
      // pass the Intent's extras to the fragment as arguments
      auctionsFrag.setArguments(getIntent().getExtras());

      // Add the fragment to the 'fragment_container' FrameLayout
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, auctionsFrag).commit();
      _getAuctionsFragment = auctionsFrag;
      invalidateOptionsMenu();
    }
    else
    {
      // Cas du layout à 2 colonnes.
      _getAuctionsFragment =
        (GetAuctionsFragment) getSupportFragmentManager().findFragmentById(R.id.get_auctions_fragment);
    }

    // Show the Up button in the action bar.
    setupActionBar();
  }

  /**
   * Set up the {@link android.app.ActionBar}, if the API is available.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  private void setupActionBar()
  {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
    {
      if (getActionBar() != null)
      {
        getActionBar().setDisplayHomeAsUpEnabled(true);
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.get_auction_results, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        // This ID represents the Home or Up button. In the case of this
        // activity, the Up button is shown. Use NavUtils to allow users
        // to navigate up one level in the application structure. For
        // more details, see the Navigation pattern on Android Design:
        //
        // http://developer.android.com/design/patterns/navigation.html#up-vs-back
        //
        NavUtils.navigateUpFromSameTask(this);
        return true;
      case R.id.action_refresh:
        if (_getAuctionsFragment != null)
        {
          _getAuctionsFragment.updateAuctions();
        }
        return true;
      case R.id.action_settings:
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Holder du fragment visible.
   */
  private AuctionDetailFragment _auctionDetailFragment;

  @Override
  public void onAuctionSelected(Resolution pResolution)
  {
    // The user selected the headline of an article from the HeadlinesFragment
    // Capture the article fragment from the activity layout
    AuctionDetailFragment auctionFrag =
      (AuctionDetailFragment) getSupportFragmentManager().findFragmentById(R.id.auction_detail_fragment);

    if (auctionFrag != null)
    {
      // If article frag is available, we're in two-pane layout...
      // Call a method in the ArticleFragment to update its content
      auctionFrag.displayAuction(pResolution);
      _auctionDetailFragment = auctionFrag;
    }
    else
    {
      // If the frag is not available, we're in the one-pane layout and must swap frags...

      // Create fragment and give it an argument for the selected article
      AuctionDetailFragment newFragment = new AuctionDetailFragment();
      Bundle args = new Bundle();
      args.putParcelable(AuctionDetailFragment.DISPLAYED_AUCTION, pResolution);
      newFragment.setArguments(args);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

      // Replace whatever is in the fragment_container view with this fragment,
      // and add the transaction to the back stack so the user can navigate back
      transaction.replace(R.id.fragment_container, newFragment).addToBackStack(null);
      // Commit the transaction
      transaction.commit();
      _auctionDetailFragment = newFragment;
    }
  }

  // ==============================================================
}
