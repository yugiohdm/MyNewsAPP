package com.example.mynews.fragment.bottom;

import static com.example.mynews.db.ReleaseDBManager.getSelectedReleaseList;
import static com.example.mynews.db.ReleaseDBManager.initReleaseDB1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mynews.AddItemActivity;

import com.example.mynews.R;
import com.example.mynews.SearchActivity;

import com.example.mynews.adapter.InfoAdapter;
import com.example.mynews.bean.FragmentData;
import com.example.mynews.bean.ReleaseModel;
import com.example.mynews.bean.Releasenews;
import com.example.mynews.bean.TypeBean;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.db.PageDBManager;

import com.example.mynews.db.ReleaseDBManager;
import com.example.mynews.view.PagerSlidingTabStrip;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fragment_mainframe extends Fragment{//主页

    ViewPager mainVp;
    PagerSlidingTabStrip tabStrip;
    ImageView addIv;
    List<Fragment>   fragmentList = new ArrayList<>();//viewpager所显示的内容
    List<TypeBean>  selectTypeList =new ArrayList<>();//所选中的类型的集合
    private InfoAdapter infoAdapter;
    private View view=null;
    private SwipeRefreshLayout swipeRefreshLayout;


    private EditText editText;

    int  type_login;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_mainframe, container, false);
            mainVp = view.findViewById(R.id.vp_FindFragment_pager);
            tabStrip = view.findViewById(R.id.fragment_tabstrip);
            addIv = view.findViewById(R.id.main_iv_add);

            LogicBackground.GetReleaseNewsByState(getContext());
            LogicBackground.GetUsers(getContext());
            initControls();

            //        创建适配器对象
            infoAdapter = new InfoAdapter(getActivity().getSupportFragmentManager(), getContext(), fragmentList, selectTypeList);
            //        设置适配器
            mainVp.setAdapter(infoAdapter);
            //      关联TabStrip和ViewPager
            tabStrip.setViewPager(mainVp);


            editText = view.findViewById(R.id.search);
            Drawable drawable=getResources().getDrawable(R.drawable.search);
            drawable.setBounds(0,0,55,55);
            editText.setCompoundDrawables(drawable,null,null,null);

            addIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AddItemActivity.class);
                    startActivity(intent);
                }
            });  //添加点击事件的监听


            editText.setFocusable(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), SearchActivity.class).putExtra("logintype",type_login));
                }
            });
        }
        return  view;
    }


    private void initControls() {
//        tabStrip.setTypeface(null,TabLayout.MODE_FIXED);

        /* 初始化页面的函数*/

        PageDBManager.initPageDB(getContext());
        List<TypeBean> typeList = PageDBManager.getSelectedTypeList();
        List<FragmentData> fragments1 = FragmentData.getFragmentList();
//        List<TypeBean> typeList = TypeBean.getTypeList();
        List<Fragment> fragments=new ArrayList<>();


        for(int j=0;j<typeList.size();j++) {
            for (int i = 0; i < fragments1.size(); i++) {
                if (typeList.get(j).getTitle().equals(fragments1.get(i).getTitle())) {
                    fragments.add(fragments1.get(i).getFragment());
                    Bundle bundle=new Bundle();
                    bundle.putString("type",fragments1.get(i).getTitle());
                    fragments1.get(i).getFragment().setArguments(bundle);
               }
           }
        }


        selectTypeList.addAll(typeList);

        //将fragment装进列表中
        fragmentList.addAll(fragments);



    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
