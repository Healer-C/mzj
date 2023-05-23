package com.utile.strong_sun.http.api;

import android.util.Log;


import com.utile.strong_sun.MyApplication;
import com.utile.strong_sun.R;
import com.utile.strong_sun.http.BaseBean;
import com.utile.strong_sun.utiles.ToastUtils;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * 网络请求回调解析
 * Created by Administrator on 2017/12/15.
 */

public abstract class ApiServiceResult<T> implements Observer<BaseBean<T>> {
    @Override
    public void onSubscribe(Disposable d) {
        MyApplication.disposables.add(d);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {
        Log.e("onError: ", throwable.toString());
        String msg = null;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int code = httpException.code();
            msg = httpException.getMessage();
            if (code == 504) {
                msg = MyApplication.getContext().getString(R.string.error_net_504);
            } else if (code == 502 || code == 404||code==400) {
                msg = MyApplication.getContext().getString(R.string.error_net_404);
            }
        } else if (throwable instanceof SocketTimeoutException) {
            msg = MyApplication.getContext().getString(R.string.error_net_time_out);
        } else if (throwable instanceof ConnectException) {
            msg = MyApplication.getContext().getString(R.string.error_net_connect);
        } else if (throwable instanceof IOException) {
            IOException e = (IOException) throwable;
            if (e instanceof SSLHandshakeException) {
                msg = MyApplication.getContext().getString(R.string.error_server_ssl_failed);
            } else if (!(e instanceof EOFException)) {
                msg = MyApplication.getContext().getString(R.string.error_net_server);
            }
        } else {
            msg = throwable.toString();
        }
        onFailure(msg);
    }

    public void onFailure(String msg) {
        ToastUtils.showShortToast(MyApplication.getContext(),msg,0);
    }

    public abstract void onFinish(BaseBean<T> baseBean);

    @Override
    public void onNext(BaseBean<T> tBaseBean) {
        onFinish(tBaseBean);
    }
}
