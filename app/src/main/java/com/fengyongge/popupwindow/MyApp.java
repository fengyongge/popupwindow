package com.fengyongge.popupwindow;

import android.app.Application;

import com.orhanobut.logger.LogLevel;

import com.orhanobut.logger.Logger;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init("fyg").logLevel(LogLevel.FULL);//debug
    }
}
