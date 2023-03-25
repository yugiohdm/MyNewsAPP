package com.example.mynews.bean;

import java.io.Serializable;

public class ImageModel implements Serializable {
    private String imagetitle;
    private  String imageurl;

    public String getImagetitle() {
        return imagetitle;
    }

    public void setImagetitle(String imagetitle) {
        this.imagetitle = imagetitle;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "imagetitle='" + imagetitle + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
