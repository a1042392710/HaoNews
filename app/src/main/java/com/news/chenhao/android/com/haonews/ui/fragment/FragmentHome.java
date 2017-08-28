package com.news.chenhao.android.com.haonews.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;
import com.news.chenhao.android.com.haonews.presenter.HomePresenter;
import com.news.chenhao.android.com.haonews.ui.adapter.HomeRecyclerAdapter;
import com.news.chenhao.android.com.haonews.ui.view.IHomeView;
import com.news.chenhao.android.com.haonews.until.AnimationUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenhao on 2017/8/17.
 */

public class FragmentHome extends BaseFragment<HomePresenter> implements IHomeView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imgProgress)
    ImageView imgProgress;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    Unbinder unbinder;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.img_nogprs)
    ImageView imgNogprs;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private HomeRecyclerAdapter mAdapter;

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this, this);
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setTextColor(Color.WHITE);
        // 初始化tabLayout的信息
        initTabLayoutData();
        /**
         * 初始化XRV
         */
        initRecyclerView();
        //获取数据
        getDate();
    }

    /**
     * 初始化tabLayout的信息
     */

    private void initTabLayoutData() {
        tabLayout.addTab(tabLayout.newTab().setText("头条"));
        tabLayout.addTab(tabLayout.newTab().setText("社会"));
        tabLayout.addTab(tabLayout.newTab().setText("国内"));
        tabLayout.addTab(tabLayout.newTab().setText("国际"));
        tabLayout.addTab(tabLayout.newTab().setText("娱乐"));
        tabLayout.addTab(tabLayout.newTab().setText("体育"));
        tabLayout.addTab(tabLayout.newTab().setText("军事"));
        tabLayout.addTab(tabLayout.newTab().setText("科技"));
        tabLayout.addTab(tabLayout.newTab().setText("财经"));
        tabLayout.addTab(tabLayout.newTab().setText("时尚"));


    }

    /**
     * 获取数据
     */
    private void getDate() {
        showProgressDialog("浩欧巴在努力的加载数据");
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(ConstantAPI.API_TYPE, ConstantAPI.API_NEWS_TOP);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void isSuccessful(HomeNew homeNews) {
        dismissDialogDialog();
        if (xRecyclerView.getVisibility() == View.GONE) {
            xRecyclerView.setVisibility(View.VISIBLE);
            imgNogprs.setVisibility(View.GONE);
        }
        List<HomeNew.Data> data = homeNews.getResult().getData();
        mAdapter = new HomeRecyclerAdapter(data, mContext);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.refreshComplete();//关闭下拉刷新
        xRecyclerView.loadMoreComplete();//关闭下拉加载
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


    @OnClick({R.id.imgProgress, R.id.img_nogprs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgProgress:
                //动画
                AnimationUtil.rotationAnimation(imgProgress);
                //重新获取数据
                getDate();
                break;
            case R.id.img_nogprs:
                //重新获取数据
                getDate();
                break;
        }
    }
}
