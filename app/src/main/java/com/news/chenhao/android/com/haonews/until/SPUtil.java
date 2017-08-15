package com.news.chenhao.android.com.haonews.until;

import android.content.Context;
import android.content.SharedPreferences;

import com.news.chenhao.android.com.haonews.BuildConfig;

import java.util.Set;


/**
 * SharedPreferences
 */
public class SPUtil {
    ///////////////////////////////////////////////////////////////////////////
    // 所有的key
    ///////////////////////////////////////////////////////////////////////////
    public static final String KEY_VERSIONCODE = "VERSIONCODE";//版本号
    public static final String KEY_LOGIN_NAME = "LOGIN_NAME";//登录用户名

    public static final String KEY_USER_SESSION = "USER_SESSION";//用户sessionId
    public static final String KEY_USER_NAME = "USER_NAME";//用户名称
    public static final String KEY_USER_TEL = "USER_TEL";//联系电话
    public static final String KEY_USER_PHOTO_URL = "USER_PHOTO_URL";//头像
    public static final String KEY_USER_UNIT_NAME = "USER_UNIT_NAME";//单位名
    public static final String KEY_USER_SN = "USER_SN";//证件号
    public static final String KEY_USER_PERSON_ID = "USER_PERSON_ID";//person id
    public static final String KEY_USER_ID = "USER_ID";//person id

    public static final String KEY_MAIN_NOTICE = "MAIN_NOTICE";//通知
    public static final String KEY_MAIN_CHECK = "MAIN_CHECK";//待处理任务
    public static final String KEY_MAIN_DANGER = "MAIN_DANGER";//漏检点

    public static final String KEY_UNIT_SAI_ID = "UNIT_SAI_ID";//行政区划ID
    public static final String KEY_UNIT_SAI_NAME = "UNIT_SAI_NAME";//行政区划
    public static final String KEY_UNIT_MOI_ID = "UNIT_MOI_ID";//消防管辖ID
    public static final String KEY_UNIT_MOI_NAME = "UNIT_MOI_NAME";//消防管辖单位名称
    public static final String KEY_UNIT_SOT_ID = "UNIT_SOT_ID"; //单位类型ID
    public static final String KEY_UPMOI_ID = "UPMOI_ID";  //上级消防单位ID

    public static final String KEY_TOP_FACILITIES_TYPE = "topFacilitiesType";//一级消防设施分类
    public static final String KEY_FACILITIES_TYPE_MAP = "facilitiesTypeMap";//二级消防设施分类

    public static final String KEY_LOGIN_DATA = "LOGIN_DATA";  //登入后的数据

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    private volatile static SPUtil ourInstance;

    public static SPUtil getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (SPUtil.class) {
                if (ourInstance == null) {
                    ourInstance = new SPUtil(context);
                }
            }
        }
        return ourInstance;
    }

    public static SPUtil getInstance(Context context, String name) {
        if (ourInstance == null) {
            synchronized (SPUtil.class) {
                if (ourInstance == null) {
                    ourInstance = new SPUtil(context, name);
                }
            }
        }
        return ourInstance;
    }

    private SharedPreferences sp;
    private Edit edit;

    private SPUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private SPUtil(Context context) {
        this(context, BuildConfig.APPLICATION_ID);
    }

    public SharedPreferences get() {
        return sp;
    }

    public class Edit {
        private SharedPreferences.Editor editor;

        public Edit() {
            editor = sp.edit();
        }

        public Edit putValue(String key, String value) {

            editor.putString(key, value);
            return this;
        }

        public Edit putValue(String key, boolean value) {
            editor.putBoolean(key, value);
            return this;
        }

        public Edit putValue(String key, float value) {
            editor.putFloat(key, value);
            return this;
        }

        public Edit putValue(String key, int value) {
            editor.putInt(key, value);
            return this;
        }

        public Edit putValue(String key, long value) {
            editor.putLong(key, value);
            return this;
        }

        public Edit putValue(String key, Set<String> value) {
            editor.putStringSet(key, value);
            return this;
        }

        public void commit() {
            editor.commit();
        }

        //保存数据，建议使用apply
        public void apply() {
            editor.apply();
        }
    }

    public Edit edit() {
        if (edit == null) {
            edit = new Edit();
        }
        return edit;
    }


    //查询某值是否存在
    public boolean contains(String key) {
        return sp.contains(key);
    }

    //删除某值
    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    //清理所有
    public void clear() {
        sp.edit().clear();
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defVal) {
        return sp.getString(key, defVal);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defVal) {
        return sp.getBoolean(key, defVal);
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float defVal) {
        return sp.getFloat(key, defVal);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defVal) {
        return sp.getInt(key, defVal);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defVal) {
        return sp.getLong(key, defVal);
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    public Set<String> getStringSet(String key, Set<String> defVal) {
        return sp.getStringSet(key, defVal);
    }
}