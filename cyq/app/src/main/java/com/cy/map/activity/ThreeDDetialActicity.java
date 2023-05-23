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
import com.utile.strong_sun.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 三维模型
 */
public class ThreeDDetialActicity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView3D)
    RecyclerView recyclerView3D;

    private TypeGridAdapter typeGridAdapterQj;
    private ArrayList<TypeBean> list = new ArrayList<>();


    @Override
    protected int setLayoutId() {
        return R.layout.three_detial_activity;
    }

    @Override
    protected void initViews() {
        String name = getIntent().getStringExtra("name");
        initToolbarNav(mToolbar);
//        tv_title.setText(name);
        tv_title.setText("三维模型");

        recyclerView3D.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView3D.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        typeGridAdapterQj = new TypeGridAdapter();
        recyclerView3D.setAdapter(typeGridAdapterQj);
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        list.add(new TypeBean(R.mipmap.ic_launcher_round, "测试"));
        typeGridAdapterQj.setList(list);
        typeGridAdapterQj.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(ThreeDDetialActicity.this, WebViewActicvity.class));
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
