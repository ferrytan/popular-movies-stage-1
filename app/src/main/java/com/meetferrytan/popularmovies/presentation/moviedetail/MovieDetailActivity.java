package com.meetferrytan.popularmovies.presentation.moviedetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.meetferrytan.popularmovies.PopularMoviesApp;
import com.meetferrytan.popularmovies.R;
import com.meetferrytan.popularmovies.data.entity.Movie;
import com.meetferrytan.popularmovies.presentation.base.BaseActivity;
import com.meetferrytan.popularmovies.util.AppConstants;

import butterknife.BindView;

public class MovieDetailActivity extends  BaseActivity<MovieDetailPresenter, MovieDetailComponent>
        implements MovieDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txv_title)
    TextView txvTitle;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.txv_year)
    TextView txvYear;
    @BindView(R.id.txv_month_day)
    TextView txvMonthDay;
    @BindView(R.id.txv_rating)
    TextView txvRating;
    @BindView(R.id.txv_synopsis)
    TextView txvSynopsis;

    @Override
    protected void createComponent() {
        mComponent = DaggerMovieDetailComponent.builder()
                .netComponent(PopularMoviesApp.getNetComponent())
                .build();
    }

    @Override
    public int createLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void startingUpActivity(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_movie_detail);
        Movie movie = getIntent().getParcelableExtra(AppConstants.BUNDLE_MOVIE);

        mPresenter.processMovieDetail(movie);
    }

    @Override
    public void showError(int errorCode, String message) {
        // nothing to do
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean show) {
        // nothing to do
    }

    @Override
    public void displayMovieDetail(String title, String posterImage, String year, String monthDay, String rating, String synopsys) {
        txvTitle.setText(title);

        Glide.with(this)
                .load(posterImage)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);

        txvYear.setText(year);
        txvMonthDay.setText(monthDay);
        txvRating.setText(rating);
        txvSynopsis.setText(synopsys);
    }
}
