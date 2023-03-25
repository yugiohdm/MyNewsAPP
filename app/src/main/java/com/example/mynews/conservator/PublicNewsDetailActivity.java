package com.example.mynews.conservator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mynews.R;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.User;

public class PublicNewsDetailActivity extends AppCompatActivity {

    private ImageView goback_publicnews_detail_activity;

    private EditText publicnews_detail_uniquekey;
    private EditText publicnews_detail_title;
    private EditText publicnews_detail_date;
    private EditText publicnews_detail_category;
    private EditText publicnews_detail_author;
    private EditText publicnews_detail_url;
    private EditText publicnews_detail_thumbnail1;
    private EditText publicnews_detail_thumbnail2;
    private EditText publicnews_detail_thumbnail3;
    private EditText publicnews_detail_is_content;
    private Button publicnews_detail_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_news_detail);


        goback_publicnews_detail_activity=findViewById(R.id.goback_publicnews_detail_activity);
        publicnews_detail_uniquekey=findViewById(R.id. publicnews_detail_uniquekey);
        publicnews_detail_title=findViewById(R.id.publicnews_detail_title);
        publicnews_detail_date=findViewById(R.id.publicnews_detail_date);
        publicnews_detail_category=findViewById(R.id.publicnews_detail_category);
        publicnews_detail_author=findViewById(R.id.publicnews_detail_author);
        publicnews_detail_url=findViewById(R.id.publicnews_detail_url);
        publicnews_detail_thumbnail1=findViewById(R.id.publicnews_detail_thumbnail1);
        publicnews_detail_thumbnail2=findViewById(R.id.publicnews_detail_thumbnail2);
        publicnews_detail_thumbnail3=findViewById(R.id.publicnews_detail_thumbnail3);
        publicnews_detail_is_content=findViewById(R.id.publicnews_detail_is_content);
        publicnews_detail_update=findViewById(R.id.publicnews_detail_update);

        setPublicNewsData();

        goback_publicnews_detail_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void  setPublicNewsData(){
        News2  news2= (News2) getIntent().getSerializableExtra("publicnews");
        publicnews_detail_uniquekey.setText(news2.getUniquekey()+"");
        publicnews_detail_title.setText(news2.getTitle());
        publicnews_detail_title.requestFocus();
        publicnews_detail_date.setText(news2.getDate());
        publicnews_detail_date.requestFocus();
        publicnews_detail_category.setText(news2.getCategory());
        publicnews_detail_category.requestFocus();
        publicnews_detail_author.setText(news2.getAuthorName());
        publicnews_detail_author.requestFocus();
        publicnews_detail_url.setText(news2.getUrl());
        publicnews_detail_url.requestFocus();
        publicnews_detail_thumbnail1.setText(news2.getThumbnailPicS());
        publicnews_detail_thumbnail1.requestFocus();
        publicnews_detail_thumbnail2.setText(news2.getThumbnailPicS02());
        publicnews_detail_thumbnail2.requestFocus();
        publicnews_detail_thumbnail3.setText(news2.getThumbnailPicS03());
        publicnews_detail_thumbnail3.requestFocus();
        publicnews_detail_is_content.setText(news2.getIsContent());
        publicnews_detail_is_content.requestFocus();
    }
}