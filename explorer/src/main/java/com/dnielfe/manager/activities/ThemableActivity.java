package com.dnielfe.manager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dnielfe.manager.R;
import com.dnielfe.manager.utils.AppPreferences;

public abstract class ThemableActivity extends AppCompatActivity {

  private int mCurrentTheme;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // get default preferences at start for setting the theme
    AppPreferences.updatePreferences(this);
    mCurrentTheme = AppPreferences.getTheme();
    if (mCurrentTheme == 0) {
      setTheme(R.style.ThemeLight);
    } else {
      setTheme(R.style.ThemeDark);
    }
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mCurrentTheme != AppPreferences.getTheme()) {
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
