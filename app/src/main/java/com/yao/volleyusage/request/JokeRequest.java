package com.yao.volleyusage.request;

import com.android.volley.Response;
import com.yao.volleyusage.net.ApiConst;
import com.yao.volleyusage.net.GsonRequest;
import com.yao.volleyusage.response.JokeResponse;

/**
 * Created by yao on 15/8/19.
 */
public class JokeRequest extends GsonRequest<JokeResponse> {

    public JokeRequest(Response.Listener<JokeResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, ApiConst.JOKE, listener, errorListener);
    }
    public JokeRequest(int method,Response.Listener<JokeResponse> listener, Response.ErrorListener errorListener) {
        super(method, ApiConst.JOKE, listener, errorListener);
    }
}
