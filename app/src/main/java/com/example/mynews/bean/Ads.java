package com.example.mynews.bean;


import java.io.Serializable;

public class Ads implements Serializable {
    public String uniquekey;

    public String date;

    public String title;
    public String imgsrc;
    public String imgsrc1;
    public String imgsrc2;
    public String subname;
    public String url;

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgsrc1() {
        return imgsrc1;
    }

    public void setImgsrc1(String imgsrc1) {
        this.imgsrc1 = imgsrc1;
    }

    public String getImgsrc2() {
        return imgsrc2;
    }

    public void setImgsrc2(String imgsrc2) {
        this.imgsrc2 = imgsrc2;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "uniquekey='" + uniquekey + '\'' +
                ", title='" + title + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", imgsrc1='" + imgsrc1 + '\'' +
                ", imgsrc2='" + imgsrc2 + '\'' +
                ", subname='" + subname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
