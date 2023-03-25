package com.example.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynews.NoContentActivity;
import com.example.mynews.R;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.bean.News;
import com.example.mynews.consumer.PublishContentActivity;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.db.LikeDBManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Categoryitem> newsList =new ArrayList<>();
    private Context context;

    int []collectstate= new int[4];

    String []likestate=new String[4];

    SharedPreferences preferences;

    public NewsAdapter(List<Categoryitem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }


    public List<Categoryitem>  getBl(){
        return newsList;
    }


    @Override
    public int getItemViewType(int position) {
        return newsList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==Categoryitem.First_TYPE) {
            return new NewsViewHolder1(LayoutInflater.from(context).inflate(R.layout.news_item1, parent, false));
        }else if(viewType==Categoryitem.SECOND_TYPE){
            return new NewsViewHolder2(LayoutInflater.from(context).inflate(R.layout.news_item2, parent, false));
        }else if(viewType==Categoryitem.THIRD_TYPE){
            return new NewsViewHolder3(LayoutInflater.from(context).inflate(R.layout.news_item3, parent, false));
        }else if (viewType==Categoryitem.FOURTH_TYPE){
            return new NewsViewHolder4(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
        }else  if(viewType==Categoryitem.FIFTH_TYPE){
            return new NewsViewHolder5(LayoutInflater.from(context).inflate(R.layout.released_new_item, parent, false));
        }else{
            return new NewsViewHolder6(LayoutInflater.from(context).inflate(R.layout.released_new_item1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos=position;preferences= context.getSharedPreferences("user1",context.MODE_PRIVATE);
        int uid=preferences.getInt("uid",-1);String username=preferences.getString("username","");
        switch (getItemViewType(position)) {
            case Categoryitem.First_TYPE:
               NewsViewHolder1 automobileNewsViewHolder1 = (NewsViewHolder1) holder;
                automobileNewsViewHolder1.news_name1.setText(newsList.get(position).getCategorynews().getTitle());
                automobileNewsViewHolder1.news_author_name1.setText(newsList.get(position).getCategorynews().getAuthor_name());
                automobileNewsViewHolder1.news_date1.setText(newsList.get(position).getCategorynews().getDate());
                ViewClickListener2(((NewsViewHolder1) holder).news_name1,((NewsViewHolder1) holder).news_layout1,((NewsViewHolder1) holder).news_layout1_1,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.SECOND_TYPE:
                NewsViewHolder2 newsViewHolder2 = (NewsViewHolder2) holder;
                newsViewHolder2.news_name2.setText(newsList.get(position).getCategorynews().getTitle());
                newsViewHolder2.news_author_name2.setText(newsList.get(position).getCategorynews().getAuthor_name());
                newsViewHolder2.news_date2.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder2.news_image1_1);
                ViewClickListener3(((NewsViewHolder2) holder).news_name2,((NewsViewHolder2) holder).news_layout2,((NewsViewHolder2) holder).news_layout2_1,((NewsViewHolder2) holder).news_layout2_2,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.THIRD_TYPE:
                NewsViewHolder3 newsViewHolder3 = (NewsViewHolder3) holder;
                newsViewHolder3.news_name3.setText(newsList.get(position).getCategorynews().getTitle());
                newsViewHolder3.news_author_name3.setText(newsList.get(position).getCategorynews().getAuthor_name());
                newsViewHolder3.news_date3.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder3.news_image1_2);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(newsViewHolder3.news_image2_2);
                ViewClickListener2(((NewsViewHolder3) holder).news_name3,((NewsViewHolder3) holder).news_layout3,((NewsViewHolder3) holder).news_layout3_1,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.FOURTH_TYPE:
                NewsViewHolder4 newsViewHolder4 = (NewsViewHolder4) holder;
                newsViewHolder4.news_name.setText(newsList.get(position).getCategorynews().getTitle());
                newsViewHolder4.news_author_name.setText(newsList.get(position).getCategorynews().getAuthor_name());
                newsViewHolder4.news_date.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder4.news_image1);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(newsViewHolder4.news_image2);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s03()).into(newsViewHolder4.news_image3);
                ViewClickListener2(((NewsViewHolder4) holder).news_name,((NewsViewHolder4) holder).news_layout,((NewsViewHolder4) holder).news_layout_1,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.FIFTH_TYPE:
                NewsViewHolder5 newsViewHolder5 = (NewsViewHolder5) holder;
                newsViewHolder5.released_username.setText(newsList.get(position).getCategorynews1().getUsername());
                newsViewHolder5.released_date.setText(newsList.get(position).getCategorynews1().getReleasedate());
                newsViewHolder5.released_content.setText(newsList.get(position).getCategorynews1().getNewscontent());
                changeCollect(0,newsViewHolder5.released_collect_image, newsViewHolder5.released_collect_text,newsList.get(position).getCategorynews1().getNid());
                changeLike(0,newsViewHolder5.released_like_image,newsViewHolder5.released_like_text,newsList.get(position).getCategorynews1().getNid(),uid);
                ViewClickListener1(newsViewHolder5.released_relativelayout,newsViewHolder5.released_linearLayout,newsList.get(position).getCategorynews1());
                newsViewHolder5.released_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (collectstate[0]){
                            case 0:
                                addCollectNews(newsList.get(pos).getCategorynews1(),1);
                                changeCollect(0,newsViewHolder5.released_collect_image, newsViewHolder5.released_collect_text,newsList.get(pos).getCategorynews1().getNid());
                                break;
                            case 1:
                                cancelCollectNews(newsList.get(pos).getCategorynews1());
                                changeCollect(0,newsViewHolder5.released_collect_image, newsViewHolder5.released_collect_text,newsList.get(pos).getCategorynews1().getNid());
                                break;
                        }
                    }
                });
                newsViewHolder5.released_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (likestate[0]){
                            case "true":
                                cancelLikeNews(newsList.get(pos).getCategorynews1(),uid,pos);
                                changeLike(0,newsViewHolder5.released_like_image,newsViewHolder5.released_like_text,newsList.get(pos).getCategorynews1().getNid(),uid);
                                break;
                            case "false":
                                addLikeNews(newsList.get(pos).getCategorynews1(),uid,username);
                                changeLike(0,newsViewHolder5.released_like_image,newsViewHolder5.released_like_text,newsList.get(pos).getCategorynews1().getNid(),uid);
                                break;
                        }
                    }
                });
                break;
            case Categoryitem.SIXTH_TYPE:
                NewsViewHolder6 newsViewHolder6 = (NewsViewHolder6) holder;
                newsViewHolder6.released_username1.setText(newsList.get(position).getCategorynews1().getUsername());
                newsViewHolder6.released_date1.setText(newsList.get(position).getCategorynews1().getReleasedate());
                newsViewHolder6.released_title.setText(newsList.get(position).getCategorynews1().getNewstitle());
                newsViewHolder6.released_content1.setText(newsList.get(position).getCategorynews1().getNewscontent());
                changeCollect(1,newsViewHolder6.released_collect_image1, newsViewHolder6.released_collect_text1,newsList.get(position).getCategorynews1().getNid());
                changeLike(1,newsViewHolder6.released_like_image1, newsViewHolder6.released_like_text1,newsList.get(position).getCategorynews1().getNid(),uid);
                ViewClickListener1(newsViewHolder6.released_relativelayout1,newsViewHolder6.released_linearLayout1,newsList.get(position).getCategorynews1());
                newsViewHolder6.released_collect1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (collectstate[1]){
                            case 0:
                                addCollectNews(newsList.get(pos).getCategorynews1(),1);
                                changeCollect(1,newsViewHolder6.released_collect_image1, newsViewHolder6.released_collect_text1,newsList.get(pos).getCategorynews1().getNid());
                                break;
                            case 1:
                                cancelCollectNews(newsList.get(pos).getCategorynews1());
                                changeCollect(1,newsViewHolder6.released_collect_image1, newsViewHolder6.released_collect_text1,newsList.get(pos).getCategorynews1().getNid());
                                break;
                        }
                    }
                });

                newsViewHolder6.released_like1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (likestate[1]){
                            case "true":
                                cancelLikeNews(newsList.get(pos).getCategorynews1(),uid,pos);
                                changeLike(1,newsViewHolder6.released_like_image1,newsViewHolder6.released_like_text1,newsList.get(pos).getCategorynews1().getNid(),uid);
                                break;
                            case "false":
                                addLikeNews(newsList.get(pos).getCategorynews1(),uid,username);
                                changeLike(1,newsViewHolder6.released_like_image1,newsViewHolder6.released_like_text1,newsList.get(pos).getCategorynews1().getNid(),uid);
                                break;
                        }
                    }
                });
                break;
        }
    }


    public void RemoveData(){
        newsList.clear();
        notifyDataSetChanged();
    }

    public void ViewClickListener1(RelativeLayout relativeLayout,LinearLayout linearLayout,Collectiblenews collectiblenews){
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

    public void ViewClickListener2(TextView textview, LinearLayout layout1, LinearLayout layout2,News news){
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setTextColor(Color.parseColor("#656865"));
                Intent intent=new Intent(context, NoContentActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("uniquekey",news.getUniquekey());
                intent.putExtra("image1",news.getThumbnail_pic_s());
                intent.putExtra("image2",news.getThumbnail_pic_s02());
                intent.putExtra("image3",news.getThumbnail_pic_s03());
                intent.putExtra("author",news.getAuthor_name());
                intent.putExtra("date",news.getDate());
                context.startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setTextColor(Color.parseColor("#656865"));
                Intent intent=new Intent(context, NoContentActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("uniquekey",news.getUniquekey());
                intent.putExtra("image1",news.getThumbnail_pic_s());
                intent.putExtra("image2",news.getThumbnail_pic_s02());
                intent.putExtra("image3",news.getThumbnail_pic_s03());
                intent.putExtra("author",news.getAuthor_name());
                intent.putExtra("date",news.getDate());
                context.startActivity(intent);
            }
        });

    }

    public void ViewClickListener3(TextView textview, LinearLayout layout_1, LinearLayout layout_2, LinearLayout layout_3,News news){
        layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setTextColor(Color.parseColor("#656865"));
                Intent intent=new Intent(context, NoContentActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("uniquekey",news.getUniquekey());
                intent.putExtra("image1",news.getThumbnail_pic_s());
                intent.putExtra("image2",news.getThumbnail_pic_s02());
                intent.putExtra("image3",news.getThumbnail_pic_s03());
                intent.putExtra("author",news.getAuthor_name());
                intent.putExtra("date",news.getDate());
                context.startActivity(intent);
            }
        });

        layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setTextColor(Color.parseColor("#656865"));
                Intent intent=new Intent(context, NoContentActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("uniquekey",news.getUniquekey());
                intent.putExtra("image1",news.getThumbnail_pic_s());
                intent.putExtra("image2",news.getThumbnail_pic_s02());
                intent.putExtra("image3",news.getThumbnail_pic_s03());
                intent.putExtra("author",news.getAuthor_name());
                intent.putExtra("date",news.getDate());
                context.startActivity(intent);
            }
        });

        layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setTextColor(Color.parseColor("#656865"));
                Intent intent=new Intent(context, NoContentActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("uniquekey",news.getUniquekey());
                intent.putExtra("image1",news.getThumbnail_pic_s());
                intent.putExtra("image2",news.getThumbnail_pic_s02());
                intent.putExtra("image3",news.getThumbnail_pic_s03());
                intent.putExtra("author",news.getAuthor_name());
                intent.putExtra("date",news.getDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList==null?0:newsList.size();
    }


    public void changeCollect(int i,ImageView imageView1, TextView textView1,int nid){
        CollectibleDBManager.initCollectibleDB1(context);
        collectstate[i]=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate[i]==1){
            imageView1.setImageResource(R.drawable.collect1);
            textView1.setText("已收藏");
        }else{
            imageView1.setImageResource(R.drawable.collect);
            textView1.setText("收藏");
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
            collectiblenews.setLikes(collectiblenews.getLikes() - 1);
            newsList.get(pos).setCategorynews1(collectiblenews);
        }
    }

    class NewsViewHolder1 extends  RecyclerView.ViewHolder{
        TextView news_name1;
        TextView news_date1;
        TextView news_author_name1;
        LinearLayout news_layout1;
        ImageView news_cross1;

        LinearLayout news_layout1_1;

        public NewsViewHolder1(View itemView) {
            super(itemView);
            news_name1 = (TextView) itemView.findViewById(R.id.news_name1);
            news_date1 = (TextView) itemView.findViewById(R.id.news_date1);
            news_author_name1 = (TextView) itemView.findViewById(R.id.news_author_name1);
            news_layout1=(LinearLayout) itemView.findViewById(R.id.news_layout1);
            news_cross1=(ImageView) itemView.findViewById(R.id.news_cross1);
            news_layout1_1=(LinearLayout) itemView.findViewById(R.id.news_layout1_1);
        }
    }

    class NewsViewHolder2 extends  RecyclerView.ViewHolder{
        TextView news_name2;
        TextView news_date2;
        TextView news_author_name2;
        ImageView news_image1_1;
        LinearLayout news_layout2;
        ImageView news_cross2;

        LinearLayout news_layout2_1;

        LinearLayout news_layout2_2;

        public NewsViewHolder2(View itemView) {
            super(itemView);
            news_name2 = (TextView) itemView.findViewById(R.id.news_name2);
            news_date2 = (TextView) itemView.findViewById(R.id.news_date2);
            news_author_name2 = (TextView) itemView.findViewById(R.id.news_author_name2);
            news_image1_1=(ImageView) itemView.findViewById(R.id.news_image1_1);
            news_layout2=(LinearLayout) itemView.findViewById(R.id.news_layout2);
            news_cross2=(ImageView) itemView.findViewById(R.id.news_cross2);
            news_layout2_1=(LinearLayout) itemView.findViewById(R.id.news_layout2_1);
            news_layout2_2=(LinearLayout) itemView.findViewById(R.id.news_layout2_2);
        }
    }

    class NewsViewHolder3 extends  RecyclerView.ViewHolder{
        TextView news_name3;
        TextView news_date3;
        TextView news_author_name3;
        ImageView news_image1_2;
        ImageView news_image2_2;
        LinearLayout news_layout3;
        ImageView news_cross3;

        LinearLayout news_layout3_1;

        public NewsViewHolder3(View itemView) {
            super(itemView);
            news_name3 = (TextView) itemView.findViewById(R.id.news_name3);
            news_date3 = (TextView) itemView.findViewById(R.id.news_date3);
            news_author_name3 = (TextView) itemView.findViewById(R.id.news_author_name3);
            news_image1_2=(ImageView) itemView.findViewById(R.id.news_image1_2);
            news_image2_2=(ImageView) itemView.findViewById(R.id.news_image2_2);
            news_layout3=(LinearLayout) itemView.findViewById(R.id.news_layout3);
            news_cross3=(ImageView) itemView.findViewById(R.id.news_cross3);
            news_layout3_1=(LinearLayout)  itemView.findViewById(R.id.news_layout3_1);
        }
    }

    class NewsViewHolder4 extends  RecyclerView.ViewHolder{
        TextView news_name;
        TextView news_date;
        TextView news_author_name;
        ImageView news_image1;
        ImageView news_image2;
        ImageView news_image3;
        LinearLayout news_layout;
        ImageView news_cross;
        LinearLayout news_layout_1;

        public NewsViewHolder4(View itemView) {
            super(itemView);
            news_name = (TextView) itemView.findViewById(R.id.news_name);
            news_date = (TextView) itemView.findViewById(R.id.news_date);
            news_author_name = (TextView) itemView.findViewById(R.id.news_author_name);
            news_image1=(ImageView) itemView.findViewById(R.id.news_image1);
            news_image2=(ImageView) itemView.findViewById(R.id.news_image2);
            news_image3=(ImageView) itemView.findViewById(R.id.news_image3);
            news_layout=(LinearLayout) itemView.findViewById(R.id.news_layout);
            news_cross=(ImageView) itemView.findViewById(R.id.news_cross);
            news_layout_1=(LinearLayout) itemView.findViewById(R.id.news_layout_1);
        }
    }

    class NewsViewHolder5 extends  RecyclerView.ViewHolder{
        TextView released_username;
        TextView released_date;
        TextView released_do;
        TextView released_content;
        LinearLayout released_share;
        ImageView released_share_image;
        TextView released_share_text;
        LinearLayout released_like;
        ImageView released_like_image;
        TextView released_like_text;
        LinearLayout released_collect;
        ImageView released_collect_image;
        TextView released_collect_text;

        ImageView released_remove;
        RelativeLayout released_relativelayout;
        LinearLayout released_linearLayout;

        public NewsViewHolder5(View itemView) {
            super(itemView);
            released_username = (TextView) itemView.findViewById(R.id.released_username);
            released_date = (TextView) itemView.findViewById(R.id.released_date);
            released_do = (TextView) itemView.findViewById(R.id.released_do);
            released_content=(TextView) itemView.findViewById(R.id.released_content);
            released_share=(LinearLayout) itemView.findViewById(R.id.released_share);
            released_share_image=(ImageView) itemView.findViewById(R.id.released_share_image);
            released_share_text=(TextView) itemView.findViewById(R.id.released_share_text);
            released_like=(LinearLayout) itemView.findViewById(R.id.released_like);
            released_like_image=(ImageView) itemView.findViewById(R.id.released_like_image);
            released_like_text=(TextView) itemView.findViewById(R.id.released_like_text);
            released_collect=(LinearLayout) itemView.findViewById(R.id.released_collect);
            released_collect_image=(ImageView) itemView.findViewById(R.id.released_collect_image);
            released_collect_text=(TextView) itemView.findViewById(R.id.released_collect_text);
            released_remove=(ImageView)itemView.findViewById(R.id.released_remove);
            released_relativelayout=(RelativeLayout) itemView.findViewById(R.id.released_relativelayout);
            released_linearLayout=(LinearLayout) itemView.findViewById(R.id.released_linearLayout);
        }
    }

    class NewsViewHolder6 extends  RecyclerView.ViewHolder{
        TextView released_username1;
        TextView released_date1;
        TextView released_do1;
        TextView released_title;
        TextView released_content1;
        LinearLayout released_share1;
        ImageView released_share_image1;
        TextView released_share_text1;
        LinearLayout released_like1;
        ImageView released_like_image1;
        TextView released_like_text1;
        LinearLayout released_collect1;
        ImageView released_collect_image1;
        TextView released_collect_text1;
        ImageView released_remove1;
        RelativeLayout released_relativelayout1;
        LinearLayout released_linearLayout1;

        public NewsViewHolder6(View itemView) {
            super(itemView);
            released_username1 = (TextView) itemView.findViewById(R.id.released_username1);
            released_date1 = (TextView) itemView.findViewById(R.id.released_date1);
            released_do1 = (TextView) itemView.findViewById(R.id.released_do1);
            released_title=(TextView)itemView.findViewById(R.id.released_title);
            released_content1=(TextView) itemView.findViewById(R.id.released_content1);
            released_share1=(LinearLayout) itemView.findViewById(R.id.released_share1);
            released_share_image1=(ImageView) itemView.findViewById(R.id.released_share_image1);
            released_share_text1=(TextView) itemView.findViewById(R.id.released_share_text1);
            released_like1=(LinearLayout) itemView.findViewById(R.id.released_like1);
            released_like_image1=(ImageView) itemView.findViewById(R.id.released_like_image1);
            released_like_text1=(TextView) itemView.findViewById(R.id.released_like_text1);
            released_collect1=(LinearLayout) itemView.findViewById(R.id.released_collect1);
            released_collect_image1=(ImageView) itemView.findViewById(R.id.released_collect_image1);
            released_collect_text1=(TextView) itemView.findViewById(R.id.released_collect_text1);
            released_remove1=(ImageView)itemView.findViewById(R.id.released_remove1);
            released_relativelayout1=(RelativeLayout) itemView.findViewById(R.id.released_relativelayout1);
            released_linearLayout1=(LinearLayout) itemView.findViewById(R.id.released_linearLayout1);
        }
    }

}
