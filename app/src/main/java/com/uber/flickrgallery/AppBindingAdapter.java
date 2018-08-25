package com.uber.flickrgallery;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.uber.flickrgallery.common.imageloader.ImageLoader;

public class AppBindingAdapter {

    @BindingAdapter(value = {"imageUrl","imageKey"},requireAll = true)
    public static void loadImage(ImageView view, String imageUrl, String imageKey) {
        ImageLoader.loadBitmap(imageUrl, imageKey, view);
    }


}
