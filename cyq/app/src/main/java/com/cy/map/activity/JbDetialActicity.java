package com.cy.map.activity;

import android.content.Intent;
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

/**
 * 界碑详情
 */
public class JbDetialActicity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewQj)
    RecyclerView recyclerViewQj;
    @BindView(R.id.recyclerView3D)
    RecyclerView recyclerView3D;
    private TypeGridAdapter typeGridAdapter;
    private TypeGridAdapter typeGridAdapterQj;
    private TypeGridAdapter typeGridAdapter3D;
    private ArrayList<TypeBean> list = new ArrayList<>();
    private ArrayList<TypeBean> list1 = new ArrayList<>();
    private ArrayList<TypeBean> list2 = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.jb_detial_activity;
    }

    @Override
    protected void initViews() {
        String name = getIntent().getStringExtra("name");
        initToolbarNav(mToolbar);
//        tv_title.setText(name);
        tv_title.setText("详情");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        typeGridAdapter = new TypeGridAdapter();
        recyclerView.setAdapter(typeGridAdapter);
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        typeGridAdapter.setList(list);
        typeGridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                RxToast.info("测试数据无法打开");
            }
        });

        recyclerViewQj.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewQj.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        typeGridAdapterQj = new TypeGridAdapter();
        recyclerViewQj.setAdapter(typeGridAdapterQj);
        list1.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list1.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list1.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list1.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list1.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        typeGridAdapterQj.setList(list1);
        typeGridAdapterQj.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(JbDetialActicity.this, WebViewActicvity.class));
            }
        });

        recyclerView3D.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView3D.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        typeGridAdapter3D = new TypeGridAdapter();
        recyclerView3D.setAdapter(typeGridAdapterQj);
        list2.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list2.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list2.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list2.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list2.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        typeGridAdapter3D.setList(list2);
        typeGridAdapter3D.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(JbDetialActicity.this, WebViewActicvity.class));
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
