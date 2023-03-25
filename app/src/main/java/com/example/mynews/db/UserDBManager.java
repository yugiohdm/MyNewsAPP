package com.example.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynews.bean.Releasenews;
import com.example.mynews.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBManager {
    public static SQLiteDatabase database;

    public static void initUserDB(Context context){
        UDBOpenHelper helper = new UDBOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public static void initUserDB1(Context context){
        UDBOpenHelper helper = new UDBOpenHelper(context);
        database = helper.getReadableDatabase();
    }


    public static void SaveUserList(User user){
        database.delete("Users",null,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("uid",user.getUid());
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        contentValues.put("type",user.getType());
        contentValues.put("phone", user.getPhone());
        contentValues.put("email", user.getEmail());
        contentValues.put("sex",user.getSex());
        contentValues.put("location", user.getLocation());
        contentValues.put("occupation", user.getOccupation());
        contentValues.put("birthday", user.getBirthday());
        contentValues.put("concerns", user.getConcerns());
        database.insert("Users", null, contentValues);
    }


    public static String getSelectedUsername(int id){
        String name = null;
        Cursor cursor=database.query("Users",new String[]{"username"},"uid=?",new String[]{String.valueOf(id)},null, null, null);
        while (cursor.moveToNext()) {

            String username=cursor.getString(cursor.getColumnIndexOrThrow("username"));

            name=username;

        }
        return name;
    }
}
