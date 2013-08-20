package com.kamoulcup.mobile.addict.auctions;

import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.kamoulcup.mobile.addict.R;
import com.kamoulcup.mobile.addict.domain.Resolution;
import com.kamoulcup.mobile.addict.task.DownloadTaskException;
import com.kamoulcup.mobile.addict.task.DownloadURLBuilder;
import com.kamoulcup.mobile.addict.task.TasksFactory;
import com.kamoulcup.mobile.addict.task.listener.IDownloadFeedTaskListener;
import com.kamoulcup.mobile.addict.utils.ErrorManager;
import com.kamoulcup.mobile.addict.utils.ResourcesUtils;
import com.kamoulcup.mobile.addict.vo.FeedProgressInfo;
import com.kamoulcup.mobile.addict.vo.FeedableItemAdapter;
import com.kamoulcup.mobile.addict.vo.ResolutionFeedableItem;

/**
 * Fragment charger de lister les derniers résultats d'enchères à consulter.
 */
public class GetAuctionsFragment extends ListFragment implements IDownloadFeedTaskListener<Resolution>
{
  /**
   * Activité en écoute du fragment.
   */
  private OnAuctionSelectedListener _callback;

  /**
   * Callback à implémenter par l'activity qui a importé ce fragment
   */
  public interface OnAuctionSelectedListener
  {
    /**
     * Réagit à la sélection d'une enchère dans la liste.
     * 
     * @param resolution
     *          L'enchère résolue (= Resolution) sélectionnée.
     */
    void onAuctionSelected(Resolution resolution);
  }

  /**
   * Wrapper des données affichées dans la liste.
   */
  private FeedableItemAdapter _adapter;

  /**
   * Dialog de progression du traitement.
   */
  private ProgressDialog _progressDialog;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    //
    // // We need to use a different list item layout for devices older than Honeycomb
    // int layout =
    // Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
    // ? android.R.layout.simple_list_item_activated_1
    // : android.R.layout.simple_list_item_1;

    _progressDialog = new ProgressDialog(getActivity());
    _progressDialog.setTitle(R.string.getResolutionsFeed);

    _adapter = new FeedableItemAdapter(getActivity(), R.layout.feedlist_item_row);
    setListAdapter(_adapter);

    retrieveAuctions();
  }

  @Override
  public void onAttach(Activity activity)
  {
    super.onAttach(activity);
    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception.
    try
    {
      _callback = (OnAuctionSelectedListener) activity;
    }
    catch (ClassCastException e)
    {
      throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
    }
  }

  @Override
  public void onStart()
  {
    super.onStart();
    // When in two-pane layout, set the listview to highlight the selected list item
    // (We do this during onStart because at the point the listview is available.)
    if (getFragmentManager().findFragmentById(R.id.auction_detail_fragment) != null)
    {
      getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
  }

  /**
   * Fonction qui "va chercher" les enchères à résoudre.
   */
  private void retrieveAuctions()
  {
    ConnectivityManager connMgr =
      (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected())
    {

      showProgressDialog();
      // fetch data
      AsyncTask<URL, FeedProgressInfo, List<Resolution>> task =
        TasksFactory.getInstance().buildDownloadResolutionsFeedTask(this)
      /* buildResolutionsMockTask(this) */;
      task.execute(new DownloadURLBuilder(getActivity()).buildURLsAuctions());
    }
    else
    {
      // display error
      ErrorManager.getInstance().displayErrorMessage(getActivity(), getString(R.string.error_no_network));
    }
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
    // Notify the parent activity of selected item
    ResolutionFeedableItem clickedItem = (ResolutionFeedableItem) getListAdapter().getItem(position);
    _callback.onAuctionSelected(clickedItem.getResolution());
    clickedItem.markAsRead();
    _adapter.notifyDataSetChanged();
    // Set the item as checked to be highlighted when in two-pane layout
    getListView().setItemChecked(position, true);
  }

  public void updateAuctions()
  {
    retrieveAuctions();
  }

  private void showProgressDialog()
  {
    _progressDialog.setCancelable(false);
    _progressDialog.setIndeterminate(true);
    _progressDialog.show();
  }

  // ==================================== Impl. IDownloadFeedTaskListener<Resolution> ========================

  @Override
  public void onTaskProgress(FeedProgressInfo info)
  {
    _progressDialog.setMessage(ResourcesUtils.getStringResourceByName(getActivity(), info.getKey()));
  }

  @Override
  public void onTaskCancelled()
  {
    _progressDialog.dismiss();
  }

  @Override
  public void onTaskError(DownloadTaskException pE)
  {
    _progressDialog.dismiss();
  }

  @Override
  public void onFeedDownloaded(List<Resolution> pEntries)
  {
    // TODO comparer ces résultats avec les Resolutions déjà connues (stockées localement)
    // TODO mettre à jour les entêtes de la liste.
    if (pEntries != null)
    {
      ResolutionFeedableItem newEntry;
      for (Resolution item : pEntries)
      {
        newEntry = new ResolutionFeedableItem(item, getActivity());
        // On n'ajoute que si cette resolution n'existe pas
        if (_adapter.getPosition(newEntry) < 0)
        {
          _adapter.add(new ResolutionFeedableItem(item, getActivity()));
        }
      }
    }
    _progressDialog.dismiss();
  }

}
