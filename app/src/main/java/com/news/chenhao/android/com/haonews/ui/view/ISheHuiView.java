package com.news.chenhao.android.com.haonews.ui.view;

import com.news.chenhao.android.com.haonews.base.IBaseView;
import com.news.chenhao.android.com.haonews.model.entity.HaoNews;

/**
 * Created by haobaobao on 2017/8/22.
 */

public interface ISheHuiView extends IBaseView {

    void isSuccessful(HaoNews data);

    void isFailure(String msg);
}
