package com.example.mynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.bean.News1;
import com.example.mynews.db.CDBOpenHelper;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;

public class NoContentActivity extends AppCompatActivity {
    private WebView descWeb;

    private ImageView no_content_gobacck;
    String url;

    String title;
    String uniquekey;
    String image1;
    String image2;
    String image3;
    String author;
    String date;

    private SharedPreferences preferences;

    private ImageView no_content_collect;

    boolean iChecked;

    int collectstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_content);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(NoContentActivity.this,R.color.white,iChecked);

        no_content_gobacck=findViewById(R.id.no_content_gobacck);
        descWeb = findViewById(R.id.desc_webview);
        no_content_collect=findViewById(R.id.no_content_collect);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        url = getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        uniquekey=getIntent().getStringExtra("uniquekey");
        image1=getIntent().getStringExtra("image1");
        image2=getIntent().getStringExtra("image2");
        image3=getIntent().getStringExtra("image3");
        date=getIntent().getStringExtra("date");
        author=getIntent().getStringExtra("author");

        setCollectState();

        Log.i("==webview==",""+url);
        if(url==null||url.equals("")){
//            descWeb.set
        }else {
//        创建WebView的设置类，对于属性进行设置
            WebSettings webSettings = descWeb.getSettings();
            webSettings.setJavaScriptEnabled(true);  //设置页面支持js交互
            webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
            webSettings.setLoadWithOverviewMode(true); //缩放至屏幕的大小
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置webview的缓存方式
            webSettings.setAllowFileAccess(true); //设置可以访问文件
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持js打开新窗口
            webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
            webSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
            //        设置要加载的网址
            descWeb.loadUrl(url);
//        默认通过手机浏览器打开网址，为了能够直接通过webview打开网址，就必须设置以下操作
            descWeb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                使用webview要加载的url
                    view.loadUrl(url);
                    return true;
                }
            });
        }

        no_content_gobacck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        no_content_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences= getSharedPreferences("user1", Context.MODE_PRIVATE);
                String username=preferences.getString("username","");
                String password=preferences.getString("password","");
                int uid=preferences.getInt("uid",0);

                News1 news1=new News1(title,date,author,url,image1,image2,image3);
                news1.setUniquekey(uniquekey);
                news1.setUid(uid);

                switch (collectstate){
                    case 0:
                        if(!username.equals("") && !password.equals("")){
                            addCollectNews(news1,1);
                            setCollectState();
                            Toast.makeText(NoContentActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NoContentActivity.this,"您未登录账号,正在跳转登录页面！",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.setClass(NoContentActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case 1:
                        cancelCollectNews(news1);
                        setCollectState();
                        Toast.makeText(NoContentActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && descWeb.canGoBack()) {
            descWeb.goBack();   //返回上一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCollectState(){
        CollectibleDBManager.initCollectibleDB1(NoContentActivity.this);
        collectstate=CollectibleDBManager.getSelectedCollectiblePublicState(uniquekey);

        if(collectstate==1){
            no_content_collect.setImageResource(R.drawable.collect1);
        }else{
            no_content_collect.setImageResource(R.drawable.collect);
        }
    }

    public void addCollectNews(News1 news1,int state){
        CollectibleDBManager.initCollectibleDB(NoContentActivity.this);
        CollectibleDBManager.SaveCollectiblePublicList(news1,state);
    }

    public void cancelCollectNews(News1 news1){
        CollectibleDBManager.initCollectibleDB(NoContentActivity.this);
        CollectibleDBManager.DeleteCollectiblePublicState(news1.getUniquekey());
    }


}