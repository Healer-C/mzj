package com.cy.map.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cy.map.R;
import com.cy.map.adapter.ZfDetialAdapter;
import com.cy.map.bean.ZfDetialBean;
import com.utile.strong_sun.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 界碑详情
 */
public class ZfDetialFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ZfDetialAdapter zfDetialAdapter;
    private ArrayList<ZfDetialBean> list = new ArrayList<>();
    private  int type;
    public ZfDetialFragment(int type) {
       this.type = type;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.zf_detial_fragment;
    }

    @Override
    protected void lazyLoad() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        zfDetialAdapter = new ZfDetialAdapter();
        recyclerView.setAdapter(zfDetialAdapter);
        if (type==0) {
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("名称来历", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("地理位置", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("政区沿革", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("政区划分", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("人口面积", "测试xxxxxxxxxxxxxxxxx"));
        }else  if (type==1){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("地形地貌", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("气候", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("水文", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("矿藏及其他自然资源", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("自然灾害", "测试xxxxxxxxxxxxxxxxx"));
        }else  if (type==2){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("农业", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("工业", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("商业外贸", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("财政金融", "测试xxxxxxxxxxxxxxxxx"));
        }else  if (type==3){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("文化艺术", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("教育", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("教育", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("医疗卫生", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("体育", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("广播电视", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("社会保障", "测试xxxxxxxxxxxxxxxxx"));
        }else  if (type==4){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("交通运输", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("交通运输", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("能源", "测试xxxxxxxxxxxxxxxxx"));

        }else  if (type==5){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("旅游业概况", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("主要风景区", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("重点名胜古迹", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("风景点", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("人口面积", "测试xxxxxxxxxxxxxxxxx"));
        }else  if (type==6){
            list.add(new ZfDetialBean("政府名称", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("概述", "测试xxxxxxxxxxxxxxxxx"));
            list.add(new ZfDetialBean("相关重大事件", "测试xxxxxxxxxxxxxxxxx"));

        }
        zfDetialAdapter.setList(list);
    }

}
