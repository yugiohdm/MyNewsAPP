package com.example.mynews.fragment.top;


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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.adapter.NewsAdapter1;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsBaseModel;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.Page;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.MyKey;
import com.example.mynews.thread.ThreadPoolProxyFactory;
import com.example.mynews.view.DividerGridItemDecoration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fragment_recommend extends Fragment {//推荐
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView1;
    private View view=null;

    NewsAdapter1 newsAdapter1;
    List<News> toplist1=new ArrayList<>();
    List<News> toplist2=new ArrayList<>();
    List<Categoryitem> categoryList=new ArrayList<>();
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认是加载中的情况
    private LoadDataTask mLoadDataTask;//定义一个初始化的加载状态为null
    Set<Integer> set;

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
            view = inflater.inflate(R.layout.fragment_recommend, container, false);
            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout1);
            recyclerView1 = view.findViewById(R.id.recyclerview1);

            type1=getArguments().getString("type");

            initview1();
            inittopnews();
            initnews1();

            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAppTheme1));//设置刷新转圈的颜色
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));//设置刷新的背景颜色
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    triggerLoadData();
                }
            });

        }
        return view;
    }




    private void triggerLoadData() {
        if (mCurState != STATE_SUCCESS) {
            if (mLoadDataTask == null) {
                //异步加载
                mLoadDataTask = new LoadDataTask();
                ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);//线程池
            }
        }
    }

//定义个耗时线程
            private class LoadDataTask implements Runnable{
                @Override
                public void run() {
                    //真正的在子线程中加载具体的数据-->得到数据(为了得到的状态值只有3个，创一个枚举)
                    LoadedResult loadedResult=initData();
                    mCurState=loadedResult.getState();
                    //UI刷新
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initOnUi();
                        }
                    });
                    //置空任务
                    mLoadDataTask = null;
                }
            }

            private LoadedResult initData() {  //做耗时的操作
                //处理耗时的数据请求 两秒后停止
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取http请求 或者 其他耗时请求 得到结果result
                //如果结果为空 return LoadedResult.EMPTY;
                //如果结果请求失败 return LoadedResult.ERROR;
                //如果结果成功 return LoadedResult.SUCCESS;
                return LoadedResult.SUCCESS;
            }

            //标识数据加载结果的枚举类
            public enum LoadedResult {
                /**
                 * STATE_ERROR = 1;//错误
                 * STATE_SUCCESS = 2;//成功
                 * STATE_EMPTY = 3;//空
                 */
                SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

                private int state;

                LoadedResult(int state) {
                    this.state = state;
                }

                public int getState() {
                    return state;
                }
            }

//主线程的处理，刷新UI
            private void initOnUi() {
                if (mCurState==STATE_LOADING){
                    swipeRefreshLayout.setRefreshing(true);//加载中
                }
                if (mCurState==STATE_ERROR){
                    swipeRefreshLayout.setRefreshing(false);//加载失败
                }
                if (mCurState==STATE_SUCCESS){
                    mCurState = STATE_LOADING;//回复初始状态
                    swipeRefreshLayout.setRefreshing(false);//加载成功
                }

    }

     private void initview1(){
         LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext());
         recyclerView1.setLayoutManager(linearLayoutManager1);
         recyclerView1.addItemDecoration(new DividerGridItemDecoration(getContext()));
         recyclerView1.setItemAnimator(new DefaultItemAnimator());
         newsAdapter1 =new NewsAdapter1(categoryList,getContext());
         recyclerView1.setAdapter(newsAdapter1);
    }


        private void inittopnews(){
        JSONObject jsonObject = new JSONObject();
        String url = "http://v.juhe.cn/toutiao/index?type=top&is_filter=1&key="+MyKey.newskey;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                NewsBaseModel newsBaseModel =gson.fromJson(response.toString(), NewsBaseModel.class);
                Log.i("=topnews=", String.valueOf(newsBaseModel));

                if (!newsBaseModel.getError_code().equals("10012")) {
                    List<News> topnewsList = newsBaseModel.getResult().getData();

                    for(int k=0;k<topnewsList.size();k++) {
                        savenews(topnewsList.get(k));
                    }

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
            type = URLEncoder.encode("头条", "utf-8");
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


                Log.i("=news1=", String.valueOf(newsList.toString()));

                set= new HashSet<>();
                while(true) {
                    int n= (int)(Math.random()*(29-0+1));
                    set.add(n);
                    if(set.size()>=5)
                        break;
                }

                for (int m:set) {
                    for (int i = 0; i< newsList.size(); i++){
                        if(m==i){
                            News topnews=new News();
                            topnews.setTitle(newsList.get(i).getTitle());
                            topnews.setUrl(newsList.get(i).getUrl());
                            topnews.setAuthor_name(newsList.get(i).getAuthor_name());
                            topnews.setUniquekey(newsList.get(i).getUniquekey());
                            topnews.setDate(newsList.get(i).getDate());
                            topnews.setThumbnail_pic_s(newsList.get(i).getThumbnail_pic_s());
                            topnews.setThumbnail_pic_s02(newsList.get(i).getThumbnail_pic_s02());
                            topnews.setThumbnail_pic_s03(newsList.get(i).getThumbnail_pic_s03());
                            toplist1.add(topnews);
                        }
                    }
                }

                List<News> topnewsList= newsList;
                for (int j=0;j<topnewsList.size();j++){
                    News news=new News();
                    news.setTitle(topnewsList.get(j).getTitle());
                    news.setAuthor_name(topnewsList.get(j).getAuthor_name());
                    news.setUniquekey(topnewsList.get(j).getUniquekey());
                    news.setUrl(topnewsList.get(j).getUrl());
                    news.setThumbnail_pic_s(topnewsList.get(j).getThumbnail_pic_s());
                    news.setThumbnail_pic_s02(topnewsList.get(j).getThumbnail_pic_s02());
                    news.setThumbnail_pic_s03(topnewsList.get(j).getThumbnail_pic_s03());
                    news.setDate(topnewsList.get(j).getDate());
                    toplist2.add(news);
                }

                Log.i("=topnews=", String.valueOf(toplist1));
                Log.i("=topnews=", String.valueOf(toplist2));

                int a=0;

                for(int k=0;k<toplist1.size();k++) {
                    if(k<3) {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist1.get(k));
                        categoryitem.setType(0);
                        categoryList.add(categoryitem);
                        a=a+1;
                    }else {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist1.get(k));
                        categoryitem.setType(1);
                        categoryList.add(categoryitem);
                        a=a+1;
                    }
                }

                for (int l=0;l<toplist2.size();l++){
                    if(toplist2.get(l).getThumbnail_pic_s().equals("")||toplist2.get(l).getThumbnail_pic_s().equals("null")) {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist2.get(l));
                        categoryitem.setType(3);
                        categoryList.add(a, categoryitem);
                        a=a+1;
                    }else if((toplist2.get(l).getThumbnail_pic_s02()==null)&&(!toplist2.get(l).getThumbnail_pic_s().equals(""))){
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist2.get(l));
                        categoryitem.setType(4);
                        categoryList.add(a, categoryitem);
                        a=a+1;
                    }else if((toplist2.get(l).getThumbnail_pic_s03()==null)&&(toplist2.get(l).getThumbnail_pic_s02()!=null)) {
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist2.get(l));
                        categoryitem.setType(5);
                        categoryList.add(a, categoryitem);
                        a=a+1;
                    } else{
                        Categoryitem categoryitem = new Categoryitem();
                        categoryitem.setCategorynews(toplist2.get(l));
                        categoryitem.setType(2);
                        categoryList.add(a, categoryitem);
                        a=a+1;
                    }
                }

                Log.i("=topnews=", String.valueOf(categoryList.toString()));

                newsAdapter1.notifyDataSetChanged();

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
