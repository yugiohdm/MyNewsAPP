package com.example.mynews;

import static com.example.mynews.util.HideStatusBarUtils.hideStatusBar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mynews.conservator.MenuActivity1;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.util.StringUtils;

public class MainActivity extends AppCompatActivity implements Runnable{
    private ImageView mIvWelcomeBg;
    //判断是否为第一次进入App
    private boolean firstInstall;

    String type;
    int[] images = {R.drawable.news_day1, R.drawable.news_day2, R.drawable.news_day3, R.drawable.news_day4,
            R.drawable.news_day5, R.drawable.news_day6, R.drawable.news_day7};
    int number = StringUtils.getRandomNumber(0, images.length - 1);

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(MainActivity.this);
        setContentView(R.layout.activity_main);
        mIvWelcomeBg=findViewById(R.id.iv_main_bg);
        mIvWelcomeBg.setImageResource(images[number]);
        //设置启动动画
        mIvWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
        new Thread(this).start();
    }

    /**
     * 是否为第一次安装
     */
    private void isFirstInstall() {
        SharedPreferences sharedPreferences = getSharedPreferences("flag", MODE_PRIVATE);
        firstInstall = sharedPreferences.getBoolean("first", true);
        // 判断是否首次安装
        if (firstInstall) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("first", false);
            edit.apply();
        }
    }

    private void statusbarType(){
        SharedPreferences preferences= getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("isChecked",false);
        editor.putInt("brightness",-1);
        editor.commit();
    }


    private void SetPage(){
        SharedPreferences preferences= getSharedPreferences("page", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("index",0);
        editor.commit();
    }

    private void loginType(){
        SharedPreferences sharedPreferences = getSharedPreferences("user1", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String username=sharedPreferences.getString("username","");
        String phone=sharedPreferences.getString("phone","");
        String email=sharedPreferences.getString("email","");
        String password=sharedPreferences.getString("password","");
        type=sharedPreferences.getString("type","");
        if((username.equals("")||phone.equals("")||email.equals(""))&&password.equals("")){
            sharedPreferences.getInt("logintype",0);
            edit.commit();
        }else{
            edit.putInt("logintype",1);
            edit.commit();
        }
    }
    @Override
    public void run() {
        //判断app是否为第一次安装
        isFirstInstall();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //跳转界面
        if (firstInstall) {
            statusbarType();
            SetPage();
            loginType();
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        }
        else{
            statusbarType();
            SetPage();
            loginType();
            if(type.equals("普通用户")) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }else if(type.equals("管理员")){
                startActivity(new Intent(MainActivity.this, MenuActivity1.class));
            }else{
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
            finish();
        }
    }
}