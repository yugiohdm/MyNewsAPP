package com.example.mynews.bean;

import java.io.Serializable;
import java.util.List;

public class NewsBaseModel implements Serializable {
    private String reason;
    private Result result;
    private String error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "NewsBaseModel{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code='" + error_code + '\'' +
                '}';
    }

   public  class Result{
        private  String stat;
        private  List<News> data;
        private  String page;
        private  String pageSize;


        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<News> getData() {
            return data;
        }

        public void setData(List<News> data) {
            this.data = data;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "stat='" + stat + '\'' +
                    ", data=" + data +
                    ", page='" + page + '\'' +
                    ", pageSize='" + pageSize + '\'' +
                    '}';
        }
    }
}
