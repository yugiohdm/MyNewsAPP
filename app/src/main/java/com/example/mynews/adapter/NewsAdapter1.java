package com.example.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynews.NoContentActivity;
import com.example.mynews.R;
import com.example.mynews.activity.mytool.HistoryFragment;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News1;
import com.example.mynews.db.HistoryDBManager;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Categoryitem> newsList =new ArrayList<>();
    private Context context;

    private SharedPreferences sharedPreferences;


    public NewsAdapter1(List<Categoryitem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }




    @Override
    public int getItemViewType(int position) {
        return newsList.get(position).getType();
    }

    public List<Categoryitem>  getBl(){
        return newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==Categoryitem.First_TYPE) {
           return new NewsFirstViewHolder1(LayoutInflater.from(context).inflate(R.layout.top_news_item, parent, false));
        }else if(viewType==Categoryitem.SECOND_TYPE){
            return new NewsFirstViewHolder2(LayoutInflater.from(context).inflate(R.layout.top_news_item1, parent,false));
        }
        else if(viewType==Categoryitem.THIRD_TYPE){
            return new NewsSecondViewHolder1(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
        }
        else if (viewType==Categoryitem.FOURTH_TYPE){
            return new NewsSecondViewHolder2(LayoutInflater.from(context).inflate(R.layout.news_item1, parent, false));
        }
        else if (viewType==Categoryitem.FIFTH_TYPE){
            return new NewsSecondViewHolder3(LayoutInflater.from(context).inflate(R.layout.news_item2, parent, false));
        }
        else{
            return new NewsSecondViewHolder4(LayoutInflater.from(context).inflate(R.layout.news_item3, parent, false));
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
                case Categoryitem.First_TYPE:
                    NewsFirstViewHolder1 topnewFirstViewHolder1 = (NewsFirstViewHolder1) holder;
                    topnewFirstViewHolder1.top_news_name.setText(newsList.get(position).getCategorynews().getTitle());
                    topnewFirstViewHolder1.top_news_author_name.setText(newsList.get(position).getCategorynews().getAuthor_name());
                    ViewClickListener1(topnewFirstViewHolder1.top_news_name,holder.itemView,newsList.get(position).getCategorynews());
                    break;
                case Categoryitem.SECOND_TYPE:
                    NewsFirstViewHolder2 topnewFirstViewHolder2 = (NewsFirstViewHolder2) holder;
                    topnewFirstViewHolder2.top_news_name1.setText(newsList.get(position).getCategorynews().getTitle());
                    topnewFirstViewHolder2.top_news_author_name1.setText(newsList.get(position).getCategorynews().getAuthor_name());
                    ViewClickListener1(topnewFirstViewHolder2.top_news_name1,holder.itemView,newsList.get(position).getCategorynews());
                       break;
                case Categoryitem.THIRD_TYPE:
                        NewsSecondViewHolder1 topnewSecondViewHolder1 = (NewsSecondViewHolder1) holder;
                        topnewSecondViewHolder1.news_name.setText(newsList.get(position).getCategorynews().getTitle());
                        topnewSecondViewHolder1.news_author_name.setText(newsList.get(position).getCategorynews().getAuthor_name());
                        topnewSecondViewHolder1.news_date.setText(newsList.get(position).getCategorynews().getDate());
                        Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(topnewSecondViewHolder1.news_image1);
                        Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(topnewSecondViewHolder1.news_image2);
                        Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s03()).into(topnewSecondViewHolder1.news_image3);
                        ViewClickListener2(((NewsSecondViewHolder1) holder).news_name,((NewsSecondViewHolder1) holder).news_layout,((NewsSecondViewHolder1) holder).news_layout_1,newsList.get(position).getCategorynews());
                        break;
              case Categoryitem.FOURTH_TYPE:
                  NewsSecondViewHolder2 topnewSecondViewHolder2 = (NewsSecondViewHolder2) holder;
                  topnewSecondViewHolder2.news_name1.setText(newsList.get(position).getCategorynews().getTitle());
                  topnewSecondViewHolder2.news_author_name1.setText(newsList.get(position).getCategorynews().getAuthor_name());
                  topnewSecondViewHolder2.news_date1.setText(newsList.get(position).getCategorynews().getDate());
                  ViewClickListener2(((NewsSecondViewHolder2) holder).news_name1,((NewsSecondViewHolder2) holder).news_layout1,((NewsSecondViewHolder2) holder).news_layout1_1,newsList.get(position).getCategorynews());
                  break;
              case Categoryitem.FIFTH_TYPE:
                  NewsSecondViewHolder3 topnewSecondViewHolder3 = (NewsSecondViewHolder3) holder;
                  topnewSecondViewHolder3.news_name2.setText(newsList.get(position).getCategorynews().getTitle());
                  topnewSecondViewHolder3.news_author_name2.setText(newsList.get(position).getCategorynews().getAuthor_name());
                  topnewSecondViewHolder3.news_date2.setText(newsList.get(position).getCategorynews().getDate());
                  Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(topnewSecondViewHolder3.news_image1_1);
                  ViewClickListener3(((NewsSecondViewHolder3) holder).news_name2,((NewsSecondViewHolder3) holder).news_layout2,((NewsSecondViewHolder3) holder).news_layout2_1,((NewsSecondViewHolder3) holder).news_layout2_2,newsList.get(position).getCategorynews());
                  break;
               case Categoryitem.SIXTH_TYPE:
                   NewsSecondViewHolder4 topnewSecondViewHolder4 = (NewsSecondViewHolder4) holder;
                   topnewSecondViewHolder4.news_name3.setText(newsList.get(position).getCategorynews().getTitle());
                   topnewSecondViewHolder4.news_author_name3.setText(newsList.get(position).getCategorynews().getAuthor_name());
                   topnewSecondViewHolder4.news_date3.setText(newsList.get(position).getCategorynews().getDate());
                   Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(topnewSecondViewHolder4.news_image1_2);
                   Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(topnewSecondViewHolder4.news_image2_2);
                   ViewClickListener2(((NewsSecondViewHolder4) holder).news_name3,((NewsSecondViewHolder4) holder).news_layout3,((NewsSecondViewHolder4) holder).news_layout3_1,newsList.get(position).getCategorynews());
                   break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public void ViewClickListener1(TextView textview, View view, News news){
        sharedPreferences= context.getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        String password=sharedPreferences.getString("password","");
        int uid=sharedPreferences.getInt("uid",0);

        view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!username.equals("") && !password.equals("")){
                     setHistory(news,uid);
                 }
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
    public void ViewClickListener2(TextView textview, LinearLayout layout1, LinearLayout layout2,News news){
        sharedPreferences= context.getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        String password=sharedPreferences.getString("password","");
        int uid=sharedPreferences.getInt("uid",0);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.equals("") && !password.equals("")){
                    setHistory(news,uid);
                }
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
                if(!username.equals("") && !password.equals("")){
                    setHistory(news,uid);
                }
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

    public void ViewClickListener3(TextView textview,LinearLayout layout_1,LinearLayout layout_2,LinearLayout layout_3,News news){
        sharedPreferences= context.getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        String password=sharedPreferences.getString("password","");
        int uid=sharedPreferences.getInt("uid",0);

        layout_1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(!username.equals("") && !password.equals("")){
                         setHistory(news,uid);
                     }
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
                     if(!username.equals("") && !password.equals("")){
                         setHistory(news,uid);
                     }
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
                     if(!username.equals("") && !password.equals("")){
                         setHistory(news,uid);
                     }
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


    public void setHistory(News news,int uid){
        News1 news1=new News1(news.getTitle(),news.getDate(),news.getAuthor_name(),news.getUrl(),news.getThumbnail_pic_s(),news.getThumbnail_pic_s02(),news.getThumbnail_pic_s03());
        news1.setUid(uid);
        news1.setUniquekey(news.getUniquekey());
        HistoryDBManager.initHistoryDB(context);
        HistoryDBManager.SaveHistoryList(news1);
    }


    class NewsSecondViewHolder1 extends  RecyclerView.ViewHolder{
        TextView news_name;
        TextView news_date;
        TextView news_author_name;
        ImageView news_image1;
        ImageView news_image2;
        ImageView news_image3;
        LinearLayout news_layout;
        ImageView news_cross;
        LinearLayout news_layout_1;

        public NewsSecondViewHolder1(View itemView) {
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


    class NewsSecondViewHolder2 extends  RecyclerView.ViewHolder{
        TextView news_name1;
        TextView news_date1;
        TextView news_author_name1;
        LinearLayout news_layout1;
        ImageView news_cross1;

        LinearLayout news_layout1_1;

        public NewsSecondViewHolder2(View itemView) {
            super(itemView);
            news_name1 = (TextView) itemView.findViewById(R.id.news_name1);
            news_date1 = (TextView) itemView.findViewById(R.id.news_date1);
            news_author_name1 = (TextView) itemView.findViewById(R.id.news_author_name1);
            news_layout1=(LinearLayout) itemView.findViewById(R.id.news_layout1);
            news_cross1=(ImageView) itemView.findViewById(R.id.news_cross1);
            news_layout1_1=(LinearLayout) itemView.findViewById(R.id.news_layout1_1);
        }
    }

    class NewsSecondViewHolder3 extends  RecyclerView.ViewHolder{
        TextView news_name2;
        TextView news_date2;
        TextView news_author_name2;
        ImageView news_image1_1;
        LinearLayout news_layout2;
        ImageView news_cross2;

        LinearLayout news_layout2_1;

        LinearLayout news_layout2_2;
        public NewsSecondViewHolder3(View itemView) {
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

    class NewsSecondViewHolder4 extends  RecyclerView.ViewHolder{
        TextView news_name3;
        TextView news_date3;
        TextView news_author_name3;
        ImageView news_image1_2;
        ImageView news_image2_2;
        LinearLayout news_layout3;
        ImageView news_cross3;

        LinearLayout news_layout3_1;

        public NewsSecondViewHolder4(View itemView) {
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


    class NewsFirstViewHolder1 extends  RecyclerView.ViewHolder{
        TextView top_news_name;
        TextView top_news;
        TextView top_news_author_name;
        LinearLayout top_news_layout;

        public NewsFirstViewHolder1(View itemView) {
            super(itemView);
            top_news_name = (TextView) itemView.findViewById(R.id.top_news_name);
            top_news = (TextView) itemView.findViewById(R.id.top_news);
            top_news_author_name = (TextView) itemView.findViewById(R.id.top_news_author_name);
            top_news_layout=(LinearLayout)itemView.findViewById(R.id.top_news_layout);
        }

    }

    class NewsFirstViewHolder2 extends  RecyclerView.ViewHolder{
        TextView top_news_name1;
        TextView top_news_author_name1;
        LinearLayout top_news_layout1;

        public NewsFirstViewHolder2(View itemView) {
            super(itemView);
            top_news_name1 = (TextView) itemView.findViewById(R.id.top_news_name1);
            top_news_author_name1 = (TextView) itemView.findViewById(R.id.top_news_author_name1);
            top_news_layout1=(LinearLayout) itemView.findViewById(R.id.top_news_layout1);
        }
    }
}
