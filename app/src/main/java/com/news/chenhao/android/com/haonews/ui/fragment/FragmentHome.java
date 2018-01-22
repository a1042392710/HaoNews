package com.news.chenhao.android.com.haonews.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.presenter.SheHuiPresenter;
import com.news.chenhao.android.com.haonews.ui.adapter.FragmentHomeAdapter;
import com.news.chenhao.android.com.haonews.ui.adapter.HomeRecyclerAdapter;
import com.news.chenhao.android.com.haonews.until.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenhao on 2017/8/17.
 */

public class FragmentHome extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imgProgress)
    ImageView imgProgress;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> mFragmentList;

    private FragmentHomeAdapter mAdapter;

    private int mPosition = 0; //记录当前页数

    @Override
    protected SheHuiPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setTextColor(Color.WHITE);
        //初始化ViewPager所需数据
        initViewPager();
        // 初始化tabLayout的信息
        initTabLayoutData();
    }

    /**
     * 初始化ViewPager所需数据
     */
    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        //给每个页面设置不同的数据源
        for (int i = 0; i < ConstantAPI.apiArray.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(ConstantAPI.API_TYPE, ConstantAPI.apiArray[i]);
            FragmentNews fragmentNews = new FragmentNews();
            fragmentNews.setArguments(bundle);
            mFragmentList.add(fragmentNews);
        }
        mAdapter = new FragmentHomeAdapter(getFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    /**
     * 初始化tabLayout的信息
     */

    private void initTabLayoutData() {

        for (int i = 0; i < ConstantAPI.apiNameArray.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(ConstantAPI.apiNameArray[i]));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPosition = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.imgProgress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgProgress:
                //动画
                AnimationUtil.rotationAnimation(imgProgress);
                FragmentNews fragmentNews = (FragmentNews) mFragmentList.get(mPosition);
                fragmentNews.getDate(ConstantAPI.apiArray[mPosition]);
//                mAdapter = new FragmentHomeAdapter(getFragmentManager(), mFragmentList);
//                //重新获取数据
//                viewPager.setAdapter(mAdapter);
//                viewPager.setCurrentItem(mPosition);
                break;
        }
    }

}
