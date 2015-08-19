package com.yao.volleyusage.net;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yao.volleyusage.manager.RequestManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 生成Object的请求
 */
public class GsonRequest<T> extends Request<T> {
    private static final String TAG = "GsonRequest";
    public RequestManager mRequestManager = RequestManager.getInstance();
    private final Gson gson = new Gson();
    protected static final JsonParser parser = new JsonParser();
    private final Map<String, String> headers;
    protected Map<String, String> params;
    private final Listener<T> listener;

    /**
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Map<String, String> headers, Map<String, String> params,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }

    /**
     * @param url URL of the request to make
     */
    public GsonRequest(int method, String url,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        headers = null;
        params = null;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return this.headers != null ? this.headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> param = super.getParams();
        if (params != null) {
            param.putAll(params);
        }
        return param;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }




    /**
     * 获取直接子类的泛型参数
     *
     * @return
     */
    protected Type getType() {
        Type superclass = getClass().getGenericSuperclass();
        while ((superclass instanceof Class) && !superclass.equals(GsonRequest.class)) {
            superclass = ((Class) superclass).getGenericSuperclass();
        }
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error != null) {
            super.deliverError(error);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            json = json.substring(7,json.length()-2);
            JsonElement rootElement = parser.parse(json);
            JsonObject root = rootElement.getAsJsonObject();
            Object result = gson.fromJson(root, getType());
            Response<Object> reponseResult = Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
            return (Response<T>) reponseResult;

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
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
