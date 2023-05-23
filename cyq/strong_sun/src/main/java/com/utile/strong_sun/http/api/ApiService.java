package com.utile.strong_sun.http.api;


import com.utile.strong_sun.bean.DataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
//    @GET("https://api.tianditu.gov.cn/v2/search")
    @GET("https://api.tianditu.gov.cn/v2/search?type=query&postStr={\"queryType\":\"1\",\"start\":0,\"mapBound\":\"116.38045981152607,39.86404339586278,116.5521211884788,39.94779265439931\",\"yingjiType\":0,\"queryTerminal\":10000,\"level\":12,\"keyWord\":\"朝阳区民政简介\",\"count\":10,\"sourceType\":0}&tk=b7cf60e0001b061040a63cdedb2bceec")
//    Observable<DataBean> searchPoint(@Query("type") String type, @Query("postStr") String postStr, @Query("tk") String tk);
    Observable<DataBean> searchPoint();
}
