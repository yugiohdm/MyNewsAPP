package com.example.mynews.conservator.fragment.bottom.manager;

import static com.example.mynews.dao.LogicBackground.newsurl;
import static com.example.mynews.dao.LogicBackground.releasenewsnewurl;
import static com.youth.banner.util.BannerUtils.dp2px;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mynews.adapter.ReleaseNewsManagerItemAdapter;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsModel;
import com.example.mynews.bean.ReleaseModel;
import com.example.mynews.bean.Releasenews;
import com.example.mynews.conservator.PublicNewsDetailActivity;
import com.example.mynews.conservator.ReleaseNewsDetailActivity;
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

public class Fragment_manager_releasenews extends Fragment {
    private View view=null;

    private EditText releasenews_mananger_edittext;
    private ImageView releasenews_detail_add;
    SwipeMenuListView releasenews_manager_listview;
    ReleaseNewsManagerItemAdapter releaseNewsManagerItemAdapter;
    List<Releasenews> releasenewsList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_manager_releasenews, container, false);
            releasenews_mananger_edittext=view.findViewById(R.id.releasenews_mananger_edittext);
            releasenews_manager_listview=view.findViewById(R.id.releasenews_manager_listview);
            releasenews_detail_add=view.findViewById(R.id.releasenews_detail_add);

            Drawable drawable=getResources().getDrawable(R.drawable.search);
            drawable.setBounds(0,0,55,55);
            releasenews_mananger_edittext.setCompoundDrawables(drawable,null,null,null);

            initView();
            initAllData();

            releasenews_mananger_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      String text=charSequence.toString();
                      if(text.equals("")){
                          releaseNewsManagerItemAdapter.removeData();
                          initAllData();
                      }else{
                          releaseNewsManagerItemAdapter.removeData();
                          initData(Integer.parseInt(text));
                      }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            releasenews_detail_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return view;
    }


    private void initView() {
        releasenews_manager_listview.setVerticalScrollBarEnabled(false);
        releaseNewsManagerItemAdapter=new ReleaseNewsManagerItemAdapter(getContext(),releasenewsList);
        releasenews_manager_listview.setAdapter(releaseNewsManagerItemAdapter);
        releasenews_manager_listview.setMenuCreator(creator);
//        publicnews_manager_listview.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        releasenews_manager_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(),releasenewsList.get(i).getNewscontent(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("releasenews",releasenewsList.get(i));
                intent.setClass(getContext(), ReleaseNewsDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        releasenews_manager_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(index==0){
                    Toast.makeText(getContext(),releasenewsList.get(position).getNewscontent(),Toast.LENGTH_SHORT).show();
//                        publicnews_manager_listview.setCloseInterpolator(new BounceInterpolator());
                    LogicBackground.DeleteReleaseNews(getContext(),releasenewsList.get(position).getNid());
                    releaseNewsManagerItemAdapter.removeItem(position);

                }
                return false;
            }
        });
    }


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


    private  void initData(int uid){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=releasenewsnewurl+"/SelectReleaseNewsByUid";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                ReleaseModel releaseModel=gson.fromJson(response.toString(), ReleaseModel.class);
                int code=releaseModel.getCode();
                Log.i("==", String.valueOf(releaseModel.getDatas()));
                if(code==141){
//                    Toast.makeText(getContext(), releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    List<Releasenews> releasenews=releaseModel.getDatas();

                    for (int j=0;j<releasenews.size();j++){
                        Releasenews releasenews1=new Releasenews();
                        releasenews1.setNid(releasenews.get(j).getNid());
                        releasenews1.setNewstitle(releasenews.get(j).getNewstitle());
                        releasenews1.setNewscontent(releasenews.get(j).getNewscontent());
                        releasenews1.setReleasedate(releasenews.get(j).getReleasedate());
                        releasenews1.setNewimageurl(releasenews.get(j).getNewimageurl());
                        releasenews1.setNewstype(releasenews.get(j).getNewstype());
                        releasenews1.setState(releasenews.get(j).getState());
                        releasenews1.setLikes(releasenews.get(j).getLikes());
                        releasenews1.setUid(releasenews.get(j).getUid());
                        releasenewsList.add(releasenews1);
                    }


                     releaseNewsManagerItemAdapter.notifyDataSetChanged();
                }else if(code==142){
                    Toast.makeText(getContext(), releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
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

    private  void initAllData(){
        JSONObject jsonObject = new JSONObject();
        String url=releasenewsnewurl+"/SelectReleaseNews";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                ReleaseModel releaseModel=gson.fromJson(response.toString(), ReleaseModel.class);
                int code=releaseModel.getCode();
                Log.i("==", String.valueOf(releaseModel.getDatas()));
                if(code==251){
//                    Toast.makeText(getContext(), releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    List<Releasenews> releasenews=releaseModel.getDatas();

                    for (int j=0;j<releasenews.size();j++){
                          Releasenews releasenews1=new Releasenews();
                          releasenews1.setNid(releasenews.get(j).getNid());
                          releasenews1.setNewstitle(releasenews.get(j).getNewstitle());
                          releasenews1.setNewscontent(releasenews.get(j).getNewscontent());
                          releasenews1.setReleasedate(releasenews.get(j).getReleasedate());
                          releasenews1.setNewimageurl(releasenews.get(j).getNewimageurl());
                          releasenews1.setNewstype(releasenews.get(j).getNewstype());
                          releasenews1.setState(releasenews.get(j).getState());
                          releasenews1.setLikes(releasenews.get(j).getLikes());
                          releasenews1.setUid(releasenews.get(j).getUid());
                          releasenewsList.add(releasenews1);
                    }


                     releaseNewsManagerItemAdapter.notifyDataSetChanged();
                }else if(code==252){
                    Toast.makeText(getContext(), releaseModel.getMessage(), Toast.LENGTH_SHORT).show();
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