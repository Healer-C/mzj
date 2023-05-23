package com.cy.map.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.map.R;
import com.cy.map.adapter.LawAdapter;
import com.cy.map.adapter.TypeGridAdapter;
import com.cy.map.bean.LawBean;
import com.cy.map.view.LinearItemDecoration;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.ToastUtils;
import com.tamsiree.rxkit.view.RxToast;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.utiles.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LawActivity  extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<LawBean> list = new ArrayList<>();
    private LawAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.law_activity;
    }

    @Override
    protected void initViews() {
        initToolbarNav(mToolbar);
        title.setText("政策法规");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearItemDecoration linearItemDecoration = new LinearItemDecoration(this, 1);
        linearItemDecoration.setMargin(DisplayUtils.dip2px(this, 10));
        recyclerView.addItemDecoration(linearItemDecoration);
        mAdapter = new LawAdapter(list);
        recyclerView.setAdapter(mAdapter);
        list.add(new LawBean("1992-1993",0));
        list.add(new LawBean("url","测试xxxxxxxx",1));
        list.add(new LawBean("url","测试xxxxxxxx",1));
        list.add(new LawBean("1993-1994",0));
        list.add(new LawBean("url","测试xxxxxxxx",1));
        list.add(new LawBean("url","测试xxxxxxxx",1));
        mAdapter.setList(list);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                RxToast.info("测试数据无法打开");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean onGoBack() {
        return false;
    }
}
