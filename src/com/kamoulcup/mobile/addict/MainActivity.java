package com.kamoulcup.mobile.addict;

import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamoulcup.mobile.addict.config.Configuration;
import com.kamoulcup.mobile.addict.utils.ResourcesUtils;

public class MainActivity extends Activity implements OnItemClickListener
{

  static final LauncherIcon[] ICONS =
  {new LauncherIcon(R.drawable.auction, "auctions", GetAuctionResultsActivity.class),
    new LauncherIcon(R.drawable.credits, "credits", CreditsActivity.class), };

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
    // setContentView(R.layout.activity_main);
    setContentView(R.layout.dashboard);
    GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
    gridview.setAdapter(new ImageAdapter(this));
    gridview.setOnItemClickListener(this);

    // Hack to disable GridView scrolling
    gridview.setOnTouchListener(new OnTouchListener()
    {
      @Override
      public boolean onTouch(View pV, MotionEvent pEvent)
      {
        return pEvent.getAction() == MotionEvent.ACTION_MOVE;
      }
    });

    // Params de l'appli
    if (Configuration.connectionUseProxy(this))
    {
      Properties systemProperties = System.getProperties();
      systemProperties.setProperty("http.proxyHost", Configuration.getProxyHost(this));
      systemProperties.setProperty("http.proxyPort", Configuration.getProxyPort(this));
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id)
  {
    Intent intent = null;
    intent = new Intent(this, ICONS[position].intentClass);
    if (intent != null)
    {
      startActivity(intent);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  // =====================================================================
  // http://agafix.org/android-how-to-make-a-dashboard-using-a-gridview/
  // =====================================================================

  static class LauncherIcon
  {
    final String text;

    final int imgId;

    final Class intentClass;

    public LauncherIcon(int imgId, String text, Class ic)
    {
      super();
      this.imgId = imgId;
      this.text = text;
      this.intentClass = ic;
    }

  }

  static class ImageAdapter extends BaseAdapter
  {
    private Context mContext;

    public ImageAdapter(Context c)
    {
      mContext = c;
    }

    @Override
    public int getCount()
    {
      return ICONS.length;
    }

    @Override
    public LauncherIcon getItem(int position)
    {
      return null;
    }

    @Override
    public long getItemId(int position)
    {
      return 0;
    }

    static class ViewHolder
    {
      public ImageView icon;

      public TextView text;
    }

    // Create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
      View v = convertView;
      ViewHolder holder;
      if (v == null)
      {
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = vi.inflate(R.layout.dashboard_icon, null);
        holder = new ViewHolder();
        holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
        holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
        v.setTag(holder);
      }
      else
      {
        holder = (ViewHolder) v.getTag();
      }

      holder.icon.setImageResource(ICONS[position].imgId);
      holder.text.setText(ResourcesUtils.getStringResourceByName(mContext, "dashboard_" + ICONS[position].text));

      return v;
    }
  }
}
