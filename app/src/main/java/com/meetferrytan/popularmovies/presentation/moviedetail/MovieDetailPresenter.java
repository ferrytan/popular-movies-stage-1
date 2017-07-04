package com.meetferrytan.popularmovies.presentation.moviedetail;

import com.meetferrytan.popularmovies.PopularMoviesApp;
import com.meetferrytan.popularmovies.R;
import com.meetferrytan.popularmovies.data.entity.Movie;
import com.meetferrytan.popularmovies.presentation.base.BasePresenter;
import com.meetferrytan.popularmovies.util.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static com.meetferrytan.popularmovies.util.AppConstants.ERROR_MESSAGE_DEFAULT;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.View> implements MovieDetailContract.Presenter {

    @Inject
    public MovieDetailPresenter(Retrofit retrofit) {

    }

    @Override
    public void processMovieDetail(Movie movie) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.getDefault());
            Date releaseDate = dateFormat.parse(movie.getReleaseDate());

            SimpleDateFormat dateFormatYear = new SimpleDateFormat(AppConstants.DATE_FORMAT_YEAR, Locale.getDefault());
            SimpleDateFormat dateFormatMonthDay = new SimpleDateFormat(AppConstants.DATE_FORMAT_MONTH_DAY, Locale.getDefault());

            String movieTitle = movie.getTitle();
            String posterImageUrl = movie.getPosterImageFullUrl();
            String year = dateFormatYear.format(releaseDate);
            String monthDay = dateFormatMonthDay.format(releaseDate);
            String strRatingFormat = (PopularMoviesApp.getNetComponent().getApplicationContext()).getString(R.string.label_rating);
            String rating = String.format(strRatingFormat, movie.getRating());
            String synopsis = movie.getSynopsys();
            getView().displayMovieDetail(movieTitle, posterImageUrl, year, monthDay, rating, synopsis);
        }catch (ParseException pe) {
            pe.printStackTrace();
            getView().showError(-1, ERROR_MESSAGE_DEFAULT);
        }
    }
}