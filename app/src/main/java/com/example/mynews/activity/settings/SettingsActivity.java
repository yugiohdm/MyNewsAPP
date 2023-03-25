package com.example.mynews.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynews.R;
import com.example.mynews.util.StatusBarUtils;

public class SettingsActivity extends AppCompatActivity {
    private ImageView goback_setting_activity;
    String settype;
    private  SharedPreferences preferences;

    boolean iChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(SettingsActivity.this,R.color.white,iChecked);
        setContentView(R.layout.activity_settings);
        //设置状态栏文字颜色及图标为深色
        goback_setting_activity=findViewById(R.id.goback_setting_activity);
        goback_setting_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences=getSharedPreferences("page", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putInt("index",2);
                editor1.commit();

                finish();
            }
        });


        settype=getIntent().getStringExtra("set");
        if (settype.equals("aa")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new Fragment_settings_log_out())
                    .commit();
        }else if(settype.equals("bb")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new Fragment_settings_have_logged_on())
                    .commit();
        }



    }

}