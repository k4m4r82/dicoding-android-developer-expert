package net.coding4ever.roedhi.moviecataloguefinalproject.ui.favorite;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.WidgetHelper;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local.MovieRepository;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewAdapter;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMovieAdapter extends BaseViewAdapter<Movie> {

    private Context context;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<Movie> createHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(R.layout.item_favorite_movie, parent);
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position) {

        final Movie movie = getItem(position);

        holder.bindView(movie);

        ((MovieViewHolder)holder).btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieRepository repository = new MovieRepository(context);

                movie.setIsFavorite(0);
                int result = repository.setFavorite(movie);
                if (result > 0) {
                    Toast.makeText(context, context.getString(R.string.msg_remove_favorite_movie), Toast.LENGTH_SHORT).show();

                    // update widget
                    WidgetHelper.refreshWidget(context);

                }

                removeItem(position);
            }
        });

    }

    public class MovieViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.img_poster) ImageView imgPoster;
        @BindView(R.id.btn_remove) Button btnRemove;

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
