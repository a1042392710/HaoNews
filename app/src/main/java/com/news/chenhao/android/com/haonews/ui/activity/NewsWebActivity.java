package com.news.chenhao.android.com.haonews.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseActivity;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by haobaobao on 2017/8/24.
 */

public class NewsWebActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.img_back)
    ImageView imgBack;

    private String mUrlStr;

    @Override
    protected int getResViewId() {
        return R.layout.activity_news_web;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUrlStr = getIntent().getStringExtra(ConstantAPI.APA_NEW_WEB_URL);
        //该方法时WebView支持javaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //当需要一个网页跳转到另一个网页时，仍在当前页面显示，而不是打开系统的
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mUrlStr);
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
