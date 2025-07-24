/**
 * Copyright 2023 Google LLC
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.react.navsdk;

import android.location.Location;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import java.util.HashMap;
import java.util.Map;

/**
 * This exposes a series of methods that can be called diretly from the React Native code. They have
 * been implemented using promises as it's not recommended for them to be synchronous.
 */
public class NavViewModule extends ReactContextBaseJavaModule implements TurboModule {

  private static final String TAG = "NavViewModule";

  private NavViewManager mNavViewManager;

  public NavViewModule(ReactApplicationContext reactContext, NavViewManager navViewManager) {
    super(reactContext);
    mNavViewManager = navViewManager;
  }

  @Override
  public String getName() {
    return "NavViewModule";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }

  @ReactMethod
  public void getCameraPosition(double viewId, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          if (mNavViewManager.getGoogleMap((int) viewId) == null) {
            promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
            return;
          }

          CameraPosition cp = mNavViewManager.getGoogleMap((int) viewId).getCameraPosition();

          if (cp == null) {
            promise.resolve(null);
            return;
          }

          LatLng target = cp.target;
          WritableMap map = Arguments.createMap();
          map.putDouble("bearing", cp.bearing);
          map.putDouble("tilt", cp.tilt);
          map.putDouble("zoom", cp.zoom);
          map.putMap("target", ObjectTranslationUtil.getMapFromLatLng(target));

          promise.resolve(map);
        });
  }

  @ReactMethod
  public void getMyLocation(double viewId, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          if (mNavViewManager.getGoogleMap((int) viewId) == null) {
            promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
            return;
          }

          try {
            Location location = mNavViewManager.getGoogleMap((int) viewId).getMyLocation();
            if (location == null) {
              promise.resolve(null);
              return;
            }

            promise.resolve(ObjectTranslationUtil.getMapFromLocation(location));
          } catch (Exception e) {
            promise.resolve(null);
            return;
          }
        });
  }

  @ReactMethod
  public void getUiSettings(double viewId, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          if (mNavViewManager.getGoogleMap((int) viewId) == null) {
            promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
            return;
          }

          UiSettings settings = mNavViewManager.getGoogleMap((int) viewId).getUiSettings();

          if (settings == null) {
            promise.resolve(null);
            return;
          }

          WritableMap map = Arguments.createMap();
          map.putBoolean("isCompassEnabled", settings.isCompassEnabled());
          map.putBoolean("isMapToolbarEnabled", settings.isMapToolbarEnabled());
          map.putBoolean("isIndoorLevelPickerEnabled", settings.isIndoorLevelPickerEnabled());
          map.putBoolean("isRotateGesturesEnabled", settings.isRotateGesturesEnabled());
          map.putBoolean("isScrollGesturesEnabled", settings.isScrollGesturesEnabled());
          map.putBoolean(
              "isScrollGesturesEnabledDuringRotateOrZoom",
              settings.isScrollGesturesEnabledDuringRotateOrZoom());
          map.putBoolean("isTiltGesturesEnabled", settings.isTiltGesturesEnabled());
          map.putBoolean("isZoomControlsEnabled", settings.isZoomControlsEnabled());
          map.putBoolean("isZoomGesturesEnabled", settings.isZoomGesturesEnabled());

          promise.resolve(map);
        });
  }

  @ReactMethod
  public void isMyLocationEnabled(double viewId, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          if (mNavViewManager.getGoogleMap((int) viewId) == null) {
            promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
            return;
          }

          promise.resolve(mNavViewManager.getGoogleMap((int) viewId).isMyLocationEnabled());
        });
  }

  @ReactMethod
  public void addMarker(double viewId, ReadableMap markerOptionsMap, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          try {
            if (mNavViewManager.getGoogleMap((int) viewId) == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
              return;
            }
            
            IMapViewFragment fragment = mNavViewManager.getFragmentForViewId((int) viewId);
            if (fragment == null || fragment.getMapController() == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, "MapController not available");
              return;
            }
            
            Marker marker = fragment.getMapController().addMarker(markerOptionsMap.toHashMap());
            if (marker != null) {
              promise.resolve(ObjectTranslationUtil.getMapFromMarker(marker));
            } else {
              promise.reject("MARKER_CREATION_FAILED", "Failed to create marker");
            }
          } catch (Exception e) {
            promise.reject("MARKER_CREATION_ERROR", "Error creating marker: " + e.getMessage());
          }
        });
  }

  @ReactMethod
  public void addPolyline(double viewId, ReadableMap polylineOptionsMap, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          try {
            if (mNavViewManager.getGoogleMap((int) viewId) == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
              return;
            }
            
            IMapViewFragment fragment = mNavViewManager.getFragmentForViewId((int) viewId);
            if (fragment == null || fragment.getMapController() == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, "MapController not available");
              return;
            }
            
            Polyline polyline = fragment.getMapController().addPolyline(polylineOptionsMap.toHashMap());
            if (polyline != null) {
              promise.resolve(ObjectTranslationUtil.getMapFromPolyline(polyline));
            } else {
              promise.reject("POLYLINE_CREATION_FAILED", "Failed to create polyline");
            }
          } catch (Exception e) {
            promise.reject("POLYLINE_CREATION_ERROR", "Error creating polyline: " + e.getMessage());
          }
        });
  }

  @ReactMethod
  public void addPolygon(double viewId, ReadableMap polygonOptionsMap, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          try {
            if (mNavViewManager.getGoogleMap((int) viewId) == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
              return;
            }
            
            IMapViewFragment fragment = mNavViewManager.getFragmentForViewId((int) viewId);
            if (fragment == null || fragment.getMapController() == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, "MapController not available");
              return;
            }
            
            Polygon polygon = fragment.getMapController().addPolygon(polygonOptionsMap.toHashMap());
            if (polygon != null) {
              promise.resolve(ObjectTranslationUtil.getMapFromPolygon(polygon));
            } else {
              promise.reject("POLYGON_CREATION_FAILED", "Failed to create polygon");
            }
          } catch (Exception e) {
            promise.reject("POLYGON_CREATION_ERROR", "Error creating polygon: " + e.getMessage());
          }
        });
  }

  @ReactMethod
  public void addCircle(double viewId, ReadableMap circleOptionsMap, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          try {
            if (mNavViewManager.getGoogleMap((int) viewId) == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
              return;
            }
            
            IMapViewFragment fragment = mNavViewManager.getFragmentForViewId((int) viewId);
            if (fragment == null || fragment.getMapController() == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, "MapController not available");
              return;
            }
            
            Circle circle = fragment.getMapController().addCircle(circleOptionsMap.toHashMap());
            if (circle != null) {
              promise.resolve(ObjectTranslationUtil.getMapFromCircle(circle));
            } else {
              promise.reject("CIRCLE_CREATION_FAILED", "Failed to create circle");
            }
          } catch (Exception e) {
            promise.reject("CIRCLE_CREATION_ERROR", "Error creating circle: " + e.getMessage());
          }
        });
  }

  @ReactMethod
  public void addGroundOverlay(double viewId, ReadableMap overlayOptionsMap, final Promise promise) {
    UiThreadUtil.runOnUiThread(
        () -> {
          try {
            if (mNavViewManager.getGoogleMap((int) viewId) == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, JsErrors.NO_MAP_ERROR_MESSAGE);
              return;
            }
            
            IMapViewFragment fragment = mNavViewManager.getFragmentForViewId((int) viewId);
            if (fragment == null || fragment.getMapController() == null) {
              promise.reject(JsErrors.NO_MAP_ERROR_CODE, "MapController not available");
              return;
            }
            
            GroundOverlay overlay = fragment.getMapController().addGroundOverlay(overlayOptionsMap.toHashMap());
            if (overlay != null) {
              promise.resolve(ObjectTranslationUtil.getMapFromGroundOverlay(overlay));
            } else {
              promise.reject("OVERLAY_CREATION_FAILED", "Failed to create ground overlay");
            }
          } catch (Exception e) {
            promise.reject("OVERLAY_CREATION_ERROR", "Error creating ground overlay: " + e.getMessage());
          }
        });
  }

  @Override
  public boolean canOverrideExistingModule() {
    return true;
  }
}
