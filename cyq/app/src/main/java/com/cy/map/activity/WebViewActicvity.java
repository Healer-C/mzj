package com.cy.map.activity;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.cy.map.App;
import com.cy.map.R;
import com.tamsiree.rxkit.RxFileTool;
import com.tamsiree.rxkit.view.RxToast;
import com.utile.strong_sun.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import me.jingbin.web.ByWebView;

public class WebViewActicvity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_main)
     LinearLayout ll_main;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initViews() {
        initToolbarNav(mToolbar);


        String name =  getIntent().getStringExtra("name");
        tv_title.setText(name);
//        tv_title.setText(name+"界桩全景");
        String root = null;
        if (App.type == 1) {
            root = "cyq";
        } else if (App.type == 2) {
            root = "ftq";
        } else if (App.type == 3) {
            root = "kfq";
        } else if (App.type == 4) {
            root = "mzj";
        }
        File rootPath = RxFileTool.getRootPath();
        String url ;
        if (rootPath!=null){
            //url = rootPath.getAbsolutePath()+ "/" + root+"/全景/"+name+"/tour.html";
            url ="file:///android_asset/mzj/全景/"+name+"/tour.html";
            System.out.println("!!!!!!!"+url);
//            if (new File(url).exists()){
                ByWebView byWebView = ByWebView
                        .with(this)
                        .setWebParent(ll_main, new LinearLayout.LayoutParams(-1, -1))
                        .useWebProgress(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .loadUrl(url);
                WebSettings webSetting = byWebView.getWebView().getSettings();
                initWebViewSettings(webSetting);

//            }else {
//                RxToast.error("全景不存在");
//            }
        }else {
            RxToast.error("全景不存在");
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean onGoBack() {
        return false;
    }

    private void initWebViewSettings(WebSettings webSetting) {
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setAllowFileAccess(true);
        webSetting.setNeedInitialFocus(true);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //新加的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSetting.setAllowUniversalAccessFromFileURLs(true);
        }
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setAllowUniversalAccessFromFileURLs(true);

    }

}