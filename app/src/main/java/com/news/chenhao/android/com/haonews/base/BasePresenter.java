package com.news.chenhao.android.com.haonews.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.news.chenhao.android.com.haonews.until.JsonUtil;
import com.news.chenhao.android.com.haonews.until.SPUtil;


/**
 * Created by chenhao on 2017/4/11.
 */

/**
 * presenter基础类
 */
public abstract class BasePresenter<V extends IBaseView> {

    protected V mView;
    protected AppCompatActivity mActivity;
    protected Fragment mFragment;
    protected final String failedStr = "无法连接服务器";
    protected JsonUtil mJsonUtil;
    protected SPUtil mSpUtil;


    public BasePresenter(AppCompatActivity activity, V view) {
        mActivity = activity;
        initPresenter(view);
    }

    public BasePresenter(Fragment fragment, V view) {
        mFragment = fragment;
        initPresenter(view);
    }

    //初始化
    private void initPresenter(V view) {
        this.mView = view;
        //实例化Json工具类
        mJsonUtil = JsonUtil.getInstance();
        if (mActivity == null) {
            //Fragment 实例化
            mSpUtil = SPUtil.getInstance(mFragment.getContext());
        } else {
            //ACtivity，实例化
            mSpUtil = SPUtil.getInstance(mActivity);
        }
    }

    //取消请求
    public abstract void cancelRequest();
}
