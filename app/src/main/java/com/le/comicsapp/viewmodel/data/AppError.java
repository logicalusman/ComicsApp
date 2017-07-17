package com.le.comicsapp.viewmodel.data;

public class AppError extends Throwable {


    public static final int ERROR_INTERNET_CONNECTION = 1;
    public static final int ERROR_UNKNOWN = 2;
    int error;

    public AppError(int err) {
        error = err;
    }

    public AppError(Throwable t, int err) {
        super(t);
        error = err;
    }

    public int getError() {
        return error;
    }

}
