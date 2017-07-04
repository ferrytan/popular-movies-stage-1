package com.meetferrytan.popularmovies.presentation.moviediscovery;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.meetferrytan.popularmovies.PopularMoviesApp;
import com.meetferrytan.popularmovies.R;
import com.meetferrytan.popularmovies.data.entity.Movie;
import com.meetferrytan.popularmovies.presentation.base.BaseActivity;
import com.meetferrytan.popularmovies.presentation.moviedetail.MovieDetailActivity;
import com.meetferrytan.popularmovies.util.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieDiscoveryActivity extends BaseActivity<MovieDiscoveryPresenter, MovieDiscoveryComponent>
        implements MovieDiscoveryContract.View {

    public static final int SORT_RATING = 0;
    public static final int SORT_POPULARITY = 1;
    public static final int SPAN_COUNT = 2;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loader)
    ProgressBar mProgressBar;
    @BindView(R.id.txv_error)
    TextView mTxvError;

    private int sortState;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void createComponent() {
        mComponent = DaggerMovieDiscoveryComponent.builder()
                .netComponent(PopularMoviesApp.getNetComponent())
                .build();
    }

    @Override
    public int createLayout() {
        return R.layout.activity_movie_discovery;
    }

    @Override
    public void startingUpActivity(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_movie_discovery);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                MovieAdapter movieAdapter = (MovieAdapter) recyclerview.getAdapter();
                if (movieAdapter != null && movieAdapter.getItemViewType(position) == MovieAdapter.VIEWTYPE_DATA) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        loadData();
    }

    /**
     * show error view
     */
    @Override
    public void showError(int errorCode, String message) {
        Log.d(getClass().getSimpleName(), "showError() called with: errorCode = [" + errorCode + "], message = [" + message + "]");
        if(mMovieAdapter == null){
            mTxvError.setVisibility(View.VISIBLE);
        }else{
            mTxvError.setVisibility(View.GONE);
        }
    }

    /**
     * show loading view
     */
    @Override
    public void showLoading(boolean show) {
        mTxvError.setVisibility(View.GONE);
        if(show){
            mProgressBar.setVisibility(View.VISIBLE);
        }else{
            mProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * show result to UI
     *
     * @param movies      list of result
     * @param hasMoreData indicate whether list is allowed to load more data
     */
    @Override
    public void showResult(List<Movie> movies, boolean hasMoreData) {
        if (mMovieAdapter == null) {
            mMovieAdapter = new MovieAdapter(this, movies, new MovieAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, Movie movie) {
                    Intent intent = new Intent(MovieDiscoveryActivity.this, MovieDetailActivity.class);
                    intent.putExtra(AppConstants.BUNDLE_MOVIE, movie);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.
                                makeSceneTransitionAnimation(MovieDiscoveryActivity.this, view, getString(R.string.activity_image_trans));
                        startActivity(intent, options.toBundle());
                    }
                    else {
                        startActivity(intent);
                    }
                }

                @Override
                public void onLoadMoreDataClick() {
                    loadData();
                }
            });
            recyclerview.setAdapter(mMovieAdapter);
        } else {
            mMovieAdapter.addItems(movies);
        }
        mMovieAdapter.setLoadMoreEnabled(hasMoreData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem item = menu.findItem(R.id.sort_by);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {
                if (sortState != selectedPosition) {
                    sortState = selectedPosition;
                    mMovieAdapter = null;
                    recyclerview.setAdapter(null);
                    mPresenter.reset();
                    loadData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setSelection(sortState);

        Context wrappedContext = new android.view.ContextThemeWrapper(this, R.style.ToolbarSpinnerTheme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(wrappedContext,
                R.array.menu_sort_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.sort_by:
                // do nothing, already handled in Spinner onItemSelectedListener
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO save list data, position, presenter's paging parameter
    }

    @OnClick(R.id.txv_error)
    public void retryLoadingData(){
        loadData();
    }

    public void loadData() {
        switch (sortState) {
            case SORT_RATING:
                mPresenter.loadTopRatedMovies();
                break;
            case SORT_POPULARITY:
                mPresenter.loadPopularMovies();
                break;
        }
    }
}

