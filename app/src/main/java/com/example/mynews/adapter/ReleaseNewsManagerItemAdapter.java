package com.example.mynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.Releasenews;

import java.util.ArrayList;
import java.util.List;

public class ReleaseNewsManagerItemAdapter extends BaseAdapter {
    Context context;
    List<Releasenews> releasenewsList=new ArrayList<>();

    public ReleaseNewsManagerItemAdapter(Context context, List<Releasenews> releasenewsList) {
        this.context = context;
        this.releasenewsList = releasenewsList;
    }

    @Override
    public int getCount() {
        return releasenewsList.size();
    }

    @Override
    public Object getItem(int i) {
        return releasenewsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ReleaseNewsViewHolder releaseNewsViewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.releasenews_manager_item, null);
            releaseNewsViewHolder = new ReleaseNewsViewHolder();
            releaseNewsViewHolder.releasenews_manager_content = view.findViewById(R.id.releasenews_manager_content);
            releaseNewsViewHolder.releasenews_manager_date = view.findViewById(R.id.releasenews_manager_date);
            releaseNewsViewHolder.releasenews_manager_state= view.findViewById(R.id.releasenews_manager_state);
            releaseNewsViewHolder.releasenews_manager_uid = view.findViewById(R.id.releasenews_manager_uid);

            view.setTag(releaseNewsViewHolder);
        }else {
            releaseNewsViewHolder= (ReleaseNewsViewHolder) view.getTag();
        }



        releaseNewsViewHolder.releasenews_manager_content.setText("用户新闻内容："+releasenewsList.get(i).getNewscontent());
        releaseNewsViewHolder.releasenews_manager_date.setText("时间：" + releasenewsList.get(i).getReleasedate());
        releaseNewsViewHolder.releasenews_manager_state.setText("审核状态：" + releasenewsList.get(i).getState());
        releaseNewsViewHolder.releasenews_manager_uid.setText("发布人ID：" + releasenewsList.get(i).getUid());

        return view;
    }

    public void removeData(){
        releasenewsList.clear();
        notifyDataSetChanged();
    }


    public void removeItem(int i){
        releasenewsList.remove(i);
        notifyDataSetChanged();
    }

    class ReleaseNewsViewHolder{
        private TextView releasenews_manager_content;
        private TextView releasenews_manager_date;
        private TextView releasenews_manager_state;
        private TextView releasenews_manager_uid;
    }
}
