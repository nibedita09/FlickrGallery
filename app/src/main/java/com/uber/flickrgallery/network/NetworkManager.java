package com.uber.flickrgallery.network;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetworkManager{

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();

    private static NetworkManager sInstance = null;
    private ThreadPoolExecutor mBackGroundExecutor;
    private MainThreadExecutor mMainThreadExecutor;

    static {
       sInstance = new NetworkManager();
    }
    private NetworkManager(){
        mBackGroundExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES,
                                                    NUMBER_OF_CORES,
                                                    KEEP_ALIVE_TIME,
                                                    KEEP_ALIVE_TIME_UNIT,
                                                    mDecodeWorkQueue);

        mMainThreadExecutor = new MainThreadExecutor();
    }

    public  static <T> void makeRequest(final ServiceRequest serviceRequest){
        sInstance.mBackGroundExecutor.execute(new BaseTask(serviceRequest.getUrl(), serviceRequest.getResponseType(), new Callback<T>() {
            @Override
            public void onResponse(final Response<T> response) {
                sInstance.mMainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Callback<T> callback = serviceRequest.getCallback();
                        if(callback != null) {
                            callback.onResponse(response);
                        }
                    }
                });
            }

            @Override
            public void onError(final Throwable throwable) {
                sInstance.mMainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Callback<T> callback = serviceRequest.getCallback();
                        if(callback != null) {
                            callback.onError(throwable);
                        }
                    }
                });
            }
        }));
    }

    private static class MainThreadExecutor implements Executor{

        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
