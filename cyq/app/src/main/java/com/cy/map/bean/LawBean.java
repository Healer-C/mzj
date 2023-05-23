package com.cy.map.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class LawBean implements MultiItemEntity {
    public String time;
    public String url;
    public String name;
    public int itemType = 0;

    @Override
    public int getItemType() {
        return itemType;
    }

    public LawBean(String time, int itemType) {
        this.time = time;
        this.itemType = itemType;
    }

    public LawBean(String url, String name, int itemType) {
        this.url = url;
        this.name = name;
        this.itemType = itemType;
    }
}
