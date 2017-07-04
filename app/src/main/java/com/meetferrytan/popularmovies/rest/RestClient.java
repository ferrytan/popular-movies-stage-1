package com.meetferrytan.popularmovies.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.meetferrytan.popularmovies.BuildConfig;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ferrytan on 7/2/17.
 */

public class RestClient {
    public static final String TMDB_API_KEY = "41f18451846ac4e5955a73f5896cc1e9";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static RestClient instance;
    private Retrofit mRetrofit;

    public RestClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new ResponseInterceptor())
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient client = clientBuilder.build();

        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter()).create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * method to get single instance of RestClient
     *
     * @return RestClient singleton
     */
    public static RestClient getInstance() {
        if (instance == null) {
            synchronized (RestClient.class) {
                if (instance == null)
                    instance = new RestClient();
            }
        }
        return instance;
    }

    /**
     * Method to create retrofit interface
     * @param T
     */
    public<T> T createInterface(Class<T> T) {
        return mRetrofit.create(T);
    }

    private class CollectionAdapter implements JsonSerializer<Collection<?>> {
        @Override
        public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.isEmpty()) // exclusion is made here
                return null;

            JsonArray array = new JsonArray();

            for (Object child : src) {
                JsonElement element = context.serialize(child);
                array.add(element);
            }

            return array;
        }
    }
}