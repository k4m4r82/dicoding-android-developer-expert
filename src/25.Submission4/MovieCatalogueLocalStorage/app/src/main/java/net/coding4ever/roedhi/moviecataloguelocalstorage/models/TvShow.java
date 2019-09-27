package net.coding4ever.roedhi.moviecataloguelocalstorage.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {

    private int id;
    private String languageId;
    private String title;
    private String overview;
    private String firstAirDate;
    private String posterPath;
    private String voteAverage;
    private int isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.languageId);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
        dest.writeInt(this.isFavorite);
    }

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.languageId = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.firstAirDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.isFavorite = in.readInt();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
