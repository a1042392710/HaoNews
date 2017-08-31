package com.news.chenhao.android.com.haonews.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.chenhao.android.com.haonews.R;
import com.news.chenhao.android.com.haonews.base.BaseFragment;
import com.news.chenhao.android.com.haonews.base.BasePresenter;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by chenhao on 2017/8/17.
 */

public class FragmentSetting extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.img_collection)
    ImageView imgCollection;
    @BindView(R.id.text_collection)
    TextView textCollection;
    @BindView(R.id.relative_one)
    RelativeLayout relativeOne;
    @BindView(R.id.img_night_mode)
    ImageView imgNightMode;
    @BindView(R.id.text_night_mode)
    TextView textNightMode;
    @BindView(R.id.switch_night_mode)
    SwitchCompat switchNightMode;
    @BindView(R.id.relative_two)
    RelativeLayout relativeTwo;
    @BindView(R.id.img_brightness_adjustment)
    ImageView imgBrightnessAdjustment;
    @BindView(R.id.text_brightness_adjustment)
    TextView textBrightnessAdjustment;
    @BindView(R.id.relative_three)
    RelativeLayout relativeThree;
    @BindView(R.id.img_close_text)
    ImageView imgCloseText;
    @BindView(R.id.text_close_text)
    TextView textCloseText;
    @BindView(R.id.switch_close_text)
    SwitchCompat switchCloseText;
    @BindView(R.id.relative_four)
    RelativeLayout relativeFour;
    @BindView(R.id.img_clear_cache)
    ImageView imgClearCache;
    @BindView(R.id.text_clear_cache)
    TextView textClearCache;
    @BindView(R.id.relative_five)
    RelativeLayout relativeFive;
    @BindView(R.id.img_score)
    ImageView imgScore;
    @BindView(R.id.text_score)
    TextView textScore;
    @BindView(R.id.relative_six)
    RelativeLayout relativeSix;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.text_share)
    TextView textShare;
    @BindView(R.id.relative_seven)
    RelativeLayout relativeSeven;
    @BindView(R.id.img_feed_back)
    ImageView imgFeedBack;
    @BindView(R.id.text_feed_back)
    TextView textFeedBack;
    @BindView(R.id.relative_eight)
    RelativeLayout relativeEight;
    @BindView(R.id.img_user_agreement)
    ImageView imgUserAgreement;
    @BindView(R.id.text_user_agreement)
    TextView textUserAgreement;
    @BindView(R.id.relative_nine)
    RelativeLayout relativeNine;
    @BindView(R.id.img_current_version)
    ImageView imgCurrentVersion;
    @BindView(R.id.text_current_version)
    TextView textCurrentVersion;
    @BindView(R.id.tv_current_version)
    TextView tvCurrentVersion;
    @BindView(R.id.relative_ten)
    RelativeLayout relativeTen;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getViewResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
