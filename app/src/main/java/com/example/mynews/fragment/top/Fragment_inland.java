package com.example.mynews.fragment.top;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.adapter.NewsAdapter2;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsBaseModel;
import com.example.mynews.bean.Page;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.MyKey;
import com.example.mynews.view.DividerGridItemDecoration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Fragment_inland extends Fragment {//国内

    private RecyclerView recyclerView10;
    private View view=null;

    NewsAdapter2 newsAdapter2;
    List<Categoryitem> inlandlist=new ArrayList<>();

    String type1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_inland, container, false);
            recyclerView10 = view.findViewById(R.id.recyclerview10);

            type1=getArguments().getString("type");

           initview10();
           initinlandnews();
           initnews1();
        }
        return view;
    }

    private void initview10(){
        LinearLayoutManager linearLayoutManager10=new LinearLayoutManager(getContext());
        recyclerView10.setLayoutManager(linearLayoutManager10);
        recyclerView10.addItemDecoration(new DividerGridItemDecoration(getContext()));
        recyclerView10.setItemAnimator(new DefaultItemAnimator());
        newsAdapter2 =new NewsAdapter2(inlandlist,getContext());
        recyclerView10.setAdapter(newsAdapter2);
    }

    private void initinlandnews(){
        JSONObject jsonObject = new JSONObject();
        String url = "http://v.juhe.cn/toutiao/index?type=guonei&is_filter=1&key="+ MyKey.newskey;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                NewsBaseModel newsBaseModel =gson.fromJson(response.toString(), NewsBaseModel.class);
                Log.i("=automobilenews=", String.valueOf(newsBaseModel));
                if (!newsBaseModel.getError_code().equals("10012")) {
                    List<News> automobilenewsList = newsBaseModel.getResult().getData();

                    for(int k=0;k< automobilenewsList.size();k++) {
                        savenews( automobilenewsList.get(k));
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void savenews(News news){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniquekey",news.getUniquekey());
            jsonObject.put("title",news.getTitle());
            jsonObject.put("date",news.getDate());
            jsonObject.put("category",news.getCategory());
            jsonObject.put("authorName",news.getAuthor_name());
            jsonObject.put("url",news.getUrl());
            jsonObject.put("thumbnailPicS",news.getThumbnail_pic_s());
            jsonObject.put("thumbnailPicS02",news.getThumbnail_pic_s02());
            jsonObject.put("thumbnailPicS03",news.getThumbnail_pic_s03());
            jsonObject.put("isContent",news.getIs_content());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = LogicBackground.newsurl+"/InsertNews";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                BaseModel baseModel=gson.fromJson(response.toString(),BaseModel.class);
                Log.i("=basemodel=",baseModel.getMessage());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void  initnews1(){
        JSONObject jsonObject = new JSONObject();
        String type=null;
        try {
            type = URLEncoder.encode("国内", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url =LogicBackground.newsurl+"/SelectNewsByPage?pagecurrent=1&pagesize=30&type="+type+"";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("分页信息", response.toString());

                Gson gson=new Gson();
                Page newsPage=gson.fromJson(response.toString(), Page.class);

                Log.d("新闻信息", newsPage.getRecords().toString());
                List<News2> newsList1=newsPage.getRecords();
                List<News> newsList=new ArrayList<>();

                for (int k=0;k<newsList1.size();k++){
                    News news=new News();
                    news.setTitle(newsList1.get(k).getTitle());
                    news.setUniquekey(newsList1.get(k).getUniquekey());
                    news.setCategory(newsList1.get(k).getCategory());
                    news.setDate(newsList1.get(k).getDate());
                    news.setAuthor_name(newsList1.get(k).getAuthorName());
                    news.setUrl(newsList1.get(k).getUrl());
                    news.setThumbnail_pic_s(newsList1.get(k).getThumbnailPicS());
                    news.setThumbnail_pic_s02(newsList1.get(k).getThumbnailPicS02());
                    news.setThumbnail_pic_s03(newsList1.get(k).getThumbnailPicS03());
                    news.setIs_content(newsList1.get(k).getIsContent());
                    newsList.add(news);
                }

                Log.i("=news1=", String.valueOf(inlandlist.toString()));

                Log.i("=news1=", String.valueOf(newsList.toString()));

                for (int i=0;i<newsList.size();i++){
                    if((newsList.get(i).getThumbnail_pic_s().equals("")||newsList.get(i).getThumbnail_pic_s()==null)) {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(0);
                        inlandlist.add(categoryitem);
                    }else if((newsList.get(i).getThumbnail_pic_s02()==null)&&(!newsList.get(i).getThumbnail_pic_s().equals(""))){
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(1);
                        inlandlist.add(categoryitem);
                    }else if((newsList.get(i).getThumbnail_pic_s03()==null)&&(newsList.get(i).getThumbnail_pic_s02()!=null)){
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(2);
                        inlandlist.add(categoryitem);
                    }else{
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(3);
                        inlandlist.add(categoryitem);
                    }
                }


                Log.i("=news1=", String.valueOf(inlandlist));

                newsAdapter2.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}