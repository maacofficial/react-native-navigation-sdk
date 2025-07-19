/**
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {
  Platform,
  UIManager,
  requireNativeComponent,
  type HostComponent,
  type ViewProps,
} from 'react-native';
import type { DirectEventHandler } from 'react-native/Libraries/Types/CodegenTypes';
import type { LatLng } from '.';
import type { Circle, GroundOverlay, Marker, Polygon, Polyline } from '../maps';

// NavViewManager is responsible for managing both the regular map fragment as well as the navigation map view fragment.
export const viewManagerName =
  Platform.OS === 'android' ? 'NavViewManager' : 'RCTNavView';

export const sendCommand = (
  viewId: number,
  command: number | undefined,
  args?: any[]
) => {
  if (command === undefined) {
    throw new Error(
      "Command not found, please make sure you're using the referencing the right method"
    );
  }

  console.log('sendCommand called with:', { viewId, command, args });

  try {
    UIManager.dispatchViewManagerCommand(
      viewId,
      Platform.OS === 'android' ? command.toString() : command,
      args
    );
    console.log('UIManager.dispatchViewManagerCommand executed successfully');
  } catch (exception) {
    console.error('Error in sendCommand:', exception);
  }
};

// Lazy load commands to avoid duplicate registration
let _commands: any = null;
export const getCommands = () => {
  if (_commands === null) {
    try {
      _commands = UIManager.getViewManagerConfig(viewManagerName)?.Commands;
      console.log('Commands loaded successfully:', _commands);
    } catch (error) {
      console.error('Error loading commands:', error);
      _commands = {};
    }
  }
  return _commands;
};

// Legacy export for backward compatibility  
export const commands = new Proxy({} as any, {
  get(_target, prop) {
    const cmds = getCommands();
    return cmds ? cmds[prop] : undefined;
  }
});

export interface NativeNavViewProps extends ViewProps {
  flex?: number | undefined;
  onMapReady?: DirectEventHandler<null>;
  onMapClick?: DirectEventHandler<LatLng>;
  onMarkerClick?: DirectEventHandler<Marker>;
  onPolylineClick?: DirectEventHandler<Polyline>;
  onPolygonClick?: DirectEventHandler<Polygon>;
  onCircleClick?: DirectEventHandler<Circle>;
  onGroundOverlayClick?: DirectEventHandler<GroundOverlay>;
  onMarkerInfoWindowTapped?: DirectEventHandler<Marker>;
  onRecenterButtonClick?: DirectEventHandler<null>;
}

type NativeNavViewManagerComponentType = HostComponent<NativeNavViewProps>;
export const NavViewManager = requireNativeComponent<NativeNavViewProps>(
  viewManagerName
) as NativeNavViewManagerComponentType;
