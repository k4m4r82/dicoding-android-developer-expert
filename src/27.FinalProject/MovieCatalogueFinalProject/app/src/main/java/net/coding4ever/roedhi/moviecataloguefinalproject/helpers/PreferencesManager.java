package net.coding4ever.roedhi.moviecataloguefinalproject.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private SharedPreferences prefs;

    public PreferencesManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setValue(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getValue(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

}
