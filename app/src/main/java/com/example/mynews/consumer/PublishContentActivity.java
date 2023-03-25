package com.example.mynews.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.bean.Collectiblenews;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;

public class PublishContentActivity extends AppCompatActivity {
    Collectiblenews collectiblenews;
    private ImageView publish_content_gobacck;

    private TextView publish_content_username;
    private TextView publish_content_date;
    private TextView publish_content_title;
    private TextView publish_content_detail;

    private ImageView publish_content_collect;
    private SharedPreferences preferences;

    boolean iChecked;

    int collectstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_content);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(PublishContentActivity.this,R.color.white,iChecked);

        publish_content_gobacck=findViewById(R.id.publish_content_gobacck);
        publish_content_username=findViewById(R.id.publish_content_username);
        publish_content_date=findViewById(R.id. publish_content_date);
        publish_content_title=findViewById(R.id.publish_content_title);
        publish_content_detail=findViewById(R.id.publish_content_detail);
        publish_content_collect=findViewById(R.id.publish_content_collect);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        collectiblenews= (Collectiblenews) getIntent().getSerializableExtra("collectiblenews");
        Log.i("=collectiblenews=", String.valueOf(collectiblenews));

        publish_content_username.setText(collectiblenews.getUsername());
        publish_content_date.setText(collectiblenews.getReleasedate());
        publish_content_title.setText(collectiblenews.getNewstitle());
        publish_content_detail.setText(collectiblenews.getNewscontent());

        changeCollect(publish_content_collect,collectiblenews.getNid());

        publish_content_gobacck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
            }
        });

        publish_content_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (collectstate){
                    case 0:
                        addCollectNews(collectiblenews,1);
                        changeCollect(publish_content_collect,collectiblenews.getNid());
                        Toast.makeText(PublishContentActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cancelCollectNews(collectiblenews);
                        changeCollect(publish_content_collect,collectiblenews.getNid());
                        Toast.makeText(PublishContentActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }


    public void changeCollect(ImageView imageView,int nid){
        CollectibleDBManager.initCollectibleDB1(PublishContentActivity.this);
        collectstate=CollectibleDBManager.getSelectedCollectibleState(nid);

        if(collectstate==1){
            imageView.setImageResource(R.drawable.collect1);
        }else{
            imageView.setImageResource(R.drawable.collect);
        }
    }

    public void addCollectNews(Collectiblenews collectiblenews, int state){
        CollectibleDBManager.initCollectibleDB(PublishContentActivity.this);
        CollectibleDBManager.SaveCollectibleList(collectiblenews,state);
    }

    public void cancelCollectNews(Collectiblenews collectiblenews){
        CollectibleDBManager.initCollectibleDB(PublishContentActivity.this);
        CollectibleDBManager.DeleteCollectibleState(collectiblenews.getNid());
    }
}