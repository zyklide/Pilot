package com.dnielfe.manager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.dnielfe.manager.R;
import com.stericson.RootTools.RootTools;

public final class AppPreferences {

  private static SharedPreferences mPrefs;
  private static int mTheme;

  private AppPreferences() {
  }

  public static void updatePreferences(Context context) {
    mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    mTheme = Integer.parseInt(mPrefs.getString("preference_theme", Integer.toString(R.style.ThemeLight)));
    getRootEnabled();
  }

  public static boolean getHiddenFiles() {
    return mPrefs.getBoolean("displayhiddenfiles", true);
  }

  public static boolean getThumbnails() {
    return mPrefs.getBoolean("showpreview", true);
  }

  public static int getViewMode() {
    return Integer.parseInt(mPrefs.getString("viewmode", "1"));
  }

  public static int getSort() {
    return Integer.parseInt(mPrefs.getString("sort", "1"));
  }

  public static boolean getReverseList() {
    return mPrefs.getBoolean("reverseList", false);
  }

  public static int getTheme() {
    return mTheme;
  }

  public static void setTheme(int theme) {
    mTheme = theme;
  }

  public static String getCustomPath() {
    return mPrefs.getString("defaultdir", Environment.getExternalStorageDirectory().getPath());
  }

  public static boolean getRootEnabled() {
    return mPrefs.getBoolean("enablerootaccess", false) && RootTools.isAccessGiven();
  }
}
