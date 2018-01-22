package com.news.chenhao.android.com.haonews.base;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.news.chenhao.android.com.haonews.until.JsonUtil;
import com.news.chenhao.android.com.haonews.until.OkHttpUtil;
import com.news.chenhao.android.com.haonews.until.SPUtil;


/**
 * Created by chenhao on 2017/4/11.
 */


//modle 基础类
public abstract class BaseModel {
    //protected  String mUrl = 用于拼接接口的前面一部分
    protected AppCompatActivity mActivity;
    protected Fragment mFragment;
    protected String mTag;
    protected SPUtil mSpUtil;
    protected JsonUtil mJsonUtil;
    protected OkHttpUtil mOkHttpUtil;

    public BaseModel(AppCompatActivity activity) {
        mActivity = activity;
        //获得类名
        mTag = activity.getClass().getSimpleName();
        //  网络处理的部分模块的初始化
        mOkHttpUtil = new OkHttpUtil(mActivity, mTag);
        //存储的初始化
        mSpUtil = SPUtil.getInstance(mActivity);
        mJsonUtil= JsonUtil.getInstance();

    }

    public BaseModel(Fragment fragment) {
        mFragment = fragment;
        mTag = fragment.getClass().getSimpleName();
        //  网络处理的部分模块的初始化
        mOkHttpUtil = new OkHttpUtil(fragment, mTag);
        mSpUtil = SPUtil.getInstance(mFragment.getContext());
    }

    //取消网络请求
    public void cancelRequest() {
        mOkHttpUtil.cancelCallWithTag(mTag);
    }
}
