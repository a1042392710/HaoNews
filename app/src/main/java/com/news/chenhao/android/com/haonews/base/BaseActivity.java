package com.news.chenhao.android.com.haonews.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.news.chenhao.android.com.haonews.R;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by chenhao on 2017/4/10.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements
        IBaseView {
    protected P mPresenter;
    protected final String TAG = this.getClass().getName();//获取当前类名
    protected Context mContext;//上下文对象
    protected Toast mTasot;//提示
    protected ProgressDialog mProgressDialog;//进度条弹框
    // 是否已编辑，提供返回上一页刷新使用
    protected boolean isEdit = false;
    protected InputMethodManager im;//输入法的管理器
    protected BaseApplication mApp;//apcation
    protected Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (BaseApplication) getApplication();
        mApp.addActivity(this);
        mContext = this;
        translucentStatusBar(); //透明状态栏
        setContentView(getResViewId());
        ButterKnife.bind(this);//注解框架的实例化
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//输入法管理器实例化
        mHandler = new Handler(this);
        mPresenter = getPresenter();
        initData(savedInstanceState);
    }


    //提示
    public void showToast(String msg) {
        if (mTasot == null) {
            mTasot = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        mTasot.setText(msg);
        mTasot.setDuration(Toast.LENGTH_SHORT);
        mTasot.show();
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        showProgressDialog(getString(resId));
    }

    //进度条显示
    @Override
    public void showProgressDialog(String... msg) {
        String message;
        if (msg != null && msg.length > 0) {
            message = msg[0];
        } else {
            message = "请稍候……";
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isFinishing() && !isDestroyed()) {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }
        } else {
            if (!isFinishing()) {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }
        }
    }

    /**
     * 设置沉浸式状状栏       两种方式 ，一种设置style 一种代码
     */
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }
    }

    //隐藏进度条
    @Override
    public void dismissDialogDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing() && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    // Touch 按下事件
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboard();
        }
        return super.onTouchEvent(event);
    }


    /**
     * 隐藏键盘
     */
    protected void hideKeyboard() {
        if (getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                    InputMethodManager
                            .HIDE_NOT_ALWAYS);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Handler
    ///////////////////////////////////////////////////////////////////////////
    static class Handler extends android.os.Handler {
        WeakReference<BaseActivity> weakReference;

        Handler(BaseActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = weakReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
    /**
     *  创建View并添加到状态栏
     */
    protected void addWindowsView(int Color) {

        View view = new View(mContext);
        view.setBackgroundColor(getResources().getColor(Color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT,
                getStatusBarHeight());
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view, params);
    }

    /**
     *  获得顶部ActionBar 的高度
     */
    protected int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    /**
     * 延迟关闭
     *
     * @param delayMillis
     */
    public void finish(int delayMillis) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delayMillis);
    }

    //布局文件
    protected abstract int getResViewId();

    //获得presenter
    protected abstract P getPresenter();

    //初始化
    protected abstract void initData(Bundle savedInstanceState);

    protected void handleMessage(Message msg) { }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mApp.removeActivity(this);
        super.onDestroy();
    }
}
