package com.meetferrytan.popularmovies.rest;

import java.io.IOException;

/**
 * Created by ferrytan on 7/2/17.
 */

public class TmdbApiException extends IOException {
    private int mErrorCode;
    private String mErrorMessage;

    public TmdbApiException(int errorCode, String errorMessage) {
        mErrorCode = errorCode;
        mErrorMessage = errorMessage;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
