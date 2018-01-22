package com.news.chenhao.android.com.haonews.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haobaobao on 2018/1/22.
 */

public class ResponseData<T> implements Serializable {
    /**
     * 请求返回的字符 ，成功或失败
     */
    private String reason;
    /**
     * 失败返回码
     */
    private int error_code;

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    /**
     * 返回数据源
     */
    private  Result<T> result ;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
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
    public class Result<T> {
        /**
         * 成功码
         */

        private String stat;
        /**
         * 返回的数据源
         */
        private List<T> data;

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getStat() {
            return stat;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public List<T> getData() {
            return data;
        }
    }


}
