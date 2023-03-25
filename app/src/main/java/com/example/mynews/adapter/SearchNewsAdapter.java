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

public class SearchNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Categoryitem> newsList =new ArrayList<>();
    private Context context;

    private SharedPreferences sharedPreferences;


    public SearchNewsAdapter(List<Categoryitem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }


    public List<Categoryitem>  getBl(){
        return newsList;
    }


    @Override
    public int getItemViewType(int position) {
        if(newsList.size()==0){
            return -1;
        }else {
            return newsList.get(position).getType();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==-1){
            return new SearchNewsNoContentViewHolder(LayoutInflater.from(context).inflate(R.layout.nonews_item,parent,false));
        }else if (viewType==Categoryitem.First_TYPE) {
            return new SearchNewsViewHolder1(LayoutInflater.from(context).inflate(R.layout.news_item1, parent, false));
        }else if(viewType==Categoryitem.SECOND_TYPE){
            return new SearchNewsViewHolder2(LayoutInflater.from(context).inflate(R.layout.news_item2, parent, false));
        }else if(viewType==Categoryitem.THIRD_TYPE){
            return new SearchNewsViewHolder3(LayoutInflater.from(context).inflate(R.layout.news_item3, parent, false));
        }else{
            return new SearchNewsViewHolder4(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case -1:
                SearchNewsNoContentViewHolder searchNewsNoContentViewHolder= (SearchNewsNoContentViewHolder) holder;
                break;
            case Categoryitem.First_TYPE:
                SearchNewsViewHolder1 searchNewsViewHolder1 = (SearchNewsViewHolder1) holder;
                searchNewsViewHolder1.news_name1.setText(newsList.get(position).getCategorynews().getTitle());
                searchNewsViewHolder1.news_author_name1.setText(newsList.get(position).getCategorynews().getAuthor_name());
                searchNewsViewHolder1.news_date1.setText(newsList.get(position).getCategorynews().getDate());
                ViewClickListener2(((SearchNewsViewHolder1) holder).news_name1,((SearchNewsViewHolder1) holder).news_layout1,((SearchNewsViewHolder1) holder).news_layout1_1,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.SECOND_TYPE:
                SearchNewsViewHolder2 searchNewsViewHolder2 = (SearchNewsViewHolder2) holder;
                searchNewsViewHolder2.news_name2.setText(newsList.get(position).getCategorynews().getTitle());
                searchNewsViewHolder2.news_author_name2.setText(newsList.get(position).getCategorynews().getAuthor_name());
                searchNewsViewHolder2.news_date2.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(searchNewsViewHolder2.news_image1_1);
                ViewClickListener3(((SearchNewsViewHolder2) holder).news_name2,((SearchNewsViewHolder2) holder).news_layout2,((SearchNewsViewHolder2) holder).news_layout2_1,((SearchNewsViewHolder2) holder).news_layout2_2,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.THIRD_TYPE:
                SearchNewsViewHolder3 searchNewsViewHolder3 = (SearchNewsViewHolder3) holder;
                searchNewsViewHolder3.news_name3.setText(newsList.get(position).getCategorynews().getTitle());
                searchNewsViewHolder3.news_author_name3.setText(newsList.get(position).getCategorynews().getAuthor_name());
                searchNewsViewHolder3.news_date3.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(searchNewsViewHolder3.news_image1_2);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(searchNewsViewHolder3.news_image2_2);
                ViewClickListener2(((SearchNewsViewHolder3) holder).news_name3,((SearchNewsViewHolder3) holder).news_layout3,((SearchNewsViewHolder3) holder).news_layout3_1,newsList.get(position).getCategorynews());
                break;
            case Categoryitem.FOURTH_TYPE:
                SearchNewsViewHolder4 searchNewsViewHolder4 = (SearchNewsViewHolder4) holder;
                searchNewsViewHolder4.news_name.setText(newsList.get(position).getCategorynews().getTitle());
                searchNewsViewHolder4.news_author_name.setText(newsList.get(position).getCategorynews().getAuthor_name());
                searchNewsViewHolder4.news_date.setText(newsList.get(position).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s()).into(searchNewsViewHolder4.news_image1);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s02()).into(searchNewsViewHolder4.news_image2);
                Glide.with(context).load(newsList.get(position).getCategorynews().getThumbnail_pic_s03()).into(searchNewsViewHolder4.news_image3);
                ViewClickListener2(((SearchNewsViewHolder4) holder).news_name,((SearchNewsViewHolder4) holder).news_layout,((SearchNewsViewHolder4) holder).news_layout_1,newsList.get(position).getCategorynews());
                break;
        }
    }


    public void RemoveData(){
        newsList.clear();
        notifyDataSetChanged();
    }

    public void ViewClickListener1(ImageView imageview){
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    @Override
    public int getItemCount() {
        return newsList.size()==0?1:newsList.size();
    }



    public void setHistory(News news, int uid){
        News1 news1=new News1(news.getTitle(),news.getDate(),news.getAuthor_name(),news.getUrl(),news.getThumbnail_pic_s(),news.getThumbnail_pic_s02(),news.getThumbnail_pic_s03());
        news1.setUid(uid);
        news1.setUniquekey(news.getUniquekey());
        HistoryDBManager.initHistoryDB(context);
        HistoryDBManager.SaveHistoryList(news1);
    }

    class SearchNewsNoContentViewHolder extends RecyclerView.ViewHolder{
         TextView nonews_content;
        public SearchNewsNoContentViewHolder(@NonNull View itemView) {
            super(itemView);
            nonews_content=itemView.findViewById(R.id.nonews_content);
        }
    }


    class SearchNewsViewHolder1 extends  RecyclerView.ViewHolder{
        TextView news_name1;
        TextView news_date1;
        TextView news_author_name1;
        LinearLayout news_layout1;
        ImageView news_cross1;

        LinearLayout news_layout1_1;

        public SearchNewsViewHolder1(View itemView) {
            super(itemView);
            news_name1 = (TextView) itemView.findViewById(R.id.news_name1);
            news_date1 = (TextView) itemView.findViewById(R.id.news_date1);
            news_author_name1 = (TextView) itemView.findViewById(R.id.news_author_name1);
            news_layout1=(LinearLayout) itemView.findViewById(R.id.news_layout1);
            news_cross1=(ImageView) itemView.findViewById(R.id.news_cross1);
            news_layout1_1=(LinearLayout) itemView.findViewById(R.id.news_layout1_1);
        }
    }

    class SearchNewsViewHolder2 extends  RecyclerView.ViewHolder{
        TextView news_name2;
        TextView news_date2;
        TextView news_author_name2;
        ImageView news_image1_1;
        LinearLayout news_layout2;
        ImageView news_cross2;

        LinearLayout news_layout2_1;

        LinearLayout news_layout2_2;

        public SearchNewsViewHolder2(View itemView) {
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

    class SearchNewsViewHolder3 extends  RecyclerView.ViewHolder{
        TextView news_name3;
        TextView news_date3;
        TextView news_author_name3;
        ImageView news_image1_2;
        ImageView news_image2_2;
        LinearLayout news_layout3;
        ImageView news_cross3;

        LinearLayout news_layout3_1;

        public SearchNewsViewHolder3(View itemView) {
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

    class SearchNewsViewHolder4 extends  RecyclerView.ViewHolder{
        TextView news_name;
        TextView news_date;
        TextView news_author_name;
        ImageView news_image1;
        ImageView news_image2;
        ImageView news_image3;
        LinearLayout news_layout;
        ImageView news_cross;
        LinearLayout news_layout_1;

        public SearchNewsViewHolder4(View itemView) {
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
