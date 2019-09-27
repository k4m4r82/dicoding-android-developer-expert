package net.coding4ever.roedhi.moviecataloguefinalproject.helpers;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import net.coding4ever.roedhi.moviecataloguefinalproject.ui.widget.FavoriteMovieWidget;

public class WidgetHelper {

    public static void refreshWidget(Context context) {

        ComponentName theWidget = new ComponentName(context, FavoriteMovieWidget.class);

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(theWidget);
        context.sendBroadcast(intent);

    }
}
