package com.kamoulcup.mobile.addict.vo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.R;

/**
 * ArrayAdapter spécialisé dans la manipulation des Feeds.
 */
public class FeedableItemAdapter extends ArrayAdapter<IFeedableItem>
{

  private Context _context;

  private int _layoutResourceId;

  public FeedableItemAdapter(Context pContext, int pTextViewResourceId)
  {
    super(pContext, pTextViewResourceId);
    _context = pContext;
    _layoutResourceId = pTextViewResourceId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    View row = convertView;
    FeedHeaderView holder = null;

    if (row == null)
    {
      LayoutInflater inflater = ((FragmentActivity) _context).getLayoutInflater();
      row = inflater.inflate(_layoutResourceId, parent, false);

      holder = new FeedHeaderView();
      holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

      row.setTag(holder);
    }
    else
    {
      holder = (FeedHeaderView) row.getTag();
    }

    IFeedableItem item = super.getItem(position);
    if (item.isUnread())
    {
      holder.txtTitle.setTypeface(null, Typeface.BOLD);
    }
    else
    {
      holder.txtTitle.setTypeface(null, Typeface.NORMAL);
    }
    holder.txtTitle.setText(item.getHeaderLabel());

    return row;
  }

  /**
   * Classe interne qui retient l'itemRenderer de l'item. Attention : sera réutilisé avec des données variables.
   */
  static class FeedHeaderView
  {
    TextView txtTitle;
  }

}
