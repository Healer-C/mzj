package com.cy.map.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.map.R;
import com.cy.map.bean.TypeBean;


public class TypeGridAdapter extends BaseQuickAdapter {
    public TypeGridAdapter() {
        super(R.layout.type_item,null);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object item) {
        TypeBean typeBean = ( TypeBean) item;
        baseViewHolder.setImageResource(R.id.iv_image,typeBean.imageId);
        baseViewHolder.setText(R.id.text,typeBean.name);
        if (typeBean.select){
            baseViewHolder.setTextColor(R.id.text,getContext().getResources().getColor(R.color.colorPrimaryDark)) ;
        }else {
            baseViewHolder.setTextColor(R.id.text,getContext().getResources().getColor(R.color.black));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
