package com.utile.strong_sun.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaoyang on 2017/11/14.
 */

public abstract class BaseFragment extends Fragment {

    private boolean mIsInited;
    private boolean mIsPrepared;
    protected Activity mContext;
    public View view;
    private Unbinder bind;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(getLayoutId(), container, false);
       bind =  ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mIsPrepared = true;
//        if (getUserVisibleHint() && !mIsInited) {
//            mIsInited = true;
            lazyLoad();
//        }
    }


    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * 懒加载
     */
    protected abstract void lazyLoad();


    public void onVisible() {

    }

    public void onUnVisible() {

    }

    /**
     * 加载后才会调用 onVisible/onUnVisible
     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (mIsInited) {
//            if (isVisibleToUser) {
//                onVisible();
//            } else {
//                onUnVisible();
//            }
//        } else {
//            if (mIsPrepared) {
//                mIsInited = true;
//                lazyLoad();
//            }
//        }
//    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }


}
