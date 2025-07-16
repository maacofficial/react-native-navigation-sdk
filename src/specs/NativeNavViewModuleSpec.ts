/**
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  // Map functions
  animateCamera(cameraPosition: Object): Promise<void>;
  moveCamera(cameraPosition: Object): Promise<void>;
  getCameraPosition(): Promise<Object>;
  getBounds(): Promise<Object>;

  // Map type and settings
  setMapType(mapType: number): Promise<void>;
  getMapType(): Promise<number>;
  setCompassEnabled(enabled: boolean): Promise<void>;
  setRotateGesturesEnabled(enabled: boolean): Promise<void>;
  setScrollGesturesEnabled(enabled: boolean): Promise<void>;
  setTiltGesturesEnabled(enabled: boolean): Promise<void>;
  setZoomGesturesEnabled(enabled: boolean): Promise<void>;
  setScrollGesturesEnabledDuringRotateOrZoom(enabled: boolean): Promise<void>;
  setMapToolbarEnabled(enabled: boolean): Promise<void>;
  setTrafficEnabled(enabled: boolean): Promise<void>;

  // Markers
  addMarkers(markers: Object[]): Promise<string[]>;
  removeMarkers(markerIds: string[]): Promise<void>;
  clearMarkers(): Promise<void>;

  // Polylines
  addPolylines(polylines: Object[]): Promise<string[]>;
  removePolylines(polylineIds: string[]): Promise<void>;
  clearPolylines(): Promise<void>;

  // Polygons
  addPolygons(polygons: Object[]): Promise<string[]>;
  removePolygons(polygonIds: string[]): Promise<void>;
  clearPolygons(): Promise<void>;

  // Circles
  addCircles(circles: Object[]): Promise<string[]>;
  removeCircles(circleIds: string[]): Promise<void>;
  clearCircles(): Promise<void>;

  // Ground Overlays
  addGroundOverlays(overlays: Object[]): Promise<string[]>;
  removeGroundOverlays(overlayIds: string[]): Promise<void>;
  clearGroundOverlays(): Promise<void>;

  // Padding
  setPadding(padding: Object): Promise<void>;

  // Follow My Location
  followMyLocation(perspective: number, zoomLevel?: number): Promise<void>;
  isFollowMyLocationEnabled(): Promise<boolean>;

  // My Location
  isMyLocationEnabled(): Promise<boolean>;
  setMyLocationEnabled(enabled: boolean): Promise<void>;
  setMyLocationButtonEnabled(enabled: boolean): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NavViewModule');
