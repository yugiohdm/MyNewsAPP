package com.example.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynews.bean.News1;

import java.util.ArrayList;
import java.util.List;

public class HistoryDBManager {
    public static SQLiteDatabase database;

    public static void initHistoryDB(Context context){
        HDBOpenHelper helper = new HDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initHistoryDB1(Context context){
        HDBOpenHelper helper = new HDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }


    public static void SaveHistoryList(News1 news1){
        ContentValues contentValues=new ContentValues();
        contentValues.put("uniquekey",news1.getUniquekey());
        contentValues.put("title",news1.getTitle());
        contentValues.put("url",news1.getUrl());
        contentValues.put("date",news1.getDate());
        contentValues.put("author_name",news1.getAuthor_name());
        contentValues.put("thumbnail_pic_s",news1.getThumbnail_pic_s());
        contentValues.put("thumbnail_pic_s02",news1.getThumbnail_pic_s02());
        contentValues.put("thumbnail_pic_s03",news1.getThumbnail_pic_s03());
        contentValues.put("uid",news1.getUid());
        database.insert("historypublicnews",null,contentValues);
    }


    public static List<News1> getSelectedHistoryList(int id){
        List<News1> news1List=new ArrayList<>();
        Cursor cursor=database.query("historypublicnews",new String[]{"uniquekey","url","title","author_name","date","thumbnail_pic_s","thumbnail_pic_s02","thumbnail_pic_s03"},"uid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            String uniquekey=cursor.getString(cursor.getColumnIndexOrThrow("uniquekey"));
            String url=cursor.getString(cursor.getColumnIndexOrThrow("url"));
            String title=cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String author_name=cursor.getString(cursor.getColumnIndexOrThrow("author_name"));
            String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String thumbnail_pic_s=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s"));
            String thumbnail_pic_s02=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s02"));
            String thumbnail_pic_s03=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s03"));
            News1 news1=new News1(title,date,author_name,url,thumbnail_pic_s,thumbnail_pic_s02,thumbnail_pic_s03);
            news1.setUniquekey(uniquekey);
            news1List.add(news1);
        }
        return news1List;
    }

     public  static void DeleteHistoryList(){
        database.delete("historypublicnews",null,null);
      }


}
