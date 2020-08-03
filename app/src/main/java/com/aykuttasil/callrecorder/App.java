package com.aykuttasil.callrecorder;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.aykuttasil.callrecorder.recorder.logger.FileFormatStrategy;
import com.aykuttasil.callrecorder.utils.PermissionUtil;
import com.aykuttasil.callrecorder.utils.ToastUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class App extends Application {
    private static Context applicationContext = null;

    public static Context getAppContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        if (PermissionUtil.checkHasPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            FormatStrategy fileFormatStrategy = FileFormatStrategy.newBuilder()
                    .tag("MyApp V" + BuildConfig.VERSION_NAME)
                    .build(getPackageName());
            Logger.addLogAdapter(new DiskLogAdapter(fileFormatStrategy));
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .tag("MyApp V" + BuildConfig.VERSION_NAME)
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        } else {
            ToastUtil.showInfo("没有sdcard读写权限");
            return;
        }
    }
}
