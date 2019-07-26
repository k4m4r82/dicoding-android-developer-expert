package net.coding4ever.roedhi.moviecataloguelocalstorage.listeners;

import java.util.List;

public interface LoadDataCallback<T> {

    void onDataLoaded(List<T> list);
    void onError(Throwable e);

}
