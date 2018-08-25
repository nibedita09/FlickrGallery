package com.uber.flickrgallery.network;

import java.lang.reflect.Type;

public class ServiceRequest<T> {
    private String url;
    private Type responseType;
    private Callback<T> callback;


    public String getUrl() {
        return url;
    }

    public Type getResponseType() {
        return responseType;

    }

    public Callback<T> getCallback() {
        return callback;
    }


    private ServiceRequest(ServiceRequestBuilder serviceRequestBuilder) {

        this.url= serviceRequestBuilder.url;
        this.responseType = serviceRequestBuilder.responseType;
        this.callback= serviceRequestBuilder.callback;
    }

    public static class ServiceRequestBuilder<T> {
        private String url;
        private Class<T> responseType;
        private Callback<T> callback;

        public ServiceRequestBuilder setUrl(String url){
            this.url = url;
            return this;
        }

        public ServiceRequestBuilder setResponseType(Class<T> responseType){
            this.responseType = responseType;
            return this;
        }

        public ServiceRequestBuilder setCallback(Callback<T> callback){
            this.callback = callback;
            return this;
        }

        public ServiceRequest build(){
            return new ServiceRequest(this);
        }

    }
}


