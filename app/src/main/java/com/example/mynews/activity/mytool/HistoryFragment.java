package com.example.mynews.activity.mytool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mynews.R;
import com.example.mynews.SearchResultActivity;
import com.example.mynews.adapter.NewsAdapter;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News1;
import com.example.mynews.db.HistoryDBManager;
import com.example.mynews.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private View view=null;

    private RecyclerView historyrecyclerview;

    List<Categoryitem> categoryitemList=new ArrayList<>();

    List<News> newsList=new ArrayList<>();

    NewsAdapter newsAdapter;

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_history, container, false);
            historyrecyclerview=view.findViewById(R.id.historyrecyclerview);
            setView();
            setData();
        }
        return view;
    }

    
    public  void setView(){
//        newsAdapter.RemoveData();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        historyrecyclerview.setLayoutManager(linearLayoutManager);
        historyrecyclerview.addItemDecoration(new DividerGridItemDecoration(getContext()));
        historyrecyclerview.setItemAnimator(new DefaultItemAnimator());
        newsAdapter=new NewsAdapter(categoryitemList,getContext());
        historyrecyclerview.setAdapter(newsAdapter);
    }

    public  void setData(){
        preferences= getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
        int uid=preferences.getInt("uid",0);
        HistoryDBManager.initHistoryDB1(getContext());
        List<News1> news1List= HistoryDBManager.getSelectedHistoryList(uid);
        Log.i("=vvv=", String.valueOf(news1List));
        if(news1List.size()!=0) {
            for (int i = 0; i < news1List.size(); i++) {
                News news = new News();
                news.setUniquekey(news1List.get(i).getUniquekey());
                news.setTitle(news1List.get(i).getTitle());
                news.setUrl(news1List.get(i).getUrl());
                news.setDate(news1List.get(i).getDate());
                news.setAuthor_name(news1List.get(i).getAuthor_name());
                news.setThumbnail_pic_s(news1List.get(i).getThumbnail_pic_s());
                news.setThumbnail_pic_s02(news1List.get(i).getThumbnail_pic_s02());
                news.setThumbnail_pic_s03(news1List.get(i).getThumbnail_pic_s03());
                newsList.add(news);
            }
            Log.i("=vvv=", String.valueOf(newsList));

            for (int i = 0; i < newsList.size(); i++) {
                if ((newsList.get(i).getThumbnail_pic_s() == null)||(newsList.get(i).getThumbnail_pic_s().equals(""))) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(0);
                    categoryitemList.add(categoryitem);
                } else if ((newsList.get(i).getThumbnail_pic_s02() == null) && (newsList.get(i).getThumbnail_pic_s()!= null)) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(1);
                    categoryitemList.add(categoryitem);
                } else if ((newsList.get(i).getThumbnail_pic_s03() == null) && (newsList.get(i).getThumbnail_pic_s02() != null)) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(2);
                    categoryitemList.add(categoryitem);
                } else {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(3);
                    categoryitemList.add(categoryitem);
                }
            }

            Log.i("=searchnews1=", String.valueOf(categoryitemList));

            newsAdapter.notifyDataSetChanged();
        }else{

        }
    }
}