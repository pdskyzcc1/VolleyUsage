package com.yao.volleyusage.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GsonHelper {
    private static final String TAG = "GsonHelper";

    private static Gson INSTANCE;

    static {
        INSTANCE = new Gson();
    }

    public static <T> T parse(String strDataJson, Class<T> classOfT) {
        T data = null;
        if (null != strDataJson) {
            try {
                data = INSTANCE.fromJson(strDataJson, classOfT);
            } catch (Exception e) {
                Log.e(TAG,"Exception:",e);
            }
        }
        return data;
    }
    public static <T> T parse(String strDataJson, Type typeOfT) {
        T data = null;
        if (null != strDataJson) {
            try {
                data = INSTANCE.fromJson(strDataJson, typeOfT);
            } catch (Exception e) {
                Log.e(TAG,"Exception:",e);
            }
        }
        return data;
    }

    public static <T> T parse(byte[] dataJson, Class<T> classOfT) {
        T data = null;
        String strDataJson = null;
        try {
            strDataJson = new String(dataJson, "utf-8");
            data = parse(strDataJson, classOfT);
        } catch (Exception e) {
            Log.e(TAG,"Exception:",e);
        }
        return data;
    }






    /**
     * 获取直接子类的泛型参数
     *
     * @return
     */
    public static Type getType(Object obj,Class superclass) {
        Type type = obj.getClass().getGenericSuperclass();
        while ((type instanceof Class) && !type.equals(superclass)) {
            type = ((Class) type).getGenericSuperclass();
        }
        if (type instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) type;
        return parameterized.getActualTypeArguments()[0];
    }


}
