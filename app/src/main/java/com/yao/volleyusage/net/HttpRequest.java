package com.yao.volleyusage.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yao.volleyusage.VolleyUsageApplication;
import com.yao.volleyusage.request.ApiConst;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求的基类
 * Created by huichuan on 16/10/10.
 */
public abstract class HttpRequest {
    private static final String TAG = "HttpRequest";


    public static final String ARGS_IS_LOGIN = "hl_is_login";

    Context appContext = VolleyUsageApplication.getApplication();


    //请求的方法,默认是get （访问权限仅限于包内）
    private int mRequestMethod = Request.Method.GET;
    private String mRequestPath;
    Response.ErrorListener mErrorListener;

    /**
     * 此二参数为必传参数
     * @param method
     * @param requestPath
     */
    protected HttpRequest(int method, String requestPath){
        mRequestMethod = method;
        mRequestPath = requestPath;
    }


    /**
     * 供子类重写的方法
     * 有额外公参重写此方法
     * @return
     */
    protected Map<String, String> getExtraParams(){
        if (isRequestPost() || isRequestGet()){
            //get 或 post 请求，将公参返回
            return BaseRequest.getPublicParams();
        }else{
            throw new IllegalStateException(mRequestPath+" 使用了不可识别的Http请求类型");
        }

    };

    /**
     * 是否是get请求
     * @return
     */
    boolean isRequestGet(){
        return (mRequestMethod == Request.Method.GET);
    }

    /**
     * 是否是Post请求
     * @return
     */
    boolean isRequestPost(){
        return (mRequestMethod == Request.Method.POST);
    }

    /**
     * 根据请求类型创建一个对应的请求
     * @return
     */
    JSONObjRequest createRequest(Response.Listener<JSONObject> listener){
        String requestUrl = ApiConst.getToggleUrl(mRequestPath);
        JSONObjRequest request = null;
        //创建内部的请求成功失败listener
        InnerSuccessListener successListener = new InnerSuccessListener(listener);
        InnerErrorListener errorListener = new InnerErrorListener(mErrorListener);

        if (mRequestMethod == Request.Method.GET){
            //GET 请求，则在url上拼接公参
            requestUrl = BaseRequest.assembleParams(requestUrl, getExtraParams());
            Log.i(TAG,"get请求的url:"+requestUrl);
            request = new JSONObjRequest(Request.Method.GET,requestUrl, successListener, errorListener);
        }else if (mRequestMethod == Request.Method.POST){
            Log.i(TAG,"post请求的url:"+requestUrl);
            request = new JSONObjRequest(Request.Method.POST,requestUrl, successListener, errorListener){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    if (getExtraParams()!=null){
                        params.putAll(getExtraParams());
                    }
                    return params;
                }
            };
        }

        return request;
    }

    /**
     * 创建InnerSuccessListener 是为了允许请求可以不传Listener
     */
    private class InnerSuccessListener implements Response.Listener<JSONObject>{

        Response.Listener<JSONObject> requestListener;

        InnerSuccessListener(Response.Listener<JSONObject> requestListener){
            this.requestListener = requestListener;
        }

        @Override
        public void onResponse(JSONObject response) {
            if (requestListener!=null){
                requestListener.onResponse(response);
            }
        }
    }

    /**
     * 创建InnerErrorListener 是为了允许请求可以不传Listener
     */
    private class InnerErrorListener implements Response.ErrorListener{
        Response.ErrorListener errorListener;

        InnerErrorListener(Response.ErrorListener errorListener){
            this.errorListener = errorListener;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            if(errorListener!=null){
                errorListener.onErrorResponse(error);
            }
        }
    }



    /**
     * 内部调用真正的请求服务器
     */
    protected abstract void innerRequestServer(String tag);




    public void addToRequestQueue(String tag) {
        addToRequestQueue(tag,false);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag, boolean checkNetWork) {
        if (checkNetWork){
            //TODO 这里可以针对检查网络情况做弹Toast处理
        }

        innerRequestServer(tag);
    }


}
