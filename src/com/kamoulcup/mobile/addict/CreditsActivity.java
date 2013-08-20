package com.kamoulcup.mobile.addict;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CreditsActivity extends Activity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
    setContentView(R.layout.activity_credits);
    // Show the Up button in the action bar.
    setupActionBar();
    // New block to print the body text in the Credits page
    TextView textView = (TextView) findViewById(R.id.creditsBodyView);
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    // String text = "JustRoids is a game written by Anders Ekstrand as a part of an Android Game "
    // + "Development Tutorial published at <a href=\"http://www.benareby.com/tutorial/\">"
    // + "www.benareby.com/tutorial</a>, where all source code is available.<br/><br/>"
    // + "A very special thank to Martin Felis, who made the asteroid image that plays a central "
    // + "part in this game.";
    textView.setText(Html.fromHtml(getResources().getString(R.string.credits_body_html)));
    // End of body text block
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
    getMenuInflater().inflate(R.menu.credits, menu);
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
    }
    return super.onOptionsItemSelected(item);
  }

}
