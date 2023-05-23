package com.utile.strong_sun;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.tamsiree.rxkit.RxTool;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mmkv.MMKV;
import com.utile.strong_sun.http.AppConfig;
import com.utile.strong_sun.http.Client;
import com.utile.strong_sun.http.GlobalHttpHandler;
import com.utile.strong_sun.utiles.FileUtil;
import com.utile.strong_sun.utiles.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.reactivex.disposables.CompositeDisposable;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyApplication extends Application {
    public static int locationType = 4326;
    private static Context context;
    public static CompositeDisposable disposables = new CompositeDisposable();
    @Override
    public void onCreate() {
        super.onCreate();

        Bugly.init(getApplicationContext(), "0f21284dc6", true);
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        context = getApplicationContext();
        initClient();
        MMKV.initialize(this);
        ToastUtils.init(this);
        RxTool.init(this);
//        AutoSizeConfig.getInstance()
//                //如果没有这个需求建议不开启
//                .setCustomFragment(false)
//                .setExcludeFontScale(true).setOnAdaptListener(new onAdaptListener() {
//            @Override
//            public void onAdaptBefore(Object o, Activity activity) {
//
//            }
//
//            @Override
//            public void onAdaptAfter(Object o, Activity activity) {
//
//            }
//        });

    }

    /**
     * 创建网络请求
     */
    public void initClient() {
        Client.builder()
                .baseUrl(AppConfig.BASEURL)
                .cacheFile(FileUtil.getCacheFile(this))
                .globalHttpHandler(getHandler())
                .build();


    }

    public GlobalHttpHandler getHandler() {
        return new GlobalHttpHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                //获取请求request
                Request build = null;
                try {
                    build = request.newBuilder()
                            .url(URLDecoder.decode(request.url().url().toString(), "utf-8"))
                            .build();
                    System.out.print(request.url().url().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return build;
            }
        };
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    public static Context getContext() {
        return context;
    }
}
