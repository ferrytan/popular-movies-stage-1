package com.meetferrytan.popularmovies;

import android.app.Application;

import com.meetferrytan.popularmovies.data.component.DaggerNetComponent;
import com.meetferrytan.popularmovies.data.component.NetComponent;
import com.meetferrytan.popularmovies.data.module.AppModule;
import com.meetferrytan.popularmovies.data.module.NetModule;

/**
 * Created by ferrytan on 7/4/17.
 */

public class PopularMoviesApp extends Application {
    private static NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    public static NetComponent getNetComponent() {
        return mNetComponent;
    }
}