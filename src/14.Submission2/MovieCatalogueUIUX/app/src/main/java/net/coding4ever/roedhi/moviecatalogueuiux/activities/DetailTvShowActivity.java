package net.coding4ever.roedhi.moviecatalogueuiux.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import net.coding4ever.roedhi.moviecatalogueuiux.R;
import net.coding4ever.roedhi.moviecatalogueuiux.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecatalogueuiux.models.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_tv_show));

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.Bind(tvShow);
    }

    private class ViewHolder {
        ImageView imgPhoto;
        TextView tvName;
        TextView tvDesc;
        TextView tvCrew;

        ViewHolder() {
            imgPhoto = findViewById(R.id.img_photo);
            tvName = findViewById(R.id.txt_name);
            tvDesc = findViewById(R.id.txt_desc);
            tvCrew = findViewById(R.id.txt_crew);
        }

        void Bind(TvShow tvShow) {
            int photoId = tvShow.getPhoto();
            if (photoId > 0) imgPhoto.setImageResource(tvShow.getPhoto());

            tvName.setText(tvShow.getName());
            tvDesc.setText(tvShow.getOverview());

            StringBuilder crews = new StringBuilder();

            for (int i = 0; i < tvShow.getCrews().length; i++){
                crews.append((i + 1) + ". " + tvShow.getCrews()[i]).append("\n");
            }

            tvCrew.setText(crews.toString());
        }
    }
}
