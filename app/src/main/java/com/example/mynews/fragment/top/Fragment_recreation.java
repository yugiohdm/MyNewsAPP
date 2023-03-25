package com.example.mynews.fragment.top;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.NoContentActivity;
import com.example.mynews.R;
import com.example.mynews.adapter.LoopAdapter;
import com.example.mynews.adapter.NewsAdapter3;
import com.example.mynews.bean.Ads;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.News1;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsBaseModel;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.Page;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.db.HistoryDBManager;
import com.example.mynews.util.MyKey;
import com.example.mynews.api.OnItemClickListener;
import com.example.mynews.view.DividerGridItemDecoration;
import com.example.mynews.view.RollPagerView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Fragment_recreation extends Fragment {//娱乐
    private RecyclerView recyclerView8;
    private View view=null;
    private RollPagerView rollPagerView3;

    NewsAdapter3 newsAdapter3;
    LoopAdapter loopAdapter3;
    List<Categoryitem> recreationlist =new ArrayList<>();
    List<Ads> adsList3=new ArrayList<>();

    private SharedPreferences sharedPreferences;

    String type1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_recreation, container, false);
            recyclerView8= view.findViewById(R.id.recyclerview8);

            type1=getArguments().getString("type");

            initview8();
            initrecreationnews();
            initnews1();
        }
       return view;
    }

    private void initview8(){
        LinearLayoutManager linearLayoutManager8=new LinearLayoutManager(getContext());
        recyclerView8.setLayoutManager(linearLayoutManager8);
        recyclerView8.addItemDecoration(new DividerGridItemDecoration(getContext()));
        recyclerView8.setItemAnimator(new DefaultItemAnimator());
        rollPagerView3 = (RollPagerView) LayoutInflater.from(getContext()).inflate(R.layout.header_news_rv, recyclerView8, false);
        newsAdapter3 =new NewsAdapter3(recreationlist,getContext());
        loopAdapter3=new LoopAdapter(rollPagerView3, adsList3);
        rollPagerView3.setAdapter(loopAdapter3);
        newsAdapter3.setHeaderView(rollPagerView3);
        ViewClickListener();
        recyclerView8.setAdapter(newsAdapter3);
    }

    private void initrecreationnews(){
        JSONObject jsonObject = new JSONObject();
        String url = "http://v.juhe.cn/toutiao/index?type=yule&is_filter=1&key="+ MyKey.newskey;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                NewsBaseModel newsBaseModel =gson.fromJson(response.toString(), NewsBaseModel.class);
                Log.i("=recreationnews=", String.valueOf(newsBaseModel));

                if (!newsBaseModel.getError_code().equals("10012")) {

                    List<News> recreationnewsList = newsBaseModel.getResult().getData();

                    for(int k=0;k<recreationnewsList.size();k++) {
                        savenews(recreationnewsList.get(k));
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void  initnews1(){
        JSONObject jsonObject = new JSONObject();
        String type=null;
        try {
            type = URLEncoder.encode("娱乐", "utf-8");
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

                Log.i("=news1=", String.valueOf(recreationlist.toString()));

                Log.i("=news1=", String.valueOf(newsList.toString()));

                for (int i=0;i<newsList.size();i++){
                    if((newsList.get(i).getThumbnail_pic_s().equals("")||newsList.get(i).getThumbnail_pic_s()==null)) {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(1);
                        recreationlist.add(categoryitem);
                    }else if((newsList.get(i).getThumbnail_pic_s02()==null)&&(!newsList.get(i).getThumbnail_pic_s().equals(""))){
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(2);
                        recreationlist.add(categoryitem);
                    }else if((newsList.get(i).getThumbnail_pic_s03()==null)&&(newsList.get(i).getThumbnail_pic_s02()!=null)){
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(3);
                        recreationlist.add(categoryitem);
                    }else{
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(newsList.get(i));
                        categoryitem.setType(4);
                        recreationlist.add(categoryitem);
                    }
                }

                int k=0;
                for (int j = 0; j< newsList.size(); j++){
                    if (!newsList.get(j).getThumbnail_pic_s().equals("")) {
                        Ads ads = new Ads();
                        ads.setUniquekey(newsList.get(j).getUniquekey());
                        ads.setTitle(newsList.get(j).getTitle());
                        ads.setImgsrc(newsList.get(j).getThumbnail_pic_s());
                        ads.setUrl(newsList.get(j).getUrl());
                        ads.setSubname(newsList.get(j).getAuthor_name());
                        ads.setImgsrc1(newsList.get(j).getThumbnail_pic_s02());
                        ads.setImgsrc2(newsList.get(j).getThumbnail_pic_s03());
                        ads.setDate(newsList.get(j).getDate());
                        adsList3.add(ads);
                        if(k==4) break;
                        k=k+1;
                    }
                }


                Log.i("=news1=", String.valueOf(recreationlist));
                Log.i("=news1=", String.valueOf(adsList3));

                newsAdapter3.notifyDataSetChanged();
                loopAdapter3.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void  ViewClickListener(){
        sharedPreferences= getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        String password=sharedPreferences.getString("password","");
        int uid=sharedPreferences.getInt("uid",0);
        rollPagerView3.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

//                      Toast.makeText(getActivity(), adsList1.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                if(!username.equals("") && !password.equals("")){
                    setHistory(adsList3.get(position),uid);
                }
                Intent intent=new Intent(getContext(), NoContentActivity.class);
                intent.putExtra("url",adsList3.get(position).getUrl());
                intent.putExtra("title", adsList3.get(position).getTitle());
                intent.putExtra("uniquekey",adsList3.get(position).getUniquekey());
                intent.putExtra("image1",adsList3.get(position).getImgsrc());
                intent.putExtra("image2",adsList3.get(position).getImgsrc1());
                intent.putExtra("image3",adsList3.get(position).getImgsrc2());
                intent.putExtra("author",adsList3.get(position).getSubname());
                intent.putExtra("date",adsList3.get(position).getDate());
                Log.i("===",""+adsList3.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    public  void setHistory(Ads ads,int uid){
        News1 news1=new News1(ads.getTitle(),ads.getDate(),ads.getSubname(),ads.getUrl(),ads.getImgsrc(),ads.getImgsrc1(),ads.getImgsrc2());
        news1.setUid(uid);
        news1.setUniquekey(ads.getUniquekey());
        HistoryDBManager.initHistoryDB(getContext());
        HistoryDBManager.SaveHistoryList(news1);
    }
}
