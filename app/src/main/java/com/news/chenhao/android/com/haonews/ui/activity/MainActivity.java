package com.news.chenhao.android.com.haonews.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseActivity;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.until.AnimationUtil;

import butterknife.BindView;

/**
 * Created by chenhao on 2017/8/15.
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bottonBar)
    BottomNavigationBar bottonBar;
    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @Override
    protected int getResViewId() {
        return R.layout.activity_main;

    }
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        bottonBar
                .addItem(new BottomNavigationItem(R.drawable.home_off, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.anecdotes_on, "趣闻"))
                .addItem(new BottomNavigationItem(R.drawable.setting_off, "设置"))
                .setFirstSelectedPosition(0)
                .initialise();
//        bottonBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);  点击的时候有水波纹，然后变色
        onLinsen();//监听事件
    }

    /**
     * 监听事件
     */
    private void onLinsen() {
        imgProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置自转动画
                AnimationUtil.rotationAnimation(imgProgress);
            }
        });

        bottonBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中 -> 选中
                switch (position) {
                    case 0:
                        showToast("首页");
                        break;
                    case 1:
                        showToast("趣闻");
                        break;
                    case 2:
                        showToast("设置");
                        break;

                }
            }

            @Override
            public void onTabUnselected(int position) {
                //选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {
                //选中 -> 选中
            }
        });
    }

}
