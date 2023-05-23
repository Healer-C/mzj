package com.cy.map.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cy.map.App;
import com.cy.map.R;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.utiles.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @Override
    protected int setLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initViews() {
        if (App.type==1){
            rlMain.setBackground(getDrawable(R.mipmap.splash));
        }else  if (App.type==2){
            rlMain.setBackground(getDrawable(R.mipmap.splash2));
        }else  if (App.type==3){
            rlMain.setBackground(getDrawable(R.mipmap.splash3));
        }else  if (App.type==4){
            rlMain.setBackground(getDrawable(R.mipmap.splash4));
        }
    }

    @Override
    protected void initData() {


    }

    @Override
    protected boolean onGoBack() {
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("InvalidR2Usage")
    @OnClick({R.id.bt_login})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        finish();
//        if (view.getId() == R.id.bt_login){
//            if (TextUtils.isEmpty(etNumber.getText().toString().trim())||TextUtils.isEmpty(etPassword.getText().toString().trim())){
//                ToastUtils.showShortToast(this,"账户名密码不能为空",0);
//                return;
//            }
//            Client.getApiService().login("Basic cm9jOnJvYw==",url,etNumber.getText().toString().trim(), etPassword.getText().toString().trim(),"password","server").compose(RxsRxSchedulers.<BaseBean>io_main())
//                    .compose(new DialogTransformer(this).<BaseBean>transformer())
//                    .subscribe(new ApiServiceResult() {
//                        @Override
//                        public void onFinish(BaseBean baseBean) {
//                                ToastUtils.showShortToast(getApplicationContext(),"登录成功",0);
//                        }
//
//                        @Override
//                        public void onError(Throwable throwable) {
//                            super.onError(throwable);
//                            ToastUtils.showShortToast(getApplicationContext(),"用户名密码错误",0);
//                        }
//                    });


//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
