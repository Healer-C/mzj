package com.cy.map.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.map.R;
import com.cy.map.adapter.FragmentAdapter;
import com.cy.map.adapter.TypeGridAdapter;
import com.cy.map.adapter.ZfDetialAdapter;
import com.cy.map.bean.TypeBean;
import com.cy.map.bean.ZfDetialBean;
import com.google.android.material.tabs.TabLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.tamsiree.rxkit.view.RxToast;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 政府详情
 */
public class ZfDetialActicity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String [] title={"政区概况","自然条件","经济概况","社会发展","交通设置","名胜古迹","重大变革"};
    private ZfDetialAdapter zfDetialAdapter;
    private ArrayList<ZfDetialBean> list = new ArrayList<>();
    private List<ZfDetialFragment> fragmentList;
    private FragmentAdapter fragmentAdapter;
    private List<String> mTitles;
    @Override
    protected int setLayoutId() {
        return R.layout.zf_detial_activity;
    }

    @Override
    protected void initViews() {
        initToolbarNav(mToolbar);
        tv_title.setText("详情");
        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        for(int i=0;i<title.length;i++){
            mTitles.add(title[i]);
            fragmentList.add(new ZfDetialFragment(i));
        }
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList,mTitles);
        viewPager.setAdapter(fragmentAdapter);
        tab.setupWithViewPager(viewPager);//与ViewPage建立关系
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean onGoBack() {
        return false;
    }


}
