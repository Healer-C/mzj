package com.cy.map.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.cy.map.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.utiles.PackageUtil;

import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 应用程序初始化页面
 */
public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private String[] permissions;
    boolean isOpenPermission = true;
    private AlertDialog mAlertDialog;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        tvVersion.setText("版本号"+ PackageUtil.getAppVersionName(this));
    }

    @Override
    protected void initData() {
        if (System.currentTimeMillis()>1704038399000L){
            new XPopup.Builder(this).dismissOnTouchOutside(false).dismissOnBackPressed(false).asConfirm("提示", "当前版本已暂停,请联系技术人员", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    finish();
                }
            }).show();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setPerssion();
                }
            }, 500);
        }
    }

    @Override
    protected boolean onGoBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setPerssion() {
        permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        if (EasyPermissions.hasPermissions(this, permissions)) {
            init();
        } else {
            // 没有权限，现在去获取
            EasyPermissions.requestPermissions(this, "此应用需要存储卡和定位等权限才可使用", 124, permissions);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (isOpenPermission) {
            init();
        } else {
            mAlertDialog = new AlertDialog.Builder(this).setCancelable(false)
                    .setTitle("提示")
                    .setMessage("请手动开启所需权限方可运行应用")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAlertDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivityForResult(intent, AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE);

                        }
                    })
                    .create();

            mAlertDialog.show();
        }
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {

    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        isOpenPermission = false;
    }

    void init() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, permissions)) {
                init();
            } else {
                if (mAlertDialog != null) {
                    mAlertDialog.show();
                }
            }
        }
    }
}
