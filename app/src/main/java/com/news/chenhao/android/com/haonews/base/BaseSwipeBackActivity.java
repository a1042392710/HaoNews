package com.news.chenhao.android.com.haonews.base;

import android.os.Bundle;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by chenhao on 2017/8/16.
 */


    public abstract class BaseSwipeBackActivity<P extends BasePresenter>extends BaseActivity<P> implements SwipeBackActivityBase {
        private SwipeBackActivityHelper mHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            mHelper.onPostCreate();
        }

        @Override
        public View findViewById(int id) {
            View v = super.findViewById(id);
            if (v == null && mHelper != null)
                return mHelper.findViewById(id);
            return v;
        }

        @Override
        public SwipeBackLayout getSwipeBackLayout() {
            return mHelper.getSwipeBackLayout();
        }

        @Override
        public void setSwipeBackEnable(boolean enable) {
            getSwipeBackLayout().setEnableGesture(enable);
        }

        @Override
        public void scrollToFinishActivity() {
            Utils.convertActivityToTranslucent(this);
            getSwipeBackLayout().scrollToFinishActivity();
        }
    }

