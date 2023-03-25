package com.example.mynews.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.adapter.AddImageViewAdapter;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.ImageModel;
import com.example.mynews.dao.RequestApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoUtil{
    public static final int PHOTO_REQUEST_CAMERA = 1;
    public static final int PHOTO_REQUEST_GALLERY = 2;
    public static final int PHOTO_REQUEST_CUT = 3;
    public static String PHOTO_FILE_NAME = System.currentTimeMillis() + ".png";

    public static RecyclerView recyclerView;

    public List<ImageModel>   imageModelList=new ArrayList<>();
    public  AddImageViewAdapter addImageViewAdapter;

    public PhotoUtil() {
    }

    @SuppressLint("WrongConstant")
    public static void shoot(Activity activity) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(1);
        PHOTO_FILE_NAME = System.currentTimeMillis() + ".png";
        if (Environment.getExternalStorageState().equals("mounted")) {
            intent.putExtra("output", getUriForFile(activity, new File(activity.getExternalCacheDir(), PHOTO_FILE_NAME)));
        }

        activity.startActivityForResult(intent, 1);
    }

    public static void select(Activity activity, RecyclerView recyclerView1) {
        recyclerView=recyclerView1;
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intentToPickPic, 2);
    }


    public static void crop(Activity activity, Uri uri, int width, int height, File saveFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        Uri uriPath = Uri.parse("file://" + saveFile.getAbsolutePath());
        intent.putExtra("output", uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, 3);
    }

    public static void crop(Activity activity, Uri uri, File saveFile) {
        crop(activity, uri, DensityUtil.dip2px(activity, 50.0F), DensityUtil.dip2px(activity, 50.0F), saveFile);
    }

    public static void saveImage(Bitmap bm, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(path);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

            if (!bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }

            System.gc();
        }

    }

    public static Uri getUriForFile(Context context, File file) {
        if (context != null && file != null) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }

            return uri;
        } else {
            throw new NullPointerException();
        }
    }




    //根据图片的Uri获取图片路径
    public static  String getRealPathFromURI(Uri uri,Activity activity) {
        Log.e("=======Uri=======", uri.toString());
        String res = null;
        String[] proj = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public  void uploadFile(String path, Activity activity) {
        Log.d("---", "进入uploadFile!");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.ImageBaseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        RequestApi requestApi = retrofit.create(RequestApi.class);
        File file = new File(path);
        //声明类型，这里是图片类型
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //将文件转化为MultipartBody.Part，第一个参数：上传文件的key；第二个参数：文件名；第三个参数：RequestBody对象
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
        //第二个参数传入了一个名字叫邓旭波，给后台用于识别判断
        Call<BaseModel> call = requestApi.upload(part, RequestBody.create(null, "ddd"));
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                Log.i("-----------------------", "进入onResponse!");
                ResponseBody error = response.errorBody();
                Log.i("-----------------------", String.valueOf(error));
                if (error != null) {
                    try {
                        String e = error.string();
                        Log.i("e-----", e);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    BaseModel model = response.body();
                    if (model.getCode() == 300) {
                        Toast.makeText(activity, model.getMessage(), Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences= activity.getSharedPreferences("userimage",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("image",RequestApi.ImageBaseUrl + model.getData());
                        editor.commit();

                        ImageModel imageModel=new ImageModel();
                        imageModel.setImagetitle(String.valueOf(model.getData()));
                        imageModel.setImageurl(RequestApi.ImageBaseUrl+model.getData());
                        imageModelList.add(imageModel);

//                        Glide.with(activity).load(RequestApi.ImageBaseUrl + model.getData()).into(imageView);
                        addImageViewAdapter.notifyDataSetChanged();
                        Log.e("-----image-----", RequestApi.ImageBaseUrl + model.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.e("-----上传异常-----", t.getMessage());
            }
        });
    }


    public  void initaddImageView(Activity activity){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        addImageViewAdapter=new AddImageViewAdapter(activity,imageModelList);
        recyclerView.setAdapter(addImageViewAdapter);
    }


}