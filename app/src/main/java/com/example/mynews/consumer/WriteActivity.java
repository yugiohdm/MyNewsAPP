package com.example.mynews.consumer;

import static com.example.mynews.dao.LogicBackground.releasenewsnewurl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.adapter.PublishNewsAdapter;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.ReleaseModel;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preferences;

    private ImageView goback_write_activity;

    private LinearLayout all_button;
    private LinearLayout published_button;
    private LinearLayout reviewed_button;
    private LinearLayout pass_button;

    private TextView all_text;
    private TextView published_text;
    private TextView reviewed_text;
    private TextView pass_text;

    private ListView type_listview;
    private TextView likes_text;

    private SharedPreferences sharedPreferences;

    List<Collectiblenews> collectiblenewsList=new ArrayList<>();

    PublishNewsAdapter publishNewsAdapter;

    boolean iChecked;

    int it = 0;

    int uid;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(WriteActivity.this,R.color.white,iChecked);

        goback_write_activity=findViewById(R.id.goback_write_activity);
        all_button=findViewById(R.id.all_button);
        published_button=findViewById(R.id.published_button);
        reviewed_button=findViewById(R.id.reviewed_button);
        pass_button=findViewById(R.id.pass_button);
        all_text=findViewById(R.id.all_text);
        published_text=findViewById(R.id.published_text);
        reviewed_text=findViewById(R.id.reviewed_text);
        pass_text=findViewById(R.id.pass_text);
        type_listview=findViewById(R.id.type_listview);
        likes_text=findViewById(R.id.likes_text);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        sharedPreferences=getSharedPreferences("user1",Context.MODE_PRIVATE);
        uid=sharedPreferences.getInt("uid",-1);
        username=sharedPreferences.getString("username","");

        goback_write_activity.setOnClickListener(this);
        all_button.setOnClickListener(this);
        published_button.setOnClickListener(this);
        reviewed_button.setOnClickListener(this);
        pass_button.setOnClickListener(this);

        setLikes(uid);
        changelayout(it);
        getTypeView(it);

    }

    public void changelayout(int t) {

        all_text.setSelected(t == 0);

        published_text.setSelected(t == 1);


        reviewed_text.setSelected(t == 2);

        pass_text.setSelected(t == 3);

        it = t;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goback_write_activity:
                finish();
                break;
            case R.id.all_button:
                it=0;
                break;
            case R.id.published_button:
                it=1;
                break;
            case R.id.reviewed_button:
                it=2;
                break;
            case R.id.pass_button:
                it=3;
                break;
        }
        changelayout(it);
        getTypeView(it);
    }
    
    
    public  void  getTypeView(int s){
        type_listview.setVerticalScrollBarEnabled(false);
        publishNewsAdapter=new PublishNewsAdapter(collectiblenewsList,WriteActivity.this);
        type_listview.setAdapter(publishNewsAdapter);
        if(s==0){
            publishNewsAdapter.removeData();
            getTypeData(s,null);
        }else if(s==1){
            publishNewsAdapter.removeData();
            getTypeData(s,"审核成功");
        }else if(s==2){
            publishNewsAdapter.removeData();
            getTypeData(s,"待审核");
        }else if(s==3){
            publishNewsAdapter.removeData();
            getTypeData(s,"审核失败");
        }
    }

    public  void  getTypeData(int h,String typestate) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (typestate==null) {
                jsonObject.put("uid", uid);
            }else {
                jsonObject.put("uid", uid);
                jsonObject.put("state", typestate);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url;
        if(h==0) {
             url = releasenewsnewurl + "/SelectReleaseNewsByUid";
        }else{
             url = releasenewsnewurl+ "/SelectReleaseNewsByStateByUid";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(WriteActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                ReleaseModel releaseModel=gson.fromJson(response.toString(),ReleaseModel.class);
                Log.i("=release=", String.valueOf(releaseModel.getDatas()));

                for (int i=0;i<releaseModel.getDatas().size();i++){
                    Collectiblenews collectiblenews=new Collectiblenews();
                    collectiblenews.setNid(releaseModel.getDatas().get(i).getNid());
                    collectiblenews.setNewstitle(releaseModel.getDatas().get(i).getNewstitle());
                    collectiblenews.setNewscontent(releaseModel.getDatas().get(i).getNewscontent());
                    collectiblenews.setNewimageurl(releaseModel.getDatas().get(i).getNewimageurl());
                    collectiblenews.setReleasedate(releaseModel.getDatas().get(i).getReleasedate());
                    collectiblenews.setState(releaseModel.getDatas().get(i).getState());
                    collectiblenews.setLikes(releaseModel.getDatas().get(i).getLikes());
                    collectiblenews.setUid(uid);
                    collectiblenews.setUsername(username);
                    collectiblenewsList.add(collectiblenews);
                }

                Log.i("=collectnews=", String.valueOf(collectiblenewsList));

                publishNewsAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WriteActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private  void setLikes(int uid){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl+"/SelectLikeByUid";
        RequestQueue requestQueue = Volley.newRequestQueue(WriteActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("=获赞信息=",response.toString());
                    int code=response.getInt("code");
                    if (code==181){
//                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        int like_count=response.getInt("data");
                        likes_text.setText(like_count+"");
                    }else if(code==182){
                        Toast.makeText(WriteActivity.this,  response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WriteActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}