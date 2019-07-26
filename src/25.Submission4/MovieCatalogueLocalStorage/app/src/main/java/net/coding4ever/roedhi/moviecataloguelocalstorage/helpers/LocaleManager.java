package net.coding4ever.roedhi.moviecataloguelocalstorage.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleManager {

    public static final  String LANGUAGE_ENGLISH   = "en";
    public static final  String LANGUAGE_INDONESIA = "in";
    public static final String LANGUAGE_KEY       = "language_key";

    private SharedPreferences prefs;

    public LocaleManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLocale(Context c) {
        updateResources(c, getLanguage());
    }

    public void setNewLocale(Context c, String language) {
        persistLanguage(language);
        updateResources(c, language);
    }

    public String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();

        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}