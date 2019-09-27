package net.coding4ever.roedhi.moviecataloguefinalproject.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.OnItemClickCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> items = new ArrayList<>();

    private OnItemClickCallback itemClickListener;

    public OnItemClickCallback getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickCallback itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.items.size());
    }

    public void clearData() {
        int count = getItemCount();

        this.items.clear();
        notifyItemRangeRemoved(0, count);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    public abstract BaseViewHolder<T> createHolder(ViewGroup parent, int viewType);

    public abstract void bindHolder(BaseViewHolder holder, int position);

}