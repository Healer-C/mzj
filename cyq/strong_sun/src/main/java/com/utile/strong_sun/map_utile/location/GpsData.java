package com.utile.strong_sun.map_utile.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.text.TextUtils;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.utile.strong_sun.MyApplication;
import com.utile.strong_sun.R;
import com.utile.strong_sun.utiles.ToastUtils;

import java.util.List;
import java.util.Observable;

public class GpsData extends Observable implements TencentLocationListener {

    public static LocationDisplay mLocationDisplay;
    private static GraphicsOverlay mGraphicsOverlay;
    private static Graphic locationGraphic;

    private TencentLocationManager mLocationManager;
    Context mContext;

    CustomDataSource dataSource;
    private PictureMarkerSymbol campsiteSymbol;
    private Point point;
    private MapView mapView;
    private static GpsData gpsData = new GpsData();
    private float mDirection;


    public GpsData() {
    }

    public static GpsData getIntance() {
        if (gpsData==null){
            gpsData = new GpsData();
        }
        return gpsData;
    }

    public void addGpsData(Context context, MapView mapView) {
        this.mContext = context;
        this.mapView = mapView;
        mLocationManager = TencentLocationManager.getInstance(context);
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_WGS84);
        mLocationDisplay = mapView.getLocationDisplay();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), R.mipmap.gps_png_wide));
        campsiteSymbol = new PictureMarkerSymbol(bitmapDrawable);
        campsiteSymbol.setAngle(0);
        campsiteSymbol.loadAsync();
        campsiteSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                dataSource = new CustomDataSource();
                addGpsStatusListener();
                mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                mLocationDisplay.setLocationDataSource(dataSource);
                mLocationDisplay.setOpacity(1);
//        mLocationDisplay.setPingAnimationSymbol(markerSymbol);
//                mLocationDisplay.setShowPingAnimation(true);
                mLocationDisplay.setDefaultSymbol(campsiteSymbol);
                mLocationDisplay.setHeadingSymbol(campsiteSymbol);
                mLocationDisplay.setShowAccuracy(true);//隐藏符号的缓存区域
                mLocationDisplay.setShowLocation(true);
                mLocationDisplay.setUseCourseSymbolOnMovement(true);
                mLocationDisplay.startAsync();
            }
        });
        locationGraphic = new Graphic();
        mGraphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    @SuppressLint("MissingPermission")
    public void addGpsStatusListener() {
        XXPermissions.with(mContext)
                // 申请单个权限
                .permission(Permission.ACCESS_FINE_LOCATION).request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
                if (all) {
                    startGps();
                } else {
                    ToastUtils.showShortToast(mContext, "获取权限不完整，请在系统权限中开放定位权限", 1);
                }
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    ToastUtils.showShortToast(mContext, "定位权限，被永久拒绝授权，请手动开启", 1);
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.startPermissionActivity(mContext, permissions);
                } else {
                    ToastUtils.showShortToast(mContext, "获取定位权限失败，请在系统权限中开放定位权限", 1);
                }
            }
        });

    }

    public void startGps() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(1000);
        request.setAllowDirection(true);
        request.setAllowGPS(true);
        request.setQQ("627693415");
        request.setIndoorLocationMode(true);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        mLocationManager.requestLocationUpdates(request, this);
    }

    public void removeGpsStatusListener() {
        mLocationManager.removeUpdates(this);
        mGraphicsOverlay.getGraphics().clear();
    }

    public void updateRotation(float degree) {
        float bearing = (float) mapView.getMapRotation();
        float tempAzimuth = degree + (360 - bearing);
//        float  tempAzimuth = degree;
//        System.out.println("north:" + degree + "  map" + bearing);
//        System.out.println(tempAzimuth);
        String s = String.valueOf(degree);
        if (TextUtils.isEmpty(s) || s.equals("NaN")) {
            return;
        }
        campsiteSymbol.setAngle((float) (tempAzimuth));
        mLocationDisplay.setHeadingSymbol(campsiteSymbol);

    }

    public void setCenter() {
        if (point == null) {
            ToastUtils.showShortToast(mContext, "当前Gps未定位", 0);
        } else {
            mapView.setViewpointCenterAsync(point, 5000);
        }
    }

    public Point getGps() {
        if (point == null) {
            return new Point(0, 0);
        } else {
            return (Point) GeometryEngine.project(point, SpatialReference.create(4326));
        }
    }

    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (tencentLocation.getLongitude() != 0 && tencentLocation.getLatitude() != 0) {
            mDirection = (float) tencentLocation.getDirection();
            point = new Point(tencentLocation.getLongitude(), tencentLocation.getLatitude(), SpatialReference.create(4326));
            point = (Point) GeometryEngine.project(point, SpatialReference.create(MyApplication.locationType));
            LocationDataSource.Location agLocation = new LocationDataSource.Location(point);
            dataSource.UpdateLocation(agLocation);

        }
    }


    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}