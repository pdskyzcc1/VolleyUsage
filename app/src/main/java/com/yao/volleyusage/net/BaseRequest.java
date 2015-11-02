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
    protected String mRequestUrl;

    public BaseRequest(String url, ErrorListener listener) {
        super(url, listener);
        mRequestUrl = url;
    }


    public BaseRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        mRequestUrl = url;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = getPublicParams();
        return params;
    }


    /**
     * 获取公共参数Map
     * @return
     */
    public static Map<String,String> getPublicParams(){
        Map<String, String> params = new HashMap<String, String>();
        return params;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
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

//            Map<String, String> getParams = getParams();
//            if (params!=null){
//                //把构造函数添加进来的参数都放进去
//                getParams.putAll(params);
//            }
            if (getParams != null && getParams.size() > 0) {
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
