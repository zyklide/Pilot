package com.dnielfe.manager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dnielfe.manager.activities.BrowserActivity;
import com.dnielfe.manager.R;
import com.dnielfe.manager.activities.ThemableActivity;

public class SettingsActivity extends ThemableActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    // set up ActionBar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public void onBackPressed() {
    this.finish();
    Intent i = new Intent(getBaseContext(), BrowserActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
  }

  public void proxyRestart() {
    restart();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menu) {
    switch (menu.getItemId()) {
      case android.R.id.home:
        this.finish();
        Intent i = new Intent(getBaseContext(), BrowserActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return true;
    }
    return false;
  }
}