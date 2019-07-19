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
import net.coding4ever.roedhi.moviecatalogueapi.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> listOfMovie = new ArrayList<>();
    private OnItemClickCallback<Movie> onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListOfMovie(ArrayList<Movie> listOfMovie) {
        this.listOfMovie = listOfMovie;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListOfMovie() {
        return listOfMovie;
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        final Movie movie = listOfMovie.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtOverview.setText(movie.getOverview());

        Glide.with(context)
                .load(movie.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOfMovie != null) {
            return listOfMovie.size();
        } else {
            return 0;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtOverview;
        ImageView imgPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
