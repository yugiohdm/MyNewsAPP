package com.example.mynews.bean;

import java.io.Serializable;
import java.util.List;

public class ReleaseModel implements Serializable {
    int code;
    String message;
    Releasenews data;
    List<Releasenews> datas;


    public ReleaseModel(int code, String message) {
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

    public Releasenews getData() {
        return data;
    }

    public void setData(Releasenews data) {
        this.data = data;
    }

    public List<Releasenews> getDatas() {
        return datas;
    }

    public void setDatas(List<Releasenews> datas) {
        this.datas = datas;
    }
}
