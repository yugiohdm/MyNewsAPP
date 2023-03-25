package com.example.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.News;
import com.example.mynews.bean.News1;
import com.example.mynews.db.HistoryDBManager;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Categoryitem> newsList =new ArrayList<>();
    private Context context;

    private SharedPreferences sharedPreferences;

    public NewsAdapter2(List<Categoryitem> newsList, Context context) {
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
        }else{
            return new NewsViewHolder4(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        }
    }

    public void ViewClickListener1(ImageView imageview){
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void ViewClickListener2(TextView textview,LinearLayout layout1, LinearLayout layout2,News news){
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

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setHistory(News news,int uid){
        News1 news1=new News1(news.getTitle(),news.getDate(),news.getAuthor_name(),news.getUrl(),news.getThumbnail_pic_s(),news.getThumbnail_pic_s02(),news.getThumbnail_pic_s03());
        news1.setUid(uid);
        news1.setUniquekey(news.getUniquekey());
        HistoryDBManager.initHistoryDB(context);
        HistoryDBManager.SaveHistoryList(news1);
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
}
