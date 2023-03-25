package com.example.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RDBOpenHelper extends SQLiteOpenHelper {
    public RDBOpenHelper(Context context) {
        super(context, "ccc.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table Releasenews(nid integer not null primary key unique,newstype varchar(1000),likes integer,uid integer,newstitle varchar(100),newimageurl varchar(1000),state varchar(100),newscontent varchar(1000),releasedate varchar(1000))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
