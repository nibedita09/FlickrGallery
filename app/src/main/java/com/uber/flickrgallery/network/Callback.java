package com.uber.flickrgallery.network;

public interface Callback<T> {

    void onResponse(Response<T> response);
    void onError(Throwable throwable);
}
