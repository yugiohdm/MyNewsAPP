package com.example.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.mynews.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class PageDBManager {

    public static SQLiteDatabase database;

    public static void initPageDB(Context context){
        PDBOpenHelper helper = new PDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initPageDB1(Context context){
        PDBOpenHelper helper = new PDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }

    /* 获取数据库当中全部行的内容，存储到集合当中*/
    public static List<TypeBean> getAllTypeList(){
        List<TypeBean> list = new ArrayList<>();
        Cursor cursor = database.query("itype", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
            String showstr = cursor.getString(cursor.getColumnIndexOrThrow("isShow"));
            Boolean isShow = Boolean.valueOf(showstr);
            TypeBean typeBean = new TypeBean(id, title, url, isShow);
            list.add(typeBean);
        }
        return list;
    }

    /* 修改数据库当中的行信息当中的选中记录*/
    public static void updateTypeList(List<TypeBean>typeList){
        for (int i = 0; i < typeList.size(); i++) {
            TypeBean typeBean = typeList.get(i);
            String title = typeBean.getTitle();
            ContentValues values = new ContentValues();
            values.put("isShow",String.valueOf(typeBean.isShow()));
            database.update("itype",values,"title=?",new String[]{title});
        }
    }

    /* 获取所有要求显示的内容的集合*/
    public static List<TypeBean> getSelectedTypeList(){
        List<TypeBean>list = new ArrayList<>();
        Cursor cursor = database.query("itype", new String[]{"id","title","url"}, "isShow=?", new String[]{"true"}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
            TypeBean bean = new TypeBean(id, title, url, true);
            list.add(bean);
        }
        return list;
    }
}
