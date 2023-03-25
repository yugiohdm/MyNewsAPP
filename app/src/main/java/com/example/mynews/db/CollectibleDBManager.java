package com.example.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News1;

import java.util.ArrayList;
import java.util.List;

public class CollectibleDBManager {
    public static SQLiteDatabase database;

    public static void initCollectibleDB(Context context){
        CDBOpenHelper helper = new CDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initCollectibleDB1(Context context){
        CDBOpenHelper helper = new CDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }


    public static void SaveCollectibleList(Collectiblenews collectiblenews,int state){
        ContentValues contentValues=new ContentValues();
        contentValues.put("uid",collectiblenews.getUid());
        contentValues.put("username",collectiblenews.getUsername());
        contentValues.put("nid",collectiblenews.getNid());
        contentValues.put("newstitle",collectiblenews.getNewstitle());
        contentValues.put("newimageurl",collectiblenews.getNewimageurl());
        contentValues.put("state",collectiblenews.getState());
        contentValues.put("newscontent",collectiblenews.getNewscontent());
        contentValues.put("releasedate",collectiblenews.getReleasedate());
        contentValues.put("likes",collectiblenews.getLikes());
        contentValues.put("collectstate",state);
        database.insert("collectiblenews",null,contentValues);
    }


    public static List<Collectiblenews> getSelectedCollectibleList(int id){
        List<Collectiblenews> collectiblenewsList=new ArrayList<>();
        Cursor cursor=database.query("collectiblenews",new String[]{"nid","newstitle","username","uid","newimageurl","newscontent","releasedate","state","likes"},"uid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            int uid =cursor.getInt(cursor.getColumnIndexOrThrow("uid"));
            String username=cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int nid=cursor.getInt(cursor.getColumnIndexOrThrow("nid"));
            String newstitle=cursor.getString(cursor.getColumnIndexOrThrow("newstitle"));
            String newsimageurl=cursor.getString(cursor.getColumnIndexOrThrow("newimageurl"));
            String newscontent=cursor.getString(cursor.getColumnIndexOrThrow("newscontent"));
            String releasedate=cursor.getString(cursor.getColumnIndexOrThrow("releasedate"));
            String state=cursor.getString(cursor.getColumnIndexOrThrow("state"));
            int likes=cursor.getInt(cursor.getColumnIndexOrThrow("likes"));

            Collectiblenews collectiblenews=new Collectiblenews();
            collectiblenews.setUsername(username);
            collectiblenews.setUid(uid);
            collectiblenews.setNewstitle(newstitle);
            collectiblenews.setNid(nid);
            collectiblenews.setNewscontent(newscontent);
            collectiblenews.setNewimageurl(newsimageurl);
            collectiblenews.setReleasedate(releasedate);
            collectiblenews.setState(state);
            collectiblenews.setLikes(likes);

            collectiblenewsList.add(collectiblenews);
        }
        return collectiblenewsList;
    }

    public static void SaveCollectiblePublicList(News1 news1,int state){
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
        contentValues.put("collectstate",state);
        database.insert("collectiblepublicnews",null,contentValues);
    }

    public static List<News> getSelectedCollectiblePublicList(int id){
        List<News> newsList=new ArrayList<>();
        Cursor cursor=database.query("collectiblepublicnews",new String[]{"uniquekey","url","title","author_name","date","thumbnail_pic_s","thumbnail_pic_s02","thumbnail_pic_s03"},"uid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            String uniquekey=cursor.getString(cursor.getColumnIndexOrThrow("uniquekey"));
            String url=cursor.getString(cursor.getColumnIndexOrThrow("url"));
            String title=cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String author_name=cursor.getString(cursor.getColumnIndexOrThrow("author_name"));
            String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String thumbnail_pic_s=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s"));
            String thumbnail_pic_s02=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s02"));
            String thumbnail_pic_s03=cursor.getString(cursor.getColumnIndexOrThrow("thumbnail_pic_s03"));

            News news=new News();
            news.setTitle(title);
            news.setAuthor_name(author_name);
            news.setDate(date);
            news.setUrl(url);
            news.setThumbnail_pic_s(thumbnail_pic_s);
            news.setThumbnail_pic_s02(thumbnail_pic_s02);
            news.setThumbnail_pic_s03(thumbnail_pic_s03);
            news.setUniquekey(uniquekey);
            newsList.add(news);
        }
        return newsList;
    }

    public static int getSelectedCollectiblePublicState(String id){
        int state = 0;
        Cursor cursor=database.query("collectiblepublicnews",new String[]{"collectstate"},"uniquekey=?",new String[]{id},null, null, null);
        while (cursor.moveToNext()) {
            state=cursor.getInt(cursor.getColumnIndexOrThrow("collectstate"));
        }
        return  state;
    }


    public static int getSelectedCollectibleState(int id){
        int state = 0;
        Cursor cursor=database.query("collectiblenews",new String[]{"collectstate"},"nid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            state=cursor.getInt(cursor.getColumnIndexOrThrow("collectstate"));
        }
        return  state;
    }

    public  static void DeleteCollectiblePublicState(String id){
        database.delete("collectiblepublicnews","uniquekey=?",new String[]{id});
    }

    public  static void DeleteCollectibleState(int id){
        database.delete("collectiblenews","nid=?",new String[]{String.valueOf(id)});
    }

    public  static void DeleteCollectiblePublicList(){
        database.delete("collectiblepublicnews",null,null);
    }


}
