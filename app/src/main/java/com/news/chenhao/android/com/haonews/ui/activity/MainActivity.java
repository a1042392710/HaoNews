package com.news.chenhao.android.com.haonews.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseActivity;
import com.news.chenhao.android.com.haonews.base.BasePresenter;
import com.news.chenhao.android.com.haonews.ui.fragment.FragmentAnecdotes;
import com.news.chenhao.android.com.haonews.ui.fragment.FragmentHome;
import com.news.chenhao.android.com.haonews.ui.fragment.FragmentSetting;

import butterknife.BindView;

/**
 * Created by chenhao on 2017/8/15.
 */

public class MainActivity extends BaseActivity {


    private FragmentHome fragmentHome;   //首页

    private FragmentSetting fragmentSetting; //设置

    private FragmentAnecdotes fragmentAnecdotes;//趣闻

    @BindView(R.id.bottonBar)
    BottomNavigationBar bottonBar;

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
        //给已经设置为透明的状态，填充一个有颜色的View 来达到变色效果
        addWindowsView(R.color.colorPrimary);
        bottonBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottonBar
                .addItem(new BottomNavigationItem(R.drawable.home_off, "首页").setInActiveColor("#212121"))
                .addItem(new BottomNavigationItem(R.drawable.anecdotes_on, "趣闻").setInActiveColor("#212121"))
                .addItem(new BottomNavigationItem(R.drawable.setting_off, "设置").setInActiveColor("#212121"))
                .setFirstSelectedPosition(0)
                .initialise();

        onLinsen();//监听事件
        //设置默认选中首页
        selectTab(0);
    }


    /**
     * 监听事件
     */
    private void onLinsen() {
        bottonBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中 -> 选中
                switch (position) {
                    case 0:
                        selectTab(position);
                        break;
                    case 1:
                        selectTab(position);
                        break;
                    case 2:
                        selectTab(position);
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


    /**
     * 切换页面
     *
     * @param position
     */

    private void selectTab(int position) {
        //首先开启一个Fragment事物
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的Framgnet ，以防有多个Fragment显示在页面上
        hideFragment(fragmentTransaction);
        switch (position) {
            case 0:
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    fragmentTransaction.add(R.id.frameLayout, fragmentHome);
                } else {
                    fragmentTransaction.show(fragmentHome);
                }
                break;

            case 1:
                if (fragmentAnecdotes == null) {
                    fragmentAnecdotes = new FragmentAnecdotes();
                    fragmentTransaction.add(R.id.frameLayout, fragmentAnecdotes);
                } else {
                    fragmentTransaction.show(fragmentAnecdotes);
                }
                break;
            case 2:
                if (fragmentSetting == null) {
                    fragmentSetting = new FragmentSetting();
                    fragmentTransaction.add(R.id.frameLayout, fragmentSetting);
                } else {
                    fragmentTransaction.show(fragmentSetting);
                }
                break;
        }
        //提交事物
        fragmentTransaction.commit();

    }


    /**
     * 隐藏所有的Ftagment
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (fragmentAnecdotes != null) {
            transaction.hide(fragmentAnecdotes);
        }
        if (fragmentSetting != null) {
            transaction.hide(fragmentSetting);
        }

    }

}
