package com.news.chenhao.android.com.haonews.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.news.chenhao.android.com.haonews.until.CrashHandler;

import java.util.ArrayList;

/**
 * Created by yubin on 2016/2/23.
 */
public class BaseApplication extends Application {
    private ArrayList<AppCompatActivity> mActivitys;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.setCustomCrashHanler(getApplicationContext());
        mContext = getApplicationContext();
        mActivitys = new ArrayList<>();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void addActivity(AppCompatActivity act) {
        this.mActivitys.add(act);
    }

    public void removeActivity(AppCompatActivity act) {
        if (mActivitys.contains(act)) {
            mActivitys.remove(act);
        }
    }

    public void finallyAll() {
        for (int i = 0, len = mActivitys.size(); i < len; i++) {
            AppCompatActivity act = mActivitys.get(i);
            act.finish();
        }
    }

    //获得全局上下文对象
    public static Context getContext() {
        return mContext;
    }
}