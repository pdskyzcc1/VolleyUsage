package com.yao.volleyusage.request;

/**
 *
 * Created by yao on 15/8/19.
 */
public class ApiConst {


    // 线上服务器地址
    private static final String HOST = "http://baike.baidu.com/api/openapi/";
    public static String PATH_BAIKE = "BaikeLemmaCardApi";






    public static String getToggleUrl(String requestPath) {
        //TODO 此处可做切换正式测试服务器操作
        return HOST + requestPath;
    }
}
