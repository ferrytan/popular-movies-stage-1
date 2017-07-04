package com.meetferrytan.popularmovies.presentation.base;

import com.meetferrytan.popularmovies.rest.TmdbApiException;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.meetferrytan.popularmovies.util.AppConstants.ERROR_MESSAGE_DEFAULT;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private WeakReference<V> viewRef;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    public void attachView(V mvpView) {
        viewRef = new WeakReference<>(mvpView);
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    protected void addDisposable(Disposable disposable) {
        this.mCompositeDisposable.add(disposable);
    }

    public void processError(Throwable throwable){
        throwable.printStackTrace();
        if (throwable instanceof TmdbApiException) {
            TmdbApiException tmdbApiException = ((TmdbApiException)throwable);
            int errorCode = tmdbApiException.getErrorCode();
            String message = tmdbApiException.getErrorMessage();
            getView().showError(errorCode, message);
        }else{
            getView().showError(-1, ERROR_MESSAGE_DEFAULT);
        }
    }
}