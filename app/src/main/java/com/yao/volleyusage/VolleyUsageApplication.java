package com.yao.volleyusage;

import android.app.Application;
import android.content.Context;

/**
 * Created by yao on 15/8/19.
 */
public class VolleyUsageApplication extends Application {


    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }


    public static Context getApplication() {
        return appContext;
    }
}
