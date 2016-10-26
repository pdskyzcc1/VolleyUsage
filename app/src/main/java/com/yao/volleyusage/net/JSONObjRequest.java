package com.yao.volleyusage.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yao.volleyusage.net.BaseRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * 此为基础JSONObject的请求,外部不可实例化
 */
class JSONObjRequest extends BaseRequest<JSONObject>{

    private Listener<JSONObject> mListener;


    public JSONObjRequest(int method, String url, Listener<JSONObject> listener,
                          ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }


}
