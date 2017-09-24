package com.news.chenhao.android.com.haonews.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 * Created by yubin on 2016/2/23.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    //得到类名
    protected final String TAG = this.getClass().getName();
    private boolean isInit;
    protected P mPresenter;
    protected View mRootView;    // 视图
    protected BaseApplication mApp;    //全APP 进程
    protected Context mContext;    //上下文对象
    protected ProgressDialog mProgressDialog; //弹窗
    protected InputMethodManager im;//键盘管理器
    protected Handler mHandler;
    protected Toast mToast;
    private Unbinder unbinder;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        attach(context);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach(context);
    }


    private void attach(Context context) {
        mContext = context;
        mApp = (BaseApplication) context.getApplicationContext();
        im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getViewResId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            isInit = true;
            mPresenter = getPresenter();
            initData(savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {

        if (unbinder != null) {
            unbinder.unbind(); //注解框架解绑
        }
        if (isInit) {
            cancelRequest();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        showProgressDialog(getString(resId));
    }

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
        if (!isRemoving() && !isDetached() && !isHidden()) {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void dismissDialogDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToastLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Handler
    ///////////////////////////////////////////////////////////////////////////
    static class Handler extends android.os.Handler {
        WeakReference<BaseFragment> weakReference;

        Handler(BaseFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment fragment = weakReference.get();
            if (fragment != null) {
                fragment.handleMessage(msg);
            }
        }
    }

    protected void handleMessage(Message msg) {
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    protected void hideKeyboard() {
        if (((BaseActivity) mContext).getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(((BaseActivity) mContext).getCurrentFocus()
                            .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected abstract P getPresenter();

    //布局ID
    protected abstract int getViewResId();

    //初始化
    protected abstract void initData(Bundle savedInstanceState);

    //取消请求
    public void cancelRequest() {
    }
}