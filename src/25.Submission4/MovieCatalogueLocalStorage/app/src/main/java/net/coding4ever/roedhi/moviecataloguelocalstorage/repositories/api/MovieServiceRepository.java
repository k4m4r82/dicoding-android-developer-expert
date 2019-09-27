package net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.coding4ever.roedhi.moviecataloguelocalstorage.helpers.TheMovieDbRestClient;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieServiceRepository {

    private String apiKey;
    private String languageId;
    private String baseUrl;
    private String imageUrl;

    public MovieServiceRepository(String baseUrl, String imageUrl, String apiKey, String languageId) {
        this.baseUrl = baseUrl;
        this.imageUrl = imageUrl;
        this.apiKey = apiKey;
        this.languageId = languageId;
    }

    public void getAll(final LoadDataCallback<Movie> callback) {

        RequestParams params = new RequestParams();
        params.put("api_key", apiKey);
        params.put("language", languageId);

        String url = String.format("%s%s", baseUrl, "movie");

        TheMovieDbRestClient.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                callback.onError(e);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    ArrayList<Movie> list = new ArrayList<>();

                    JSONArray arrayJson = response.getJSONArray("results");

                    for (int i = 0; i < arrayJson.length(); i++) {

                        JSONObject obj = arrayJson.getJSONObject(i);

                        String posterImageUrl = String.format("%s%s",
                                imageUrl, obj.getString("poster_path"));

                        Movie movie = new Movie();
                        movie.setId(Integer.valueOf(obj.getString("id")));
                        movie.setLanguageId(languageId);
                        movie.setTitle(obj.getString("title"));
                        movie.setOverview(obj.getString("overview"));
                        movie.setPosterPath(posterImageUrl);
                        movie.setReleaseDate(obj.getString("release_date"));
                        movie.setVoteAverage(obj.getString("vote_average"));

                        list.add(movie);
                    }

                    callback.onDataLoaded(list);

                } catch (Exception e) {
                    callback.onError(e);
                    e.printStackTrace();
                }

            }
        });

    }

}
