package com.utile.strong_sun.map_utile.show_map;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.utile.strong_sun.MyApplication;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyDefaultOnTouchListener extends DefaultMapViewOnTouchListener {
    private MapView mapView;
    private Context mContext;

    public MyDefaultOnTouchListener(Context context, MapView mapView) {
        super(context, mapView);
        this.mContext = context;
        this.mapView = mapView;
    }
    @Override
    public boolean onRotate(MotionEvent motionEvent, double v) {
        return false;
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        android.graphics.Point point = new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY()));
        ListenableFuture<List<IdentifyLayerResult>> identifyLayerResultsFuture = mapView.identifyLayersAsync(point, 12, false, 3);
        List<IdentifyLayerResult> identifyLayerResults = null;
        try {
            identifyLayerResults = identifyLayerResultsFuture.get();
            ArrayList<GeoElement> data = new ArrayList();
            for (IdentifyLayerResult identifyLayerResult : identifyLayerResults) {
                List<GeoElement> elements = identifyLayerResult.getElements();
                data.addAll(elements);
            }
            if (data.size()>0){
                EventBus.getDefault().post((Feature)data.get(0));
            }

        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

//        ListenableFuture<List<IdentifyGraphicsOverlayResult>> identifyLayerResultsFuture = mapView.identifyGraphicsOverlaysAsync(point, 12, false, 1);
//        try {
//            List<IdentifyGraphicsOverlayResult> identifyGraphicsOverlayResults = identifyLayerResultsFuture.get();
//            if (identifyGraphicsOverlayResults.size() > 0) {
//                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = identifyGraphicsOverlayResults.get(0);
//                if (identifyGraphicsOverlayResult.getGraphics().size() > 0) {
//                    Graphic graphic = identifyGraphicsOverlayResult.getGraphics().get(0);
//                    EventBus.getDefault().post(graphic);
//                }
//                ;
//            }
//        } catch (ExecutionException executionException) {
//            executionException.printStackTrace();
//        } catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//        }

        return super.onSingleTapUp(e);
    }
}

