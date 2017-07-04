package com.meetferrytan.popularmovies.presentation.base;

/**
 * Created by ferrytan on 7/4/17.
 */

public interface BaseContract {

    public interface View{
        void showError(int errorCode, String message);
        void showLoading(boolean show);
    }

    public interface Presenter<V extends View>{
        V getView();
        void attachView(V mvpView);
        void detachView();
    }

    public interface Component<P extends Presenter>{
        P presenter();
    }
}
