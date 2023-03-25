package com.example.mynews;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynews.adapter.AddItemAdapter;
import com.example.mynews.bean.TypeBean;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.db.PageDBManager;
import com.example.mynews.util.SetLuminanceUtil;

import java.util.List;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView backIv;
    ListView addLv;
// 数据源
    List<TypeBean>mDatas;
    private AddItemAdapter addItemAdapteradapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
//        查找控件
        backIv = findViewById(R.id.add_iv_back);
        addLv = findViewById(R.id.add_lv);
        backIv.setOnClickListener(this);
//        mDatas = TypeBean.getTypeList();
        PageDBManager.initPageDB(this);
        mDatas = PageDBManager.getAllTypeList();
//        创建适配器对象
        addItemAdapteradapter = new AddItemAdapter(this, mDatas);
//        设置适配器
        addLv.setAdapter(addItemAdapteradapter);



        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_iv_back:
                preferences=getSharedPreferences("page", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putInt("index",0);
                editor1.commit();

                Intent intent = new Intent(AddItemActivity.this, MenuActivity.class);
                startActivity(intent); //销毁当前的activity，返回上一级界面
                finish();
                break;
        }
    }

//    onCreate (创建了)   onStart(启动了)  onResume(获取焦点)  onPause(失去焦点)  onStop(停止)   onDestory(销毁)   onRestart(重新启动)
    @Override
    protected void onPause() {
        super.onPause();

        PageDBManager.initPageDB1(this);
        PageDBManager.updateTypeList(mDatas);
    }
}
