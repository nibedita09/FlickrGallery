package com.uber.flickrgallery.presenter;

import com.uber.flickrgallery.interactor.FlickrServiceInteractor;
import com.uber.flickrgallery.model.Photo;
import com.uber.flickrgallery.model.Picture;
import com.uber.flickrgallery.model.SearchEntity;
import com.uber.flickrgallery.network.Callback;
import com.uber.flickrgallery.network.Response;
import com.uber.flickrgallery.view.GalleryFragmentScreen;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class GalleryFragmentPresenter {

    private final static int SUCCESS = 200;

    private final GalleryFragmentScreen screen;
    private final FlickrServiceInteractor interactor;

    public GalleryFragmentPresenter(FlickrServiceInteractor flickrServiceInteractor, GalleryFragmentScreen galleryFragmentScreen){
        screen = galleryFragmentScreen;
        interactor = flickrServiceInteractor;
    }

    public void getFlickrData(String key){
        screen.showLoadingSpinner();
        this.interactor.fetchSearchedFlickrData(key, new Callback<SearchEntity>() {
            @Override
            public void onResponse(Response<SearchEntity> response) {
                screen.dismissLoadingSpinner();
                if(response.getCode() == SUCCESS) {
                    final SearchEntity searchEntity = response.getBody();
                    final List<Photo> photos = searchEntity.getPhotos().getPhoto();
                    final List<Picture> pictures = new ArrayList<>();
                    for (Photo photo : photos) {
                        final Picture picture = new Picture();
                        picture.setImageUrl(photo.getId(), photo.getSecret(),photo.getFarm(), photo.getServer());
                        pictures.add(picture);
                    }
                    screen.onReceivePictures(pictures);
                }else {
                    screen.showErrorDialog();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                screen.dismissLoadingSpinner();
                if(throwable instanceof UnknownHostException)
                    screen.showNetworkErrorDialog();
            }
        });
    }
}
