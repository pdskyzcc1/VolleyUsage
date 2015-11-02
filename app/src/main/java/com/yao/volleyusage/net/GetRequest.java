package com.yao.volleyusage.net;


import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.yao.volleyusage.manager.RequestManager;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 生成Object的请求,get请求
 */
public class GetRequest<T> {
    private static final String TAG = "GetRequest";

    public RequestManager mRequestManager = RequestManager.getInstance();
    GsonRequest<T> request;

    private Type mType;

    /**
     * 只有公参的请求
     * @param url URL of the request to make
     */
    public GetRequest(String url,
                      Listener<T> listener, ErrorListener errorListener) {
        this(url,null,null,listener,errorListener);
    }

    /**
     * @param url URL of the request to make
     */
    public GetRequest(String url, Map<String, String> params,
                      Listener<T> listener, ErrorListener errorListener) {
        this(url,null,params,listener,errorListener);
    }


    public GetRequest(String url, Map<String, String> headers, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
        url = BaseRequest.assembleParams(url,params,getParams());
        mType = GsonRequest.getType(this, GetRequest.class);
        request = new GsonRequest<T>(Request.Method.GET, url, headers,null,listener, errorListener);
        request.setGsonType(mType);
    }



    /**
     * 存放当前请求不变的公参，发get请求时会将这些公参拼接进去
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = BaseRequest.getPublicParams();
        return params;

    }




    public void addToRequestQueue() {
        mRequestManager.addToRequestQueue(request);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag) {
        mRequestManager.addToRequestQueue(request, tag);
    }




}


