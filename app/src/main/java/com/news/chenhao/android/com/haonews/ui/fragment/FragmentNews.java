package com.news.chenhao.android.com.haonews.ui.fragment;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.model.entity.HaoNews;
import com.news.chenhao.android.com.haonews.presenter.FragmentNewsPresenter;
import com.news.chenhao.android.com.haonews.ui.adapter.HomeRecyclerAdapter;
import com.news.chenhao.android.com.haonews.ui.view.IFragmentNewsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by haobaobao on 2017/9/24.
 */

public class FragmentNews extends BaseFragment<FragmentNewsPresenter> implements IFragmentNewsView {

    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.img_nogprs)
    ImageView imgNogprs;

    private HomeRecyclerAdapter mAdapter;
    private String mApi;//查询条件
    private List<HaoNews> mList = new ArrayList<>();


    @Override
    protected FragmentNewsPresenter getPresenter() {
        return new FragmentNewsPresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_home_top;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mApi = getArguments().getString(ConstantAPI.API_TYPE, ConstantAPI.API_NEWS_TOP);
        /**
         * 初始化列表
         */
        initRecyclerView();
        //获取数据
        getDate(mApi);
    }

    /**
     * 获取数据
     */
    public void getDate(String api) {
        this.mApi = api;
        showProgressDialog("浩欧巴在努力的加载数据");
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(ConstantAPI.API_TYPE, api);
        mPresenter.searchList(null, params);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setLoadingMoreEnabled(false);//关闭上拉加载
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getDate(mApi);

            }

            @Override
            public void onLoadMore() {
                //上拉加载
                getDate(mApi);

            }
        });
    }


    @Override
    public void isSuccessful(List<HaoNews> homeNews) {
        this.mList.clear();
        mList.addAll(homeNews);
        dismissDialogDialog();
        if (xRecyclerView.getVisibility() == View.GONE) {
            xRecyclerView.setVisibility(View.VISIBLE);
            imgNogprs.setVisibility(View.GONE);
        }
        if (mList != null) {
                mAdapter = new HomeRecyclerAdapter(mList, mContext);
                xRecyclerView.setAdapter(mAdapter);
        }
        xRecyclerView.refreshComplete();//关闭下拉刷新动画
        xRecyclerView.loadMoreComplete();//关闭下拉加载动画
    }

    @Override
    public void isFailure(String msg) {
        dismissDialogDialog();
        showToast(msg);
        if (xRecyclerView.getVisibility() == View.VISIBLE) {
            xRecyclerView.setVisibility(View.GONE);
            imgNogprs.setVisibility(View.VISIBLE);
        }
        xRecyclerView.refreshComplete();//关闭下拉刷新动画
        xRecyclerView.loadMoreComplete();//关闭下拉加载动画
    }


    @OnClick({R.id.img_nogprs})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_nogprs:
                //重新获取数据
                getDate(mApi);
                break;
        }
    }

}
