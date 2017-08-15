package com.news.chenhao.android.com.haonews.ui.view;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */
public interface Http4byteCallback extends HttpCallback {
    void onResponse(Call call, Response response, byte[] bytes) throws IOException;
}
