package com.cy.map;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.esri.arcgisruntime.data.Feature;
import com.utile.strong_sun.MyApplication;

import org.litepal.LitePal;



public class App extends MyApplication {

    private static Context context;
    public static  int  type = 4; //1朝阳 2丰台  3开发 4民政局
    public static  String  typePath = "民政局"; //朝阳区  丰台区  开发区 民政局
    public static Feature featureSelect;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
        initData();
    }

    private void initData() {


    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
    }
}
