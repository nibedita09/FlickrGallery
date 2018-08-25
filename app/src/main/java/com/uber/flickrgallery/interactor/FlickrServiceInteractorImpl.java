package com.uber.flickrgallery.interactor;

import com.uber.flickrgallery.model.SearchEntity;
import com.uber.flickrgallery.network.Callback;
import com.uber.flickrgallery.network.NetworkManager;
import com.uber.flickrgallery.network.ServiceRequest;


public class FlickrServiceInteractorImpl implements FlickrServiceInteractor {

    private static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    private static final String DEFAULT_KEY = "kittens";
    private static final String FLICKR_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+API_KEY+"&\n" +
            "format=json&nojsoncallback=1&safe_search=1&text=";


    @Override
    public void fetchSearchedFlickrData(String key, final Callback<SearchEntity> callback){
        String url = null;
        if(key == null)
            url = FLICKR_URL.concat(DEFAULT_KEY);
        else
            url = FLICKR_URL.concat(key);

        final ServiceRequest serviceRequest = new ServiceRequest.ServiceRequestBuilder<SearchEntity>()
                .setResponseType(SearchEntity.class)
                .setUrl(url)
                .setCallback(callback).build();
        NetworkManager.makeRequest(serviceRequest);
    }
}
