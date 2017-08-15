package com.news.chenhao.android.com.haonews.until;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chenhao on 2017/1/18.
 * 文件下载类
 */

public class DownLoadFileUtil {
    //下载文件就调这个方法
    public static void downloadFile(final Context context, final String urlStr, final String
            path, final Callback callback) {
        // 参数    上下文对象  、 下载地址 、 保存路径 、 成功和失败的回调
        final ProgressDialog progressDialog = new ProgressDialog(context);
        //Dialog  设置样式为横条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //用ProgressDialog的地方，最好加下这个属性，防止4.0系统出问题 ，在logding的时候，按外面不取消DIalog
        progressDialog.setCanceledOnTouchOutside(false);
        //按外面不取消，并且按返回键也不取消DIalog
        progressDialog.setCancelable(false);
        //消息
        progressDialog.setMessage("正在下载文件");
        //横条最大值为100
        progressDialog.setMax(100);
        //显示
        progressDialog.show();
        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Environment.getExternalStorageState() 得到SD卡的状态，状态为Media_Mounted 也就是 SD卡正常挂载
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //实例化URL对象 也就是网页地址
                        URL url = new URL(urlStr);
                        // HTTP请求
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //设置超时时间，超过五秒就是超时
                        conn.setConnectTimeout(5000);
                        // 获取到文件的大小
                        long max = conn.getContentLength();
                        //得到文件的二进制流
                        InputStream is = conn.getInputStream();
                        //在本地创建一个和服务器一样的空白文件
                        File file = new File(path);
                        // getParentFile 获得父目录 ，.mkdirs 生成文件夹，如果传来的path路径为多个文件件下的文件，这个方法就可以创建文件夹
                        file.getParentFile().mkdirs();
                        //文件输出流
                        FileOutputStream fos = new FileOutputStream(file);
                        //把流暂时读取到一个缓冲数据组里面
                        BufferedInputStream bis = new BufferedInputStream(is);
                        //实例化一个大小为1024字节的数组
                        byte[] buffer = new byte[1024];
                        int len;
                        int total = 0;
                        //文件总长度和已下载量     类似 20/100
                        String maxStr = String.format("%.2fM", (float) (max / 1024f / 1024));
                        //显示到Dialog上
                        progressDialog.setProgressNumberFormat(maxStr);
                        //循环读取
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            total += len;
                            // 获取当前下载量
                            progressDialog.setProgress((int) ((total / (double) max) * 100));
                        }
                        fos.close();//关闭流
                        bis.close();
                        is.close();
                        //下载成功，回调，返回地址，路径，和接口
                        call(true, urlStr, path, callback);
                        progressDialog.dismiss();
                    } else {
                        //返回错误
                        call(false, urlStr, path, callback);
                    }
                } catch (IOException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    //返回错误
                    call(false, urlStr, path, callback);
                }
            }
        }).start();
    }

    private static void call(final boolean successful, final String urlStr, final String path,
                             final Callback callback) {
        // Callback接口有传过来
        if (callback != null) {
            //就回到主线程
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                        @Override
                        public void run() {
                            //将数据通过接口返回，主线程处理逻辑
                            callback.callback(successful, urlStr, path);
                        }
                    });
        }
    }

    public interface Callback {
        void callback(boolean successful, String urlStr, String path);
    }
}
