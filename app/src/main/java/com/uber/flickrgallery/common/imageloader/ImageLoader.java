package com.uber.flickrgallery.common.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.uber.flickrgallery.network.Callback;
import com.uber.flickrgallery.network.NetworkManager;
import com.uber.flickrgallery.network.Response;
import com.uber.flickrgallery.network.ServiceRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImageLoader {

    private static final ImageCache imageCache;
    private static final int SUCCESS = 200;

    static {
        imageCache = new ImageCache();
    }

    private ImageLoader(){

    }

    public static void loadBitmap(String url, String imageKey, ImageView imageView) {
        final Bitmap bitmap = imageCache.getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            setImage(bitmap, imageView);
        } else {
            downloadImage(url, imageKey, imageView);
        }
    }

    private static void downloadImage(String url, final String imageKey, final ImageView imageView){
        final ServiceRequest serviceRequest = new ServiceRequest.ServiceRequestBuilder()
                .setUrl(url)
                .setResponseType(Bitmap.class)
                .setCallback(new Callback<Bitmap>() {
                    @Override
                    public void onResponse(final Response<Bitmap> response) {
                        final Bitmap bitmap = response.getBody();
                        imageCache.addBitmapToMemoryCache(imageKey, bitmap);
                        if(response.getCode() == SUCCESS){
                            setImage(bitmap, imageView);

                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }
                }).build();
        NetworkManager.makeRequest(serviceRequest);
    }

    private static void setImage(final Bitmap bitmap, final ImageView imageView) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(scaleBitmap(bitmap,imageView));
            }
        });
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, ImageView placeholder){
        final int scaledHeight = placeholder.getMeasuredHeight();
        final int scaledWidth = placeholder.getMeasuredWidth();
        return Bitmap.createScaledBitmap(bitmap, scaledWidth,scaledHeight, true);
    }

}
