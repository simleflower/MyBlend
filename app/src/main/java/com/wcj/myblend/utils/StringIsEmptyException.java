package com.wcj.myblend.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;



public class StringIsEmptyException extends Exception {

    public StringIsEmptyException() {
    }

    public StringIsEmptyException(String message) {
        super(message);
    }

    public StringIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringIsEmptyException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public StringIsEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
