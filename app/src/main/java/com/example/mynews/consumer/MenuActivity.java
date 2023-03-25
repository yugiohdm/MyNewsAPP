package com.example.mynews.consumer;

import static com.example.mynews.util.PhotoUtil.getRealPathFromURI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mynews.R;
import com.example.mynews.conservator.MenuActivity1;
import com.example.mynews.fragment.bottom.Fragment_mainframe;
import com.example.mynews.consumer.fragment.buttom.my.Fragment_my;
import com.example.mynews.consumer.fragment.bottom.Fragment_publish;
import com.example.mynews.util.HideStatusBarUtils;
import com.example.mynews.util.PhotoUtil;
import com.example.mynews.util.SetLuminanceUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    BottomNavigationView mNavigationView;
    Fragment_mainframe fragment_mainframe=new Fragment_mainframe();
    Fragment_publish fragment_publish=new Fragment_publish();
    Fragment_my fragment_my=new Fragment_my();

    private SharedPreferences preferences;

    PhotoUtil photoUtil=new PhotoUtil();

    @Override
    protected void onStart() {
        super.onStart();
        HideStatusBarUtils.getStatusBarHeight(MenuActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();

        setPage();
    }


    private void init() {
                //获取页面标签对象
                viewPager = findViewById(R.id.viewPager);
                viewPager.addOnPageChangeListener(this);
                mNavigationView = findViewById(R.id.navigation);
                mNavigationView.setOnNavigationItemSelectedListener(this);

                FragmentManager fragmentManager=getSupportFragmentManager();

                //页面切换
                viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                    @NonNull
                    @Override
                    public Fragment getItem(int position) {
                        switch (position){
                            case 0:
                                return  fragment_mainframe;
                            case 1:
                                return  fragment_publish;
                            case 2:
                                return  fragment_my;
                        }
                        return null;
                    }
                    @Override
                    public int getCount() {
                        return 3;
                    }


                });


            }



            //实现接口的相关方法  implements上面两个方法后 alt+enter就会弹出这些接口，直接回车实现他们
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                viewPager.setCurrentItem(menuItem.getOrder());
                return true;
            }





    private void setPage() {
          preferences=getSharedPreferences("page", Context.MODE_PRIVATE);
          int page=preferences.getInt("index",0);
          mNavigationView.getMenu().getItem(page).setChecked(true);
          viewPager.setCurrentItem(page);
      }


    @Override
    protected void onResume() {
        super.onResume();

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(MenuActivity.this,brightness);


    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoUtil.PHOTO_REQUEST_GALLERY) {
            Uri uri = data.getData();
            String path = getRealPathFromURI(uri,MenuActivity.this);
            Log.i("path-------------------", "" + path);
            photoUtil.initaddImageView(MenuActivity.this);
            photoUtil.uploadFile(path, MenuActivity.this);
        }

    }

}



