package com.news.chenhao.android.com.haonews.ui.view;

import com.news.chenhao.android.com.haonews.base.IBaseView;
import com.news.chenhao.android.com.haonews.model.entity.HaoNews;
import com.news.chenhao.android.com.haonews.model.entity.ResponseData;

import java.util.List;

/**
 * Created by haobaobao on 2017/8/22.
 */

public interface IFragmentNewsView extends IBaseView {

    void isSuccessful(List<HaoNews> haoNews);

    void isFailure(String msg);
}
