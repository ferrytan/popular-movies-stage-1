package com.meetferrytan.popularmovies.presentation.moviediscovery.repository;

import com.meetferrytan.popularmovies.rest.ResponseCollection;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ferrytan on 7/2/17.
 */

public interface MovieDiscoveryAPI {

    @GET("movie/popular")
    Observable<ResponseCollection> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ResponseCollection> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);
}
