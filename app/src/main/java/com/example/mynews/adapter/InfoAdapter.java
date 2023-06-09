package com.example.mynews.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mynews.bean.TypeBean;

import java.util.List;

public class InfoAdapter extends FragmentStatePagerAdapter {
    Context context;
    List<Fragment>fragmentList;  //viewpager每个页面展示的fragment的集合
    List<TypeBean>typeBeanList;
    public InfoAdapter(FragmentManager fm, Context context, List<Fragment>fragmentList, List<TypeBean>typeBeanList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.typeBeanList = typeBeanList;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
// 返回指定位置的标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        TypeBean typeBean = typeBeanList.get(position);
        String title = typeBean.getTitle();
        return title;
    }
}
