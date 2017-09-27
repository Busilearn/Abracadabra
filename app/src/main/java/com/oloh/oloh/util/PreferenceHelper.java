package com.oloh.oloh.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by stran on 24/08/2017.
 */


public class PreferenceHelper {

    public final static String FIRST_TIME = "FirstTime";
    public final static String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";
    public final static String SUBMIT_LOGS = "CrashLogs";
    // Handle Local Caching of data for responsiveness
    public static final String MY_CART_LIST_LOCAL = "MyCartItems";
    private static PreferenceHelper preferenceHelperInstance = new PreferenceHelper();

    private PreferenceHelper() {
    }

    public static PreferenceHelper getPrefernceHelperInstace() {

        return preferenceHelperInstance;
    }

    public void setBoolean(Context appContext, String key, Boolean value) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putBoolean(key, value).apply();
    }

    public void setString(Context appContext, String key, String value) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putString(key, value).apply();
    }

    // To retrieve values from shared preferences:
    public boolean getBoolean(Context appContext, String key,
                              Boolean defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getBoolean(key, defaultValue);
    }

    public String getString(Context appContext, String key, String defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getString(key, defaultValue);
    }


}
