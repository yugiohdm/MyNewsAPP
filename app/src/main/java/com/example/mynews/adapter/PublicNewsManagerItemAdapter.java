package com.example.mynews.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.User;

import java.util.ArrayList;
import java.util.List;

public class PublicNewsManagerItemAdapter extends BaseAdapter {
    Context context;
    List<News2> news2List=new ArrayList<>();


    public PublicNewsManagerItemAdapter(Context context, List<News2> news2List) {
        this.context = context;
        this.news2List = news2List;
    }

    @Override
    public int getCount() {
        return news2List.size();
    }

    @Override
    public Object getItem(int i) {
        return news2List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PublicNewsViewHolder publicNewsViewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.publicnews_manager_item, null);
            publicNewsViewHolder = new PublicNewsViewHolder();
            publicNewsViewHolder.publicnews_manager_title = view.findViewById(R.id.publicnews_manager_title);
            publicNewsViewHolder.publicnews_manager_date = view.findViewById(R.id.publicnews_manager_date);
            publicNewsViewHolder.publicnews_manager_category = view.findViewById(R.id.publicnews_manager_category);
            publicNewsViewHolder.publicnews_manager_author = view.findViewById(R.id.publicnews_manager_author);

            view.setTag(publicNewsViewHolder);
        }else{
            publicNewsViewHolder= (PublicNewsViewHolder) view.getTag();
        }

        publicNewsViewHolder.publicnews_manager_title.setText("新闻名称：" + news2List.get(i).getTitle());
        publicNewsViewHolder.publicnews_manager_date.setText("时间：" + news2List.get(i).getDate());
        publicNewsViewHolder.publicnews_manager_category.setText("新闻类型：" + news2List.get(i).getCategory());
        publicNewsViewHolder.publicnews_manager_author.setText("作者：" + news2List.get(i).getAuthorName());
        return view;
    }

    public void removeData(){
        news2List.clear();
        notifyDataSetChanged();
    }


    public void removeItem(int i){
        news2List.remove(i);
        notifyDataSetChanged();
    }



    class PublicNewsViewHolder{
        private TextView publicnews_manager_title;
        private TextView publicnews_manager_date;
        private TextView publicnews_manager_category;
        private TextView publicnews_manager_author;
    }
}
