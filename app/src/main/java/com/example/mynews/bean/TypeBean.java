package com.example.mynews.bean;

import androidx.fragment.app.Fragment;

import com.example.mynews.fragment.top.Fragment_automobile;
import com.example.mynews.fragment.top.Fragment_fashion;
import com.example.mynews.fragment.top.Fragment_finance;
import com.example.mynews.fragment.top.Fragment_game;
import com.example.mynews.fragment.top.Fragment_health;
import com.example.mynews.fragment.top.Fragment_inland;
import com.example.mynews.fragment.top.Fragment_international;
import com.example.mynews.fragment.top.Fragment_military;
import com.example.mynews.fragment.top.Fragment_recommend;
import com.example.mynews.fragment.top.Fragment_recreation;
import com.example.mynews.fragment.top.Fragment_sports;
import com.example.mynews.fragment.top.Fragment_technology;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
* 绑定接口名称和类型的类
* */
public class TypeBean implements Serializable{
    private int id;
    private String title;
    private String url;
    private boolean isShow;




    public TypeBean(int id, String title, String url, boolean isShow) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.isShow = isShow;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) { this.isShow = show; }

    public static List<TypeBean> getTypeList(){
        List<TypeBean>mDatas = new ArrayList<>();
        TypeBean tb1 = new TypeBean(0, "推荐", NewsURL.headline_url, true);
        TypeBean tb2 = new TypeBean(1, "健康", NewsURL.health_url, true);
        TypeBean tb3 = new TypeBean(2, "体育", NewsURL.sport_url, true);
        TypeBean tb4 = new TypeBean(3, "汽车", NewsURL.automobile_url, true);
        TypeBean tb5 = new TypeBean(4, "娱乐", NewsURL.entertainment_url, true);
        TypeBean tb6 = new TypeBean(5, "科技", NewsURL.science_url, true);
        TypeBean tb7 = new TypeBean(6, "财经", NewsURL.finance_url, true);
        TypeBean tb8 = new TypeBean(7, "军事", NewsURL.military_url, true);
        TypeBean tb9= new TypeBean(8, "游戏", NewsURL.game_url, true);
        TypeBean tb10 = new TypeBean(9, "国内", NewsURL.home_url, true);
        TypeBean tb11= new TypeBean(10, "国际", NewsURL.internation_url, true);
        TypeBean tb12=new TypeBean(11,"时尚",NewsURL.fashion_url,true);
        mDatas.add(tb1);
        mDatas.add(tb2);
        mDatas.add(tb3);
        mDatas.add(tb4);
        mDatas.add(tb5);
        mDatas.add(tb6);
        mDatas.add(tb7);
        mDatas.add(tb8);
        mDatas.add(tb9);
        mDatas.add(tb10);
        mDatas.add(tb11);
        mDatas.add(tb12);
        return mDatas;
    }


    public static List<TypeBean> getToolTypeList(){
        List<TypeBean>mDatas = new ArrayList<>();
        TypeBean tb1 = new TypeBean(0, "收藏", null, true);
        TypeBean tb2 = new TypeBean(1, "点赞", null, true);
        TypeBean tb3 = new TypeBean(2, "历史", null, true);
        mDatas.add(tb1);
        mDatas.add(tb2);
        mDatas.add(tb3);
        return mDatas;
    }


    public static List<TypeBean> getManagerTypeList(){
        List<TypeBean>mDatas = new ArrayList<>();
        TypeBean tb1 = new TypeBean(0, "新闻管理", null, true);
        TypeBean tb2 = new TypeBean(1, "用户管理", null, true);
        mDatas.add(tb1);
        mDatas.add(tb2);
        return mDatas;
    }
}
