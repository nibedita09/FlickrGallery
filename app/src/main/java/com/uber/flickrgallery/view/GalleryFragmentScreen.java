package com.uber.flickrgallery.view;

import com.uber.flickrgallery.model.Picture;

import java.util.List;

public interface GalleryFragmentScreen {

    void showLoadingSpinner();
    void dismissLoadingSpinner();
    void showErrorDialog();
    void showNetworkErrorDialog();
    void onReceivePictures(List<Picture> pictureList);
}
