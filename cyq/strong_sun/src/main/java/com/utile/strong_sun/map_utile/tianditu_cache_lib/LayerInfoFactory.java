/*
 * Copyright 2017 wshunli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.utile.strong_sun.map_utile.tianditu_cache_lib;


import com.esri.arcgisruntime.arcgisservices.LevelOfDetail;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;

import java.util.ArrayList;
import java.util.List;

public class LayerInfoFactory {
private static final String URL_VECTOR_2000 = "http://t0.tianditu.gov.cn/vec_c/wmts";
    private static final String URL_VECTOR_ANNOTATION_CHINESE_2000 = "http://t0.tianditu.gov.cn/cva_c/wmts";
    private static final String URL_VECTOR_ANNOTATION_ENGLISH_2000 = "http://t0.tianditu.gov.cn/eva_c/wmts";
    private static final String URL_IMAGE_2000 = "http://t0.tianditu.gov.cn/img_c/wmts";
    private static final String URL_IMAGE_ANNOTATION_CHINESE_2000 = "http://t0.tianditu.gov.cn/cia_c/wmts";
    private static final String URL_IMAGE_ANNOTATION_ENGLISH_2000 = "http://t0.tianditu.gov.cn/cia_c/wmts";
    private static final String URL_TERRAIN_2000 = "http://t0.tianditu.gov.cn/ter_c/wmts";
    private static final String URL_TERRAIN_ANNOTATION_CHINESE_2000 = "http://t0.tianditu.gov.cn/cta_c/wmts";

    private static final String URL_VECTOR_MERCATOR = "http://t0.tianditu.gov.cn/vec_w/wmts";
    private static final String URL_VECTOR_ANNOTATION_CHINESE_MERCATOR = "http://t0.tianditu.gov.cn/cva_w/wmts";
    private static final String URL_VECTOR_ANNOTATION_ENGLISH_MERCATOR = "http://t0.tianditu.gov.cn/eva_w/wmts";
    private static final String URL_IMAGE_MERCATOR = "http://t0.tianditu.gov.cn/img_w/wmts";
    private static final String URL_IMAGE_ANNOTATION_CHINESE_MERCATOR = "http://t0.tianditu.gov.cn/cia_w/wmts";
    private static final String URL_IMAGE_ANNOTATION_ENGLISH_MERCATOR = "http://t0.tianditu.gov.cn/cia_w/wmts";
    private static final String URL_TERRAIN_MERCATOR = "http://t0.tianditu.gov.cn/ter_w/wmts";
    private static final String URL_TERRAIN_ANNOTATION_CHINESE_MERCATOR = "http://t0.tianditu.gov.cn/cta_w/wmts";
    private static final String LAYER_NAME_VECTOR = "vec";
    private static final String LAYER_NAME_VECTOR_ANNOTATION_CHINESE = "cva";
    private static final String LAYER_NAME_VECTOR_ANNOTATION_ENGLISH = "eva";
    private static final String LAYER_NAME_IMAGE = "img";
    private static final String LAYER_NAME_IMAGE_ANNOTATION_CHINESE = "cia";
    private static final String LAYER_NAME_IMAGE_ANNOTATION_ENGLISH = "eia";
    private static final String LAYER_NAME_TERRAIN = "ter";
    private static final String LAYER_NAME_TERRAIN_ANNOTATION_CHINESE = "cta";

    private static final String TILE_MATRIX_SET_MERCATOR = "w";
    private static final String TILE_MATRIX_SET_2000 = "c";
    private static final int minZoomLevel = 1;
    private static final int maxZoomLevel = 18;
    private static final Point ORIGIN_2000 = new Point(-180, 90,
            SpatialReference.create(LayerInfoFactory.SRID_2000));
    private static final Point ORIGIN_MERCATOR = new Point(-20037508.3427892,
            20037508.3427892,
            SpatialReference.create(LayerInfoFactory.SRID_MERCATOR));

    private static final int SRID_2000 = 4326;
    private static final int SRID_MERCATOR = 3857;

    private static final double X_MIN_2000 = -180;
    private static final double Y_MIN_2000 = -90;
    private static final double X_MAX_2000 = 180;
    private static final double Y_MAX_2000 = 90;

    private static final double X_MIN_MERCATOR = -20037508.3427892;
    private static final double Y_MIN_MERCATOR = -20037508.3427892;
    private static final double X_MAX_MERCATOR = 20037508.3427892;
    private static final double Y_MAX_MERCATOR = 20037508.3427892;


    private static final double[] SCALES = {
            2.958293554545656E8, 1.479146777272828E8,
            7.39573388636414E7, 3.69786694318207E7,
            1.848933471591035E7, 9244667.357955175,
            4622333.678977588, 2311166.839488794,
            1155583.419744397, 577791.7098721985,
            288895.85493609926, 144447.92746804963,
            72223.96373402482, 36111.98186701241,
            18055.990933506204, 9027.995466753102,
            4513.997733376551, 2256.998866688275,
            1128.4994333441375
    };

private static final double[] RESOLUTIONS_MERCATOR = {
        78271.51696402048, 39135.75848201024,
        19567.87924100512, 9783.93962050256,
        4891.96981025128, 2445.98490512564,
        1222.99245256282, 611.49622628141,
        305.748113140705, 152.8740565703525,
        76.43702828517625, 38.21851414258813,
        19.109257071294063, 9.554628535647032,
        4.777314267823516, 2.388657133911758,
        1.194328566955879, 0.5971642834779395,
        0.298582141738970};
    private static final double[] RESOLUTIONS_2000 = {
            0.7031249999891485, 0.35156249999999994,
            0.17578124999999997, 0.08789062500000014,
            0.04394531250000007, 0.021972656250000007,
            0.01098632812500002, 0.00549316406250001,
            0.0027465820312500017, 0.0013732910156250009,
            0.000686645507812499, 0.0003433227539062495,
            0.00017166137695312503, 0.00008583068847656251,
            0.000042915344238281406, 0.000021457672119140645,
            0.000010728836059570307, 0.000005364418029785169};
    private static List<LevelOfDetail> getLods102100(){
        List<LevelOfDetail> lods = new ArrayList<LevelOfDetail>();

        for (int i = minZoomLevel; i <= maxZoomLevel; i++) {
            LevelOfDetail item = new LevelOfDetail(i, RESOLUTIONS_MERCATOR[i - 1], SCALES[i - 1]);
            lods.add(item);
        }
        return lods;
    }
    private static List<LevelOfDetail> getLods2000(){
        List<LevelOfDetail> lods = new ArrayList<LevelOfDetail>();
        for (int i = minZoomLevel; i <= maxZoomLevel; i++) {
            LevelOfDetail item = new LevelOfDetail(i, RESOLUTIONS_2000[i - 1], SCALES[i - 1]);
            lods.add(item);
        }
        return lods;
    }

    public static TianDiTuLayerInfo getLayerInfo(int layerType) {
        TianDiTuLayerInfo layerInfo = new TianDiTuLayerInfo();
        switch (layerType) {
            case TianDiTuLayerTypes.TIANDITU_IMAGE_2000:
                layerInfo.setUrl(LayerInfoFactory.URL_IMAGE_2000);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE);
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_IMAGE_ANNOTATION_CHINESE_2000);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE_ANNOTATION_CHINESE);
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_IMAGE_ANNOTATION_ENGLISH_2000);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE_ANNOTATION_ENGLISH);
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_IMAGE_ANNOTATION_CHINESE_MERCATOR);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE_ANNOTATION_CHINESE);
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_IMAGE_ANNOTATION_ENGLISH_MERCATOR);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE_ANNOTATION_ENGLISH);
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_MERCATOR:
                layerInfo.setUrl(LayerInfoFactory.URL_IMAGE_MERCATOR);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_IMAGE);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_2000:
                layerInfo.setUrl(LayerInfoFactory.URL_VECTOR_2000);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_VECTOR_ANNOTATION_CHINESE_2000);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR_ANNOTATION_CHINESE);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_VECTOR_ANNOTATION_ENGLISH_2000);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR_ANNOTATION_ENGLISH);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_VECTOR_ANNOTATION_CHINESE_MERCATOR);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR_ANNOTATION_CHINESE);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_VECTOR_ANNOTATION_ENGLISH_MERCATOR);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR_ANNOTATION_ENGLISH);
                break;
            case TianDiTuLayerTypes.TIANDITU_VECTOR_MERCATOR:
                layerInfo.setUrl(LayerInfoFactory.URL_VECTOR_MERCATOR);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_VECTOR);
                break;
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_2000:
                layerInfo.setUrl(LayerInfoFactory.URL_TERRAIN_2000);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_TERRAIN);
                break;
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_TERRAIN_ANNOTATION_CHINESE_2000);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_TERRAIN_ANNOTATION_CHINESE);
                break;
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_MERCATOR:
                layerInfo.setUrl(LayerInfoFactory.URL_TERRAIN_MERCATOR);
                layerInfo.setLayerName(LayerInfoFactory.LAYER_NAME_TERRAIN);
                break;
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR:
                layerInfo
                        .setUrl(LayerInfoFactory.URL_TERRAIN_ANNOTATION_CHINESE_MERCATOR);
                layerInfo
                        .setLayerName(LayerInfoFactory.LAYER_NAME_TERRAIN_ANNOTATION_CHINESE);
                break;
        }
        handleLayerInfo(layerInfo, layerType);
        return layerInfo;
    }

    private static void handleLayerInfo(TianDiTuLayerInfo layerInfo,
                                        int layerType) {
        switch (layerType) {
            case TianDiTuLayerTypes.TIANDITU_IMAGE_2000:
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000:
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_2000:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000:
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_2000:
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000:
                layerInfo.setOrigin(LayerInfoFactory.ORIGIN_2000);
                layerInfo.setSrid(LayerInfoFactory.SRID_2000);
                layerInfo.setxMin(LayerInfoFactory.X_MIN_2000);
                layerInfo.setyMin(LayerInfoFactory.Y_MIN_2000);
                layerInfo.setxMax(LayerInfoFactory.X_MAX_2000);
                layerInfo.setyMax(LayerInfoFactory.Y_MAX_2000);
                layerInfo.setScales(LayerInfoFactory.SCALES);
                layerInfo.setResolutions(LayerInfoFactory.RESOLUTIONS_2000);
                layerInfo.setTileMatrixSet(LayerInfoFactory.TILE_MATRIX_SET_2000);
                layerInfo.setLods(getLods2000());
                break;
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_IMAGE_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_VECTOR_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_MERCATOR:
            case TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR:
                layerInfo.setOrigin(LayerInfoFactory.ORIGIN_MERCATOR);
                layerInfo.setSrid(LayerInfoFactory.SRID_MERCATOR);
                layerInfo.setxMin(LayerInfoFactory.X_MIN_MERCATOR);
                layerInfo.setyMin(LayerInfoFactory.Y_MIN_MERCATOR);
                layerInfo.setxMax(LayerInfoFactory.X_MAX_MERCATOR);
                layerInfo.setyMax(LayerInfoFactory.Y_MAX_MERCATOR);
                layerInfo.setScales(LayerInfoFactory.SCALES);
                layerInfo.setResolutions(LayerInfoFactory.RESOLUTIONS_MERCATOR);
                layerInfo.setTileMatrixSet(LayerInfoFactory.TILE_MATRIX_SET_MERCATOR);
                layerInfo.setLods(getLods102100());
                break;
        }
    }
}
