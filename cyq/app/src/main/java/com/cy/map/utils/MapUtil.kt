package com.cy.map.utils

import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.mapping.view.MapView
import com.utile.strong_sun.MyApplication

class MapUtil {
    companion object {
        fun setGeometryViewCenter(geometry: Geometry, mapView: MapView) {
            if (geometry.geometryType == GeometryType.POINT) {
                mapView.setViewpointCenterAsync(geometry.extent.center, 5000.0)
            } else {
                val width = geometry.extent.width
                val height = geometry.extent.height
                val center = geometry.extent.center
                val listAreaPoint =
                    PointCollection(SpatialReference.create(MyApplication.locationType))
                listAreaPoint.add(Point(center.x + width * 2, center.y + height * 2))
                listAreaPoint.add(Point(center.x + width * 2, center.y - height * 2))
                listAreaPoint.add(Point(center.x - width * 2, center.y - height * 2))
                listAreaPoint.add(Point(center.x - width * 2, center.y + height * 2))
                val geometry: Geometry = Polyline(listAreaPoint)
                mapView.setViewpointGeometryAsync(geometry)
            }
        }
    }
}