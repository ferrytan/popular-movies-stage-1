package com.meetferrytan.popularmovies.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.meetferrytan.popularmovies.util.AppConstants.ERROR_MESSAGE_DEFAULT;
import static com.meetferrytan.popularmovies.util.AppConstants.JSON_KEY_STATUS_CODE;
import static com.meetferrytan.popularmovies.util.AppConstants.JSON_KEY_STATUS_MESSAGE;

/**
 * Created by ferrytan on 7/2/17.
 */

public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int responseCode = response.code();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // should check JSON format of overall response & throw an exception if returns unexpected json format
            return response;
        } else {
            final ResponseBody body = response.body();
            JsonParser parser = new JsonParser();

            JsonObject jsonResponse = parser.parse(body.string()).getAsJsonObject();

            if(jsonResponse.has(JSON_KEY_STATUS_CODE) && jsonResponse.has(JSON_KEY_STATUS_MESSAGE)) {
                int statusCode = jsonResponse.get(JSON_KEY_STATUS_CODE).getAsInt();
                String statusMessage = jsonResponse.get(JSON_KEY_STATUS_MESSAGE).getAsString();
                // check if error response returns respective code/message
                throw new TmdbApiException(statusCode, statusMessage);
            }else{
                throw new TmdbApiException(responseCode, ERROR_MESSAGE_DEFAULT);
            }
        }
    }
}