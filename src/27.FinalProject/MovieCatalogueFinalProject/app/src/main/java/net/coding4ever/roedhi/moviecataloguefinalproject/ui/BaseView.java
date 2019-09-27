package net.coding4ever.roedhi.moviecataloguefinalproject.ui;

import java.util.List;

public interface BaseView<T> {

    void onLoadDataSucceed(List<T> list);
    void onLoadDataFailure();

}
