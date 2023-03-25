package com.example.mynews.consumer.fragment.bottom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.adapter.EditTextAdapter;

import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.ItemBean;
import com.example.mynews.bean.Releasenews;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.PhotoUtil;
import com.google.gson.Gson;
import com.lzp.android.image.dialog.PhotographDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class Fragment_publish extends Fragment {//发布
    private View view=null;
    private LinearLayout add_title_layout;
    private ImageView add_title_image;
    private TextView add_title_text;

    private TextView release;

    private TextView preview;

    private EditText add_content;


    private ListView add_title_view;

    private LinearLayout add_image_layout;
    private EditTextAdapter editTextAdapter;

    private List<ItemBean> itemBeans;


    private RecyclerView add_image_view;



    int addtitlestate=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_publish, container, false);

            add_content=view.findViewById(R.id.add_content);
            preview=view.findViewById(R.id.preview);
            release=view.findViewById(R.id.release);
            add_title_layout=view.findViewById(R.id.add_title_layout);
            add_title_image=view.findViewById(R.id.add_title_image);
            add_title_text=view.findViewById(R.id.add_title_text);
            add_title_view=view.findViewById(R.id.add_title_view);
            add_image_layout=view.findViewById(R.id.add_image_layout);
            add_image_view=view.findViewById(R.id.add_image_view);



            add_content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String content=add_content.getText().toString();
                            if (content.equals("")){
                                 release.setTextColor(Color.parseColor("#aaaaaa"));
                                 preview.setTextColor(Color.parseColor("#aaaaaa"));
                            }else{
                                  release.setTextColor(Color.parseColor("#F44336"));
                                  preview.setTextColor(Color.parseColor("#272727"));

                                release.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(),"发布",Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
                                        String username=sharedPreferences.getString("username","");
                                        String phone=sharedPreferences.getString("phone","");
                                        String email=sharedPreferences.getString("email","");
                                        String password=sharedPreferences.getString("password","");
                                        int uid=sharedPreferences.getInt("uid",0);

                                        if((username.equals("")||phone.equals("")||email.equals(""))&&password.equals("")){
                                            Intent intent=new Intent();
                                            intent.setClass(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Log.i("=content=",content);
                                            if(addtitlestate==1) {
                                                String title = editTextAdapter.getTitle();
                                                Log.i("=title=",""+title);
                                                if(title.equals("")) {
                                                    setPublishNews(uid,null, content, null);
                                                }else{
                                                    setPublishNews(uid,title, content, null);
                                                }
                                            }else if(addtitlestate==0){
                                                     setPublishNews(uid,null, content, null);
                                            }
                                        }

                                    }
                                });

                                preview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(),"预览",Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
                                        String username=sharedPreferences.getString("username","");
                                        String phone=sharedPreferences.getString("phone","");
                                        String email=sharedPreferences.getString("email","");
                                        String password=sharedPreferences.getString("password","");

                                        if((username.equals("")||phone.equals("")||email.equals(""))&&password.equals("")){
                                            Intent intent=new Intent();
                                            intent.setClass(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }else{

                                        }
                                    }
                                });
                            }
                }

                @Override
                public void afterTextChanged(Editable editable) {
              }
           });


            add_title_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (addtitlestate){
                        case 0:
                            add_title_image.setImageResource(R.drawable.addtitle1);
                            add_title_text.setTextColor(Color.parseColor("#2c2c2c"));
                            add_title_text.setText("取消标题");
                            addtitlestate=1;
                            initTitleView();
                            break;
                        case 1:
                            add_title_image.setImageResource(R.drawable.addtitle);
                            add_title_text.setTextColor(Color.parseColor("#cdcdcd"));
                            add_title_text.setText("添加标题");
                            addtitlestate=0;
                            destroyTitleView();
                            break;
                    }
                }
            });


            add_image_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotographDialog photographDialog = new PhotographDialog(getContext());
                    photographDialog.show();
                    photographDialog.setOnBtnClickLister(new PhotographDialog.OnBtnClickLister() {
                        @Override
                        public void shoot() {
                            Log.i("Message", "------------点击拍照!");
                        }

                        @Override
                        public void select() {
                            Log.i("Message", "------------点击相册!");
                            PhotoUtil.select(getActivity(),add_image_view);
                        }

                        @Override
                        public void cancle() {
                            Log.i("Message", "------------点击取消!");
                        }
                    });
                }
            });
        }

        return view;
    }






     private void initTitleView(){
             itemBeans=new ArrayList<>();
             ItemBean itemBean=new ItemBean();
             itemBean.setText("");
             itemBeans.add(itemBean);

             editTextAdapter=new EditTextAdapter(getContext(),itemBeans);
             editTextAdapter.notifyDataSetChanged();
             add_title_view.setAdapter(editTextAdapter);
     }

     private  void destroyTitleView(){
              itemBeans.remove(0);
              editTextAdapter.notifyDataSetChanged();
     }


     private  void  setPublishNews(int uid,String title,String content,String imageurl){
         JSONObject jsonObject = new JSONObject();
         try {
             if(imageurl==null){
                 jsonObject.put("newstitle", title);
                 jsonObject.put("newscontent", content);
                 jsonObject.put("uid",uid);
             }else if((title==null)&&(imageurl==null)){
                 jsonObject.put("newscontent", content);
                 jsonObject.put("uid",uid);
             }else if(title==null){
                 jsonObject.put("newsimageurl", imageurl);
                 jsonObject.put("newscontent", content);
                 jsonObject.put("uid",uid);
             }else{
                 jsonObject.put("newstitle", title);
                 jsonObject.put("newscontent", content);
                 jsonObject.put("newsimageurl", imageurl);
                 jsonObject.put("uid",uid);
             }
         } catch (JSONException e) {
             throw new RuntimeException(e);
         }
         String url = LogicBackground.releasenewsnewurl +"/InsertReleaseNews";
         RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 try {
                     Log.d("发布新闻的信息",response.toString());
                     int code = response.getInt("code");
                     Log.d("code", String.valueOf(code));
                     if(code==121){
                         Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                         JSONObject datas=response.getJSONObject("data");
                         Gson gson=new Gson();
                         Releasenews releasenews =gson.fromJson(datas.toString(),Releasenews.class);
                         Log.i("=releasenews=", String.valueOf(releasenews));
                     }else if(code==122){
                         Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                     }
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
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


}
