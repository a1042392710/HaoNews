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
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;
import com.news.chenhao.android.com.haonews.presenter.HomeTopPresenter;
import com.news.chenhao.android.com.haonews.presenter.SheHuiPresenter;
import com.news.chenhao.android.com.haonews.ui.adapter.HomeRecyclerAdapter;
import com.news.chenhao.android.com.haonews.ui.view.ISheHuiView;
import com.news.chenhao.android.com.haonews.until.CollectionsUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by haobaobao on 2017/9/24.
 */

public class FragmentHomeSheHui extends BaseFragment<SheHuiPresenter> implements ISheHuiView {

    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.img_nogprs)
    ImageView imgNogprs;

    private HomeRecyclerAdapter mAdapter;


    @Override
    protected SheHuiPresenter getPresenter() {
        return new SheHuiPresenter(this,this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_home_top;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /**
         * 初始化XRV
         */
        initRecyclerView();
        //获取数据
        getDate();
    }

    /**
     * 获取数据
     */
    private void getDate() {
        showProgressDialog("浩欧巴在努力的加载数据");
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(ConstantAPI.API_TYPE,ConstantAPI.API_NEWS_SHEHUI);
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
                getDate();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                getDate();

            }
        });
    }




    @Override
    public void isSuccessful(HomeNew homeNews) {
        dismissDialogDialog();
        if (xRecyclerView.getVisibility() == View.GONE) {
            xRecyclerView.setVisibility(View.VISIBLE);
            imgNogprs.setVisibility(View.GONE);
        }
        if (homeNews.getResult()!=null) {
            List<HomeNew.Data> data = homeNews.getResult().getData();
            if (!CollectionsUtil.isEmpty(data)) {
                mAdapter = new HomeRecyclerAdapter(data, mContext);
                xRecyclerView.setAdapter(mAdapter);
            }
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


    @OnClick({ R.id.img_nogprs})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_nogprs:
                //重新获取数据
                getDate();
                break;
        }
    }

}
