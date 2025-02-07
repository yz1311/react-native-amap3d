package cn.qiuxiang.react.amap3d.maps

import android.view.View
import cn.qiuxiang.react.amap3d.toLatLng
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CustomMapStyleOptions
import com.amap.api.maps.model.MyLocationStyle
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import java.lang.Exception

@Suppress("unused")
internal class AMapViewManager : ViewGroupManager<AMapView>() {
    companion object {
        const val SET_STATUS = 1
    }

    var context:ThemedReactContext? = null;

    override fun getName(): String {
        return "AMapView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): AMapView {
        context = reactContext;
        return AMapView(reactContext)
    }

    override fun onDropViewInstance(view: AMapView) {
        super.onDropViewInstance(view)
        view.onDestroy()
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf("setStatus" to SET_STATUS)
    }

    override fun receiveCommand(overlay: AMapView, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            SET_STATUS -> overlay.animateTo(args)
        }
    }

    override fun addView(mapView: AMapView, child: View, index: Int) {
        mapView.add(child)
        super.addView(mapView, child, index)
    }

    override fun removeViewAt(parent: AMapView, index: Int) {
        parent.remove(parent.getChildAt(index))
        super.removeViewAt(parent, index)
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any> {
        return MapBuilder.of(
                "onClick", MapBuilder.of("registrationName", "onAMapClick"),
                "onLongClick", MapBuilder.of("registrationName", "onAMapLongClick"),
                "onLocation", MapBuilder.of("registrationName", "onAMapLocation"),
                "onAnimateCancel", MapBuilder.of("registrationName", "onAMapAnimateCancel"),
                "onAnimateFinish", MapBuilder.of("registrationName", "onAMapAnimateFinish"),
                "onStatusChange", MapBuilder.of("registrationName", "onAMapStatusChange"),
                "onStatusChangeComplete", MapBuilder.of("registrationName", "onAMapStatusChangeComplete")
        )
    }

    @ReactProp(name = "locationEnabled")
    fun setMyLocationEnabled(view: AMapView, enabled: Boolean) {
        view.setLocationEnabled(enabled)
    }

    @ReactProp(name = "showsIndoorMap")
    fun showIndoorMap(view: AMapView, show: Boolean) {
        view.map.showIndoorMap(show)
    }

    @ReactProp(name = "showsIndoorSwitch")
    fun setIndoorSwitchEnabled(view: AMapView, show: Boolean) {
        view.map.uiSettings.isIndoorSwitchEnabled = show
    }

    @ReactProp(name = "showsBuildings")
    fun showBuildings(view: AMapView, show: Boolean) {
        view.map.showBuildings(show)
    }

    @ReactProp(name = "showsLabels")
    fun showMapText(view: AMapView, show: Boolean) {
        view.map.showMapText(show)
    }

    @ReactProp(name = "showsCompass")
    fun setCompassEnabled(view: AMapView, show: Boolean) {
        view.map.uiSettings.isCompassEnabled = show
    }

    @ReactProp(name = "showsZoomControls")
    fun setZoomControlsEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isZoomControlsEnabled = enabled
    }

    @ReactProp(name = "showsScale")
    fun setScaleControlsEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isScaleControlsEnabled = enabled
    }

    @ReactProp(name = "mapLanguage")
    fun setLanguage(view: AMapView, mapLanguage:Int) {
        view.map.setMapLanguage(if(mapLanguage == 1) {"en"} else {"zh_cn"})
    }

    @ReactProp(name = "showsLocationButton")
    fun setMyLocationButtonEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isMyLocationButtonEnabled = enabled
    }

    @ReactProp(name = "showsTraffic")
    fun setTrafficEnabled(view: AMapView, enabled: Boolean) {
        view.map.isTrafficEnabled = enabled
    }

    @ReactProp(name = "maxZoomLevel")
    fun setMaxZoomLevel(view: AMapView, zoomLevel: Float) {
        view.map.maxZoomLevel = zoomLevel
    }

    @ReactProp(name = "minZoomLevel")
    fun setMinZoomLevel(view: AMapView, zoomLevel: Float) {
        view.map.minZoomLevel = zoomLevel
    }

    @ReactProp(name = "zoomLevel")
    fun setZoomLevel(view: AMapView, zoomLevel: Float) {
        view.map.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel))
    }

    @ReactProp(name = "mapType")
    fun setMapType(view: AMapView, mapType: Int) {
        view.map.mapType = mapType + 1
    }

    @ReactProp(name = "zoomEnabled")
    fun setZoomGesturesEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isZoomGesturesEnabled = enabled
    }

    @ReactProp(name = "scrollEnabled")
    fun setScrollGesturesEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isScrollGesturesEnabled = enabled
    }

    @ReactProp(name = "rotateEnabled")
    fun setRotateGesturesEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isRotateGesturesEnabled = enabled
    }

    @ReactProp(name = "tiltEnabled")
    fun setTiltGesturesEnabled(view: AMapView, enabled: Boolean) {
        view.map.uiSettings.isTiltGesturesEnabled = enabled
    }

    @ReactProp(name = "center")
    fun setCenter(view: AMapView, center: ReadableMap) {
        view.map.moveCamera(CameraUpdateFactory.changeLatLng(center.toLatLng()))
    }

    @ReactProp(name = "region")
    fun setRegion(view: AMapView, region: ReadableMap) {
        view.setRegion(region)
    }

    @ReactProp(name = "limitRegion")
    fun setLimitRegion(view: AMapView, limitRegion: ReadableMap) {
        view.setLimitRegion(limitRegion)
    }

    @ReactProp(name = "tilt")
    fun changeTilt(view: AMapView, tilt: Float) {
        view.map.moveCamera(CameraUpdateFactory.changeTilt(tilt))
    }

    @ReactProp(name = "rotation")
    fun changeRotation(view: AMapView, rotation: Float) {
        view.map.moveCamera(CameraUpdateFactory.changeBearing(rotation))
    }

    @ReactProp(name = "locationInterval")
    fun setLocationInterval(view: AMapView, interval: Int) {
        view.setLocationInterval(interval.toLong())
    }

    @ReactProp(name = "locationStyle")
    fun setLocationStyle(view: AMapView, style: ReadableMap) {
        view.setLocationStyle(style)
    }

    @ReactProp(name = "customMapStyleId")
    fun setCustomMapStyle(view: AMapView, styleId: String) {
        if(styleId != null && styleId != "") {
            view.map.setCustomMapStyle(
                    CustomMapStyleOptions()
                            .setEnable(true)
                            .setStyleId(styleId)
            );
        } else {
            view.map.setCustomMapStyle(
                    CustomMapStyleOptions()
                            .setEnable(false)
            );
        }
    }

    @ReactProp(name = "customMapEnabled")
    fun setCustomMapEnabled(view: AMapView, enabled: Boolean) {
        try {
            if(enabled) {
                view.map.setCustomMapStyle(
                        CustomMapStyleOptions()
                                .setEnable(true)
                                .setStyleData(context!!.assets.open("style.data").readBytes())
                                .setStyleExtraData(context!!.assets.open("style_extra.data").readBytes())
                );
            } else {
                view.map.setCustomMapStyle(
                        CustomMapStyleOptions()
                                .setEnable(false)
                );
            }
        } catch (e: Exception) {

        }
    }

    @ReactProp(name = "locationType")
    fun setLocationStyle(view: AMapView, type: String) {
        when (type) {
            "show" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_SHOW)
            "locate" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
            "follow" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
            "map_rotate" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)
            "location_rotate" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
            "location_rotate_no_center" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
            "follow_no_center" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
            "map_rotate_no_center" -> view.setLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER)
        }
    }
}
