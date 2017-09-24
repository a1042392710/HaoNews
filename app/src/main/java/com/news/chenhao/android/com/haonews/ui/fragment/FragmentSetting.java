package com.news.chenhao.android.com.haonews.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenhao on 2017/8/17.
 */

public class FragmentSetting extends BaseFragment {
    @BindView(R.id.title)
    TextView title;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setTextColor(Color.WHITE);
    }


}
