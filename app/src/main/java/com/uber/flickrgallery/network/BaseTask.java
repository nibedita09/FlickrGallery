package com.uber.flickrgallery.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class BaseTask<T> implements Runnable {

    private Callback mCallback;
    private final String mUrl;
    private Type mResponseType;

    public BaseTask(String url, Type responseType, Callback callback) {
        mCallback = callback;
        mUrl = url;
        mResponseType = responseType;
    }

    @Override
    public void run() {
        get(mUrl);
    }

    private void get(String url){
        HttpsURLConnection urlConnection = null;
        try {
            URL endPoint = new URL(url);
            urlConnection = getConnection(endPoint);
            InputStream in = (InputStream) urlConnection.getContent();
            if(mCallback != null) {
                final Response response = new Response();
                if(mResponseType ==  null){
                    response.setBody(getStringFromInputStream(in));
                } else if(mResponseType.equals(Bitmap.class)){
                    response.setBody(BitmapFactory.decodeStream(in));
                } else {
                    final String result = getStringFromInputStream(in);
                    final T t = (new Gson()).fromJson(result, mResponseType);
                    response.setBody(t);
                }
                response.setCode(urlConnection.getResponseCode());
                mCallback.onResponse(response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if(mCallback != null)
                mCallback.onError(ex);
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private HttpsURLConnection getConnection(URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(3000);
        return connection;
    }

    private String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
