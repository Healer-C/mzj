package com.cy.map.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.map.R;
import com.cy.map.adapter.TypeGridAdapter;
import com.cy.map.bean.TypeBean;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.tamsiree.rxkit.view.RxToast;
import com.utile.strong_sun.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 兴趣点详情
 */
public class PointDetialActicity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TypeGridAdapter typeGridAdapter;
    private ArrayList<TypeBean> list = new ArrayList<>();
    @Override
    protected int setLayoutId() {
        return R.layout.point_detial_activity;
    }

    @Override
    protected void initViews() {
        String name = getIntent().getStringExtra("name");
        initToolbarNav(mToolbar);
        tv_title.setText(name);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 5 , true));
        typeGridAdapter = new TypeGridAdapter();
        recyclerView.setAdapter(typeGridAdapter);
        list.add(new TypeBean(R.mipmap.ic_launcher_round,"测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round,"测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round,"测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round,"测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round,"测试"));
        typeGridAdapter.setList(list);
        typeGridAdapter.setOnItemClickListener(new OnItemClickListener() {
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
