package com.dnielfe.manager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.dnielfe.manager.R;
import com.stericson.RootTools.RootTools;

public final class AppPreferences {

  private static SharedPreferences mPrefs;

  private AppPreferences() {
  }

  public static void updatePreferences(Context context) {
    mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    rootAccess();
  }

  public static boolean showThumbnail() {
    return mPrefs.getBoolean("showpreview", true);
  }

  public static boolean showHiddenFiles() {
    return mPrefs.getBoolean("displayhiddenfiles", true);
  }

  public static boolean rootAccess() {
    return mPrefs.getBoolean("enablerootaccess", false) && RootTools.isAccessGiven();
  }

  public static boolean reverseListView() {
    return mPrefs.getBoolean("reverseList", false);
  }

  public static String getDefaultDir() {
    return mPrefs.getString("defaultdir", Environment.getExternalStorageDirectory().getPath());
  }

  public static int getListAppearance() {
    return Integer.parseInt(mPrefs.getString("viewmode", "1"));
  }

  public static int getSortType() {
    return Integer.parseInt(mPrefs.getString("sort", "1"));
  }

  public static int getDefaultTheme() {
    return mTheme;
  }

  public static void setDefaultTheme(int theme) {
    mTheme = theme;
  }
}
