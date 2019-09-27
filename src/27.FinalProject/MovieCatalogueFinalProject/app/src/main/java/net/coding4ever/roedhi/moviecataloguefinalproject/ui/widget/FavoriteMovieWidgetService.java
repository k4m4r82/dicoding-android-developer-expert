package net.coding4ever.roedhi.moviecataloguefinalproject.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteMovieWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteMovieStackRemoteViewsFactory(this.getApplicationContext());
    }

}
