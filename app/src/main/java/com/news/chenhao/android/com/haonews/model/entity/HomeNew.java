package com.news.chenhao.android.com.haonews.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class HomeNew implements Serializable {
    /**
     * 请求返回的字符 ，成功或失败
     */
    private String reason;
    /**
     * 返回的结果
     */
    private Result result;
    /**
     * 失败返回码
     */
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

    /**
     * 返回的结果
     */
    public class Result {
        /**
         * 成功码
         */

        private String stat;
        /**
         * 返回的数据源
         */
        private List<Data> data;

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getStat() {
            return stat;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public List<Data> getData() {
            return data;
        }
    }

    public class Data {
        /**
         * key
         */
        private String uniquekey;
        /**
         * 标题
         */
        private String title;
        /**
         * 时间
         */
        private String date;
        /**
         * 类型，例如头条、娱乐
         */
        private String category;
        /**
         * 作者
         */
        private String author_name;
        /**
         * 新闻主页面的URL
         */
        private String url;
        /**
         * 图片1
         */
        private String thumbnail_pic_s;
        /**
         * 图片2
         */
        private String thumbnail_pic_s02;
        /**
         * 图片3
         */
        private String thumbnail_pic_s03;

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public String getUniquekey() {
            return uniquekey;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }
    }
}
