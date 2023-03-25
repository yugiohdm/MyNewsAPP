package com.example.mynews.conservator;

import static com.example.mynews.dao.LogicBackground.releasenewsnewurl;
import static com.example.mynews.dao.LogicBackground.userurl;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.Releasenews;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ReleaseNewsDetailActivity extends AppCompatActivity {
   private ImageView goback_releasenews_detail_activity;

    private EditText releasenews_detail_nid;
    private EditText releasenews_detail_title;
    private EditText releasenews_detail_content;
    private EditText releasenews_detail_imageurl;
    private EditText releasenews_detail_date;
    private EditText releasenews_detail_type;
    private EditText releasenews_detail_state;
    private EditText releasenews_detail_like;
    private EditText releasenews_detail_uid;
    private Button releasenews_detail_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_news_detail);

        goback_releasenews_detail_activity=findViewById(R.id.goback_releasenews_detail_activity);
        releasenews_detail_nid=findViewById(R.id.releasenews_detail_nid);
        releasenews_detail_title=findViewById(R.id.releasenews_detail_title);
        releasenews_detail_content=findViewById(R.id.releasenews_detail_content);
        releasenews_detail_imageurl=findViewById(R.id.releasenews_detail_imageurl);
        releasenews_detail_date=findViewById(R.id.releasenews_detail_date);
        releasenews_detail_type=findViewById(R.id.releasenews_detail_type);
        releasenews_detail_state=findViewById(R.id.releasenews_detail_state);
        releasenews_detail_like=findViewById(R.id.releasenews_detail_like);
        releasenews_detail_uid=findViewById(R.id.releasenews_detail_uid);
        releasenews_detail_update=findViewById(R.id.releasenews_detail_update);

        setReleaseNewsData();

        goback_releasenews_detail_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        releasenews_detail_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int releasenews_nid=Integer.parseInt(releasenews_detail_nid.getText().toString());
                Log.i("==nid", String.valueOf(releasenews_nid));
                String releasenews_title=releasenews_detail_title.getText().toString();
                String releasenews_content=releasenews_detail_content.getText().toString();
                String releasenews_imageurl=releasenews_detail_imageurl.getText().toString();
                String releasenews_date=releasenews_detail_date.getText().toString();
                String releasenews_type=releasenews_detail_type.getText().toString();
                String releasenews_state=releasenews_detail_state.getText().toString();
                int releasenews_like= Integer.parseInt(releasenews_detail_like.getText().toString());
                int releasenews_uid= Integer.parseInt(releasenews_detail_uid.getText().toString());
                UpdateReleaseNewsDetail(releasenews_nid,releasenews_title,releasenews_content,releasenews_imageurl,releasenews_date,releasenews_type,releasenews_state, releasenews_like,releasenews_uid);
            }
        });

    }

    private void  setReleaseNewsData(){
        Releasenews releasenews = (Releasenews) getIntent().getSerializableExtra("releasenews");
        releasenews_detail_nid.setText(releasenews.getNid()+"");
        releasenews_detail_title.setText(releasenews.getNewstitle());
        releasenews_detail_title.requestFocus();
        releasenews_detail_content.setText(releasenews.getNewscontent());
        releasenews_detail_content.requestFocus();
        releasenews_detail_imageurl.setText(releasenews.getNewimageurl());
        releasenews_detail_imageurl.requestFocus();
        releasenews_detail_date.setText(releasenews.getReleasedate());
        releasenews_detail_date.requestFocus();
        releasenews_detail_type.setText(releasenews.getNewstype());
        releasenews_detail_type.requestFocus();
        releasenews_detail_state.setText(releasenews.getState());
        releasenews_detail_state.requestFocus();
        releasenews_detail_like.setText(releasenews.getLikes()+"");
        releasenews_detail_like.requestFocus();
        releasenews_detail_uid.setText(releasenews.getUid()+"");
        releasenews_detail_uid.requestFocus();
    }

    private void UpdateReleaseNewsDetail(int nid,String newstitle,String newscontent,String newsimageurl,String releasedate,String newstype,String state,int likes,int uid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nid",nid);
            jsonObject.put("newstitle",newstitle);
            jsonObject.put("newscontent",newscontent);
            jsonObject.put("newimageurl",newsimageurl);
            jsonObject.put("releasedate",releasedate);
            jsonObject.put("newstype",newstype);
            jsonObject.put("state",state);
            jsonObject.put("likes",likes);
            jsonObject.put("uid",uid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl+"/UpdateReleaseNews";
        RequestQueue requestQueue = Volley.newRequestQueue(ReleaseNewsDetailActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                BaseModel baseModel=gson.fromJson(response.toString(),BaseModel.class);
                Log.i("==", String.valueOf(baseModel));
                int code=baseModel.getCode();
                if(code==261){
                    Toast.makeText(ReleaseNewsDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(code==262){
                    Toast.makeText(ReleaseNewsDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ReleaseNewsDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ReleaseNewsDetailActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}