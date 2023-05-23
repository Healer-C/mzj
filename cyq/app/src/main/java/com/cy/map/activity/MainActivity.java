package com.cy.map.activity;

import static com.esri.arcgisruntime.internal.jni.bb.as;
import static com.esri.arcgisruntime.internal.jni.bb.p;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Range;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.map.App;
import com.cy.map.Constants;
import com.cy.map.R;
import com.cy.map.adapter.TypeGridAdapter;
import com.cy.map.bean.StarBean;
import com.cy.map.bean.State;
import com.cy.map.bean.TypeBean;
import com.cy.map.image_click.ClickableArea;
import com.cy.map.image_click.ClickableAreasImage;
import com.cy.map.image_click.OnClickableAreaClickedListener;
import com.cy.map.image_click.photo.PhotoViewAttacher;
import com.cy.map.utils.AddGridLayerMabager;
import com.cy.map.view.BreatheView;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.internal.jni.by;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.google.firebase.firestore.GeoPoint;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;
import com.tamsiree.rxkit.RxFileTool;
import com.tamsiree.rxkit.view.RxToast;
import com.utile.strong_sun.MyApplication;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.map_utile.location.GpsData;
import com.utile.strong_sun.map_utile.show_map.MapShowManager;
import com.utile.strong_sun.map_utile.show_map.MyDefaultOnTouchListener;
import com.utile.strong_sun.utiles.DisplayUtils;
import com.utile.strong_sun.utiles.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import butterknife.BindView;
//import io.grpc.Context;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MainActivity extends BaseActivity implements SensorEventListener, OnClickableAreaClickedListener {
    @BindView(R.id.mapView)
    MapView mapview;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.recyclerView_one)
    RecyclerView recyclerView_one;
    @BindView(R.id.recyclerView_two)
    RecyclerView recyclerView_two;
    @BindView(R.id.iv_layer)
    ImageView iv_layer;
    @BindView(R.id.iv_all)
    ImageView iv_all;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.ll_booton)
    LinearLayout ll_booton;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.detial)
    TextView detial;
    private ArcGISMap map;
    private MapShowManager mapShowManager;
    private Sensor mSensor;
    private GpsData gpsData;
    private SensorManager mSM;
    private TypeGridAdapter typeGridAdapter;
    private TypeGridAdapter typeGridAdapterTwo;
    private ArrayList<TypeBean> list1 = new ArrayList<>();
    private ArrayList<TypeBean> list2 = new ArrayList<>();
    private GraphicsOverlay pointMarke;
    private int selectQx;
    private boolean imageLayer = false;
    private String titleDes;
    private String name;
    private LoadingPopupView loadingPopupView;
    private GraphicsOverlay graphicsOverlaySatr;
    private GraphicsOverlay graphicsOverlayGif;
    private GraphicsOverlay graphicsOverlaySatrBJ;
    private PictureMarkerSymbol pictureMarkerSymbol;
    private BreatheView breatheView;
    @Override
    protected int setLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
//        clearGraphics();
//        mapview.getGraphicsOverlays().remove(graphicsOverlaySatrBJ);
        if(App.type==4){
            clearGraphics();
            iv_all.setVisibility(View.GONE);
            mapview.setVisibility(View.VISIBLE);
        }else {
            iv_all.setImageResource(R.drawable.all);
            ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(iv_all), this);
            List<ClickableArea> clickableAreas = getClickableAreas();
            clickableAreasImage.setClickableAreas(clickableAreas);
        }

        detial.setOnClickListener(v -> {
            if (bottomBar.getCurrentTabPosition() == 0) {
                startActivity(new Intent(this, ZfDetialActicity.class));
            } else if (bottomBar.getCurrentTabPosition() == 1) {
                if (App.type==4){
                    switch (selectQx) {
                        case 0:
                            startActivity(new Intent(MainActivity.this, QxJxDetialActicity.class));
//                            startActivity(new Intent(MainActivity.this, WebViewActicvity.class).putExtra("name",name));
                            break;
                        case 1:
                            startActivity(new Intent(MainActivity.this, WebViewActicvity.class).putExtra("name",name));
                            break;
                        case 2:
//                            startActivity(new Intent(MainActivity.this, QxJxDetialActicity.class));
                            startActivity(new Intent(MainActivity.this, WebViewActicvity.class).putExtra("name",name));
                            break;
                    }
                }else {
                    switch (selectQx) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            startActivity(new Intent(MainActivity.this, QxJxDetialActicity.class));
                            break;
                        case 4:
                            startActivity(new Intent(MainActivity.this, WebViewActicvity.class).putExtra("name",name));
//                        startActivity(new Intent(MainActivity.this, JbDetialActicity.class));
                            break;
                        case 5:
                            startActivity(new Intent(MainActivity.this, QjDetialActicity.class));
                            break;
                        case 6:
                            startActivity(new Intent(MainActivity.this, ThreeDDetialActicity.class));
                            break;
                    }
                }


            } else {
                startActivity(new Intent(this, PointDetialActicity.class).putExtra("name", "测试 "));
            }
        });
        // Define your clickable area (pixel values: x coordinate, y coordinate, width, height) and assign an object to it
    iv_close.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_booton.setVisibility(View.GONE);
        }
    });
        map = new ArcGISMap();
        mapview.setMap(map);
        mapview.setAttributionTextVisible(false);
        MyDefaultOnTouchListener myDefaultOnTouchListener = new MyDefaultOnTouchListener(this, mapview);
        mapview.setOnTouchListener(myDefaultOnTouchListener);
        mapview.getMap().setMaxScale(5500);//修改地图层级
        gpsData = GpsData.getIntance();
        gpsData.addGpsData(this, mapview);
        mapShowManager = new MapShowManager(this, mapview);
        mapShowManager.loadTianDiTu(imageLayer);
        Point point = new Point(116.39722,39.90960, SpatialReference.create(4326));
        mapview.setViewpointCenterAsync(point,1523811);
        mSM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSM.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSM.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);//注册回调函数

        recyclerView_one.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView_one.addItemDecoration(new GridSpacingItemDecoration(3, 4, true));
        typeGridAdapter = new TypeGridAdapter();
        recyclerView_one.setAdapter(typeGridAdapter);
        if(App.type==4){
            list1.add(new TypeBean(R.mipmap.qx, "区界"));
            list1.add(new TypeBean(R.mipmap.jb, "界桩（碑）"));
//            list1.add(new TypeBean(R.mipmap.jb, "市界"));
            list1.add(new TypeBean(R.mipmap.qx, "界碑检测"));
        }else {
            list1.add(new TypeBean(R.mipmap.qx, "区界"));
            list1.add(new TypeBean(R.mipmap.jx, "街道及乡镇"));
            list1.add(new TypeBean(R.mipmap.jx, "社区"));
            list1.add(new TypeBean(R.mipmap.jb, "社区网格"));
            list1.add(new TypeBean(R.mipmap.jb, "界桩（碑）"));
            list1.add(new TypeBean(R.mipmap.qx, "界碑检测"));
//        list1.add(new TypeBean(R.mipmap.jb, "全景"));
            list1.add(new TypeBean(R.mipmap.jb, "三维实景"));
        }
        typeGridAdapter.setList(list1);
        recyclerView_two.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView_two.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        typeGridAdapterTwo = new TypeGridAdapter();
        recyclerView_two.setAdapter(typeGridAdapterTwo);
        list2.add(new TypeBean(R.mipmap.mz, "民政简介"));
        list2.add(new TypeBean(R.mipmap.sq, "社区服务中心"));
        list2.add(new TypeBean(R.mipmap.jz, "救助站"));
        list2.add(new TypeBean(R.mipmap.etfly, "儿童福利院"));
        list2.add(new TypeBean(R.mipmap.yljg, "养老机构"));
        list2.add(new TypeBean(R.mipmap.hydjc, "婚姻登记处"));
        list2.add(new TypeBean(R.mipmap.jxb, "军休办"));
        list2.add(new TypeBean(R.mipmap.flqy, "福利企业"));
        list2.add(new TypeBean(R.mipmap.cscs, "慈善超市"));
        list2.add(new TypeBean(R.mipmap.bzss, "殡葬设施"));
        list2.add(new TypeBean(R.mipmap.lsjnw, "烈士纪念物"));
        list2.add(new TypeBean(R.mipmap.fczx, "福彩中心"));
        typeGridAdapterTwo.setList(list2);
        title.setText(bottomBar.getTabAtPosition(0).getTitle());
        addLayer(bottomBar.getTabAtPosition(0).getTitle(), null);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                clearGraphics();
                if (tabId == R.id.tab_one) {
                    Point point = new Point(116.39722,39.90960, SpatialReference.create(4326));
                    mapview.setViewpointCenterAsync(point,1753811);
//                    String path = bottomBar.getCurrentTab().getTitle()+ "/" + "gk";
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    clearGraphics();
                    if (graphicsOverlaySatr!=null) {
                        clearGraphics();
                        mapview.getGraphicsOverlays().add(graphicsOverlaySatr);
//                        mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
                    }
                    if (graphicsOverlaySatrBJ!=null) {
                        clearGraphics();
                        mapview.getGraphicsOverlays().remove(graphicsOverlaySatrBJ);
                        mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
                    }
//                    else {
//                        mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
//                    }

                    addLayer( bottomBar.getCurrentTab().getTitle() + "/区界" , titleDes);
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/界桩（碑）" , titleDes);

//                    clear();
//                    clearGraphics();
//                    addLayer(path, titleDes);

                    recyclerView_one.setVisibility(View.GONE);
                    recyclerView_two.setVisibility(View.GONE);
                    if(App.type==4){
                        clearGraphics();
                        iv_all.setVisibility(View.GONE);
                        mapview.setVisibility(View.VISIBLE);

                    }else {
                        iv_all.setVisibility(View.VISIBLE);
                        mapview.setVisibility(View.GONE);
                    }
                    ll_booton.setVisibility(View.GONE);
                    List<TypeBean> data = (List<TypeBean>) typeGridAdapter.getData();
                    for (TypeBean typeBean1  : data){
                        typeBean1.select = false;
                    }
                    typeGridAdapter.notifyDataSetChanged();
                    List<TypeBean> data1 = (List<TypeBean>) typeGridAdapterTwo.getData();
                    for (TypeBean typeBean1  : data1){
                        typeBean1.select = false;
                    }
                    typeGridAdapterTwo.notifyDataSetChanged();
                } else if (tabId == R.id.tab_two) {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    Point point = new Point(116.496357,39.943263, SpatialReference.create(4326));
                    mapview.setViewpointCenterAsync(point,380000);//点击区划管理居中显示地图
                    clearGraphics();
//                    if(graphicsOverlaySatrBJ!=null){
//                        mapview.getGraphicsOverlays().remove(graphicsOverlaySatrBJ);
//                    }
                    mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/区界" , titleDes);
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/市界" , titleDes);
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/界桩（碑）" , titleDes);
//                    String path = bottomBar.getCurrentTab().getTitle()+ "/" + "gk";
//                    clear();
//                    clearGraphics();
//                    addLayer(path, titleDes);
                    if (titleDes != null) {
                        //高亮显示所选街道

                    }
                    recyclerView_one.setVisibility(View.VISIBLE);
                    recyclerView_two.setVisibility(View.GONE);
                    iv_all.setVisibility(View.GONE);
                    mapview.setVisibility(View.VISIBLE);
                    ll_booton.setVisibility(View.VISIBLE);
                    if (titleDes != null) {
                        title.setText(titleDes);
                    }
                    typeGridAdapterTwo.notifyDataSetChanged();
                    List<TypeBean> data1 = (List<TypeBean>) typeGridAdapterTwo.getData();
                    for (TypeBean typeBean1  : data1){
                        typeBean1.select = false;
                    }
                    typeGridAdapterTwo.notifyDataSetChanged();

                } else if (tabId == R.id.tab_three) {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    recyclerView_one.setVisibility(View.GONE);
                    recyclerView_two.setVisibility(View.VISIBLE);
                    iv_all.setVisibility(View.GONE);
                    mapview.setVisibility(View.VISIBLE);
                    ll_booton.setVisibility(View.VISIBLE);
                    if (titleDes != null) {
                        title.setText(titleDes);
                    }
                    List<TypeBean> data = (List<TypeBean>) typeGridAdapter.getData();
                    for (TypeBean typeBean1  : data){
                        typeBean1.select = false;
                    }
                    typeGridAdapter.notifyDataSetChanged();

                } else if (tabId == R.id.tab_four) {
                    recyclerView_one.setVisibility(View.GONE);
                    recyclerView_two.setVisibility(View.GONE);
                    iv_all.setVisibility(View.GONE);
                    mapview.setVisibility(View.VISIBLE);
                    ll_booton.setVisibility(View.VISIBLE);
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                if (tabId == R.id.tab_two) {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    if (ll_booton.getVisibility() == View.GONE){
                        ll_booton.setVisibility(View.VISIBLE);
                        recyclerView_one.setVisibility(View.VISIBLE);
                    }else {
                        ll_booton.setVisibility(View.GONE);
                        recyclerView_one.setVisibility(View.GONE);
                    }
                    recyclerView_two.setVisibility(View.GONE);
                } else if (tabId == R.id.tab_three) {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    if (ll_booton.getVisibility() == View.GONE){
                        ll_booton.setVisibility(View.VISIBLE);
                        recyclerView_two.setVisibility(View.VISIBLE);
                    }else {
                        ll_booton.setVisibility(View.GONE);
                        recyclerView_two.setVisibility(View.GONE);
                    }
                    recyclerView_one.setVisibility(View.GONE);

                }else if (tabId == R.id.tab_one){
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    clearGraphics();
                    Point point = new Point(116.39722,39.90960, SpatialReference.create(4326));
                    mapview.setViewpointCenterAsync(point,2500000);
                }
            }
        });
        bottomBar.setTabSelectionInterceptor(new TabSelectionInterceptor() {
            @Override
            public boolean shouldInterceptTabSelection(@IdRes int oldTabId, @IdRes int newTabId) {
                if (newTabId == R.id.tab_four) {
                    startActivity(new Intent(MainActivity.this, LawActivity.class));
                    return true;
                }

                return false;
            }
        });

        typeGridAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                TypeBean typeBean = (TypeBean) adapter.getItem(position);
                List<TypeBean> data = (List<TypeBean>) adapter.getData();
                for (TypeBean typeBean1  : data){
                    if (data.indexOf(typeBean1)==position){
                        typeBean1.select = true;
                    }else {
                        typeBean1.select = false;
                    }
                }
                adapter.notifyDataSetChanged();
                String path = bottomBar.getCurrentTab().getTitle() + "/" + typeBean.name;
                clear();
                if (typeBean.name.contains("界桩")){
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    clearGraphics();
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/区界" , titleDes);
//                    addLayer( bottomBar.getCurrentTab().getTitle() + "/市界" , titleDes);
                    addLayer(path, titleDes);
                    mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
//                }else if (typeBean.name.contains("市界")){
//                    clearGraphics();
//                    addLayer( bottomBar.getCurrentTab().getTitle() + "/区界" , titleDes);
//                    addLayer(path, titleDes);
//                    mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
                }else if (typeBean.name.contains("区界")) {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    clearGraphics();
                    addLayer(path, titleDes);
//                    addLayer( bottomBar.getCurrentTab().getTitle() + "/市界" , titleDes);
                    mapview.getGraphicsOverlays().add(graphicsOverlaySatr);
                }
                else if (typeBean.name.contains("界碑检测")){
                    if (mapview.getGraphicsOverlays().contains(gifGraphicsOverlay)){
                        mapview.getGraphicsOverlays().contains(gifGraphicsOverlay);
                    }

                    addGifMarker();
//                    Point point1 = new Point(116.508,40.208, SpatialReference.create(4326));
////                    pictureMarkerSymbol:pictureMarkerSymbol = pictureMarkerSymbol(url:)
//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.star);
//                    try {
//                        Point point1 = new Point(116.508,40.208, SpatialReference.create(4326));
//                        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.gps_png_wide);
//                        pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
//                        graphicsOverlayGif.getGraphics().add( new Graphic(point1, pictureMarkerSymbol));
//                        mapview.getGraphicsOverlays().add(graphicsOverlayGif);
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


                    clearGraphics();
                    addLayer( bottomBar.getCurrentTab().getTitle() + "/区界" , titleDes);
                    addLayer(path, titleDes);
                    mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
                }
                else {
                    mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
                    clearGraphics();
                    addLayer(path, titleDes);
                }
                selectQx = position;

            }
        });
        typeGridAdapterTwo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                TypeBean typeBean = (TypeBean) adapter.getItem(position);
                List<TypeBean> data = (List<TypeBean>) adapter.getData();
                for (TypeBean typeBean1  : data){
                    if (data.indexOf(typeBean1)==position){
                        typeBean1.select = true;
                    }else {
                        typeBean1.select = false;
                    }
                }
                adapter.notifyDataSetChanged();
                String path = bottomBar.getCurrentTab().getTitle() + "/" + typeBean.name;
                clear();
                addLayer(path, titleDes);
//                recyclerView_one.setVisibility(View.GONE);
//                recyclerView_two.setVisibility(View.GONE);

            }


        });
        iv_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLayer = !imageLayer;
                if (imageLayer) {
/*                    iv_layer.setImageResource(R.mipmap.layer_img);
                    mapShowManager.loadTianDiTu(true);*/

                    iv_layer.setImageResource(R.mipmap.layer);
                    mapShowManager.loadTianDiTu(true);
                } else {
//                    iv_layer.setImageResource(R.mipmap.layer);
//                    mapShowManager.loadTianDiTu(false);

                    iv_layer.setImageResource(R.mipmap.layer_img);
                    mapShowManager.loadTianDiTu(false);
                }

            }
        });
        graphicsOverlaySatr = new GraphicsOverlay();
        graphicsOverlaySatrBJ = new GraphicsOverlay();
        graphicsOverlayGif = new GraphicsOverlay();
        mapview.getGraphicsOverlays().add(graphicsOverlayGif);
        mapview.getGraphicsOverlays().add(graphicsOverlaySatr);
        mapview.getGraphicsOverlays().add(graphicsOverlaySatrBJ);
        ArrayList<StarBean> arrayList = new ArrayList<>();
        arrayList.add(new StarBean( "海淀区  ",new Point(  116.304782,39.966128, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "门头沟区 ",new Point(  116.108265,39.946491, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "丰台区  ",new Point(  116.293111,39.865036, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "石景山区 ",new Point(  116.229603,39.912008, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "顺义区  ",new Point(  116.667284,40.156062, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "昌平区  ",new Point(  116.23786, 40.226799, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "房山区  ",new Point(  116.149892,39.755039, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "通州区  ",new Point(  116.735549,39.922355, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "平谷区  ",new Point(  117.12805,40.147034, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "密云区  ",new Point(   116.849427,40.382955, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "大兴区  ",new Point(   116.348097,39.732805, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "怀柔区  ",new Point(    116.638526,40.322545, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "西城区  ",new Point(   116.3724,39.918562, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "朝阳区  ",new Point(     116.449767,39.927254, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "东城区  ",new Point(  116.422731,39.934568, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "延庆区  ",new Point(  115.981186,40.462693, SpatialReference.create(4326))));
        arrayList.add(new StarBean( "天安门  ",new Point(  116.403981,39.914935, SpatialReference.create(4326))));



        arrayList.add(new StarBean( "京冀线18",new Point(  116.633,41.051, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线17",new Point(  116.425,40.907, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线16",new Point(  116.462,40.787, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线15",new Point(  116.162,40.649, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线12",new Point(  115.896,40.227, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线10",new Point(  115.504,39.901, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线8",new Point(  116.021,39.575, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线9",new Point(  115.710,39.56, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线6",new Point(  116.422,39.49, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线5",new Point(  116.688,39.595, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京津冀",new Point(  116.814,39.605, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京津冀IIIA",new Point(  116.909,39.676, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线4",new Point(  116.954,39.781, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线2",new Point(  116.829,40.037, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线1",new Point(  117.105,40.057, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京津冀II",new Point(  117.215,40.077, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京津冀I",new Point(  117.396,40.222, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线22",new Point(  117.215,40.498, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线21",new Point(  117.456,40.669, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线20",new Point(  117.175,40.699, SpatialReference.create(4326)),true));
        arrayList.add(new StarBean( "京冀线19",new Point(  116.819,40.835, SpatialReference.create(4326)),true));
        //arrayList.add(new StarBean( " ",new Point(  116.508,40.208, SpatialReference.create(4326)),true));

        arrayList.add(new StarBean( "北京市政府  ",new Point(  116.731394,39.910433, SpatialReference.create(4326))));
        for (StarBean starBean : arrayList) {
            addStar(starBean);
        }

    }

    private void clearGraphics() {
        if (mapview.getGraphicsOverlays().contains(graphicsOverlaySatr)) {
            mapview.getGraphicsOverlays().remove(graphicsOverlaySatr);
        }
        if (mapview.getGraphicsOverlays().contains(graphicsOverlaySatrBJ)) {
            mapview.getGraphicsOverlays().remove(graphicsOverlaySatrBJ);
        }
    }

    private void addStar(StarBean starBean)  {

        try {
            Point point = (Point) GeometryEngine.project(starBean.getPoint(), SpatialReference.create(MyApplication.locationType));
//            if (pictureMarkerSymbol==null) {
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.star);
//                pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
//
//            }
            if (starBean.getShowLable()){
                BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.star);
                pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
                graphicsOverlaySatr.getGraphics().add(new Graphic(point, pictureMarkerSymbol));
//                addGifMarker();

//                ImageView imageView = findViewById(R.id.mapView);
//                Glide.with(this)
//                        // 加载网络图片
////                        .load("https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif")
////                        // 加载drawable
//                .load(R.drawable.gif)
////                         加载assets资源的loading.gif 图片
////                .load("file:///android_asset/loading.gif")
//                        .into(imageView);

//                BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.make2);
//                pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
//                pictureMarkerSymbol = new PictureMarkerSymbol("https://hbimg.b0.upaiyun.com/57b164a16451b1c60b66cd9049eb417fe1a5169e6baa-Kbl5yT_fw658");
//                Point point1 = new Point(116.731394,39.910433, SpatialReference.create(4326));


//                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//                PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(this, drawable);
//                Graphic graphic = new Graphic(new Point(113,22), pictureMarkerSymbol);
//                PictureMarkerSymbol pic = new PictureMarkerSymbol((BitmapDrawable) getResources().getDrawable(R.drawable.gif));
//                ImageView imageView = new ImageView(App.getContext());
//                String url = "R.drawable.gif";
//                GlideEngine.createGlideEngine().loadAsGifImage(App.getContext(),url,imageView);

//                pictureMarkerSymbol.loadAsync();
//                graphicsOverlaySatrBJ.getGraphics().add( new Graphic(point, pictureMarkerSymbol));

            }else {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.star);
                pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
                graphicsOverlaySatr.getGraphics().add(new Graphic(point, pictureMarkerSymbol));
            }
            if (starBean.getShowLable()){
            TextSymbol textSymbol = new TextSymbol(
                    14f,
                    starBean.getName(),
                    Color.RED,
                    TextSymbol.HorizontalAlignment.CENTER,
                    TextSymbol.VerticalAlignment.BOTTOM
            );
//            textSymbol.setBackgroundColor(Color.argb(50, 0, 0, 0)) ;
            textSymbol.setOffsetY( -28f);
                graphicsOverlaySatrBJ.getGraphics().add( new Graphic(point, textSymbol));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addLayer(String path, String titleDes) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AddGridLayerMabager.Companion.addLayer(mapview, path, titleDes);
                return null;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (loadingPopupView==null)
                loadingPopupView = new XPopup.Builder(MainActivity.this).asLoading("数据加载中...");
                loadingPopupView.show();
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                loadingPopupView.dismiss();
            }
        }.execute();

    }

    private void clear() {
        if (App.type==4){
            map.getOperationalLayers().removeAll( map.getOperationalLayers());
        }else {
            if (App.featureSelect != null) {
                App.featureSelect.getFeatureTable().getFeatureLayer().unselectFeature(App.featureSelect);
            }
            App.featureSelect = null;
            ArrayList<Layer> layers = new ArrayList<>();
            for (Layer layer : map.getOperationalLayers()) {
                if (map.getOperationalLayers().indexOf(layer) != 0) {
                    layers.add(layer);
                }
            }
            map.getOperationalLayers().removeAll(layers);
        }


    }

//    private void extracted(String string) {
////                String str =  "{\"queryType\":\"1\",\"start\":0,\"mapBound\":\"116.38045981152607,39.86404339586278,116.5521211884788,39.94779265439931\",\"yingjiType\":0,\"queryTerminal\":10000,\"level\":12,\"keyWord\":\""+ string+"\",\"count\":10,\"sourceType\":0}";
////                Client.getApiService().searchPoint("query",str,"b7cf60e0001b061040a63cdedb2bceec").compose(RxsRxSchedulers.<DataBean>io_main())
//        Client.getApiService().searchPoint().compose(RxsRxSchedulers.<DataBean>io_main())
////                    .compose(new DialogTransformer(this).<BaseBean>transformer())
//                .subscribe(new ApiServiceResult() {
//                    @Override
//                    public void onNext(@NonNull Object o) {
//                        DataBean dataBean = (DataBean) o;
//                        if (dataBean.getPois() == null) {
//                            return;
//                        }
//                        if (pointMarke == null) {
//                            pointMarke = new GraphicsOverlay();
//                            mapview.getGraphicsOverlays().add(pointMarke);
//                        } else {
//                            pointMarke.getGraphics().clear();
//                        }
//                        for (DataBean.PoisBean poisBean : dataBean.getPois()) {
//                            String lonlat = poisBean.getLonlat();
//                            String[] split = lonlat.split(",");
//                            double lon = Double.parseDouble(split[0]);
//                            double lat = Double.parseDouble(split[1]);
//                            Point point = new Point(
//                                    lon,
//                                    lat,
//                                    SpatialReference.create(MyApplication.locationType)
//                            );
//                            Map map = new HashMap();
//                            map.put("name", poisBean.getName());
//                            PictureMarkerSymbol symbol = new PictureMarkerSymbol(
//                                    (BitmapDrawable) ContextCompat.getDrawable(
//                                            MainActivity.this,
//                                            R.mipmap.point
//                                    ));
//                            pointMarke.getGraphics().add(new Graphic(point, map, symbol));
//                        }
//                        mapview.setViewpointGeometryAsync(pointMarke.getExtent(), 100);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        super.onError(throwable);
//                    }
//
//                    @Override
//                    public void onFinish(com.utile.strong_sun.http.BaseBean baseBean) {
//
//                    }
//                });
//    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean onGoBack() {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float tempAzimuth = 0;
            float degree = sensorEvent.values[0];
            float bearing = (float) mapview.getMapRotation();
            tempAzimuth = degree + (360 - bearing);
            gpsData.updateRotation(tempAzimuth);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAttribute(Feature feature) {
        title.setText("");
        if (App.featureSelect != null) {
            if ( App.featureSelect.getFeatureTable()!=null&&App.featureSelect.getFeatureTable().getFeatureLayer()!=null) {
                App.featureSelect.getFeatureTable().getFeatureLayer().unselectFeature(App.featureSelect);
            }else {
                App.featureSelect = null;
            }
        }
        App.featureSelect = feature;
        feature.getFeatureTable().getFeatureLayer().selectFeature(feature);
        Map<String, Object> attributes = feature.getAttributes();
        if (attributes.containsKey("level")){
            detial.setVisibility(View.GONE);
            if (attributes.containsKey("name")){
                title.setText(attributes.get("name").toString());
                name = attributes.get("name").toString();
            }
        }else {
            detial.setVisibility(View.VISIBLE);
            if (attributes.containsKey("name")){
                title.setText(attributes.get("name").toString());
                name = attributes.get("name").toString();
            }
        }
        if (feature.getGeometry() instanceof Polyline){
            title.setText(title.getText().toString().trim()+"--长度:"+attributes.get("length").toString()+"米");
        }else if (feature.getGeometry() instanceof Polygon){
            title.setText(title.getText().toString().trim()+"--面积:"+attributes.get("area").toString()+"平方米");
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mapview.dispose();
        mSM.unregisterListener(this);
        gpsData.removeGpsStatusListener();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mapview.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapview.pause();
        super.onPause();
    }

    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof State) {
            String text = ((State) item).getName();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            titleDes = text;
            bottomBar.selectTabAtPosition(1);
            title.setText("");
            try {
            if (map.getOperationalLayers().size()>0) {
                QueryParameters query = new QueryParameters();
                query.setReturnGeometry(true);
                query.setWhereClause("1=1");
                FeatureLayer featureLayer   = (FeatureLayer) map.getOperationalLayers().get(0);
                ListenableFuture<FeatureQueryResult>  featureQueryResult =
                        featureLayer.getFeatureTable().queryFeaturesAsync(query);
                FeatureQueryResult result = featureQueryResult.get();
                Iterator<Feature> iterator =result.iterator();
                while (iterator.hasNext()) {
                    Feature  feature = iterator.next();
                    if (feature.getAttributes().containsKey("name")&&feature.getAttributes().get("name").equals(text)) {
                        if (App.featureSelect != null) {
                            App.featureSelect.getFeatureTable().getFeatureLayer().unselectFeature(App.featureSelect);
                        }
                        title.setText(text);
                        App.featureSelect = feature;
                        feature.getFeatureTable().getFeatureLayer().selectFeature(feature);
                        mapview.setViewpointGeometryAsync(feature.getGeometry(),100);
                        if (feature.getGeometry() instanceof Polyline){
                            title.setText(title.getText().toString().trim()+"--长度:"+feature.getAttributes().get("length").toString()+"米");
                        }else if (feature.getGeometry() instanceof Polygon){
                            title.setText(title.getText().toString().trim()+"--面积:"+feature.getAttributes().get("area").toString()+"平方米");
                        }
                        break;
                    }

                }
            }
            }catch (Exception e){}
        }
    }


    @NonNull
    private List<ClickableArea> getClickableAreas() {

        List<ClickableArea> clickableAreas = new ArrayList<>();
        clickableAreas.add(new ClickableArea(203, 473, 100, 30, new State("来广营地区")));
        clickableAreas.add(new ClickableArea(30, 534, 100, 30, new State("奥运村地区")));
        clickableAreas.add(new ClickableArea(313, 583, 100, 30, new State("东湖街道")));
        clickableAreas.add(new ClickableArea(146, 596, 100, 30, new State("大屯街道")));
        clickableAreas.add(new ClickableArea(299, 632, 100, 30, new State("望京街道")));
        clickableAreas.add(new ClickableArea(54, 671, 100, 30, new State("亚运村街道")));
        clickableAreas.add(new ClickableArea(214, 750, 100, 30, new State("太阳宫地区")));
        clickableAreas.add(new ClickableArea(138, 787, 100, 30, new State("和平街街道")));
        clickableAreas.add(new ClickableArea(262, 793, 100, 30, new State("麦子店街道")));
        clickableAreas.add(new ClickableArea(226, 830, 100, 30, new State("左家庄街道")));
        clickableAreas.add(new ClickableArea(178, 940, 100, 30, new State("香河园街道")));
        clickableAreas.add(new ClickableArea(226, 1007, 100, 30, new State("三里屯街道")));
        clickableAreas.add(new ClickableArea(211, 1095, 100, 30, new State("朝外街道")));
        clickableAreas.add(new ClickableArea(324, 1193, 100, 30, new State("建外街道")));
        clickableAreas.add(new ClickableArea(284, 1234, 100, 30, new State("双井街道")));
        clickableAreas.add(new ClickableArea(244, 1285, 100, 30, new State("劲松街道")));
        clickableAreas.add(new ClickableArea(344, 1326, 100, 30, new State("南磨房地区")));
        clickableAreas.add(new ClickableArea(260, 1363, 100, 30, new State("潘家园街道")));
        clickableAreas.add(new ClickableArea(465, 1409, 100, 30, new State("垡头街道")));
        clickableAreas.add(new ClickableArea(379, 1511, 100, 30, new State("十八里店地区")));
        clickableAreas.add(new ClickableArea(273, 1559, 100, 30, new State("小红门地区")));
        clickableAreas.add(new ClickableArea(897, 368, 100, 30, new State("首都机场街道")));
        clickableAreas.add(new ClickableArea(642, 433, 100, 30, new State("孙河地区")));
        clickableAreas.add(new ClickableArea(603, 584, 100, 30, new State("崔各庄地区")));
        clickableAreas.add(new ClickableArea(934, 735, 100, 30, new State("金盏地区")));
        clickableAreas.add(new ClickableArea(567, 734, 100, 30, new State("酒仙桥街道")));
        clickableAreas.add(new ClickableArea(653, 780, 100, 30, new State("将台地区")));
        clickableAreas.add(new ClickableArea(767, 819, 100, 30, new State("东坝地区")));
        clickableAreas.add(new ClickableArea(582, 830, 100, 30, new State("酒仙桥街道")));
        clickableAreas.add(new ClickableArea(612, 873, 100, 30, new State("东风地区")));
        clickableAreas.add(new ClickableArea(760, 913, 100, 30, new State("平房地区")));
        clickableAreas.add(new ClickableArea(928, 963, 100, 30, new State("常营地区")));
        clickableAreas.add(new ClickableArea(627, 1023, 100, 30, new State("六里屯街道")));
        clickableAreas.add(new ClickableArea(644, 1063, 100, 30, new State("八里庄街道")));
        clickableAreas.add(new ClickableArea(774, 1120, 100, 30, new State("高碑店地区")));
        clickableAreas.add(new ClickableArea(870, 1150, 100, 30, new State("三间房地区")));
        clickableAreas.add(new ClickableArea(933, 1172, 100, 30, new State("管庄地区")));
        clickableAreas.add(new ClickableArea(884, 1384, 100, 30, new State("黑庄户地区")));
        clickableAreas.add(new ClickableArea(688, 1396, 100, 30, new State("王四营地区")));
        clickableAreas.add(new ClickableArea(809, 1414, 100, 30, new State("豆各庄地区")));
        clickableAreas.add(new ClickableArea(648, 1480, 100, 30, new State("王四营地区")));
        return clickableAreas;
    }


//添加动画模拟gif
    private GraphicsOverlay gifGraphicsOverlay = new GraphicsOverlay();
    private List<Point> data = new ArrayList<>();
    private List<PictureMarkerSymbol> gifSymbol = new ArrayList<>();
    private int index = 0;

    private Handler handler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 11:
                    index++;
                    if (index >= gifSymbol.size() - 1) {
                        index = 0;
                    }
                    for (Graphic graphic : gifGraphicsOverlay.getGraphics()) {
                        graphic.setSymbol(gifSymbol.get(index));
                    }
                    setData();
                    break;
            }
            return false;
        }
    });

    public void addGifMarker() {
        if(mapview.getGraphicsOverlays().contains(gifGraphicsOverlay)){
            mapview.getGraphicsOverlays().remove(gifGraphicsOverlay);
        }
        mapview.getGraphicsOverlays().add(gifGraphicsOverlay);
        //Point point1 = new Point(116.39722,39.90960, SpatialReference.create(4326));
        Point point1 = new Point(  116.633,41.051, SpatialReference.create(4326));
        Point point2 = new Point(  116.425,40.907, SpatialReference.create(4326));
        Point point3 = new Point(  116.462,40.787, SpatialReference.create(4326));
        Point point4 = new Point(  116.162,40.649, SpatialReference.create(4326));
        Point point5 = new Point(  115.896,40.227, SpatialReference.create(4326));
        Point point6 = new Point(  115.504,39.901, SpatialReference.create(4326));
        Point point7 = new Point(  116.021,39.575, SpatialReference.create(4326));
        Point point8 = new Point(  115.710,39.56, SpatialReference.create(4326));
        Point point9 = new Point(  116.422,39.49, SpatialReference.create(4326));
        Point point10 = new Point(  116.688,39.595, SpatialReference.create(4326));
        Point point11 = new Point(  116.814,39.605, SpatialReference.create(4326));
        Point point12 = new Point(  116.909,39.676, SpatialReference.create(4326));
        Point point13 = new Point(  116.954,39.781, SpatialReference.create(4326));
        Point point14 = new Point(  116.829,40.037, SpatialReference.create(4326));
        Point point15 = new Point(  117.105,40.057, SpatialReference.create(4326));
        Point point16 = new Point(  117.215,40.077, SpatialReference.create(4326));
        Point point17 = new Point(  117.396,40.222, SpatialReference.create(4326));
        Point point18 = new Point(  117.215,40.498, SpatialReference.create(4326));
        Point point19 = new Point(  117.456,40.669, SpatialReference.create(4326));
        Point point20 = new Point(  117.175,40.699, SpatialReference.create(4326));
        Point point21 = new Point(  116.819,40.835, SpatialReference.create(4326));
        String string = new String();


        data.add(point1);
        data.add(point2);
        data.add(point3);
        data.add(point4);
        data.add(point5);
        data.add(point6);
        data.add(point7);
        data.add(point8);
        data.add(point9);
        data.add(point10);
        data.add(point11);
        data.add(point13);
        data.add(point12);
        data.add(point14);
        data.add(point15);
        data.add(point16);
        data.add(point17);
        data.add(point18);
        data.add(point19);
        data.add(point20);
        data.add(point21);



        gifSymbol.add(new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(App.getContext(),R.mipmap.gif1)));
        gifSymbol.add(new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.gif2)));
        gifSymbol.add(new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.gif3)));
//        gifSymbol.add(new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.make2)));

        for (Point point : data) {
            gifGraphicsOverlay.getGraphics().add(new Graphic(point, gifSymbol.get(0)));
        }
        setData();
    }

    public void setData() {
        handler2.sendEmptyMessageDelayed(11, 300);
    }




    //添加视频



    private void addmp4(Point point) throws ExecutionException, InterruptedException {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(App.getContext(), R.mipmap.star);
        pictureMarkerSymbol = PictureMarkerSymbol.createAsync(bitmapDrawable).get();
        graphicsOverlaySatr.getGraphics().add(new Graphic(point, pictureMarkerSymbol));
//
//        TMarker marker = new TMarker();
//        marker.setCoordinate(new TCoordinate(latitude, longitude)); // 设置标记点的经纬度坐标
//        marker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.marker)); // 设置标记点的图标
//
//        // 添加标记点到地图上

//        mapview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(PictureMarkerSymbol pictureMarkerSymbol) {
//
//            }
//        }){
//
//        }
//        mapController.addMarker(marker);
////        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/video_file_name");
////        marker.setTag(videoUri); // 将视频文件的Uri作为标记点的tag
//
//        mapController.setOnMarkerClickListener(new TMapController.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(TMarker marker) {
//                // 播放与标记关联的视频文件
//                Uri videoUri = (Uri) marker.getTag();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(videoUri, "video/*");
//                startActivity(intent);
//                return true;
//            }
//        });
    }










}
