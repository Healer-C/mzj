package com.cy.map.bean;

import com.esri.arcgisruntime.geometry.Point;


public class StarBean {
    private Point point;
    private String name;
    private boolean isShowLable = false;

    public StarBean( String name, Point point,boolean isShowLable) {
        this.point = point;
        this.name = name;
        this.isShowLable = isShowLable;
    }

    public StarBean(String name, Point point) {
        this.point = point;
        this.name = name;
    }

    public Boolean getShowLable() {
        return isShowLable;
    }

    public void setShowLable(Boolean showLable) {
        isShowLable = showLable;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
