package com.example.mynews.conservator.fragment.bottom.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mynews.R;
import com.example.mynews.adapter.TabAdapter;
import com.example.mynews.bean.TypeBean;
import com.example.mynews.view.MyViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Fragment_manager extends Fragment {
    private View view=null;
    private TabLayout tab_Fragment_manager_title;                            //定义TabLayout
    private MyViewPager vp_Fragment_manager_pager;                             //定义viewPager
    private FragmentPagerAdapter fadapter;

    private LinearLayout statusbar_layout;

    Fragment_manager_news fragment_manager_news;
    Fragment_manager_users fragment_manager_users;

    List<Fragment> fragmentList = new ArrayList<>();//viewpager所显示的内容

    List<TypeBean>  selectTypeList =new ArrayList<>();//所选中的类型的集合

    private SharedPreferences sharedPreferences;

    int statusHeight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_manager, container, false);
            tab_Fragment_manager_title = view.findViewById(R.id.tab_Fragment_manager_title);
            vp_Fragment_manager_pager = view.findViewById(R.id.vp_Fragment_manager_pager);
            statusbar_layout=view.findViewById(R.id.statusbar_layout);

            sharedPreferences=getActivity().getSharedPreferences("height", Context.MODE_PRIVATE);
            statusHeight=sharedPreferences.getInt("statrsheight",0);
            ViewGroup.LayoutParams lp;
            lp=statusbar_layout.getLayoutParams();
            lp.height=statusHeight;
            statusbar_layout.setLayoutParams(lp);


            initControls();
            fadapter = new TabAdapter(getActivity().getSupportFragmentManager(),fragmentList,selectTypeList);
            vp_Fragment_manager_pager.setAdapter(fadapter);
            //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
            //TabLayout加载viewpager
            tab_Fragment_manager_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            tab_Fragment_manager_title.setupWithViewPager(vp_Fragment_manager_pager);

        }
        return view;
    }

    private void initControls() {

        /* 初始化页面的函数*/
        List<TypeBean> typeList = TypeBean.getManagerTypeList();

        fragment_manager_news=new Fragment_manager_news();
        fragment_manager_users=new Fragment_manager_users();

        //设置TabLayout的模式
        tab_Fragment_manager_title.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        for(int i=0;i<typeList.size();i++) {
            tab_Fragment_manager_title.addTab( tab_Fragment_manager_title.newTab().setText(typeList.get(i).getTitle()));
        }

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(fragment_manager_news);
        fragments.add(fragment_manager_users);


        selectTypeList.addAll(typeList);

        //将fragment装进列表中
        fragmentList.addAll(fragments);
    }




}