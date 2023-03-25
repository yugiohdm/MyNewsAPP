package com.example.mynews.bean;

import java.io.Serializable;

public class NewsURL  implements Serializable {
//    公共的key   http://v.juhe.cn/toutiao/index?key=33d6c82794f8a730477cadfa87d6f3b8&type=top
     public static String key = "33d6c82794f8a730477cadfa87d6f3b8";

     public static String info_url = "http://v.juhe.cn/toutiao/index?key="+key+"&type=";
//    推荐
     public static String headline_url = info_url +"top";
//    国内
     public static String home_url = info_url +"guonei";
//    国际
    public static String internation_url = info_url+"guoji";
//    娱乐
    public static String entertainment_url = info_url+"yule";
//    体育
    public static String sport_url = info_url+"tiyu";
//    军事
    public static String military_url = info_url+"junshi";
//    科技
    public static String science_url = info_url+"keji";
//    财经
    public static String finance_url = info_url+"caijing";
//    健康
   public static String health_url = info_url+"jiankang";
//    游戏
    public static String game_url = info_url+"youxi";
//    汽车
    public static String automobile_url = info_url+"qiche";
//  时尚
    public static String fashion_url = info_url+"shishang";
}
