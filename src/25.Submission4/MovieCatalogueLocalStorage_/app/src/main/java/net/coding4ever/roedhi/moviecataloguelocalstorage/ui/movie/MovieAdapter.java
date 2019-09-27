package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.movie;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.Movie;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseViewAdapter;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseViewAdapter<Movie> {

    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<Movie> createHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(R.layout.item_movie, parent);
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position) {

        final Movie movie = getItem(position);

        holder.bindView(movie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MovieAdapter.this.getItemClickListener() == null) return;

                MovieAdapter.this.getItemClickListener().onItemClicked(movie);
            }
        });
    }

    public class MovieViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.img_poster) ImageView imgPoster;

        public MovieViewHolder(@LayoutRes int resId, @NonNull ViewGroup parent) {
            super(resId, parent);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Movie obj) {

            txtTitle.setText(obj.getTitle());
            txtOverview.setText(obj.getOverview());

            Glide.with(context)
                    .load(obj.getPosterPath())
                    .into(imgPoster);
        }
    }
}
