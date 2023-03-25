package com.example.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UDBOpenHelper extends SQLiteOpenHelper {

    public UDBOpenHelper(Context context) {
        super(context, "aaa.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table Users(uid integer not null primary key unique,username varchar(1000),password varchar(1000),type varchar(1000),phone varchar(1000),email varchar(1000),sex varchar(100),location varchar(1000),occupation varchar(1000),birthday varchar(1000),concerns integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
