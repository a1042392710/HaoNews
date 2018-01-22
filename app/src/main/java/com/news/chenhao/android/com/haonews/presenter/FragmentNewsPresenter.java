package com.news.chenhao.android.com.haonews.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.model.HomeModel;
import com.news.chenhao.android.com.haonews.model.entity.HaoNews;
import com.news.chenhao.android.com.haonews.model.entity.ResponseData;
import com.news.chenhao.android.com.haonews.ui.view.HttpCallback;
import com.news.chenhao.android.com.haonews.ui.view.IFragmentNewsView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class FragmentNewsPresenter extends BasePresenter<IFragmentNewsView> {
    private HomeModel homeModel;

    public FragmentNewsPresenter(Fragment fragment, IFragmentNewsView view) {
        super(fragment, view);
        homeModel = new HomeModel(fragment);
    }

    public void searchList(String methed, ArrayMap<String, Object> params) {

        homeModel.getRequest(methed, params, new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                mView.isFailure(failedStr);
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr) throws IOException {
                ResponseData<HaoNews> responseData = mJsonUtil.getByType(bodyStr,
                        new TypeToken<ResponseData<HaoNews>>() {}.getType());
                List<HaoNews> haoNews = responseData.getResult().getData();
                mView.isSuccessful(haoNews);
            }
        });


    }


    @Override
    public void cancelRequest() {

    }
}
