package net.coding4ever.roedhi.moviecatalogueapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecatalogueapi.models.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context context;
    private ArrayList<TvShow> listOfTvShow = new ArrayList<>();
    private OnItemClickCallback<TvShow> onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListOfTvShow(ArrayList<TvShow> listOfTvShow) {
        this.listOfTvShow = listOfTvShow;
        notifyDataSetChanged();
    }

    public ArrayList<TvShow> getListOfTvShow() {
        return listOfTvShow;
    }

    public TvShowAdapter(Context context) {
        this.context = context;
    }
    
    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TvShowViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        final TvShow tvShow = listOfTvShow.get(position);

        holder.txtTitle.setText(tvShow.getTitle());
        holder.txtOverview.setText(tvShow.getOverview());

        Glide.with(context)
                .load(tvShow.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(tvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOfTvShow != null) {
            return listOfTvShow.size();
        } else {
            return 0;
        }
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtOverview;
        ImageView imgPoster;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
