package com.news.chenhao.android.com.haonews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {


    public List<HomeNew.Data> data = null;
    public HomeRecyclerAdapter(List<HomeNew.Data> data) {
        this.data = data;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(data.get(position).getTitle());
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return data.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
