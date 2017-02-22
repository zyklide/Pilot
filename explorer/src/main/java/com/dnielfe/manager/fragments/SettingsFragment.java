package com.dnielfe.manager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.dnielfe.manager.R;
import com.dnielfe.manager.activities.SettingsActivity;
import com.dnielfe.manager.utils.AppPreferences;

public final class SettingsFragment extends PreferenceFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
    init();
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof SettingsActivity)) {
      throw new RuntimeException("should only be attached to SettingsActivity");
    }
  }

  private void init() {
    final ListPreference theme = (ListPreference) findPreference("preference_theme");
    theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        final int chosenTheme = Integer.parseInt((String) newValue);
        if (chosenTheme != AppPreferences.getDefaultTheme()) {
          AppPreferences.setDefaultTheme(chosenTheme);
          ((SettingsActivity) getActivity()).proxyRestart();
          return true;
        }
        return false;
      }
    });
  }
}