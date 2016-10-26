package com.yao.volleyusage.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.yao.volleyusage.net.HttpGsonRequest;
import com.yao.volleyusage.response.BaikeResponse;

import java.util.Map;

/**
 *
 * Created by yao on 15/8/19.
 */
public class BaikeRequest extends HttpGsonRequest<BaikeResponse> {

    private String keyword;
    public BaikeRequest(String keyWord,Response.Listener<BaikeResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, ApiConst.PATH_BAIKE, listener, errorListener);
        this.keyword = keyWord;
    }


    @Override
    protected Map<String, String> getExtraParams() {
        Map<String,String> params = super.getExtraParams();
        params.put("scope","103");
        params.put("format","json");
        params.put("appid","379020");
        params.put("bk_length","600");
        params.put("bk_key",keyword);

        return params;
    }
}
