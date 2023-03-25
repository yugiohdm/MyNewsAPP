package com.example.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LDBOpenHelper extends SQLiteOpenHelper {
    public LDBOpenHelper(Context context) {
        super(context, "ddd.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table likenews(luid integer not null,newstype varchar(1000),likes integer,lusername varchar(1000),nid integer  not null,newstitle varchar(100),newimageurl varchar(1000),state varchar(100),newscontent varchar(1000),releasedate varchar(100),likestate varchar(100),primary key (luid,nid))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
