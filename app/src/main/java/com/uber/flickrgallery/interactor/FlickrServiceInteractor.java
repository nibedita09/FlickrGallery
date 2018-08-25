package com.uber.flickrgallery.interactor;

import com.uber.flickrgallery.model.SearchEntity;
import com.uber.flickrgallery.network.Callback;


public interface FlickrServiceInteractor {

    void fetchSearchedFlickrData(String key, final Callback<SearchEntity> callback);
}
