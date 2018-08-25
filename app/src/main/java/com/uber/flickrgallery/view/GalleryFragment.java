package com.uber.flickrgallery.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uber.flickrgallery.R;
import com.uber.flickrgallery.common.ui.BaseFragment;
import com.uber.flickrgallery.common.ui.CommonUtil;
import com.uber.flickrgallery.common.ui.FragmentCallback;
import com.uber.flickrgallery.interactor.FlickrServiceInteractorImpl;
import com.uber.flickrgallery.model.Picture;
import com.uber.flickrgallery.presenter.GalleryFragmentPresenter;

import java.util.List;

public class GalleryFragment extends BaseFragment implements GalleryFragmentScreen {

    private FragmentCallback mFragmentCallback;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mGalleryAdapter;
    private GalleryFragmentPresenter mPresenter;
    private static final int numberOfColumns = 3;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GalleryFragmentPresenter(new FlickrServiceInteractorImpl(), this);
        mPresenter.getFlickrData(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mView == null) {
            mView = inflater.inflate(R.layout.fragment_gallery, null);
            mView.findViewById(R.id.searchView).setFocusable(false);
        }else {
            if(mGalleryAdapter != null) mGalleryAdapter.refresh(null);
            return mView;
        }

        mRecyclerView = mView.findViewById(R.id.listView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        mRecyclerView.addItemDecoration(CommonUtil.getItemDecoration(getResources().getDimensionPixelSize(R.dimen.photos_list_spacing), numberOfColumns));
        final SearchView searchView = mView.findViewById(R.id.searchView);
        searchView.setFocusableInTouchMode(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.getFlickrData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context !=null && context instanceof FragmentCallback)
            mFragmentCallback = (FragmentCallback) context;
    }

    @Override
    public void showLoadingSpinner() {
        mFragmentCallback.showSpinner(getString(R.string.loading));
    }

    @Override
    public void dismissLoadingSpinner() {
        mFragmentCallback.dismissSpinner();
    }

    @Override
    public void showErrorDialog() {
        mFragmentCallback.showErrorDialog(getString(R.string.error), "Technical Error!! Please try later");
    }

    @Override
    public void showNetworkErrorDialog() {
        mFragmentCallback.showErrorDialog(getString(R.string.error), "Network issue!! Please check your network and try later");
    }

    @Override
    public void onReceivePictures(List<Picture> pictureList) {
        if(mGalleryAdapter == null) {
            mGalleryAdapter = new GalleryAdapter(pictureList);
            mRecyclerView.setAdapter(mGalleryAdapter);
        }else {
            mGalleryAdapter.refresh(pictureList);
        }
    }
}
