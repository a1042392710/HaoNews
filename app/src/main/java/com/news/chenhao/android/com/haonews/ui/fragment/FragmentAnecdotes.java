package com.news.chenhao.android.com.haonews.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.until.AnimationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenhao on 2017/8/17.
 */

public class FragmentAnecdotes extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imgProgress)
    ImageView imgProgress;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_anecdotes;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setTextColor(Color.WHITE);
    }




    @OnClick(R.id.imgProgress)
    public void onViewClicked() {
        AnimationUtil.rotationAnimation(imgProgress);
    }
}
