package net.coding4ever.roedhi.moviecataloguefinalproject.ui.tvshow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.TvShow;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewAdapter;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowAdapter extends BaseViewAdapter<TvShow> {

    private Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<TvShow> createHolder(ViewGroup parent, int viewType) {
        return new TvShowViewHolder(R.layout.item_movie, parent);
    }

    @Override
    public void bindHolder(BaseViewHolder holder, int position) {

        final TvShow tvShow = getItem(position);

        holder.bindView(tvShow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TvShowAdapter.this.getItemClickListener() == null) return;

                TvShowAdapter.this.getItemClickListener().onItemClicked(tvShow);
            }
        });

    }

    public class TvShowViewHolder extends BaseViewHolder<TvShow> {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.img_poster) ImageView imgPoster;

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
