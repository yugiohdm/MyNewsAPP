package com.example.mynews.activity.mytool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mynews.R;
import com.example.mynews.adapter.InfoAdapter;
import com.example.mynews.adapter.TabAdapter;
import com.example.mynews.bean.TypeBean;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;
import com.example.mynews.view.PagerSlidingTabStrip;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ToolContentActivity extends AppCompatActivity {

    private TabLayout tab_Fragment_title;                            //定义TabLayout
    private ViewPager vp_Fragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fadapter;

    private EditText editText;

    CollectFragment collectFragment;
    LikeFragment likeFragment;

    HistoryFragment historyFragment;

    List<Fragment> fragmentList = new ArrayList<>();//viewpager所显示的内容

    List<TypeBean>  selectTypeList =new ArrayList<>();//所选中的类型的集合

    private ImageView goback_tool_content_activity;

    private SharedPreferences preferences;
    boolean iChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_content);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(ToolContentActivity.this,R.color.white,iChecked);

        tab_Fragment_title = findViewById(R.id.tab_Fragment_title);
        vp_Fragment_pager = findViewById(R.id.vp_Fragment_pager);
        goback_tool_content_activity=findViewById(R.id.goback_tool_content_activity);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        initControls();

        fadapter = new TabAdapter(getSupportFragmentManager(),fragmentList,selectTypeList);
        vp_Fragment_pager.setAdapter(fadapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_Fragment_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabTextView(tab,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabTextView(tab,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_Fragment_title.setupWithViewPager(vp_Fragment_pager);

        editText = findViewById(R.id.search1);
        Drawable drawable=getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0,0,55,55);
        editText.setCompoundDrawables(drawable,null,null,null);

        goback_tool_content_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int typepage=getIntent().getIntExtra("tooltype",0);
        vp_Fragment_pager.setCurrentItem(typepage
        );
    }


    private void initControls() {

        /* 初始化页面的函数*/
        List<TypeBean> typeList = TypeBean.getToolTypeList();

        collectFragment=new CollectFragment();
        likeFragment=new LikeFragment();
        historyFragment=new HistoryFragment();


        //设置TabLayout的模式
        tab_Fragment_title.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        for(int i=0;i<typeList.size();i++) {
            tab_Fragment_title.addTab(tab_Fragment_title.newTab().setText(typeList.get(i).getTitle()));
        }

        List<Fragment> fragments=new ArrayList<>();
         fragments.add(collectFragment);
         fragments.add(likeFragment);
         fragments.add(historyFragment);


        selectTypeList.addAll(typeList);

        //将fragment装进列表中
        fragmentList.addAll(fragments);
    }

    public void changeTabTextView(TabLayout.Tab tab,boolean isBold){
          if (tab == null || tab.getText() == null) {
             return;
          }

        if (isBold){
            // 选中字体加粗
            String trim = tab.getText().toString().trim();
            Log.i("bold", trim);
        }else{
            // 选中字体不加粗
            String trim = tab.getText().toString().trim();
            Log.i("bold1", trim);
        }
    }
}