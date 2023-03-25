package com.example.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HDBOpenHelper extends SQLiteOpenHelper {
    public HDBOpenHelper(Context context) {
        super(context, "news1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table historypublicnews(uniquekey varchar(1000) primary key not null unique,title varchar(1000),url varchar(1000),date varchar(1000),category varchar(1000),author_name varchar(1000),thumbnail_pic_s varchar(1000),thumbnail_pic_s02 varchar(1000),thumbnail_pic_s03 varchar(1000),is_content varchar(1000),uid integer)";
        sqLiteDatabase.execSQL(sql);

        String sql1 = "create table  historynews(uid integer,username varchar(1000),nid integer primary key not null unique,newstitle varchar(100),newimageurl varchar(1000),state varchar(100),newscontent varchar(1000),releasedate varchar(100),likes integer)";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
