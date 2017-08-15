package com.news.chenhao.android.com.haonews.until;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.news.chenhao.android.com.haonews.ui.view.Http4byteCallback;
import com.news.chenhao.android.com.haonews.ui.view.HttpCallback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHTTP请求类
 */
public class OkHttpUtil {
    private OkHttpClient mOkHttpClient;
    Handler mHandler;
    WeakReference<AppCompatActivity> weakActivity;
    WeakReference<Fragment> weakFragment;
    String mTag;

    public OkHttpUtil(AppCompatActivity activity, String tag) {
        weakActivity = new WeakReference<>(activity);
        init(tag);
    }

    public OkHttpUtil(Fragment fragment, String tag) {
        weakFragment = new WeakReference<>(fragment);
        init(tag);
    }

    private void init(String tag) {
        mTag = tag;
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        //如果handler的实例化不是放在主线程的话，那么调用Looper.getMainLooper方法可以达到一样的效果
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *  Get请求
     *  参数1 :  接口
     *  参数2 :  方法名
     *  参数3 :  携带参数
     */
    public void doGetRequest(final String url,
                             final String method,
                             final ArrayMap<String, Object> params,
                             final HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //根据公司需要，拼接接口  和携带参数（params）
                StringBuilder urlSb = new StringBuilder(url + method);
                if (params != null && !params.isEmpty()) {
                    urlSb.append("?");
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        urlSb.append(entry.getKey());
                        urlSb.append("=");
                        urlSb.append(entry.getValue());
                        urlSb.append("&");
                    }
                    urlSb.deleteCharAt(urlSb.length() - 1);
                }



                Request request = new Request.Builder()
                        //这里可以做拼接，目前只是用单独的URL 需要拼接的时候可以拼接
//                        .url(url)
                        .url(urlSb.toString())
                        .tag(mTag)
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws
                            IOException {
                        final String bodyStr = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    try {
                                        if (response.isSuccessful()) {
                                            callback.onResponse(call, response, bodyStr);
                                        } else {
                                            callback.onFailure(call,
                                                    new RuntimeException("http status:" +
                                                            response.code()));
                                        }
                                    } catch (IOException e) {
                                        callback.onFailure(call, e);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * GET获取byte[]
     */
    public void doGet4byteRequest(final String url, final String method, final ArrayMap<String,
            Object> params, final
                                  Http4byteCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder urlSb = new StringBuilder(url + method);
                if (params != null && !params.isEmpty()) {
                    urlSb.append("?");
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        urlSb.append(entry.getKey());
                        urlSb.append("=");
                        urlSb.append(entry.getValue());
                        urlSb.append("&");
                    }
                    urlSb.deleteCharAt(urlSb.length() - 1);
                }
                Request request = new Request.Builder()
                        .url(urlSb.toString())
                        .tag(mTag)
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws
                            IOException {
                        final byte[] bytes = response.body().bytes();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    try {
                                        if (response.isSuccessful()) {
                                            callback.onResponse(call, response, bytes);
                                        } else {
                                            callback.onFailure(call,
                                                    new RuntimeException("http status:" +
                                                            response.code()));
                                        }
                                    } catch (IOException e) {
                                        callback.onFailure(call, e);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * POST请求Form 表单
     */
    public void doPostFormRequest(final String url,
                                  final String method,
                                  final ArrayMap<String, Object> params,
                                  final HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody.Builder body = new FormBody.Builder();
                if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        body.add(entry.getKey(), entry.getValue().toString());
                    }
                }
                Request request = new Request.Builder()
                        .url(url + method)
                        .tag(mTag)
                        .post(body.build())
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws
                            IOException {
                        final String bodyStr = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    try {
                                        if (response.isSuccessful()) {
                                            callback.onResponse(call, response, bodyStr);
                                        } else {
                                            callback.onFailure(call,
                                                    new RuntimeException("http status:" +
                                                            response.code()));
                                        }
                                    } catch (IOException e) {
                                        callback.onFailure(call, e);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * POST请求Json
     */
    public void doPostJsonRequest(final String url,
                                  final String method,
                                  final ArrayMap<String, Object> params,
                                  final HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json");
                String json = "";
                if (params != null && !params.isEmpty()) {
                    json = JsonUtil.getInstance().getGson().toJson(params);
                }
                RequestBody body = RequestBody.create(JSON, json);

                Request request = new Request.Builder()
                        .url(url + method)
                        .tag(mTag)
                        .post(body)
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isViewGone()) {
                                    callback.onFailure(call, e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws
                            IOException {
                        final String bodyStr = response.body().string();
                        //POST 方法，如果Handler是用主线程改造的就把run里面的代码发送到主线程跑，如果是子线程就发到子线程里面跑
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //页面没有销毁的情况下
                                if (!isViewGone()) {
                                    try {
                                        // 请求返回结果为200 也就是成功
                                        if (response.isSuccessful()) {
                                            callback.onResponse(call, response, bodyStr);
                                        } else {
                                            //失败就返回失败的接口
                                            callback.onFailure(call,
                                                    new RuntimeException("http status:" +
                                                            response.code()));
                                        }
                                    } catch (IOException e) {
                                        callback.onFailure(call, e);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * 界面已销毁
     */
    private boolean isViewGone() {
        Fragment fragment = null;
        AppCompatActivity activity = null;
        if (weakFragment != null) {
            fragment = weakFragment.get();
        } else if (weakActivity != null) {
            activity = weakActivity.get();
        }

        if (fragment != null) {
            return fragment.isDetached();
        } else if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return activity.isFinishing() || activity.isDestroyed();
            } else {
                return activity.isFinishing();
            }
        } else {
            return false;
        }
    }

    /**
     * 取消请求
     */
    public void cancelCallWithTag(String tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }
}