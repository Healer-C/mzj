package com.cy.map.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import com.cy.map.App
import com.cy.map.R
import com.esri.arcgisruntime.concurrent.ListenableFuture
import com.esri.arcgisruntime.data.Feature
import com.esri.arcgisruntime.data.FeatureQueryResult
import com.esri.arcgisruntime.data.QueryParameters
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.layers.Layer
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.*
import com.tamsiree.rxkit.RxFileTool
import com.tamsiree.rxkit.view.RxToast
import com.utile.strong_sun.utiles.FileUtils
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.FileHeader
import java.io.File
import java.lang.Exception
import java.util.*
import androidx.core.content.ContextCompat





class AddGridLayerMabager {

    companion object {
//        private val mSimpleMarkerSymbol: SimpleMarkerSymbol =
//            SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, -0x10000, 8f)
        var pinStarBlueDrawable =
            ContextCompat.getDrawable( App.getContext(), R.mipmap.mark) as BitmapDrawable?
        private val mSimpleMarkerSymbol: PictureMarkerSymbol = PictureMarkerSymbol(pinStarBlueDrawable)

        var pinStarRedDrawable =
            ContextCompat.getDrawable( App.getContext(), R.mipmap.point) as BitmapDrawable?
        private val mSimpleMarkerSymbol2: PictureMarkerSymbol = PictureMarkerSymbol(pinStarRedDrawable)

        private val mSimpleLineSymbol: SimpleLineSymbol =
            SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0x10000, 3f)
        private val mSimpleFillSymbol: SimpleFillSymbol =
            SimpleFillSymbol(
                SimpleFillSymbol.Style.SOLID,
                Color.parseColor("#20ff0000"),
                mSimpleLineSymbol
            )

        fun addLayer(map: MapView, fildName: String, shp: String?) {
            var rootPath: String? = null
            var root: String = ""
            if (App.type == 1) {
                root = "cyq"
            } else if (App.type == 2) {
                root = "ftq"
            } else if (App.type == 3) {
                root = "kfq"
            } else if (App.type == 4) {
                root = "mzj"
            }
            RxFileTool.rootPath?.let { it ->
                rootPath = it.absolutePath + "/" + root
                val file = File(rootPath)
                if (!file.exists()) {
                    file.mkdirs()
                    FileUtils.copyFileFromAssets(
                        App.getContext(),
                        "$root.zip",
                        "$rootPath/$root.zip"
                    )

                    rootPath?.let {
//                        RxZipTool.unzipFile((it+"/data.zip"),it);
                        unzipFileByKeyword(File(it + "/$root.zip"), File(it), null)
                    }
                }
                getShp("$rootPath/", map, fildName, shp)
            }

        }

        private fun getShp(rootPath: String?, map: MapView, name: String, shp: String?) {
            var path = rootPath + name
            if (shp != null) {
                path = "$path/$shp"
            }
            var file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            var listTpk = GetAllFileName(path, "shp")
            if (listTpk.isNullOrEmpty()) {
                val activity = map.context
                (activity as Activity).runOnUiThread(Runnable { //更新UI
                    RxToast.info(name + "暂无数据")
                })

                return
            }
            listTpk.forEach { path ->
                val file = RxFileTool.getFileByPath(path)
                file?.let {
                    //判断是否存在
                    addShp(file, map)
                }
            }
            fullMapShow(mapView = map,shp)
        }

        private fun addShp(file: File, map: MapView) {
            val shapefileFeatureTable = ShapefileFeatureTable(file.absolutePath)
            shapefileFeatureTable.loadAsync() //异步方式读取文件
            shapefileFeatureTable.addDoneLoadingListener {
                var mainShapefileLayer = FeatureLayer(shapefileFeatureTable)
                val geometryType = shapefileFeatureTable.geometryType
                map.map.operationalLayers.add(mainShapefileLayer)
                val toJson = mainShapefileLayer.spatialReference?.toJson()
                System.out.println(toJson + "?????????")
                var colour: String? = null
                for (item in mainShapefileLayer.featureTable.fields) {
                    if (item.name.equals("color")) {
                        var query = QueryParameters()
                        query.isReturnGeometry = true
                        query.whereClause = "1=1"
                        val featureQueryResult: ListenableFuture<FeatureQueryResult> =
                            mainShapefileLayer.featureTable.queryFeaturesAsync(query)
                        val result = featureQueryResult.get()
                        val iterator: Iterator<Feature> = result.iterator()
                        var feature: Feature
                        while (iterator.hasNext()) {
                            feature = iterator.next()
                            colour = feature.attributes["color"].toString()
                            break
                        }
                        break
                    }
                }
                if (geometryType == GeometryType.POINT || geometryType == GeometryType.MULTIPOINT) {

                    if (colour != null) {
                        val simpleMarkerSymbol: SimpleMarkerSymbol =
                            SimpleMarkerSymbol(
                                SimpleMarkerSymbol.Style.CIRCLE,
                                Color.parseColor(colour),
                                8f
                            )
                        simpleMarkerSymbol.size = 20f
                        var simpleRenderer = SimpleRenderer(simpleMarkerSymbol)
                        mainShapefileLayer.renderer = simpleRenderer
                    } else {
                        if(file.name=="test.shp"){
                            var simpleRenderer = SimpleRenderer(mSimpleMarkerSymbol2)
                            mainShapefileLayer.renderer = simpleRenderer
                        }else{
                            var simpleRenderer = SimpleRenderer(mSimpleMarkerSymbol)
                            mainShapefileLayer.renderer = simpleRenderer
                        }
                    }
                } else if (geometryType == GeometryType.POLYLINE) {
                    if (colour != null) {
                        val simpleLineSymbol: SimpleLineSymbol =
                            SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor(colour), 3f)
                        var simpleRenderer = SimpleRenderer(simpleLineSymbol)
                        mainShapefileLayer.renderer = simpleRenderer
                    }else{
                        var simpleRenderer = SimpleRenderer(mSimpleLineSymbol)
                        mainShapefileLayer.renderer = simpleRenderer
                    }

                } else if (geometryType == GeometryType.ENVELOPE || geometryType == GeometryType.POLYGON) {
                    if (colour != null) {
                        var fillColour = colour
                        val simpleLineSymbol: SimpleLineSymbol =
                            SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,  Color.parseColor(colour), 3f)
                         val simpleFillSymbol: SimpleFillSymbol =
                            SimpleFillSymbol(
                                SimpleFillSymbol.Style.SOLID,
                                Color.parseColor(fillColour.replace("#", "#20")),
                                simpleLineSymbol
                            )
                        var simpleRenderer = SimpleRenderer(simpleFillSymbol)
                        mainShapefileLayer.renderer = simpleRenderer
                    }else {
                        var simpleRenderer = SimpleRenderer(mSimpleFillSymbol)
                        mainShapefileLayer.renderer = simpleRenderer
                    }
                } else {
                    var simpleRenderer = SimpleRenderer()
                    mainShapefileLayer.renderer = simpleRenderer
                }
            }
        }

        @JvmStatic
        fun unzipFileByKeyword(zipFile: File?, destDir: File?, passwd: String?): List<File>? {
            return try {
                //1.判断指定目录是否存在
                if (zipFile == null) {
                    throw ZipException("压缩文件不存在.")
                }
                if (destDir == null) {
                    throw ZipException("解压缩路径不存在.")
                }
                if (destDir.isDirectory && !destDir.exists()) {
                    destDir.mkdir()
                }

                //2.初始化zip工具
                val zFile = ZipFile(zipFile)
                zFile.setFileNameCharset("GBK")
//                zFile.setFileNameCharset("UTF-8")
                if (!zFile.isValidZipFile) {
                    throw ZipException("压缩文件不合法,可能被损坏.")
                }
                //3.判断是否已加密
                if (zFile.isEncrypted) {
                    zFile.setPassword(passwd!!.toCharArray())
                }
                //4.解压所有文件
                zFile.extractAll(destDir.absolutePath)
                val headerList: MutableList<FileHeader?> =
                    zFile.fileHeaders as MutableList<FileHeader?>
                val extractedFileList: MutableList<File> = ArrayList()
                for (fileHeader in headerList) {
                    if (!fileHeader!!.isDirectory) {
                        extractedFileList.add(File(destDir, fileHeader.fileName))
                    }
                }
                extractedFileList
            } catch (e: ZipException) {
                e.printStackTrace()
                null
            }
        }

        fun fullMapShow(mapView: MapView, shp: String?) {
            val map: MutableMap<*, *> = HashMap<Any?, Any?>()

            val geometryList: MutableList<Geometry> = ArrayList()
            val operationList: List<Layer>? = mapView.map.operationalLayers
            if (operationList != null) {
                for (operationLayer in operationList) {
                    if (shp!=null&&operationList.indexOf(operationLayer)==0) {
                        continue
                    }
                        val operaGeometry: Geometry? = operationLayer.fullExtent
                        val s = operaGeometry!!.extent.xMax.toString()
                        if (operaGeometry != null && s != "NaN" && !operaGeometry.spatialReference?.toJson()
                                .toString().equals("{\"wkt\":\"\"}")
                        ) {
                            geometryList.add(
                                GeometryEngine.project(
                                    operaGeometry,
                                    SpatialReference.create(4326)
                                )
                            )
                        }
                }
            }
            // 图层几何范围合并
            if (geometryList != null && geometryList.size != 0) {
                try {
                    val fullEnvelop = GeometryEngine.combineExtents(geometryList)
                    mapView.setViewpointGeometryAsync(fullEnvelop, 50.0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val vecFile = Vector<String>()
        fun GetAllFileName(fileAbsolutePath: String?, suffix: String?): Vector<String> {
            vecFile.clear()
            getUrl(fileAbsolutePath, suffix)
            return vecFile
        }

        private fun getUrl(fileAbsolutePath: String?, suffix: String?) {
            val file = File(fileAbsolutePath)
            val subFile = file.listFiles()
            for (iFileLength in subFile.indices) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory) {
                    val filename = subFile[iFileLength].name
                    // 判断是否为suffix结尾
                    if (filename.trim { it <= ' ' }.toLowerCase().endsWith(suffix!!)) {
                        vecFile.add(subFile[iFileLength].absolutePath)
                    }
                } else {
                    getUrl(subFile[iFileLength].absolutePath, suffix)
                }
            }
        }
    }

}