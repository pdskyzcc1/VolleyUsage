package com.yao.volleyusage.request;

import com.android.volley.Response;
import com.yao.volleyusage.net.ApiConst;
import com.yao.volleyusage.net.GetRequest;
import com.yao.volleyusage.response.VideoResponse;

import java.util.HashMap;
import java.util.Map;


/**
 * @author shc
 * @since 15/11/2
 */
public class VideoRequest extends GetRequest<VideoResponse> {


    public VideoRequest(Map<String, String> params, Response.Listener<VideoResponse> listener, Response.ErrorListener errorListener) {
        super(ApiConst.VIDEO, params, listener, errorListener);
    }


    /**
     * 存放当前请求特定的不变公参，发get请求时会将这些公参拼接进去
     * @return
     */
    @Override
    protected Map<String, String> getParams() {
        Map<String,String> params = super.getParams();
//        params.put("token", "token");
        return params;
    }

    /**
     * 拼接当前请求会动态改变的参数,例如 页面id
     * @return
     */
    public static Map<String,String> concateParams(String pageId){
        HashMap<String, String> param = new HashMap<>();
//        param.put("pageId", pageId);
        return param;
    }


}
