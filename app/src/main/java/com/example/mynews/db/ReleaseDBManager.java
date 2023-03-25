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

public class ReleaseDBManager {

    public static SQLiteDatabase database;

    public static void initReleaseDB(Context context){
        RDBOpenHelper helper = new RDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initReleaseDB1(Context context){
        RDBOpenHelper helper = new RDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }

    public static void SaveReleaseList(Releasenews releasenews){
            database.delete("Releasenews",null,null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("uid",releasenews.getUid());
            contentValues.put("nid",releasenews.getNid());
            contentValues.put("newstitle",releasenews.getNewstitle());
            contentValues.put("newimageurl",releasenews.getNewimageurl());
            contentValues.put("state", releasenews.getState());
            contentValues.put("newstype", releasenews.getNewstype());
            contentValues.put("newscontent", releasenews.getNewscontent());
            contentValues.put("releasedate", releasenews.getReleasedate());
            contentValues.put("likes", releasenews.getLikes());
            database.insert("Releasenews", null, contentValues);
    }

    public static List<Collectiblenews> getSelectedReleaseList(String type){
        List<Collectiblenews> collectiblenewsList=new ArrayList<>();
        Cursor cursor=database.query("Releasenews",new String[]{"nid","newstype","newstitle","uid","newimageurl","newscontent","releasedate","state","likes"},"newstype=?",new String[]{type},null, null, null);
        while (cursor.moveToNext()) {
            int uid =cursor.getInt(cursor.getColumnIndexOrThrow("uid"));
            int nid=cursor.getInt(cursor.getColumnIndexOrThrow("nid"));
            String newstitle=cursor.getString(cursor.getColumnIndexOrThrow("newstitle"));
            String newsimageurl=cursor.getString(cursor.getColumnIndexOrThrow("newimageurl"));
            String newscontent=cursor.getString(cursor.getColumnIndexOrThrow("newscontent"));
            String releasedate=cursor.getString(cursor.getColumnIndexOrThrow("releasedate"));
            String state=cursor.getString(cursor.getColumnIndexOrThrow("state"));
            int likes=cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
            String newstype=cursor.getString(cursor.getColumnIndexOrThrow("newstype"));

            Collectiblenews collectiblenews=new Collectiblenews();
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
}
