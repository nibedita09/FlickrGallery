package com.uber.flickrgallery.common.ui;

public interface FragmentCallback {

    void showSpinner(String message);
    void dismissSpinner();
    void showErrorDialog(String title, String message);

}
