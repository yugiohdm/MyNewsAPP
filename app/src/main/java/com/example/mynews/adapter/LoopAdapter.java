package com.example.mynews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mynews.R;
import com.example.mynews.bean.Ads;
import com.example.mynews.view.RollPagerView;


import java.util.List;

/**
 * 图片无限轮播的RollPagerView适配器
 */
public class LoopAdapter extends LoopPagerAdapter {

    private List<Ads> adsList;

    public LoopAdapter(RollPagerView viewPager, List<Ads> list) {
        super(viewPager);
        this.adsList=list;
    }



    @Override
    public View getView(final ViewGroup container, final int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_rollpagerveiw, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_rpv);
        imageView.setBackgroundResource(R.drawable.loading);
        Glide.with(container.getContext()).load(adsList.get(position).imgsrc).into(imageView);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView tv_rpv = (TextView) view.findViewById(R.id.tv_rpv);
        tv_rpv.setText(adsList.get(position).title);
        return view;
    }

    @Override
    public int getRealCount() {
        return adsList.size();
    }
}
