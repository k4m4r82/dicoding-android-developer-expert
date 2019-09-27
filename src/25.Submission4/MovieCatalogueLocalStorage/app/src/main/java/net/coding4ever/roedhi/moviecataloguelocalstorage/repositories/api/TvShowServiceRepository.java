package net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.coding4ever.roedhi.moviecataloguelocalstorage.helpers.TheMovieDbRestClient;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.TvShow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowServiceRepository {

    private String apiKey;
    private String languageId;
    private String baseUrl;
    private String imageUrl;

    public TvShowServiceRepository(String baseUrl, String imageUrl, String apiKey, String languageId) {
        this.baseUrl = baseUrl;
        this.imageUrl = imageUrl;
        this.apiKey = apiKey;
        this.languageId = languageId;
    }

    public void getAll(final LoadDataCallback<TvShow> callback) {

        RequestParams params = new RequestParams();
        params.put("api_key", apiKey);
        params.put("language", languageId);

        String url = String.format("%s%s", baseUrl, "tv");

        TheMovieDbRestClient.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                callback.onError(e);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    ArrayList<TvShow> list = new ArrayList<>();

                    JSONArray arrayJson = response.getJSONArray("results");

                    for (int i = 0; i < arrayJson.length(); i++) {

                        JSONObject obj = arrayJson.getJSONObject(i);

                        String posterImageUrl = String.format("%s%s",
                                imageUrl, obj.getString("poster_path"));

                        TvShow tvShow = new TvShow();
                        tvShow.setId(Integer.valueOf(obj.getString("id")));
                        tvShow.setLanguageId(languageId);
                        tvShow.setTitle(obj.getString("name"));
                        tvShow.setOverview(obj.getString("overview"));
                        tvShow.setPosterPath(posterImageUrl);
                        tvShow.setFirstAirDate(obj.getString("first_air_date"));
                        tvShow.setVoteAverage(obj.getString("vote_average"));

                        list.add(tvShow);
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
