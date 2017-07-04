package com.meetferrytan.popularmovies.presentation.moviediscovery;

import com.meetferrytan.popularmovies.presentation.base.BasePresenter;
import com.meetferrytan.popularmovies.presentation.moviediscovery.repository.MovieDiscoveryAPI;
import com.meetferrytan.popularmovies.rest.ResponseCollection;
import com.meetferrytan.popularmovies.util.AppConstants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MovieDiscoveryPresenter extends BasePresenter<MovieDiscoveryContract.View> implements MovieDiscoveryContract.Presenter {
    private MovieDiscoveryAPI mAPI;
    private int mPage;

    @Inject
    public MovieDiscoveryPresenter(Retrofit retrofit) {
        mAPI = retrofit.create(MovieDiscoveryAPI.class);
        reset();
    }

    @Override
    public void loadPopularMovies() {
        getView().showLoading(true);
        mPage++;

        Disposable disposable = mAPI.getPopularMovies(AppConstants.TMDB_API_KEY, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseCollection>() {
                    @Override
                    public void accept(@NonNull ResponseCollection responseCollection) throws Exception {
                        boolean hasMoreData = mPage<responseCollection.getTotalPages();
                        getView().showLoading(false);
                        getView().showResult(responseCollection.getResults(), hasMoreData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getView().showLoading(false);
                        processError(throwable);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void loadTopRatedMovies() {
        getView().showLoading(true);
        mPage++;
        Disposable disposable = mAPI.getTopRatedMovies(AppConstants.TMDB_API_KEY, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseCollection>() {
                    @Override
                    public void accept(@NonNull ResponseCollection responseCollection) throws Exception {
                        boolean hasMoreData = mPage<responseCollection.getTotalPages();
                        getView().showLoading(false);
                        getView().showResult(responseCollection.getResults(), hasMoreData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getView().showLoading(false);
                        processError(throwable);
                    }
                });
        addDisposable(disposable);
    }

    public void reset(){
        mPage = 0;
    }
}