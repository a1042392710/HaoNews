package com.news.chenhao.android.com.haonews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by haobaobao on 2017/9/24.
 */

public class FragmentHomeAdapter extends FragmentPagerAdapter {

   private List<Fragment> list;
    public FragmentHomeAdapter(FragmentManager fm , List<Fragment>list) {
        super(fm );
        this.list=list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
