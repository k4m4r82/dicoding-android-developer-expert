package net.coding4ever.roedhi.moviecatalogueuiux.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int photo;
    private String name;
    private String shortOverview;
    private String overview;
    private String[] crews;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortOverview() {
        return shortOverview;
    }

    public void setShortOverview(String shortOverview) {
        this.shortOverview = shortOverview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String[] getCrews() {
        return crews;
    }

    public void setCrews(String[] crews) {
        this.crews = crews;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photo);
        dest.writeString(this.name);
        dest.writeString(this.shortOverview);
        dest.writeString(this.overview);
        dest.writeStringArray(this.crews);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.photo = in.readInt();
        this.name = in.readString();
        this.shortOverview = in.readString();
        this.overview = in.readString();
        this.crews = in.createStringArray();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
