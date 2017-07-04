package com.meetferrytan.popularmovies.data.module;

import android.app.Application;
import android.content.Context;

import com.meetferrytan.popularmovies.util.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ferrytan on 7/4/17.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationScope
    Context provideContext() {
        return mApplication;
    }
}