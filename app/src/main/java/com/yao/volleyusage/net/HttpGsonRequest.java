package com.yao.volleyusage.net;

import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yao.volleyusage.utils.GsonHelper;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * 所有需要GSON解析的Http请求,可以继承此抽象Request
 */
public abstract class HttpGsonRequest<T> extends HttpRequest{
    private static final String TAG = "HttpGsonRequest";

    private final Listener<T> mListener;

    /**
     * http请求的构造方法
     * @param method {@link com.android.volley.Request.Method#GET,com.android.volley.Request.Method#POST}
     * @param requestPath
     * @param listener
     * @param errorListener
     */
    public HttpGsonRequest(int method,String requestPath,
                      Listener<T> listener, ErrorListener errorListener) {
        super(method,requestPath);
        mListener = listener;
        mErrorListener = errorListener;
    }



    /**
     * 内部调用真正的请求服务器
     */
    @Override
    protected void innerRequestServer(String tag){
        final Type mType = GsonHelper.getType(this, HttpGsonRequest.class);
        JSONObjRequest request = createRequest(new SuccessListener(mType));
        request.addToRequestQueue(tag);
    }




    /**
     * 请求成功的listener
     */
    class SuccessListener implements Listener<JSONObject>{

        final Type mType;
        public SuccessListener(Type type){
            mType = type;
        }

        @Override
        public void onResponse(JSONObject response) {
            //增加一个请求前登录状态参数
            try {
                if (mListener != null) {
                    T result = GsonHelper.parse(response.toString(), mType);
                    mListener.onResponse(result);
                }
            } catch (Exception e) {
                Log.e(TAG,"get解析异常",e);
                mErrorListener.onErrorResponse(new VolleyError(e));
            }
        }
    }


}


