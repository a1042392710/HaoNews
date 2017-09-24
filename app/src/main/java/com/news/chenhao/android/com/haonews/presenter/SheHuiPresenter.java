package com.news.chenhao.android.com.haonews.presenter;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.model.HomeModel;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;
import com.news.chenhao.android.com.haonews.ui.view.HttpCallback;
import com.news.chenhao.android.com.haonews.ui.view.ISheHuiView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import android.support.v4.util.ArrayMap;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class SheHuiPresenter extends BasePresenter<ISheHuiView> {
    private HomeModel homeModel;

    public SheHuiPresenter(Fragment fragment, ISheHuiView view) {
        super(fragment, view);
        homeModel = new HomeModel(fragment);
    }

    public void searchList(String methed, ArrayMap<String, Object> params) {

        homeModel.getRequest( methed, params, new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                mView.isFailure(failedStr);
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr) throws IOException {
                HomeNew homeNew = new Gson().fromJson(bodyStr, HomeNew.class);
                mView.isSuccessful(homeNew);
            }
        });


    }


    @Override
    public void cancelRequest() {

    }
}
