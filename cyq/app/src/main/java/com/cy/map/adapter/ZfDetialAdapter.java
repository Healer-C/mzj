package com.cy.map.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.map.R;
import com.cy.map.bean.TypeBean;
import com.cy.map.bean.ZfDetialBean;

public class ZfDetialAdapter extends BaseQuickAdapter {
    public ZfDetialAdapter() {
        super(R.layout.zf_detial_item,null);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object item) {
        ZfDetialBean typeBean = ( ZfDetialBean) item;
        baseViewHolder.setText(R.id.tvLable,typeBean.lable);
        baseViewHolder.setText(R.id.tvValue,typeBean.value);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}