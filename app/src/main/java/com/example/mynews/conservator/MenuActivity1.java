package com.example.mynews.conservator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.conservator.fragment.bottom.Fragment_mine;
import com.example.mynews.conservator.fragment.bottom.manager.Fragment_manager;
import com.example.mynews.util.HideStatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity1 extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    private LinearLayout btn1;
    private TextView tv1;
    private ImageView imgmanager;
    private LinearLayout btn2;
    private TextView tv2;
    private ImageView imgmine;



    int it = 0;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        HideStatusBarUtils.getStatusBarHeight(MenuActivity1.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStatusBarUtils.ExtendImage(MenuActivity1.this);
        setContentView(R.layout.activity_menu1);

        vp = findViewById(R.id.vp_vp1);
        btn1 = findViewById(R.id.ly_btn1);
        tv1 = findViewById(R.id.tv_manager);
        imgmanager = findViewById(R.id.img_manager);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.ly_btn2);
        tv2 = findViewById(R.id.tv_mine);
        imgmine = findViewById(R.id.img_mine);
        btn2.setOnClickListener(this);

        fragments.add(new Fragment_manager());
        fragments.add(new Fragment_mine());

        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        changelayout(it);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        it=0;
                        break;
                    case 1:
                        it=1;
                        break;
                }
                changelayout(it);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void changelayout(int it) {
        tv1.setSelected(it == 0);
        imgmanager.setSelected(it == 0);

        tv2.setSelected(it == 1);
        imgmine.setSelected(it == 1);

        it = it;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_btn1: {
                vp.setCurrentItem(0);
                break;
            }
            case R.id.ly_btn2: {
                vp.setCurrentItem(1);
                break;
            }
        }
    }

    class MyPageAdapter extends FragmentPagerAdapter {
        public MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}