package com.uber.flickrgallery.presenter;

import com.uber.flickrgallery.interactor.FlickrServiceInteractor;
import com.uber.flickrgallery.model.Photo;
import com.uber.flickrgallery.model.Photos;
import com.uber.flickrgallery.model.Picture;
import com.uber.flickrgallery.model.SearchEntity;
import com.uber.flickrgallery.network.Callback;
import com.uber.flickrgallery.network.Response;
import com.uber.flickrgallery.view.GalleryFragmentScreen;

import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GalleryFragmentPresenterTest {

    private GalleryFragmentPresenter galleryFragmentPresenter;
    private FakeScreen fakeScreen;
    private FakeInteractor fakeInteractor;

    @Before
    public void setUp(){
        fakeScreen = new FakeScreen();
        fakeInteractor = new FakeInteractor();
        galleryFragmentPresenter = new GalleryFragmentPresenter(fakeInteractor, fakeScreen);
    }

    @Test
    public void test_getFlickrData_Success_WithNoSearchKey() throws Exception {
        fakeInteractor.setStatus(true);
        assertNull(fakeScreen.mPictureList);
        assertFalse(fakeScreen.isSpinnerShown);
        galleryFragmentPresenter.getFlickrData(null);
        assertTrue(fakeScreen.isSpinnerShown);
        assertNotNull(fakeScreen.mPictureList);
        assertTrue(fakeScreen.mPictureList.size() == 5);

    }

    @Test
    public void test_getFlickrData_Fail_WithNoSearchKey() throws Exception {

        fakeInteractor.setStatus(false);
        fakeScreen.reset();
        assertNull(fakeScreen.mPictureList);
        assertFalse(fakeScreen.isSpinnerShown);
        assertFalse(fakeScreen.isErrorDialogShown);
        galleryFragmentPresenter.getFlickrData(null);
        assertTrue(fakeScreen.isSpinnerShown);
        assertNull(fakeScreen.mPictureList);
        assertTrue(fakeScreen.isNetworkErrorDialogShown);
    }

    @Test
    public void test_getFlickrData_Success_WithSearchKey() throws Exception {
        fakeInteractor.setStatus(true);
        assertNull(fakeScreen.mPictureList);
        assertFalse(fakeScreen.isSpinnerShown);
        galleryFragmentPresenter.getFlickrData("1234_567");
        assertTrue(fakeScreen.isSpinnerShown);
        assertNotNull(fakeScreen.mPictureList);
        assertTrue(fakeScreen.mPictureList.size() == 5);

    }

    @Test
    public void test_getFlickrData_Fail_WithSearchKey() throws Exception {

        fakeInteractor.setStatus(false);
        fakeScreen.reset();
        assertNull(fakeScreen.mPictureList);
        assertFalse(fakeScreen.isSpinnerShown);
        assertFalse(fakeScreen.isErrorDialogShown);
        galleryFragmentPresenter.getFlickrData("1234_567");
        assertTrue(fakeScreen.isSpinnerShown);
        assertNull(fakeScreen.mPictureList);
        assertTrue(fakeScreen.isNetworkErrorDialogShown);
    }

    private class FakeInteractor implements FlickrServiceInteractor{

        private boolean mIsSuccess;

        public void setStatus(boolean isSuccess){
            this.mIsSuccess = isSuccess;
        }

        @Override
        public void fetchSearchedFlickrData(String key, Callback<SearchEntity> callback) {
            if(mIsSuccess) {
                Response response = new Response();
                response.setBody(createSearchEntity());
                response.setCode(200);
                callback.onResponse(response);
            } else{
                callback.onError(new UnknownHostException());
            }
        }
    }

    private class FakeScreen implements GalleryFragmentScreen{

        List<Picture> mPictureList;
        boolean isSpinnerShown = false;
        boolean isErrorDialogShown = false;
        boolean isNetworkErrorDialogShown =false;

        @Override
        public void showLoadingSpinner() {
            this.isSpinnerShown = true;
        }

        @Override
        public void dismissLoadingSpinner() {

        }

        @Override
        public void showErrorDialog() {
            this.isErrorDialogShown = true;
        }

        @Override
        public void showNetworkErrorDialog() {
            this.isNetworkErrorDialogShown = true;
        }

        @Override
        public void onReceivePictures(List<Picture> pictureList) {
            mPictureList = pictureList;
        }

        public void reset(){
            mPictureList = null;
            isErrorDialogShown = false;
            isSpinnerShown = false;
            isNetworkErrorDialogShown = false;
        }
    }

    private SearchEntity createSearchEntity(){
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setPhotos(createPhotos());
        return searchEntity;
    }

    private Photos createPhotos(){
        Photos photos = new Photos();
        List<Photo> list = new ArrayList<>();
        for (int i=0; i < 5; i++) {
            Photo photo = new Photo();
            photo.setId(String.valueOf(i));
            photo.setFarm(000);
            photo.setSecret("007"+i);
            photo.setServer("700"+i);
            list.add(photo);
        }
        photos.setPhoto(list);
        return photos;
    }


}