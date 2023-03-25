package com.example.mynews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.bean.TypeBean;

import java.util.List;

public class AddItemAdapter extends BaseAdapter{
    Context context;
    List<TypeBean>mDatas;

    public AddItemAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_add_lv,null);
//        初始化convertview当中的控件
        TextView nameTv = convertView.findViewById(R.id.item_add_tv);
        final ImageView iv = convertView.findViewById(R.id.item_add_iv);
//        获取指定位置的数据
        final TypeBean typeBean = mDatas.get(position);
        nameTv.setText(typeBean.getTitle());
        if (typeBean.isShow()) {
            iv.setImageResource(R.drawable.subscribe_checked);
            nameTv.setTextColor(Color.parseColor("#F44336"));
        }else {
            iv.setImageResource(R.drawable.subscribe_unchecked);
        }
//        为了避免用户全都不选中的情况，不符合新闻的展示习惯，所以前两个的显示不需要用户选择，每次必须展示
        if (position == 0 ||position == 1) {
            iv.setVisibility(View.INVISIBLE);
        }else {
            iv.setVisibility(View.VISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    typeBean.setShow(!typeBean.isShow());  //改变选中状态
                    if (typeBean.isShow()) {
                        iv.setImageResource(R.drawable.subscribe_checked);
                        nameTv.setTextColor(Color.parseColor("#F44336"));
                    } else {
                        iv.setImageResource(R.drawable.subscribe_unchecked);
                        nameTv.setTextColor(Color.parseColor("#aaaaaa"));
                    }
                }
            });
        }
        return convertView;
    }
}
