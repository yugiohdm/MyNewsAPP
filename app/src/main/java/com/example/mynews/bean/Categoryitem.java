package com.example.mynews.bean;

import java.io.Serializable;

public class Categoryitem  implements Serializable {
    /**
     * 样式的声明
     */
    public static final int TENTH_TYPE=9;
    public static final int NINTH_TYPE=8;
    public static final int EIGHTH_TYPE=7;
    public static final int SEVENTH_TYPE = 6;
    public static final int SIXTH_TYPE = 5;
    public static final int FIFTH_TYPE = 4;
    public static final int FOURTH_TYPE = 3;
    public static final int THIRD_TYPE = 2;
    public static final int SECOND_TYPE = 1;
    public static final int First_TYPE = 0;

    /**
     * 代表数据
     */
    private News categorynews;


    private Collectiblenews categorynews1;

    private Releasenews categorynews2;

    /**
     * 区别布局类型
     */
    private int type;

    public News getCategorynews() {
        return categorynews;
    }

    public void setCategorynews(News categorynews) {
        this.categorynews = categorynews;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Collectiblenews getCategorynews1() {
        return categorynews1;
    }

    public void setCategorynews1(Collectiblenews categorynews1) {
        this.categorynews1 = categorynews1;
    }

    public Releasenews getCategorynews2() {
        return categorynews2;
    }

    public void setCategorynews2(Releasenews categorynews2) {
        this.categorynews2 = categorynews2;
    }
}

