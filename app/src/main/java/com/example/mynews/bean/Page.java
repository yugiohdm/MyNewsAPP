package com.example.mynews.bean;

import android.os.Build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Page  implements Serializable {


    private List<News2> records;


    private long total ;

    private long size ;


    protected long current ;



    private List<OrderItem> orders ;


    private boolean optimizeCountSql ;

    private boolean searchCount ;


    private boolean optimizeJoinOfCountSql ;


    private String countId;


    private Long maxLimit;

    private long pages;


    public List<News2> getRecords() {
        return records;
    }

    public void setRecords(List<News2> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isOptimizeJoinOfCountSql() {
        return optimizeJoinOfCountSql;
    }

    public void setOptimizeJoinOfCountSql(boolean optimizeJoinOfCountSql) {
        this.optimizeJoinOfCountSql = optimizeJoinOfCountSql;
    }

    public String getCountId() {
        return countId;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public Long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", orders=" + orders +
                ", optimizeCountSql=" + optimizeCountSql +
                ", searchCount=" + searchCount +
                ", optimizeJoinOfCountSql=" + optimizeJoinOfCountSql +
                ", countId='" + countId + '\'' +
                ", maxLimit=" + maxLimit +
                ", pages=" + pages +
                '}';
    }
}
