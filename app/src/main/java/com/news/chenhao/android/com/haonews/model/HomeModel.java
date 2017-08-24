package com.news.chenhao.android.com.haonews.model;

import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.news.chenhao.android.com.haonews.base.BaseModel;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.ui.view.HttpCallback;

/**
 * Created by chenhao on 2017/8/15.
 */

public class HomeModel extends BaseModel {

    public HomeModel(Fragment fragment) {
        super(fragment);
    }

    public void getRequest( String method, ArrayMap<String, Object> params, HttpCallback callback) {
        mOkHttpUtil.doGetRequest(ConstantAPI.API_NEWS, method, params, callback);
    }
}
