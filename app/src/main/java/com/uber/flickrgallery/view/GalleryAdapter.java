package com.uber.flickrgallery.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.uber.flickrgallery.R;
import com.uber.flickrgallery.databinding.ListItemBinding;
import com.uber.flickrgallery.model.Picture;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<Picture> mData;

    public GalleryAdapter(List<Picture> pictureList){
        mData = pictureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ListItemBinding listItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item,
                parent, false);
        return new ViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Picture picture = mData.get(position);
        holder.mBinding.setPicture(picture);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ListItemBinding mBinding;
        public ViewHolder(ListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            mBinding = listItemBinding;
        }
    }

    public void refresh(List<Picture> photoList){
        if(photoList != null) {
            this.mData.clear();
            this.mData.addAll(photoList);
        }
        notifyDataSetChanged();
    }
}
