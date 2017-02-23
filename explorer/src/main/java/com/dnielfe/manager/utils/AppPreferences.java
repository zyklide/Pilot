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
    mTheme = Integer.parseInt(mPrefs.getString("prefTheme", Integer.toString(R.style.ThemeLight)));
    getRootEnabled();
  }

  public static boolean getHiddenFiles() {
    return mPrefs.getBoolean("prefHiddenFiles", true);
  }

  public static boolean getThumbnails() {
    return mPrefs.getBoolean("prefThumbnails", true);
  }

  public static int getViewMode() {
    return Integer.parseInt(mPrefs.getString("prefViewMode", "1"));
  }

  public static int getSort() {
    return Integer.parseInt(mPrefs.getString("prefSort", "1"));
  }

  public static boolean getReverseList() {
    return mPrefs.getBoolean("prefReverseList", false);
  }

  public static int getTheme() {
    return mTheme;
  }

  public static void setTheme(int theme) {
    mTheme = theme;
  }

  public static String getCustomPath() {
    return mPrefs.getString("prefCustomPath", Environment.getExternalStorageDirectory().getPath());
  }

  public static boolean getRootEnabled() {
    return mPrefs.getBoolean("prefRootEnabled", false) && RootTools.isAccessGiven();
  }
}
