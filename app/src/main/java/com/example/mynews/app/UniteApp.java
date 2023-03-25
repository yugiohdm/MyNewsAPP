package com.example.mynews.app;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.example.mynews.db.PageDBManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class UniteApp extends Application{

//    创建Volle有需要的请求队列
    private static RequestQueue httpQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        PageDBManager.initPageDB(this);  //声明全局的数据库对象
        httpQueue = Volley.newRequestQueue(this);
        initImageLoader(getApplicationContext());   //初始化图片加载框架ImageLoader
    }

    public static RequestQueue getHttpQueue(){
        return httpQueue;
    }
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.MAX_PRIORITY).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
