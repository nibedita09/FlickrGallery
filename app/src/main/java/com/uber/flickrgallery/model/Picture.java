package com.uber.flickrgallery.model;


import java.util.Observable;

public class Picture extends Observable{

    public String imageUrl;
    public String imageKey;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String id, String secret, int farm, String server) {
        setImageKey(id+"_"+secret);
        final String imageUrl = "https://farm"+farm+".static.flickr.com/"+server+"/"+ imageKey +".jpg";
        this.imageUrl = imageUrl;
        notifyObservers();
    }

    public String getImageKey(){
        return imageKey;
    }

    public void setImageKey(String imageKey){
        this.imageKey = imageKey;
        notifyObservers();
    }
}
