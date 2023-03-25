package com.example.mynews.activity.mytool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.adapter.NewsAdapter;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News1;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.db.HistoryDBManager;
import com.example.mynews.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CollectFragment extends Fragment {

    private View view=null;

    private RecyclerView collectrecyclerview;

    SharedPreferences preferences;

    List<Categoryitem> categoryitemList=new ArrayList<>();

    List<News> newsList=new ArrayList<>();

    List<Collectiblenews> collectiblenewsList=new ArrayList<>();

    NewsAdapter newsAdapter;

    int a=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_collect, container, false);
            collectrecyclerview=view.findViewById(R.id.collectrecyclerview);
            setView();
            setData();
        }
        return view;
    }

    public  void setView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        collectrecyclerview.setLayoutManager(linearLayoutManager);
        collectrecyclerview.addItemDecoration(new DividerGridItemDecoration(getContext()));
        collectrecyclerview.setItemAnimator(new DefaultItemAnimator());
        newsAdapter=new NewsAdapter(categoryitemList,getContext());
        collectrecyclerview.setAdapter(newsAdapter);
    }


    public  void setData(){
        preferences= getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
        int uid=preferences.getInt("uid",0);

        newsAdapter.RemoveData();

        CollectibleDBManager.initCollectibleDB1(getContext());
        collectiblenewsList=CollectibleDBManager.getSelectedCollectibleList(uid);
        newsList= CollectibleDBManager.getSelectedCollectiblePublicList(uid);


            Log.i("=vvv=", String.valueOf(newsList));
            Log.i("=vcv=", String.valueOf(collectiblenewsList));


            for (int i = 0; i < newsList.size(); i++) {
                if ((newsList.get(i).getThumbnail_pic_s() == null)||(newsList.get(i).getThumbnail_pic_s().equals(""))) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(0);
                    categoryitemList.add(a,categoryitem);
                    a++;
                } else if ((newsList.get(i).getThumbnail_pic_s02() == null) && (newsList.get(i).getThumbnail_pic_s()!= null)) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(1);
                    categoryitemList.add(a,categoryitem);
                    a++;
                } else if ((newsList.get(i).getThumbnail_pic_s03() == null) && (newsList.get(i).getThumbnail_pic_s02() != null)) {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(2);
                    categoryitemList.add(a,categoryitem);
                    a++;
                } else {
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews(newsList.get(i));
                    categoryitem.setType(3);
                    categoryitemList.add(a,categoryitem);
                    a++;
                }
            }

            for(int j=0;j<collectiblenewsList.size();j++){
                if((collectiblenewsList.get(j).getNewstitle()==null||collectiblenewsList.get(j).getNewstitle().equals(""))&&(collectiblenewsList.get(j).getNewimageurl()==null||collectiblenewsList.get(j).getNewimageurl().equals(""))){
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews1(collectiblenewsList.get(j));
                    categoryitem.setType(4);
                    categoryitemList.add(a,categoryitem);
                    a++;
                }else if(collectiblenewsList.get(j).getNewimageurl()==null||collectiblenewsList.get(j).getNewimageurl().equals("")){
                    Categoryitem categoryitem = new Categoryitem();
                    categoryitem.setCategorynews1(collectiblenewsList.get(j));
                    categoryitem.setType(5);
                    categoryitemList.add(a,categoryitem);
                    a++;
                }
            }

            Log.i("=searchnews1=", String.valueOf(categoryitemList));

            newsAdapter.notifyDataSetChanged();

    }
}