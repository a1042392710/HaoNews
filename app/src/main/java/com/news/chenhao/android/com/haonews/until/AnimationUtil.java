package com.news.chenhao.android.com.haonews.until;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by chenhao on 2017/8/15.
 */

public class AnimationUtil  {
    /**
     *  自转
     */
    public static void rotationAnimation(View view){
        Animation anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(800); // 设置动画时间
        /**   Android Interpolator 动画插入器
         *（1）LinearInterpolator：动画从开始到结束，变化率是线性变化。
         *（2）AccelerateInterpolator：动画从开始到结束，变化率是一个加速的过程。
         *（3）DecelerateInterpolator：动画从开始到结束，变化率是一个减速的过程。
         *（4）CycleInterpolator：动画从开始到结束，变化率是循环给定次数的正弦曲线。
         *（5）AccelerateDecelerateInterpolator：动画从开始到结束，变化率是先加速后减速的过程。
         */
        anim.setInterpolator(new LinearInterpolator()); // 设置插入器
        view.startAnimation(anim);
    }
}
