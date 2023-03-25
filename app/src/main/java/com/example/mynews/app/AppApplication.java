package com.example.mynews.app;

import android.app.Activity;
import android.app.Application;
import java.util.ArrayList;
import java.util.List;


public class AppApplication extends Application {

    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化第三方SDK
        //MOB短信SDK初始化

    }

    /**
     * 得到AppApplication对象
     *
     * @return
     */
    public static AppApplication getInstance() {
        return instance;
    }

    /**
     * 存储Activity的集合
     */
    private static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity进集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 将Activity移除集合
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有的Actiityv
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
