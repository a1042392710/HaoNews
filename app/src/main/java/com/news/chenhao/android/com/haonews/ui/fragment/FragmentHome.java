package com.news.chenhao.android.com.haonews.ui.fragment;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.news.chenhao.android.com.haonews.until.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put("type", "top");
        mPresenter.searchList(ConstantAPI.API_NEWS_TOP, params);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
//        xRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //上拉刷新
                getDate();
            }

            @Override
            public void onLoadMore() {
                //下拉加载
                getDate();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.imgProgress)
    public void onViewClicked() {
        AnimationUtil.rotationAnimation(imgProgress);
    }

    @Override
    public void isSuccessful(HomeNew homeNews) {
        if (xRecyclerView.getVisibility()==View.GONE){
            xRecyclerView.setVisibility(View.VISIBLE);
            imgNogprs.setVisibility(View.GONE);
        }
        List<HomeNew.Data> data = homeNews.getResult().getData();
        mAdapter = new HomeRecyclerAdapter(data);
        xRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void isFailure(String msg) {
        showToast(msg);
        xRecyclerView.setVisibility(View.GONE);
        imgNogprs.setVisibility(View.VISIBLE);
    }
}
