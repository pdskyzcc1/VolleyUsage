package com.yao.volleyusage.net;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * 生成Object的请求
 */
public class GsonRequest<T> extends BaseRequest<T> {
    private static final String TAG = "GsonRequest";
    private final Gson gson = new Gson();
    protected static final JsonParser parser = new JsonParser();
    private final Map<String, String> headers;
    protected Map<String, String> params;
    private final Response.Listener<T> listener;

    //request泛型的类
    private Type mGsonType;

    /**
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Map<String, String> headers, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }

    /**
     * @param url URL of the request to make
     */
    public GsonRequest(int method, String url,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url,null,null,listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return this.headers != null ? this.headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> param = super.getParams();
        if (params != null) {
            param.putAll(params);
        }
        return param;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * 获取直接子类的泛型参数
     *
     * @return
     */
    protected static Type getType(Object obj,Class superclass) {
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


    /**
     * 设置gson解析反射对应的类型
     * @param type
     */
    public void setGsonType(Type type){
        mGsonType = type;
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error != null) {
            super.deliverError(error);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            json = json.substring(11,json.length()-15);
            Log.i(TAG,"reponse json:"+json);
            Log.i(TAG,"reponse json start:"+json.substring(0,20));
            Log.i(TAG,"reponse json end:"+json.substring(json.length()-30));
            JsonElement rootElement = parser.parse(json);

            JsonObject root = rootElement.getAsJsonObject();
            if (mGsonType == null){
                mGsonType = getType(this,GsonRequest.class);
            }
            Object result = gson.fromJson(root, mGsonType);
            Response<Object> reponseResult = Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
            return (Response<T>) reponseResult;

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }



}