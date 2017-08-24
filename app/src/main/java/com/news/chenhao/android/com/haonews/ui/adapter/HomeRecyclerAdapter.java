package com.news.chenhao.android.com.haonews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.ConstantAPI;
import com.news.chenhao.android.com.haonews.model.entity.HomeNew;
import com.news.chenhao.android.com.haonews.ui.activity.MainActivity;
import com.news.chenhao.android.com.haonews.ui.activity.NewsWebActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haobaobao on 2017/8/22.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<HomeNew.Data> data = null;

    public HomeRecyclerAdapter(List<HomeNew.Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.mTitle.setText(data.get(position).getTitle());//标题
        viewHolder.mAuthor.setText(data.get(position).getAuthor_name());//作者
        //照片123
        Glide.with(context).load(data.get(position).getThumbnail_pic_s()).centerCrop().error(R.drawable.no_photo)
                .into(viewHolder.imgPhoto1);
        Glide.with(context).load(data.get(position).getThumbnail_pic_s()).centerCrop().error(R.drawable.no_photo)
                .into(viewHolder.imgPhoto2);
        Glide.with(context).load(data.get(position).getThumbnail_pic_s()).centerCrop().error(R.drawable.no_photo)
                .into(viewHolder.imgPhoto3);
        if (position == data.size() - 1) {
            viewHolder.mView.setVisibility(View.INVISIBLE);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsWebActivity.class);
                intent.putExtra(ConstantAPI.APA_NEW_WEB_URL, data.get(position).getUrl());

                context.startActivity(intent);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return data.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;//标题
        TextView mAuthor;//作者
        TextView mView;//下划线
        ImageView imgPhoto1;//照片1.2.3
        ImageView imgPhoto2;
        ImageView imgPhoto3;
        LinearLayout layout;//根布局

        ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.text);
            mAuthor = (TextView) view.findViewById(R.id.tv_author);
            mView = (TextView) view.findViewById(R.id.view);
            imgPhoto1 = (ImageView) view.findViewById(R.id.img_photo1);
            imgPhoto2 = (ImageView) view.findViewById(R.id.img_photo2);
            imgPhoto3 = (ImageView) view.findViewById(R.id.img_photo3);
            layout = (LinearLayout) view.findViewById(R.id.layout);
        }
    }
}
