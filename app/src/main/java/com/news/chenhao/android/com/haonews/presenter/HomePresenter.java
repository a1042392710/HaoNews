package com.news.chenhao.android.com.haonews.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.base.ConsTant;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.model.HomeModel;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;
import com.news.chenhao.android.com.haonews.ui.view.HttpCallback;
import com.news.chenhao.android.com.haonews.ui.view.IHomeView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import android.support.v4.util.ArrayMap;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class HomePresenter extends BasePresenter<IHomeView> {
    private HomeModel homeModel;

    public HomePresenter(Fragment fragment, IHomeView view) {
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
