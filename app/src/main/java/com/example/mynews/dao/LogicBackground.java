package com.example.mynews.dao;

import static com.example.mynews.db.ReleaseDBManager.SaveReleaseList;
import static com.example.mynews.db.ReleaseDBManager.initReleaseDB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.ReleaseModel;
import com.example.mynews.bean.Releasenews;
import com.example.mynews.bean.User;
import com.example.mynews.bean.UserModel;
import com.example.mynews.consumer.WriteActivity;
import com.example.mynews.db.ReleaseDBManager;
import com.example.mynews.db.UserDBManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LogicBackground {
    public static String ip= "8.134.162.148";

    public static String ip1= "182.91.172.232";

    public static String port="8081";
    public static String userurl ="http://"+ip+":"+port+"/user";

    public static String newsurl="http://"+ip+":"+port+"/news";

    public static String collectiblenewsurl="http://"+ip+":"+port+"/collectiblenews";

    public static String releasenewsnewurl="http://"+ip+":"+port+"/releasenews";



    public  static void addLike(Context context,int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl + "/AddReleaseNewsLikesByNid";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("=获赞数信息=",response.toString());
                    int code=response.getInt("code");
                    if (code==191){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(code==192){
                        Toast.makeText(context,  response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public  static  void reduceLike(Context context,int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl + "/ReduceReleaseNewsLikesByNid";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("=获赞数信息=",response.toString());
                    int code=response.getInt("code");
                    if (code==201){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(code==202){
                        Toast.makeText(context,  response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void DeleteUsers(Context context,int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=userurl + "/DeleteUser";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                UserModel userModel=gson.fromJson(response.toString(),UserModel.class);
                int code=userModel.getCode();
                Log.i("==", String.valueOf(userModel.getDatas()));
                if(code==110){
                    Toast.makeText(context, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(code==220){
                    Toast.makeText(context, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void DeleteReleaseNews(Context context,int nid){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid",nid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl + "/DeleteReleaseNews";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                BaseModel baseModel=gson.fromJson(response.toString(),BaseModel.class);
                int code=baseModel.getCode();
                Log.i("==", String.valueOf(baseModel.getDatas()));
                if(code==271){
                    Toast.makeText(context, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(code==272){
                    Toast.makeText(context, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void DeletePublicNews(Context context,String uniquekey){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniquekey",uniquekey);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=newsurl+ "/DeleteNews";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                BaseModel baseModel=gson.fromJson(response.toString(),BaseModel.class);
                int code=baseModel.getCode();
                Log.i("==", String.valueOf(baseModel.getDatas()));
                if(code==101){
                    Toast.makeText(context, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(code==202){
                    Toast.makeText(context, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void GetReleaseNewsByState(Context context){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("state","审核成功");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl+ "/SelectReleaseNewsByState";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                ReleaseModel releaseModel=gson.fromJson(response.toString(),ReleaseModel.class);
                int code=releaseModel.getCode();
                if(code==251){
                    Toast.makeText(context, releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<releaseModel.getDatas().size();i++){
                        Releasenews releasenews=new Releasenews();
                        releasenews.setNid(releaseModel.getDatas().get(i).getNid());
                        releasenews.setNewstitle(releaseModel.getDatas().get(i).getNewstitle());
                        releasenews.setNewscontent(releaseModel.getDatas().get(i).getNewscontent());
                        releasenews.setNewimageurl(releaseModel.getDatas().get(i).getNewimageurl());
                        releasenews.setNewstype(releaseModel.getDatas().get(i).getNewstype());
                        releasenews.setReleasedate(releaseModel.getDatas().get(i).getReleasedate());
                        releasenews.setState(releaseModel.getDatas().get(i).getState());
                        releasenews.setLikes(releaseModel.getDatas().get(i).getLikes());
                        releasenews.setUid(releaseModel.getDatas().get(i).getUid());

                        ReleaseDBManager.initReleaseDB(context);
                        ReleaseDBManager.SaveReleaseList(releasenews);
                    }
                    Log.i("++", String.valueOf(releaseModel.getDatas()));
//                    releasenewsList=releaseModel.getDatas();
                }else if(code==252){
                    Toast.makeText(context, releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void GetUsers(Context context){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type","普通用户");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=userurl+ "/SelectUserByType";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                UserModel userModel=gson.fromJson(response.toString(),UserModel.class);
                int code=userModel.getCode();
                if(code==1110){
                    Toast.makeText(context, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<userModel.getDatas().size();i++){
                        User user=new User();
                        user.setUid(userModel.getDatas().get(i).getUid());
                        user.setUsername(userModel.getDatas().get(i).getUsername());
                        user.setPassword(userModel.getDatas().get(i).getPassword());
                        user.setPhone(userModel.getDatas().get(i).getPhone());
                        user.setEmail(userModel.getDatas().get(i).getEmail());
                        user.setSex(userModel.getDatas().get(i).getSex());
                        user.setType(userModel.getDatas().get(i).getType());
                        user.setLocation(userModel.getDatas().get(i).getLocation());
                        user.setOccupation(userModel.getDatas().get(i).getOccupation());
                        user.setBirthday(userModel.getDatas().get(i).getBirthday());
                        user.setConcerns(userModel.getDatas().get(i).getConcerns());

                        UserDBManager.initUserDB(context);
                        UserDBManager.SaveUserList(user);
                    }
//                    releasenewsList=releaseModel.getDatas();
                }else if(code==2220){
                    Toast.makeText(context, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
