package com.meetferrytan.popularmovies.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
/**
 * Created by ferrytan on 7/2/17.
 *
 * An Object that represents a movie.
 * created based on structure from themoviedb API
 * some fields are removed from the object due to the app's functionality
 *
 * json structure:
    {
        "vote_count": 3524,
        "id": 61791,
        "video": false,
        "vote_average": 7,
        "title": "Rise of the Planet of the Apes",
        "popularity": 12.959285,
        "poster_path": "/esqXMJv6PiK7GJVRwd2FA3SZUoW.jpg",
        "original_language": "en",
        "original_title": "Rise of the Planet of the Apes",
        "genre_ids": [
            53,
            28,
            18,
            878
        ],
        "backdrop_path": "/caIpcr9xhvRcWfOMRDq3F5iIcLB.jpg",
        "adult": false,
        "overview": "Scientist Will Rodman is determined to find a cure for Alzheimer's, the disease which has slowly consumed his father. Will feels certain he is close to a breakthrough and tests his latest serum on apes, noticing dramatic increases in intelligence and brain activity in the primate subjects – especially Caesar, his pet chimpanzee.",
        "release_date": "2011-08-03"
    }
 **/

public class Movie implements Parcelable{
    public static final String POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_PATH_IMG_QUALITY = "w185";
    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("poster_path")
    private String mPosterImagePath;

    @SerializedName("overview")
    private String mSynopsys;

    @SerializedName("vote_average")
    private double mRating;

    @SerializedName("release_date")
    private String mReleaseDate;

    public Movie() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterImagePath() {
        return mPosterImagePath;
    }


    public String getPosterImageFullUrl() {
        return POSTER_PATH_BASE_URL + POSTER_PATH_IMG_QUALITY + mPosterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        mPosterImagePath = posterImagePath;
    }

    public String getSynopsys() {
        return mSynopsys;
    }

    public void setSynopsys(String synopsys) {
        mSynopsys = synopsys;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPosterImagePath);
        dest.writeString(this.mSynopsys);
        dest.writeDouble(this.mRating);
        dest.writeString(this.mReleaseDate);
    }

    protected Movie(Parcel in) {
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mPosterImagePath = in.readString();
        this.mSynopsys = in.readString();
        this.mRating = in.readDouble();
        this.mReleaseDate = in.readString();
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
