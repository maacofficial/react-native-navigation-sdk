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
  // Camera and map info methods
  getCameraPosition(viewId: number): Promise<Object>;
  getMyLocation(viewId: number): Promise<Object>;
  getUiSettings(viewId: number): Promise<Object>;
  isMyLocationEnabled(viewId: number): Promise<boolean>;

  // Map objects
  addMarker(viewId: number, markerOptionsMap: Object): Promise<Object>;
  addPolyline(viewId: number, polylineOptionsMap: Object): Promise<Object>;
  addPolygon(viewId: number, polygonOptionsMap: Object): Promise<Object>;
  addCircle(viewId: number, circleOptionsMap: Object): Promise<Object>;
  addGroundOverlay(viewId: number, overlayOptionsMap: Object): Promise<Object>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NavViewModule');
