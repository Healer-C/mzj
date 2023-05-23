package com.utile.strong_sun.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.utile.strong_sun.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
public abstract class BaseActivity extends AppCompatActivity   {
    private Unbinder bind;
    public ProgressDialog progressDialog;
    /**
     * 初始化参数
     * 考虑系统恢复Activity调用onCreate时从savedInstanceState中恢复参数的情况
     *
     * @param savedInstanceState
     */
//    protected abstract void initParams(Bundle savedInstanceState);

    /**
     * 由子类设置资源ID
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initViews();


    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 处理返回事件
     * 子类返回false 则表示交给父类处理
     *
     * @return 子类是否已处理
     */
    protected abstract boolean onGoBack();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        bind = ButterKnife.bind(this);
        initViews();
        ImmersionBar.with(this).hideBar(BarHide.FLAG_SHOW_BAR).navigationBarColor(R.color.colorPrimaryDark).statusBarColor(R.color.colorPrimaryDark).fitsSystemWindows(true) .init();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }







    /**
     * 处理toolbar上的返回键
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!onGoBack()) {
                supportFinishAfterTransition();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 处理返回键
     * 三星，OPPO等机型手机上有不执行onBackPressed()的情况，因此统一在onKeyDown中处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (!onGoBack()) {
                supportFinishAfterTransition();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //==========================Helper====================================//

    /**
     * 启动活动
     * @param clazz
     * @param bundle
     */
    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 启动活动 需要处理返回结果
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void startActivityForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public Context getContext(){
        return  this;
    }

    public void dialogShow() {
        if (progressDialog == null) {
            progressDialog =
                    new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("正在加载数据...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }
    public void dismiss (){
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, getResources().getColor(R.color.whith));
    }
    protected void initToolbarNav(Toolbar toolbar, int color) {
        Drawable drawable = getResources().getDrawable(R.drawable.back);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

