package com.cy.map.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.map.R;
import com.cy.map.bean.LawBean;
import com.cy.map.bean.TypeBean;

import java.util.List;

public class LawAdapter extends BaseMultiItemQuickAdapter {
    public LawAdapter(@Nullable List data) {
        super(data);
       addItemType(0,R.layout.law_item1);
       addItemType(1,R.layout.law_item2);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object item) {
        LawBean lawBean = ( LawBean) item;
        int itemViewType = baseViewHolder.getItemViewType();

        if (itemViewType==0){
            baseViewHolder.setText(R.id.tv_time,lawBean.time);
        }else if (itemViewType==1){
            baseViewHolder.setText(R.id.tv_pdf,lawBean.name);
        }

    }


}
