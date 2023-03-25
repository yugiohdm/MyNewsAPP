package com.example.mynews;

import static com.example.mynews.dao.LogicBackground.newsurl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.adapter.NewsAdapter;
import com.example.mynews.adapter.SearchNewsAdapter;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsModel;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;
import com.example.mynews.view.DividerGridItemDecoration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ImageView goback_searchResult_activity;

    private EditText search_result;

    private TextView search_result_button;

    RecyclerView search_view;

    private SharedPreferences preferences;


    List<Categoryitem> searchlist=new ArrayList<>();

    SearchNewsAdapter searchNewsAdapter;

    String searchContent;

    boolean iChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);

        StatusBarUtils.setWindowStatusBarColor(SearchResultActivity.this,R.color.white,iChecked);

        goback_searchResult_activity=findViewById(R.id.goback_searchResult_activity);
        search_result=findViewById(R.id.search_result);
        search_result_button=findViewById(R.id.search_result_button);
        search_view=findViewById(R.id.search_view);

        searchContent=getIntent().getStringExtra("SEARCH");


        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);


        goback_searchResult_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(SearchResultActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setSearchView();
        setSearchData(searchContent,SearchResultActivity.this);


        search_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchtext=search_result.getText().toString();
                searchNewsAdapter.RemoveData();
                setSearchData(searchtext,SearchResultActivity.this);
            }
        });

    }


    private void  setSearchView(){
        search_result.setText(searchContent);
        search_result.requestFocus();
//        search_result.setSelection(search_result.getText().length());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SearchResultActivity.this);
        search_view.setLayoutManager(linearLayoutManager);
        search_view.addItemDecoration(new DividerGridItemDecoration(SearchResultActivity.this));
        search_view.setItemAnimator(new DefaultItemAnimator());
        searchNewsAdapter =new SearchNewsAdapter(searchlist,SearchResultActivity.this);
        search_view.setAdapter(searchNewsAdapter);
    }

    private  void setSearchData(String searchcontent, Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",searchcontent);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = newsurl+ "/SelectTallNews";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                NewsModel newsModel=gson.fromJson(response.toString(),NewsModel.class);
                Log.i("=basemodel=",newsModel.getMessage());
                Log.i("=datas=", String.valueOf(newsModel.getDatas()));
                List<News2> newsList1=newsModel.getDatas();
                Log.i("=newsList=", String.valueOf(newsList1));

                if (newsList1==null){

                }else{
                    List<News> newsList=new ArrayList<>();

                    for (int i=0;i<newsList1.size();i++){
                        News news=new News();
                        news.setTitle(newsList1.get(i).getTitle());
                        news.setUniquekey(newsList1.get(i).getUniquekey());
                        news.setCategory(newsList1.get(i).getCategory());
                        news.setDate(newsList1.get(i).getDate());
                        news.setAuthor_name(newsList1.get(i).getAuthorName());
                        news.setUrl(newsList1.get(i).getUrl());
                        news.setThumbnail_pic_s(newsList1.get(i).getThumbnailPicS());
                        news.setThumbnail_pic_s02(newsList1.get(i).getThumbnailPicS02());
                        news.setThumbnail_pic_s03(newsList1.get(i).getThumbnailPicS03());
                        news.setIs_content(newsList1.get(i).getIsContent());
                        newsList.add(news);
                    }

                    Log.i("=searchnews1=", String.valueOf(newsList.toString()));
                    for (int i=0;i<newsList.size();i++){
                        if((newsList.get(i).getThumbnail_pic_s().equals("")||newsList.get(i).getThumbnail_pic_s()==null)) {
                            Categoryitem categoryitem = new Categoryitem();
                            categoryitem.setCategorynews(newsList.get(i));
                            categoryitem.setType(0);
                            searchlist.add(categoryitem);
                        }else if((newsList.get(i).getThumbnail_pic_s02()==null)&&(!newsList.get(i).getThumbnail_pic_s().equals(""))){
                            Categoryitem categoryitem = new Categoryitem();
                            categoryitem.setCategorynews(newsList.get(i));
                            categoryitem.setType(1);
                            searchlist.add(categoryitem);
                        }else if((newsList.get(i).getThumbnail_pic_s03()==null)&&(newsList.get(i).getThumbnail_pic_s02()!=null)){
                            Categoryitem categoryitem = new Categoryitem();
                            categoryitem.setCategorynews(newsList.get(i));
                            categoryitem.setType(2);
                            searchlist.add(categoryitem);
                        }else{
                            Categoryitem categoryitem = new Categoryitem();
                            categoryitem.setCategorynews(newsList.get(i));
                            categoryitem.setType(3);
                            searchlist.add(categoryitem);
                        }
                    }

                    Log.i("=searchnews1=", String.valueOf(searchlist));

                    searchNewsAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(activity, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}