package com.news.chenhao.android.com.haonews.until;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Json工具类
 */
public class JsonUtil {
    //    private volatile static JsonUtil ourInstance = new JsonUtil();
    private Gson mGson;

    public static JsonUtil getInstance() {
        return new JsonUtil();
//        if (ourInstance == null) {
//            synchronized (SPUtil.class) {
//                if (ourInstance == null) {
//                    ourInstance = new JsonUtil();
//                }
//            }
//        }
//        return ourInstance;
    }

    private JsonUtil() {
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public Gson getGson() {
        return mGson;
    }

    public <T> T getByClass(String json, Class<T> cls) {
        try {
            return mGson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T getByType(String json, Type typeOfT) {
        try {
            return mGson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}