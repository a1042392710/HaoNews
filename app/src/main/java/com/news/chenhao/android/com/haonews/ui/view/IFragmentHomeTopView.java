package com.news.chenhao.android.com.haonews.ui.view;

import com.news.chenhao.android.com.haonews.base.IBaseView;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;

/**
 * Created by haobaobao on 2017/8/22.
 */

public interface IFragmentHomeTopView extends IBaseView {

    void isSuccessful(HomeNew data);

    void isFailure(String msg);
}
