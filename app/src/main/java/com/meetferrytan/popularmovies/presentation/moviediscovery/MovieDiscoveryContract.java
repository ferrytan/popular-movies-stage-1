package com.meetferrytan.popularmovies.presentation.moviediscovery;

import com.meetferrytan.popularmovies.data.entity.Movie;
import com.meetferrytan.popularmovies.presentation.base.BaseContract;

import java.util.List;

public interface MovieDiscoveryContract {

    interface View extends BaseContract.View {
        void showResult(List<Movie> movies, boolean hasMoreData);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void loadPopularMovies();
        void loadTopRatedMovies();
    }
}