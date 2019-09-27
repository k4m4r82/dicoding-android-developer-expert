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
import net.coding4ever.roedhi.moviecataloguefinalproject.models.TvShow;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local.TvShowRepository;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewAdapter;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteTvShowAdapter extends BaseViewAdapter<TvShow> {

    private Context context;

    public FavoriteTvShowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<TvShow> createHolder(ViewGroup parent, int viewType) {
        return new FavoriteTvShowAdapter.TvShowViewHolder(R.layout.item_favorite_movie, parent);
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position) {

        final TvShow tvShow = getItem(position);

        holder.bindView(tvShow);

        ((FavoriteTvShowAdapter.TvShowViewHolder)holder).btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TvShowRepository repository = new TvShowRepository(context);

                tvShow.setIsFavorite(0);
                int result = repository.setFavorite(tvShow);
                if (result > 0)
                    Toast.makeText(context, context.getString(R.string.msg_remove_favorite_tv_show), Toast.LENGTH_SHORT).show();

                removeItem(position);
            }
        });

    }

    public class TvShowViewHolder extends BaseViewHolder<TvShow> {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.img_poster) ImageView imgPoster;
        @BindView(R.id.btn_remove) Button btnRemove;

        public TvShowViewHolder(@LayoutRes int resId, @NonNull ViewGroup parent) {
            super(resId, parent);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(TvShow obj) {

            txtTitle.setText(obj.getTitle());
            txtOverview.setText(obj.getOverview());

            Glide.with(context)
                    .load(obj.getPosterPath())
                    .into(imgPoster);
        }
    }

}
