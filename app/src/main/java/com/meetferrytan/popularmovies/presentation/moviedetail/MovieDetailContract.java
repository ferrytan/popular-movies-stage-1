package com.meetferrytan.popularmovies.presentation.moviedetail;

import com.meetferrytan.popularmovies.data.entity.Movie;
import com.meetferrytan.popularmovies.presentation.base.BaseContract;

public interface MovieDetailContract {

    interface View extends BaseContract.View {
        void displayMovieDetail(String title, String posterImage, String year, String monthDay, String rating, String synopsys);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void processMovieDetail(Movie movie);
    }
}