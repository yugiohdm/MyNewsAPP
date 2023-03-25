package com.example.mynews.dao;


import com.example.mynews.bean.BaseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RequestApi {
    public final  static String ImageBaseUrl="http://182.91.172.232:8081/";


    @Multipart
    @POST("releasenews/upload")
    Call<BaseModel> upload(@Part MultipartBody.Part uploadFile, @Part("name") RequestBody name);


}
