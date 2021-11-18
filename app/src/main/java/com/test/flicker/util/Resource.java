package com.test.flicker.util;

import static com.test.flicker.util.Resource.Status.ERROR;
import static com.test.flicker.util.Resource.Status.LOADING;
import static com.test.flicker.util.Resource.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable public final Throwable error;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Throwable error) {
        return new Resource<>(ERROR, null, error);
    }

    public static <T> Resource<T> loading(@NonNull T data) {
        return new Resource<>(LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING }
}