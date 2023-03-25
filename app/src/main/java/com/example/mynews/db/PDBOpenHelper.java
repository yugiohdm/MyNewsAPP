package com.example.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mynews.bean.NewsURL;

public class PDBOpenHelper extends SQLiteOpenHelper{
    public PDBOpenHelper(Context context) {
        super(context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table itype(id integer primary key not null,title varchar(10),url text,isShow varchar(10))";
        db.execSQL(sql);

        String insertSql = "insert into itype values(?,?,?,?)";
        db.execSQL(insertSql,new Object[]{0,"推荐",NewsURL.headline_url,"true"});
        db.execSQL(insertSql,new Object[]{1, "健康", NewsURL.health_url, "true"});
        db.execSQL(insertSql,new Object[]{2, "体育", NewsURL.sport_url, "true"});
        db.execSQL(insertSql,new Object[]{3, "汽车", NewsURL.automobile_url, "true"});
        db.execSQL(insertSql,new Object[]{4, "娱乐", NewsURL.entertainment_url, "true"});
        db.execSQL(insertSql,new Object[]{5, "科技", NewsURL.science_url, "true"});
        db.execSQL(insertSql,new Object[]{6, "财经", NewsURL.finance_url, "true"});
        db.execSQL(insertSql,new Object[]{7, "军事", NewsURL.military_url, "true"});
        db.execSQL(insertSql,new Object[]{8, "游戏", NewsURL.game_url, "true"});
        db.execSQL(insertSql,new Object[]{9, "国内", NewsURL.home_url, "true"});
        db.execSQL(insertSql,new Object[]{10, "国际", NewsURL.internation_url, "true"});
        db.execSQL(insertSql,new Object[]{11,"时尚",NewsURL.fashion_url,"true"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
