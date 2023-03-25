package com.example.mynews.bean;

import java.io.Serializable;
import java.util.List;

public class NewsModel implements Serializable {
    int code;
    String message;
    News2 data;
    List<News2> datas;


    public NewsModel(int code, String message) {
        this.code = code;
        this.message = message;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public News2 getData() {
        return data;
    }

    public void setData(News2 data) {
        this.data = data;
    }

    public List<News2> getDatas() {
        return datas;
    }

    public void setDatas(List<News2> datas) {
        this.datas = datas;
    }
}
