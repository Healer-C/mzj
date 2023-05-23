package com.utile.strong_sun.map_utile.bean;

import com.esri.arcgisruntime.geometry.Point;

public class GpsDataBean {
    Point point;
    String msg;

    public GpsDataBean(String msg) {
        this.msg = msg;
    }

    public GpsDataBean(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
