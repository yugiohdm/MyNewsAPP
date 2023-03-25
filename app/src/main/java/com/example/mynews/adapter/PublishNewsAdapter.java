package com.example.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mynews.consumer.PublishContentActivity;
import com.example.mynews.R;
import com.example.mynews.bean.Collectiblenews;

import com.example.mynews.databinding.ItemAddLvBinding;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.db.LikeDBManager;


import java.util.ArrayList;
import java.util.List;

public class PublishNewsAdapter extends BaseAdapter {

    public static final int ITEM_A = 0;
    public static final int ITEM_B = 1;
    public static final int ITEM_C =2;
    public static final int ITEM_D=3;

    List<Collectiblenews> collectiblenewsList =new ArrayList<>();
    Context context;

    int collectstate1;

    int collectstate2;


    int collectstate3;

    int collectstate4;

    SharedPreferences preferences;

    String []likestate=new String[4];
    public PublishNewsAdapter(List<Collectiblenews> collectiblenewsList, Context context) {
        this.collectiblenewsList = collectiblenewsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return collectiblenewsList.size();
    }

    @Override
    public Object getItem(int i) {
        return collectiblenewsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getItemViewType(int position) {
            if((collectiblenewsList.get(position).getNewstitle()==null||collectiblenewsList.get(position).getNewstitle().equals(""))&&(collectiblenewsList.get(position).getNewimageurl()==null||collectiblenewsList.get(position).getNewimageurl().equals(""))){
                return ITEM_A;
            }else if(collectiblenewsList.get(position).getNewimageurl()==null||collectiblenewsList.get(position).getNewimageurl().equals("")){
                return ITEM_B;
            }else if(collectiblenewsList.get(position).getNewstitle()==null||collectiblenewsList.get(position).getNewstitle().equals("")){
                return ITEM_C;
            }else{
                return ITEM_D;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        PublishViewHolder1 publishViewHolder1;
        PublishViewHolder2 publishViewHolder2;
        PublishViewHolder3 publishViewHolder3;

        preferences= context.getSharedPreferences("user1",context.MODE_PRIVATE);
        int uid=preferences.getInt("uid",-1);String username=preferences.getString("username","");

        switch (getItemViewType(i)){
            case ITEM_A:
                    view= LayoutInflater.from(context).inflate(R.layout.release_news_item,null);
                    publishViewHolder1=new PublishViewHolder1();
                    publishViewHolder1.release_username=view.findViewById(R.id.release_username);
                    publishViewHolder1.release_date=view.findViewById(R.id.release_date);
                    publishViewHolder1.release_do=view.findViewById(R.id.release_do);
                    publishViewHolder1.release_content=view.findViewById(R.id.release_content);
                    publishViewHolder1.release_state=view.findViewById(R.id.release_state);
                    publishViewHolder1.release_share=view.findViewById(R.id.release_share);
                    publishViewHolder1.release_like=view.findViewById(R.id.release_like);
                    publishViewHolder1.release_like_image=view.findViewById(R.id.release_like_image);
                    publishViewHolder1.release_like_text=view.findViewById(R.id.release_like_text);
                    publishViewHolder1.release_collect=view.findViewById(R.id.release_collect);
                    publishViewHolder1.release_collect_image=view.findViewById(R.id.release_collect_image);
                    publishViewHolder1.release_collect_text=view.findViewById(R.id.release_collect_text);
                    publishViewHolder1.release_relativelayout=view.findViewById(R.id.release_relativelayout);
                    publishViewHolder1.release_linearLayout=view.findViewById(R.id.release_linearLayout);
                    view.setTag(publishViewHolder1);

                publishViewHolder1.release_username.setText(collectiblenewsList.get(i).getUsername());
                publishViewHolder1.release_date.setText(collectiblenewsList.get(i).getReleasedate());
                publishViewHolder1.release_content.setText(collectiblenewsList.get(i).getNewscontent());

                switch (collectiblenewsList.get(i).getState()){
                    case "待审核":
                        publishViewHolder1.release_state.setText(collectiblenewsList.get(i).getState());
                        publishViewHolder1.release_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法分享",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder1.release_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法点赞",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder1.release_collect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法收藏",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "审核成功":
                        int count=0;
                        changeCollect1(publishViewHolder1.release_collect_image,publishViewHolder1.release_collect_text, collectiblenewsList.get(i).getNid());
                        changeLike(0,publishViewHolder1.release_like_image,publishViewHolder1.release_like_text,collectiblenewsList.get(i).getNid(),uid);
                        publishViewHolder1.release_state.setText(count+"阅读");
                        publishViewHolder1.release_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        publishViewHolder1.release_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (likestate[0]){
                                    case "true":
                                        cancelLikeNews(collectiblenewsList.get(i),uid,i);
                                        changeLike(0,publishViewHolder1.release_like_image,publishViewHolder1.release_like_text,collectiblenewsList.get(i).getNid(),uid);
                                        break;
                                    case "false":
                                        addLikeNews(collectiblenewsList.get(i),uid,username);
                                        changeLike(0,publishViewHolder1.release_like_image,publishViewHolder1.release_like_text,collectiblenewsList.get(i).getNid(),uid);
                                        break;
                                }
                            }
                        });
                        publishViewHolder1.release_collect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (collectstate1){
                                    case 0:
                                        addCollectNews(collectiblenewsList.get(i),1);
                                        changeCollect1(publishViewHolder1.release_collect_image,publishViewHolder1.release_collect_text, collectiblenewsList.get(i).getNid());
                                        break;
                                    case 1:
                                        cancelCollectNews(collectiblenewsList.get(i));
                                        changeCollect1(publishViewHolder1.release_collect_image,publishViewHolder1.release_collect_text, collectiblenewsList.get(i).getNid());
                                        break;
                                }
                            }
                        });
                        ViewClickListener(publishViewHolder1.release_relativelayout,publishViewHolder1.release_linearLayout,collectiblenewsList.get(i));
                        break;
                    case "审核失败":
                        publishViewHolder1.release_state.setText("审核失败");
                        publishViewHolder1.release_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能分享",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder1.release_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能点赞",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder1.release_collect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能收藏",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }

                break;
            case ITEM_B:
                    view= LayoutInflater.from(context).inflate(R.layout.release_news_item1,null);
                    publishViewHolder2=new PublishViewHolder2();
                    publishViewHolder2.release_username1=view.findViewById(R.id.release_username1);
                    publishViewHolder2.release_date1=view.findViewById(R.id.release_date1);
                    publishViewHolder2.release_do1=view.findViewById(R.id.release_do1);
                    publishViewHolder2.release_title=view.findViewById(R.id.release_title);
                    publishViewHolder2.release_content1=view.findViewById(R.id.release_content1);
                    publishViewHolder2.release_state1=view.findViewById(R.id.release_state1);
                    publishViewHolder2.release_share1=view.findViewById(R.id.release_share1);
                    publishViewHolder2.release_like1=view.findViewById(R.id.release_like1);
                    publishViewHolder2.release_like_image1=view.findViewById(R.id.release_like_image1);
                    publishViewHolder2.release_like_text1=view.findViewById(R.id.release_like_text1);
                    publishViewHolder2.release_collect1=view.findViewById(R.id.release_collect1);
                    publishViewHolder2.release_collect_image1=view.findViewById(R.id.release_collect_image1);
                    publishViewHolder2.release_collect_text1=view.findViewById(R.id.release_collect_text1);
                    publishViewHolder2.release_relativelayout1=view.findViewById(R.id.release_relativelayout1);
                    publishViewHolder2.release_linearLayout1=view.findViewById(R.id.release_linearLayout1);
                    view.setTag(publishViewHolder2);

                publishViewHolder2.release_username1.setText(collectiblenewsList.get(i).getUsername());
                publishViewHolder2.release_date1.setText(collectiblenewsList.get(i).getReleasedate());
                publishViewHolder2.release_title.setText(collectiblenewsList.get(i).getNewstitle());
                publishViewHolder2.release_content1.setText(collectiblenewsList.get(i).getNewscontent());

                switch (collectiblenewsList.get(i).getState()){
                    case "待审核":
                        publishViewHolder2.release_state1.setText(collectiblenewsList.get(i).getState());
                        publishViewHolder2.release_share1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法分享",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder2.release_like1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法点赞",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder2.release_collect1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核中，无法收藏",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "审核成功":
                        int count=0;
                        changeCollect2(publishViewHolder2.release_collect_image1,publishViewHolder2.release_collect_text1, collectiblenewsList.get(i).getNid());
                        changeLike(1,publishViewHolder2.release_like_image1,publishViewHolder2.release_like_text1,collectiblenewsList.get(i).getNid(),uid);
                        publishViewHolder2.release_state1.setText(count+"阅读");
                        publishViewHolder2.release_share1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        publishViewHolder2.release_like1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (likestate[1]){
                                    case "true":
                                        cancelLikeNews(collectiblenewsList.get(i),uid,i);
                                        changeLike(1,publishViewHolder2.release_like_image1,publishViewHolder2.release_like_text1,collectiblenewsList.get(i).getNid(),uid);
                                        break;
                                    case "false":
                                        addLikeNews(collectiblenewsList.get(i),uid,username);
                                        changeLike(1,publishViewHolder2.release_like_image1,publishViewHolder2.release_like_text1,collectiblenewsList.get(i).getNid(),uid);
                                        break;
                                }
                            }
                        });
                        publishViewHolder2.release_collect1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (collectstate2){
                                    case 0:
                                        addCollectNews(collectiblenewsList.get(i),1);
                                        changeCollect2(publishViewHolder2.release_collect_image1,publishViewHolder2.release_collect_text1, collectiblenewsList.get(i).getNid());
                                        break;
                                    case 1:
                                        cancelCollectNews(collectiblenewsList.get(i));
                                        changeCollect2(publishViewHolder2.release_collect_image1,publishViewHolder2.release_collect_text1, collectiblenewsList.get(i).getNid());
                                        break;
                                }
                            }
                        });
                        ViewClickListener(publishViewHolder2.release_relativelayout1,publishViewHolder2.release_linearLayout1,collectiblenewsList.get(i));
                        break;
                    case "审核失败":
                        publishViewHolder2.release_state1.setText("审核失败");
                        publishViewHolder2.release_share1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能分享",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder2.release_like1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能点赞",Toast.LENGTH_SHORT).show();
                            }
                        });
                        publishViewHolder2.release_collect1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context,"审核失败，不能收藏",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
                break;
            case ITEM_C:
                break;
            case ITEM_D:
                break;
        }
        return view;
    }



    public void changeCollect1(ImageView imageView1, TextView textView1,int nid){
        CollectibleDBManager.initCollectibleDB1(context);
        collectstate1=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate1==1){
            imageView1.setImageResource(R.drawable.collect1);
            textView1.setText("已收藏");
        }else{
            imageView1.setImageResource(R.drawable.collect);
            textView1.setText("收藏");
        }
    }

    public void changeCollect2(ImageView imageView2, TextView textView2,int nid){
        CollectibleDBManager.initCollectibleDB1(context);
        collectstate2=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate2==1){
            imageView2.setImageResource(R.drawable.collect1);
            textView2.setText("已收藏");
        }else{
            imageView2.setImageResource(R.drawable.collect);
            textView2.setText("收藏");
        }
    }

    public void changeCollect3(ImageView imageView3, TextView textView3,int nid){
        CollectibleDBManager.initCollectibleDB1(context);
        collectstate3=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate3==1){
            imageView3.setImageResource(R.drawable.collect1);
            textView3.setText("已收藏");
        }else{
            imageView3.setImageResource(R.drawable.collect);
            textView3.setText("收藏");
        }
    }

    public void changeCollect4(ImageView imageView4, TextView textView4,int nid){
        CollectibleDBManager.initCollectibleDB1(context);
        collectstate4=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate4==1){
            imageView4.setImageResource(R.drawable.collect1);
            textView4.setText("已收藏");
        }else{
            imageView4.setImageResource(R.drawable.collect);
            textView4.setText("收藏");
        }
    }


    public void  changeLike(int i,ImageView imageView1, TextView textView1,int nid,int luid){
        LikeDBManager.initLikeDB1(context);
        likestate[i]=LikeDBManager.getSelectedLikestate(nid,luid);

        if (likestate[i].equals("true")){
            imageView1.setImageResource(R.drawable.like1);
            textView1.setText(LikeDBManager.getLikes(nid)+"");
            textView1.setTextColor(Color.parseColor("#d81e06"));
        }else{
            imageView1.setImageResource(R.drawable.like);
            textView1.setText("赞");
            textView1.setTextColor(Color.parseColor("#272727"));
        }
    }

    public void addCollectNews(Collectiblenews collectiblenews, int state){
        CollectibleDBManager.initCollectibleDB(context);
        CollectibleDBManager.SaveCollectibleList(collectiblenews,state);
    }

    public void cancelCollectNews(Collectiblenews collectiblenews){
        CollectibleDBManager.initCollectibleDB(context);
        CollectibleDBManager.DeleteCollectibleState(collectiblenews.getNid());
    }


    public void addLikeNews(Collectiblenews collectiblenews,int luid,String lusername){
        LikeDBManager.initLikeDB(context);
        LikeDBManager.SaveLikeList(context,collectiblenews,luid,lusername);
    }

    public void cancelLikeNews(Collectiblenews collectiblenews,int luid,int pos){
        LikeDBManager.initLikeDB(context);
        LikeDBManager.DeleteLikeNew(context,collectiblenews.getNid(),luid);
        if (collectiblenews.getLikes()>0) {
            collectiblenewsList.get(pos).setLikes(collectiblenews.getLikes() - 1);
        }
    }

    public void removeData(){
        collectiblenewsList.clear();
        notifyDataSetChanged();
    }

    public  void  ViewClickListener(RelativeLayout relativeLayout,LinearLayout linearLayout,Collectiblenews collectiblenews){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, PublishContentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("collectiblenews",collectiblenews);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, PublishContentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("collectiblenews",collectiblenews);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }



    class PublishViewHolder1{

        private TextView release_username;
        private TextView release_date;
        private ImageView release_do;
        private TextView release_content;
        private TextView release_state;
        private LinearLayout release_share;
        private ImageView release_share_image;
        private TextView release_share_text;
        private LinearLayout release_like;
        private ImageView release_like_image;
        private TextView release_like_text;
        private LinearLayout release_collect;
        private ImageView release_collect_image;
        private TextView release_collect_text;
        private RelativeLayout release_relativelayout;
        private LinearLayout release_linearLayout;

    }

    class PublishViewHolder2{

        private TextView release_username1;
        private TextView release_date1;
        private ImageView release_do1;
        private TextView release_title;
        private TextView release_content1;
        private TextView release_state1;
        private LinearLayout release_share1;
        private ImageView release_share_image1;
        private TextView release_share_text1;
        private LinearLayout release_like1;
        private ImageView release_like_image1;
        private TextView release_like_text1;
        private LinearLayout release_collect1;
        private ImageView release_collect_image1;
        private TextView release_collect_text1;

        private RelativeLayout release_relativelayout1;
        private LinearLayout release_linearLayout1;
    }

    class PublishViewHolder3{

    }

}
