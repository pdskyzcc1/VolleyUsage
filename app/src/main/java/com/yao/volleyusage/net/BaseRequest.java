package com.yao.volleyusage.net;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.yao.volleyusage.manager.RequestManager;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class BaseRequest<T> extends Request<T> {
    private static final String TAG = "BaseRequest";

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public RequestManager mRequestManager = RequestManager.getInstance();

    Map<String, String> headers = new HashMap<>();
    protected String mRequestUrl;


    public BaseRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        mRequestUrl = url;
    }


    /**
     * 获取公共参数Map
     * @return
     */
    public static Map<String,String> getPublicParams(){
        Map<String, String> params = new HashMap<String, String>();
//        params.put("brand", Build.BRAND);
//        params.put("model", Build.MODEL);
        return params;
    }

    /**
     * 避免param为空
     */
    static String addParam(String param){
        if (param == null){
            return "";
        }else{
            return param;
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //创建一个存header的HashMap,供httpDNS增加参数使用
        return headers;
    }


    /**
     * 拼装参数到URL后面
     * @param url
     * @param params
     * @return
     */
    public static String assembleParams(String url, Map<String,String>... params) {
        String assmbleUrl = url;
        try {
            Map<String,String> getParams = new HashMap<>();
            for (int i=0;i<params.length;i++){
                if (params[i] == null){
                    continue;
                }
                getParams.putAll(params[i]);
            }

            if (getParams.size() > 0) {
                StringBuilder encodedParams = new StringBuilder();

                String paramsEncoding = DEFAULT_PARAMS_ENCODING;

                for (Map.Entry<String, String> entry : getParams.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
                if (url.contains("?")){
                    //带？代表此url含有参数
                    if (url.endsWith("&")){
                        assmbleUrl = url + encodedParams.toString();
                    }else{
                        assmbleUrl = url + "&" + encodedParams.toString();
                    }
                }else{
                    assmbleUrl = url + "?" + encodedParams.toString();
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "拼接URL异常", e);
        }

        return assmbleUrl;
    }


    public void addToRequestQueue() {
        mRequestManager.addToRequestQueue(this);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag) {
        mRequestManager.addToRequestQueue(this, tag);
    }

}
