package com.utile.strong_sun.map_utile.show_map;

import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR;
import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_IMAGE_MERCATOR;
import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_VECTOR_2000;
import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000;
import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR;
import static com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerTypes.TIANDITU_VECTOR_MERCATOR;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.esri.arcgisruntime.data.GeodatabaseFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.utile.strong_sun.base.BaseActivity;
import com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayer;
import com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuLayerBuilder;
import com.utile.strong_sun.map_utile.tianditu_cache_lib.TianDiTuMethodsClass;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class MapShowManager {
    private Context mContext;
    private MapView mapView;
    // 点符号
    private SimpleMarkerSymbol mSimpleMarkerSymbol;
    // 线符号
    private SimpleLineSymbol mSimpleLineSymbol;
    // 面填充线样式
    private SimpleLineSymbol mSimpleLineSymbol_fill;
    // 面符号
    private SimpleFillSymbol mSimpleFillSymbol;
    private final BaseActivity activity;
    public static List<GeodatabaseFeatureTable> featureTables;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dataCount--;
            if (dataCount == 0) {
                EventBus.getDefault().post("lodingSuccess");
            }
        }
    };
    private int dataCount;

    public MapShowManager(Context context, MapView mapView) {
        this.mContext = context;
        this.mapView = mapView;

        activity = (BaseActivity) mContext;

    }

    public void addLayer() {

    }




    // 加载天地图影像
    private TianDiTuLayer img_mercator, iac_mercator;

    public void loadTianDiTu(boolean isImage) {
        if (img_mercator!=null){
            mapView.getMap().getBasemap().getBaseLayers().remove(img_mercator);
        }
        if (iac_mercator!=null){
            mapView.getMap().getBasemap().getBaseLayers().remove(iac_mercator);
        }
        String cachePath = mContext.getFilesDir() + "/TianDiTuCache/"+(isImage?"img":"iac");
        if (isImage) {
            img_mercator = new TianDiTuLayerBuilder()
                    .setLayerType(TIANDITU_IMAGE_MERCATOR)
                    .setCachePath(cachePath)
                    .setToken(TianDiTuMethodsClass.key)
                    .build();
            img_mercator.loadAsync();
            iac_mercator = new TianDiTuLayerBuilder()
                    .setLayerType(TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR)
                    .setCachePath(cachePath)
                    .setToken(TianDiTuMethodsClass.key)
                    .build();
            iac_mercator.loadAsync();
            mapView.getMap().getBasemap().getBaseLayers().add(img_mercator);
            mapView.getMap().getBasemap().getBaseLayers().add(iac_mercator);
        }else {
            img_mercator = new TianDiTuLayerBuilder()
                    .setLayerType(TIANDITU_VECTOR_MERCATOR)
                    .setCachePath(cachePath)
                    .setToken(TianDiTuMethodsClass.key)
                    .build();
            img_mercator.loadAsync();
            iac_mercator = new TianDiTuLayerBuilder()
                    .setLayerType(TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR)
                    .setCachePath(cachePath)
                    .setToken(TianDiTuMethodsClass.key)
                    .build();
            iac_mercator.loadAsync();
            mapView.getMap().getBasemap().getBaseLayers().add(img_mercator);
            mapView.getMap().getBasemap().getBaseLayers().add(iac_mercator);
        }
    }
    /**
     * 初始化业务图层-shapefile
     */
//    public void initOperationalLayers() {
//        String path = SystemDirPath.getDefaultDisplayLayersLayers();
//        String json = ReadJsonManager.read(SystemDirPath.getDisplayPathJson());
//        DisplayLayers displayLayers = null;
//        try {
//            Gson gson = new Gson();
//            displayLayers = gson.fromJson(json, DisplayLayers.class);
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//        }
//        SystemDirPath.DisplayLayers = displayLayers;
//        List<FileUtils.FileInfo> newFiles = new ArrayList<>();
//        boolean isSeniorSymbol = false;
//        DictionarySymbolStyle symbolStyle = null;
//        DdataBean ddataBean = null;
//        StyleBean styleBean = null;
//        PipelineFlowBean pipelineFlowBean = null;
//        //无高级符号化
//        if (displayLayers != null && displayLayers.isIsSeniorSymbol()) {
//            isSeniorSymbol = true;
//        }
//
//        if (displayLayers == null || displayLayers.getDisplayLayers() == null || displayLayers.getDisplayLayers().size() == 0) {
//            newFiles = FileUtils.getFileListInfo(path, "shp");
//        } else {
//            List<FileUtils.FileInfo> fileInfos = FileUtils.getFileListInfo(path, "shp");
//
//            for (DisplayLayers.DisplayLayersBean displayLayersBean : displayLayers.getDisplayLayers()) {
//                for (FileUtils.FileInfo fileInf : fileInfos) {
//                    if (fileInf.FileName.replace(".shp", "").equals(displayLayersBean.getName())) {
//                        fileInf.colour = displayLayersBean.getColour();
//                        fileInf.maximumScale = displayLayersBean.getMaximumScale();
//                        fileInf.minimumScale = displayLayersBean.getMinimumScale();
//                        newFiles.add(fileInf);
//                    }
//                }
//            }
//        }
//        if (isSeniorSymbol) {
//            List<FileUtils.FileInfo> fileListInfo = FileUtils.getFileListInfo(SystemDirPath.getDefaultConfig(), "stylx");
//            if (fileListInfo != null && fileListInfo.size() > 0) {
//                symbolStyle = new DictionarySymbolStyle("mil2525d", fileListInfo.get(0).FilePath);
//            }
//            String json1 = ReadJsonManager.read(SystemDirPath.getDefaultConfig() + "/PipelineClass.json");
//            String json2 = ReadJsonManager.read(SystemDirPath.getDefaultConfig() + "/PipelineCode.json");
//            String json3 = ReadJsonManager.read(SystemDirPath.getDefaultConfig() + "/pipelineFlowDir.json");
//            if (symbolStyle != null && json1 != null && json2 != null) {
//                Gson gson = new Gson();
//                ddataBean = gson.fromJson(json1, DdataBean.class);
//                styleBean = gson.fromJson(json2, StyleBean.class);
//                if (json3!=null){
//                    pipelineFlowBean = gson.fromJson(json3, PipelineFlowBean.class);
//                }
//            }
//        }
//        if (newFiles == null) return;
//        Collections.reverse(newFiles);
//        for (int i = 0; i < newFiles.size(); i++) {
//            FileUtils.FileInfo fileInfo = newFiles.get(i);
//            final ShapefileFeatureTable shapefileFeatureTable = new ShapefileFeatureTable(fileInfo.FilePath);
//            shapefileFeatureTable.loadAsync();//异步方式读取文件
//            DictionarySymbolStyle finalSymbolStyle = symbolStyle;
//            MyApplication.symbolStyle = symbolStyle;
//            DdataBean finalDdataBean = ddataBean;
//            StyleBean finalStyleBean = styleBean;
//            MyApplication.ddataBean = finalDdataBean;
//            MyApplication.pipelineFlowBean = pipelineFlowBean;
//            boolean finalIsSeniorSymbol = isSeniorSymbol;
//            shapefileFeatureTable.addDoneLoadingListener(() -> {
//                FeatureLayer mainShapefileLayer = null;
//                SimpleRenderer simpleRenderer = null;
//                mainShapefileLayer = new FeatureLayer(shapefileFeatureTable);
//                mainShapefileLayer.setVisible(EasySP.init(mContext).getBoolean(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "shp", false));
//                int symbolColor = 0;
//                // 图层符号
//                if (TextUtils.isEmpty(fileInfo.colour)) {
//                    symbolColor = EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "symbolColor", 0xffff0000);
//                } else {
////                    if (EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "symbolColor", 0xffff0000) == 0xffff0000)
//                    symbolColor = Color.parseColor(fileInfo.colour);
////                    else
////                        symbolColor = EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "symbolColor", 0xffff0000);
//                }
//                float symbolSize = EasySP.init(mContext).getFloat(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "symbolSize", 1.5f);
//                // 符号样式
//                mSimpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, symbolColor, symbolSize);
//                mSimpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, symbolColor, symbolSize);
//                mSimpleLineSymbol_fill = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, symbolColor, symbolSize);
//                mSimpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED, mSimpleLineSymbol_fill);
//                // 图层标注
//                int isBz;
//                if (mainShapefileLayer.getName().equals(activity.getResources().getString(R.string.curve_area))) {
//                    isBz = EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "selectTag", 1);
//                } else {
//                    isBz = EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "selectTag", 0);
//                }
//
//                if (isBz == 1) {
//                    String showFieldName;
//                    if (mainShapefileLayer.getName().equals(activity.getResources().getString(R.string.curve_area))) {
//                        showFieldName = EasySP.init(mContext).getString(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "showField", "面积");
//                    } else {
//                        showFieldName = EasySP.init(mContext).getString(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "showField", "FID");
//                    }
//
//                    int showFieldColor = EasySP.init(mContext).getInt(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "color", 0xffff0000);
//                    float showFieldSize = EasySP.init(mContext).getFloat(SystemDirPath.getProjectName() + mainShapefileLayer.getName() + "size", 12f);
//                    String strLabelDefinition = null;
//                    GeometryType geometryType = shapefileFeatureTable.getGeometryType();
//                    if (geometryType == GeometryType.POINT || geometryType == GeometryType.MULTIPOINT) {
//                        strLabelDefinition = "{\"labelExpression\":\"[" + showFieldName + "]\",\"labelPlacement\":\"esriServerPointLabelPlacementBelowCenter\",\"symbol\":{\"color\":" + Util.fromStrToARGB(showFieldColor) + ",\"font\":{\"size\":" + showFieldSize + ",\"family\":\"Microsoft YaHei\"},\"type\":\"esriTS\"}}";
//                    } else if (geometryType == GeometryType.POLYLINE) {
//                        strLabelDefinition = "{\"labelExpression\":\"[" + showFieldName + "]\",\"labelPlacement\":\"esriServerPolyineLabelPlacementCenter\",\"symbol\":{\"color\":" + Util.fromStrToARGB(showFieldColor) + ",\"font\":{\"size\":" + showFieldSize + ",\"family\":\"Microsoft YaHei\"},\"type\":\"esriTS\"}}";
//
//                    } else if (geometryType == GeometryType.ENVELOPE || geometryType == GeometryType.POLYGON) {
//                        strLabelDefinition = "{\"labelExpression\":\"[" + showFieldName + "]\",\"labelPlacement\":\"esriServerPolygonLabelPlacementCenter\",\"symbol\":{\"color\":" + Util.fromStrToARGB(showFieldColor) + ",\"font\":{\"size\":" + showFieldSize + ",\"family\":\"Microsoft YaHei\"},\"type\":\"esriTS\"}}";
//                    }
//                    LabelDefinition labelDefinition = LabelDefinition.fromJson(strLabelDefinition);
//                    List<LabelDefinition> labelDefinitionList = mainShapefileLayer.getLabelDefinitions();
//                    if (labelDefinitionList.size() != 0)
//                        labelDefinitionList.clear();
//                    mainShapefileLayer.getLabelDefinitions().add(labelDefinition);
//                    // 启用标注
//                    mainShapefileLayer.setLabelsEnabled(true);
//                } else {
//                    // 关闭图层标注功能
//                    mainShapefileLayer.setLabelsEnabled(false);
//                }
//                String max = EasySP.init(mContext).getString(SystemDirPath.getProjectName() + ":max:" + mainShapefileLayer.getName(), "0");
//                if (fileInfo.maximumScale == 0) {
//                    mainShapefileLayer.setMaxScale((Double.parseDouble(max)));
//                } else {
//                    if (max.equals("0")) {
//                        mainShapefileLayer.setMaxScale((Double.parseDouble(fileInfo.maximumScale + "")));
//                    } else {
//                        mainShapefileLayer.setMaxScale((Double.parseDouble(max)));
//                    }
//                }
//                String min = EasySP.init(mContext).getString(SystemDirPath.getProjectName() + ":min:" + mainShapefileLayer.getName(), "0");
//                if (fileInfo.minimumScale == 0) {
//                    mainShapefileLayer.setMinScale((Double.parseDouble(min)));
//                } else {
//                    if (max.equals("0")) {
//                        mainShapefileLayer.setMinScale((Double.parseDouble(fileInfo.minimumScale + "")));
//                    } else {
//                        mainShapefileLayer.setMinScale((Double.parseDouble(min)));
//                    }
//                }
//                boolean add = mapView.getMap().getOperationalLayers().add(mainShapefileLayer);
//                GeometryType geometryType = shapefileFeatureTable.getGeometryType();
//                if (geometryType == GeometryType.POINT || geometryType == GeometryType.MULTIPOINT) {
//                    if (finalIsSeniorSymbol && finalSymbolStyle != null && finalDdataBean != null && finalStyleBean != null) {
//                        SymbolStyleUtile.setSymbolStyle(mainShapefileLayer, finalSymbolStyle, finalDdataBean, finalStyleBean, "S_CODE");
//                    } else {
//                        mSimpleMarkerSymbol.setSize(20f);
//                        simpleRenderer = new SimpleRenderer(mSimpleMarkerSymbol);
//                        mainShapefileLayer.setRenderer(simpleRenderer);
//                    }
//                } else if (geometryType == GeometryType.POLYLINE) {
//                    if (finalSymbolStyle != null && finalDdataBean != null && finalStyleBean != null && finalDdataBean.getStyle() != null) {
//                        SymbolStyleUtile.setLineStyel(mainShapefileLayer, finalSymbolStyle, finalDdataBean, "FLOW_DIREC");
//                    } else {
//                        simpleRenderer = new SimpleRenderer(mSimpleLineSymbol);
//                        mainShapefileLayer.setRenderer(simpleRenderer);
//                    }
//                } else if (geometryType == GeometryType.ENVELOPE || geometryType == GeometryType.POLYGON) {
//                    simpleRenderer = new SimpleRenderer(mSimpleFillSymbol);
//                    mainShapefileLayer.setRenderer(simpleRenderer);
//                } else {
//                    simpleRenderer = new SimpleRenderer();
//                    mainShapefileLayer.setRenderer(simpleRenderer);
//                }
//            });
//        }
//      mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                EventBus.getDefault().post("lodingSuccess");
//            }
//        },500);
//    }



    private void fullMapShow() {
        List<Geometry> geometryList = new ArrayList<>();
        // 可编辑图层
        List<Layer> operationList = mapView.getMap().getOperationalLayers();
        if (operationList != null && operationList.size() != 0) {
            for (Layer operationLayer : operationList) {
                if (operationLayer != null) {
                    Geometry operaGeometry = operationLayer.getFullExtent();
                    if (operaGeometry != null) {
                        geometryList.add(operaGeometry);
                    }
                }
            }
        }
        // 图层几何范围合并
        if (geometryList != null && geometryList.size() != 0) {
            try {
                Envelope fullEnvelop = GeometryEngine.combineExtents(geometryList);
                mapView.setViewpointGeometryAsync(fullEnvelop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
