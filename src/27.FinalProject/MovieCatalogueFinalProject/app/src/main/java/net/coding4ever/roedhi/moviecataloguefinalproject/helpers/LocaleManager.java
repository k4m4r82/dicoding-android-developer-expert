package net.coding4ever.roedhi.moviecataloguefinalproject.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleManager {

    public static final String LANGUAGE_ENGLISH   = "en";
    public static final String LANGUAGE_INDONESIA = "in";
    public static final String LANGUAGE_KEY       = "language_key";

    private PreferencesManager prefManager;

    public LocaleManager(Context context) {
        prefManager = new PreferencesManager(context);
    }

    public void setLocale(Context c) {
        updateResources(c, getLanguage());
    }

    public void setNewLocale(Context c, String language) {
        persistLanguage(language);
        updateResources(c, language);
    }

    public String getLanguage() {
        return  prefManager.getValue(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    private void persistLanguage(String language) {
        prefManager.setValue(LANGUAGE_KEY, language);
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