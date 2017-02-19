package com.dnielfe.manager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dnielfe.manager.utils.AppPreferences;

public abstract class ThemableActivity extends AppCompatActivity {

  private int mCurrentTheme;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // get default preferences at start for setting the theme
    AppPreferences.updatePreferences(this);

    mCurrentTheme = AppPreferences.getDefaultTheme();
    setTheme(mCurrentTheme);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mCurrentTheme != AppPreferences.getDefaultTheme()) {
      restart();
    }
  }

  protected void restart() {
    final Bundle outState = new Bundle();
    onSaveInstanceState(outState);
    final Intent intent = new Intent(this, getClass());
    finish();
    overridePendingTransition(0, 0);
    startActivity(intent);
  }
}
