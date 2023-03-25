package com.example.mynews.bean;

import android.os.Build;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItem {

    /**
     * 需要进行排序的字段
     */
    private String column;
    /**
     * 是否正序排列，默认 true
     */
    private boolean asc = true;

    public OrderItem(String column, boolean asc) {
        this.column = column;
    }

    public static OrderItem asc(String column) {
        return build(column, true);
    }

    public static OrderItem desc(String column) {
        return build(column, false);
    }

    public static List<OrderItem> ascs(String... columns) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
        }
        return null;
    }

    public static List<OrderItem> descs(String... columns) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
        }
        return null;
    }

    private static OrderItem build(String column, boolean asc) {
        return new OrderItem(column, asc);
    }

}
