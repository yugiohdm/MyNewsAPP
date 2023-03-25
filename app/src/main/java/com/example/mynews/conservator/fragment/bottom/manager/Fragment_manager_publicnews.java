package com.example.mynews.conservator.fragment.bottom.manager;

import static com.example.mynews.dao.LogicBackground.newsurl;
import static com.example.mynews.dao.LogicBackground.userurl;
import static com.youth.banner.util.BannerUtils.dp2px;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
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
import com.example.mynews.adapter.PublicNewsManagerItemAdapter;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsModel;
import com.example.mynews.conservator.PublicNewsDetailActivity;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.view.SwipeMenu;
import com.example.mynews.view.SwipeMenuCreator;
import com.example.mynews.view.SwipeMenuItem;
import com.example.mynews.view.SwipeMenuListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_manager_publicnews extends Fragment {
    private View view=null;

    private EditText publicnews_mananger_edittext;
    private ImageView publicnews_detail_add;

    SwipeMenuListView publicnews_manager_listview;



    PublicNewsManagerItemAdapter publicNewsManagerItemAdapter;

    List<News2> news2List=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_manager_publicnews, container, false);
            publicnews_mananger_edittext=view.findViewById(R.id.publicnews_mananger_edittext);
            publicnews_manager_listview=view.findViewById(R.id.publicnews_manager_listview);
            publicnews_detail_add=view.findViewById(R.id.publicnews_detail_add);

            Drawable drawable=getResources().getDrawable(R.drawable.search);
            drawable.setBounds(0,0,55,55);
            publicnews_mananger_edittext.setCompoundDrawables(drawable,null,null,null);

            initView();
            initData("");

            publicnews_mananger_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String text = charSequence.toString();
                    publicNewsManagerItemAdapter.removeData();
                    initData(text);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            publicnews_detail_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
        return view;
    }


    private void initView(){
        publicnews_manager_listview.setVerticalScrollBarEnabled(false);
        publicNewsManagerItemAdapter=new PublicNewsManagerItemAdapter(getContext(),news2List);
        publicnews_manager_listview.setAdapter(publicNewsManagerItemAdapter);
        publicnews_manager_listview.setMenuCreator(creator);
//        publicnews_manager_listview.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        publicnews_manager_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(),news2List.get(i).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("publicnews",news2List.get(i));
                intent.setClass(getContext(), PublicNewsDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        publicnews_manager_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                 if(index==0){
                        Toast.makeText(getContext(),news2List.get(position).getTitle(),Toast.LENGTH_SHORT).show();
//                        publicnews_manager_listview.setCloseInterpolator(new BounceInterpolator());
                        LogicBackground.DeletePublicNews(getContext(),news2List.get(position).getUniquekey());
                        publicNewsManagerItemAdapter.removeItem(position);

                 }
                return false;
            }
        });


    }



    /**
     * 创建每个item的侧滑选项
     */

     SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                        // 删除
                        SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                        deleteItem.setBackground(R.color.colorAppTheme);
                        deleteItem.setWidth(dp2px(90));
                        deleteItem.setTitle("删除");
                        deleteItem.setTitleSize(20);
                        deleteItem.setTitleColor(Color.WHITE);
                        menu.addMenuItem(deleteItem);

            }
        };
        // 添加到SwipeMenuListView



    private  void initData(String title){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=newsurl+"/SelectNewsByTitle";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                NewsModel newsModel=gson.fromJson(response.toString(), NewsModel.class);
                int code=newsModel.getCode();
                Log.i("==", String.valueOf(newsModel.getDatas()));
                if(code==231){
//                    Toast.makeText(getContext(), newsModel.getMessage(), Toast.LENGTH_SHORT).show();
                    List<News2> news2s=newsModel.getDatas();

                    for (int j=0;j<news2s.size();j++){
                        News2 news2=new News2();
                        news2.setUniquekey(news2s.get(j).getUniquekey());
                        news2.setTitle(news2s.get(j).getTitle());
                        news2.setUrl(news2s.get(j).getUrl());
                        news2.setDate(news2s.get(j).getDate());
                        news2.setCategory(news2s.get(j).getCategory());
                        news2.setAuthorName(news2s.get(j).getAuthorName());
                        news2.setThumbnailPicS(news2s.get(j).getThumbnailPicS());
                        news2.setThumbnailPicS02(news2s.get(j).getThumbnailPicS02());
                        news2.setThumbnailPicS03(news2s.get(j).getThumbnailPicS03());
                        news2.setIsContent(news2s.get(j).getIsContent());
                        news2List.add(news2);
                    }

                    Log.i("+lengh+", String.valueOf(news2List.size()));

                    publicNewsManagerItemAdapter.notifyDataSetChanged();
                }else if(code==232){
                    Toast.makeText(getContext(), newsModel.getMessage(), Toast.LENGTH_SHORT).show();
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