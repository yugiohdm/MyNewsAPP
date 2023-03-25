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
import android.widget.RelativeLayout;
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

public class NewsAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Categoryitem> newsList=new ArrayList<>();
    private Context context;
    private View headerView;

    private SharedPreferences sharedPreferences;

    public NewsAdapter3(List<Categoryitem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }


    public  void setHeaderView(View headerView){
        this.headerView=headerView;
        notifyItemChanged(0);
    }

    public List<Categoryitem>  getBl(){
        return newsList;
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView==null){
            return newsList.get(position).getType();
        }
        else {
            if (position == 0) return Categoryitem.First_TYPE;
            return newsList.get(position-1).getType();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(headerView!=null&&viewType==Categoryitem.First_TYPE){
            return new ViewHolderNormal(headerView);
        }
        if (viewType==Categoryitem.SECOND_TYPE){
            return  new NewsViewHolder1(LayoutInflater.from(context).inflate(R.layout.news_item1,parent,false));
        }
        if (viewType==Categoryitem.THIRD_TYPE){
            return  new NewsViewHolder2(LayoutInflater.from(context).inflate(R.layout.news_item2,parent,false));
        }
        if (viewType==Categoryitem.FOURTH_TYPE){
            return  new NewsViewHolder3(LayoutInflater.from(context).inflate(R.layout.news_item3,parent,false));
        }else if(viewType==Categoryitem.FIFTH_TYPE){
            return  new NewsViewHolder4(LayoutInflater.from(context).inflate(R.layout.news_item,parent,false));
        }else if (viewType==Categoryitem.SIXTH_TYPE){
            return  new NewsViewHolder5(LayoutInflater.from(context).inflate(R.layout.released_new_item,parent,false));
        }else{
            return  new NewsViewHolder6(LayoutInflater.from(context).inflate(R.layout.released_new_item1,parent,false));
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        return headerView == null ? position : position - 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos=getRealPosition(holder);
        switch (getItemViewType(position)) {
            case Categoryitem.First_TYPE:
                break;
            case Categoryitem.SECOND_TYPE:
                NewsViewHolder1 newsViewHolder1 = (NewsViewHolder1) holder;
                newsViewHolder1.news_name1.setText(newsList.get(pos).getCategorynews().getTitle());
                newsViewHolder1.news_author_name1.setText(newsList.get(pos).getCategorynews().getAuthor_name());
                newsViewHolder1.news_date1.setText(newsList.get(pos).getCategorynews().getDate());
                ViewClickListener2(((NewsViewHolder1) holder).news_name1,((NewsViewHolder1) holder).news_layout1,((NewsViewHolder1) holder).news_layout1_1,newsList.get(pos).getCategorynews());
                break;
            case Categoryitem.THIRD_TYPE:
                NewsViewHolder2 newsViewHolder2 = (NewsViewHolder2) holder;
                newsViewHolder2.news_name2.setText(newsList.get(pos).getCategorynews().getTitle());
                newsViewHolder2.news_author_name2.setText(newsList.get(pos).getCategorynews().getAuthor_name());
                newsViewHolder2.news_date2.setText(newsList.get(pos).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder2.news_image1_1);
                ViewClickListener3(((NewsViewHolder2) holder).news_name2,((NewsViewHolder2) holder).news_layout2,((NewsViewHolder2) holder).news_layout2_1,((NewsViewHolder2) holder).news_layout2_2,newsList.get(pos).getCategorynews());
                break;
            case Categoryitem.FOURTH_TYPE:
                NewsViewHolder3 newsViewHolder3 = (NewsViewHolder3) holder;
                newsViewHolder3.news_name3.setText(newsList.get(pos).getCategorynews().getTitle());
                newsViewHolder3.news_author_name3.setText(newsList.get(pos).getCategorynews().getAuthor_name());
                newsViewHolder3.news_date3.setText(newsList.get(pos).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder3.news_image1_2);
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s02()).into(newsViewHolder3.news_image2_2);
                ViewClickListener2(((NewsViewHolder3) holder).news_name3,((NewsViewHolder3) holder).news_layout3,((NewsViewHolder3) holder).news_layout3_1,newsList.get(pos).getCategorynews());
                break;
            case Categoryitem.FIFTH_TYPE:
                NewsViewHolder4 newsViewHolder4 = (NewsViewHolder4) holder;
                newsViewHolder4.news_name.setText(newsList.get(pos).getCategorynews().getTitle());
                newsViewHolder4.news_author_name.setText(newsList.get(pos).getCategorynews().getAuthor_name());
                newsViewHolder4.news_date.setText(newsList.get(pos).getCategorynews().getDate());
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s()).into(newsViewHolder4.news_image1);
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s02()).into(newsViewHolder4.news_image2);
                Glide.with(context).load(newsList.get(pos).getCategorynews().getThumbnail_pic_s03()).into(newsViewHolder4.news_image3);
                ViewClickListener2(((NewsViewHolder4) holder).news_name,((NewsViewHolder4) holder).news_layout,((NewsViewHolder4) holder).news_layout_1,newsList.get(pos).getCategorynews());
                break;

            case Categoryitem.SIXTH_TYPE:
                NewsViewHolder5 newsViewHolder5 = (NewsViewHolder5) holder;
//                newsViewHolder5.released_username.setText(newsList.get(position).getCategorynews1().getUsername());
                newsViewHolder5.released_date.setText(newsList.get(position).getCategorynews1().getReleasedate());
                newsViewHolder5.released_content.setText(newsList.get(position).getCategorynews1().getNewscontent());
                newsViewHolder5.released_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                newsViewHolder5.released_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case Categoryitem.SEVENTH_TYPE:
                NewsViewHolder6 newsViewHolder6 = (NewsViewHolder6) holder;
//                newsViewHolder6.released_username1.setText(newsList.get(position).getCategorynews1().getUsername());
                newsViewHolder6.released_date1.setText(newsList.get(position).getCategorynews1().getReleasedate());
                newsViewHolder6.released_title.setText(newsList.get(position).getCategorynews1().getNewstitle());
                newsViewHolder6.released_content1.setText(newsList.get(position).getCategorynews1().getNewscontent());
                newsViewHolder6.released_collect1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                newsViewHolder6.released_like1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return headerView == null ? newsList.size() : newsList.size() + 1;
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


    public void setHistory(News news,int uid){
        News1 news1=new News1(news.getTitle(),news.getDate(),news.getAuthor_name(),news.getUrl(),news.getThumbnail_pic_s(),news.getThumbnail_pic_s02(),news.getThumbnail_pic_s03());
        news1.setUid(uid);
        news1.setUniquekey(news.getUniquekey());
        HistoryDBManager.initHistoryDB(context);
        HistoryDBManager.SaveHistoryList(news1);
    }


    public class ViewHolderNormal extends RecyclerView.ViewHolder {
        private ImageView iv_item_news_detail_rv;
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_zan;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            if (itemView == headerView) return;
            iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.iv_item_news_detail_rv);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_news_detail);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author_news_detail);
            tv_zan = (TextView) itemView.findViewById(R.id.tv_zan_news_detail);
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
