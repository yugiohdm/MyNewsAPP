package com.example.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.Releasenews;
import com.example.mynews.dao.LogicBackground;

import java.util.ArrayList;
import java.util.List;

public class LikeDBManager {
    public static SQLiteDatabase database;

    public static void initLikeDB(Context context){
        LDBOpenHelper helper = new LDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initLikeDB1(Context context){
        LDBOpenHelper helper = new LDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }


    public static void SaveLikeList(Context context,Collectiblenews collectiblenews,int luid,String lusername){
        Cursor cursor=database.query("likenews",null,"nid=? and luid=?",new String[]{String.valueOf(collectiblenews.getNid()), String.valueOf(luid)},null, null, null);
        if(cursor.moveToFirst()==false) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("luid", luid);
            contentValues.put("lusername", lusername);
            contentValues.put("nid", collectiblenews.getNid());
            contentValues.put("newstitle", collectiblenews.getNewstitle());
            contentValues.put("newimageurl", collectiblenews.getNewimageurl());
            contentValues.put("state", collectiblenews.getState());
            contentValues.put("newstype", collectiblenews.getNewstype());
            contentValues.put("newscontent", collectiblenews.getNewscontent());
            contentValues.put("releasedate", collectiblenews.getReleasedate());
            contentValues.put("likes", collectiblenews.getLikes()+1);
            contentValues.put("likestate", "true");
            database.insert("likenews", null, contentValues);
            LogicBackground.addLike(context, collectiblenews.getNid());
        }else if(cursor.moveToFirst()==true){

        }
    }


    public static List<Collectiblenews>  getSelectedLikeList(int id){
        List<Collectiblenews> collectiblenewsList=new ArrayList<>();
        Cursor cursor=database.query("likenews",new String[]{"nid","newstype","newstitle","lusername","luid","newimageurl","newscontent","releasedate","state","likes"},"luid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            int uid =cursor.getInt(cursor.getColumnIndexOrThrow("luid"));
            String username=cursor.getString(cursor.getColumnIndexOrThrow("lusername"));
            int nid=cursor.getInt(cursor.getColumnIndexOrThrow("nid"));
            String newstitle=cursor.getString(cursor.getColumnIndexOrThrow("newstitle"));
            String newsimageurl=cursor.getString(cursor.getColumnIndexOrThrow("newimageurl"));
            String newscontent=cursor.getString(cursor.getColumnIndexOrThrow("newscontent"));
            String releasedate=cursor.getString(cursor.getColumnIndexOrThrow("releasedate"));
            String state=cursor.getString(cursor.getColumnIndexOrThrow("state"));
            int likes=cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
            String newstype=cursor.getString(cursor.getColumnIndexOrThrow("newstype"));

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
            collectiblenews.setNewstype(newstype);

            collectiblenewsList.add(collectiblenews);
        }
        return collectiblenewsList;
    }


    public static String getSelectedLikestate(int id1,int id2){
        String likestate="false";
        Cursor cursor=database.query("likenews",new String[]{"likestate"},"nid=? and luid=?",new String[]{String.valueOf(id1), String.valueOf(id2)},null, null, null);
        while (cursor.moveToNext()) {
            likestate=cursor.getString(cursor.getColumnIndexOrThrow("likestate"));
        }
        return likestate;
    }

    public static int getLikes(int id){
        int likes=0;
        Cursor cursor=database.query("likenews",new String[]{"likes"},"nid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {
            likes=likes+cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
        }
        return likes;
    }


    public  static void DeleteLikeNew(Context context,int id1,int id2){
        database.delete("likenews","nid=? and luid=?",new String[]{String.valueOf(id1), String.valueOf(id2)});
        LogicBackground.reduceLike(context,id1);
    }




}
