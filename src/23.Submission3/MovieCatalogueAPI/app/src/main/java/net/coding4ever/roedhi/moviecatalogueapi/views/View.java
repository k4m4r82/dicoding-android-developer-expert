package net.coding4ever.roedhi.moviecatalogueapi.views;

import java.util.ArrayList;

public interface View<T> {

    void onLoadDataSucceed(ArrayList<T> list);
    void onLoadDataFailure();

}
