package com.news.chenhao.android.com.haonews.base;

import android.support.annotation.StringRes;

/**
 * Created by chenhao on 2017/4/11.
 */
//基础接口
public interface IBaseView {
    void showProgressDialog(@StringRes int resId);

    //显示进度框
    void showProgressDialog(String... res);


    //隐藏进度条
    void dismissDialogDialog();
}
