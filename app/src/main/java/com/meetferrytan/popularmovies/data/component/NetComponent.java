package com.meetferrytan.popularmovies.data.component;

import android.content.Context;

import com.meetferrytan.popularmovies.data.module.AppModule;
import com.meetferrytan.popularmovies.data.module.NetModule;
import com.meetferrytan.popularmovies.util.ApplicationScope;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ferrytan on 7/4/17.
 */

@ApplicationScope
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
    Context getApplicationContext();
}