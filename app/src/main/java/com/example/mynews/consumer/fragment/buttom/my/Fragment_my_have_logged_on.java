package com.example.mynews.consumer.fragment.buttom.my;

import static com.example.mynews.dao.LogicBackground.releasenewsnewurl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.consumer.WriteActivity;
import com.example.mynews.activity.mytool.ToolContentActivity;
import com.example.mynews.activity.settings.SettingsActivity;
import com.example.mynews.view.RoundRectImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_my_have_logged_on extends Fragment {//已登录我的页面

    private ImageView my_image_view;
    private ImageView set1;

    private TextView my_username;

    private TextView text_concerns;

    private TextView text_likes;

    private LinearLayout collect_layout;
    private LinearLayout history_layout;
    private LinearLayout like_layout;

    private LinearLayout publish_layout;


    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_have_logged_on, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        my_image_view=view.findViewById(R.id.my_image_view);
        set1=view.findViewById(R.id.set1);
        my_username=view.findViewById(R.id.my_username);
        text_likes=view.findViewById(R.id.text_likes);
        text_concerns=view.findViewById(R.id.text_concerns);
        collect_layout=view.findViewById(R.id.collect_layout);
        history_layout=view.findViewById(R.id.history_layout);
        like_layout=view.findViewById(R.id.like_layout);
        publish_layout=view.findViewById(R.id.publish_layout);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.mmexport1668407944997);
        Bitmap outBitmap= RoundRectImageView.getRoundBitmapByShader(bitmap,100,100,90,0);
        my_image_view.setImageBitmap(outBitmap);

        sharedPreferences=getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        int uid=sharedPreferences.getInt("uid",-1);
        int concerns=sharedPreferences.getInt("concerns",0);

        my_username.setText(username);
        text_concerns.setText(concerns+"");
        setLikes(uid);

        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), SettingsActivity.class);
                intent.putExtra("set","bb");
                startActivity(intent);
            }
        });

        collect_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ToolContentActivity.class);
                intent.putExtra("tooltype",0);
                startActivity(intent);
            }
        });

        history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ToolContentActivity.class);
                intent.putExtra("tooltype",2);
                startActivity(intent);
            }
        });

        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ToolContentActivity.class);
                intent.putExtra("tooltype",1);
                startActivity(intent);
            }
        });

        publish_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

    }

    private  void setLikes(int uid){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl+"/SelectLikeByUid";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("=获赞信息=",response.toString());
                    int code=response.getInt("code");
                    if (code==181){
//                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        int like_count=response.getInt("data");
                        text_likes.setText(like_count+"");
                    }else if(code==182){
                        Toast.makeText(getContext(),  response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}