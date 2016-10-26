package com.yao.volleyusage.net;

import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有只需要返回JSON的http请求,可以继承此抽象Request
 */
public class HttpJSONRequest extends HttpRequest{
    private static final String TAG = "HttpJSONRequest";

    private final Listener<JSONObject> mListener;
    //构造时传进来的额外参数
    private final Map<String,String> mExtraParams = new HashMap<>();

    /**
     * 没有额外请求参数的构造方法
     */
    public HttpJSONRequest(int method, String requestPath,
                           Listener<JSONObject> listener, ErrorListener errorListener) {
        this(method,requestPath,null,listener,errorListener);
    }
    /**
     * 需要返回json响应的构造
     * @param method
     * @param requestPath
     * @param listener
     * @param errorListener
     */
    public HttpJSONRequest(int method, String requestPath, Map<String,String> extraParams,
                           Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method,requestPath);
        mListener = listener;
        mErrorListener = errorListener;
        //额外请求参数
        if (extraParams!=null){
            mExtraParams.putAll(extraParams);
        }

    }

    @Override
    protected Map<String, String> getExtraParams() {
        Map<String,String> params = super.getExtraParams();
        params.putAll(mExtraParams);
        return params;
    }

    /**
     * 内部调用真正的请求服务器
     */
    @Override
    protected void innerRequestServer(String tag){
        JSONObjRequest request = createRequest(new SuccessListener());
        request.addToRequestQueue(tag);
    }


    /**
     * 响应成功的回调
     */
    class SuccessListener implements Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            Log.i(TAG,"收到的响应结果："+response.toString());
            mListener.onResponse(response);
        }
    }


}


