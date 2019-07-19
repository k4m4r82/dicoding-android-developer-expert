package net.coding4ever.roedhi.moviecatalogueapi.presenters;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.coding4ever.roedhi.moviecatalogueapi.BuildConfig;
import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.helpers.TheMovieDbRestClient;
import net.coding4ever.roedhi.moviecatalogueapi.models.Movie;
import net.coding4ever.roedhi.moviecatalogueapi.views.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieFragmentPresenter {

    private static final String TAG = MovieFragmentPresenter.class.getSimpleName();

    private Context context;
    private View<Movie> view;

    public MovieFragmentPresenter(View<Movie> view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loadMovies(){

        String apiKey = BuildConfig.TheMovieDBApiKey;
        String languageId = context.getResources().getString(R.string.language_id);

        RequestParams params = new RequestParams();
        params.put("api_key", apiKey);
        params.put("language", languageId);

        String baseUrl = context.getResources().getString(R.string.the_moviedb_base_api);
        String url = String.format("%s%s", baseUrl, "movie");

        TheMovieDbRestClient.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                view.onLoadDataFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    ArrayList<Movie> listMovie = new ArrayList<>();

                    JSONArray arrayJson = response.getJSONArray("results");

                    for (int i = 0; i < arrayJson.length(); i++) {

                        JSONObject obj = arrayJson.getJSONObject(i);

                        String posterImageUrl = String.format("%s%s",
                                context.getResources().getString(R.string.poster_image_url), obj.getString("poster_path"));

                        Movie movie = new Movie();
                        movie.setTitle(obj.getString("title"));
                        movie.setOverview(obj.getString("overview"));
                        movie.setPosterPath(posterImageUrl);
                        movie.setReleaseDate(obj.getString("release_date"));
                        movie.setVoteAverage(obj.getString("vote_average"));

                        listMovie.add(movie);
                    }

                    view.onLoadDataSucceed(listMovie);

                } catch (Exception e) {
                    view.onLoadDataFailure();
                    e.printStackTrace();
                }

            }
        });
    }
}
