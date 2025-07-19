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
  // Terms and Conditions
  areTermsAccepted(): Promise<boolean>;
  showTermsAndConditionsDialog(
    title: string,
    companyName: string,
    shouldOnlyShowDriverAwarenessDisclaimer: boolean
  ): Promise<boolean>;
  resetTermsAccepted(): Promise<void>;

  // Navigation Session  
  initializeNavigator(
    termsAndConditionsDialogOptions?: Object | null,
    taskRemovedBehavior?: number
  ): void;
  initializeNavigationSession(): Promise<void>;
  isInitialized(): Promise<boolean>;
  cleanup(): Promise<void>;

  // Routing
  buildRoute(destinations: Object[]): Promise<void>;
  buildRouteToMultipleDestinations(
    destinations: Object[],
    routingOptions?: Object
  ): Promise<void>;
  displayRoute(): Promise<void>;
  clearRoute(): Promise<void>;
  continueToNextDestination(): Promise<boolean>;

  // Navigation
  startGuidance(): Promise<void>;
  stopGuidance(): Promise<void>;
  isGuidanceRunning(): Promise<boolean>;
  startNavigation(): Promise<void>;
  stopNavigation(): Promise<void>;

  // Location
  allowBackgroundLocationUpdates(allow: boolean): Promise<void>;
  enableRoadSnappedLocationUpdates(intervalMilliseconds: number): Promise<void>;
  disableRoadSnappedLocationUpdates(): Promise<void>;

  // Simulation
  enableTurnByTurnNavigationEvents(
    numNextStepsToPreview: number
  ): Promise<void>;
  disableTurnByTurnNavigationEvents(): Promise<void>;
  registerSpeedAlertCallback(): Promise<void>;
  unregisterSpeedAlertCallback(): Promise<void>;
  setSpeedAlertOptions(options: Object): Promise<void>;
  getSpeedAlertOptions(): Promise<Object>;

  // Simulation
  setLocationSimulationOptions(options: Object): Promise<void>;
  getLocationSimulationOptions(): Promise<Object>;
  removeLocationSimulationOptions(): Promise<void>;

  // Voice
  setAudioGuidance(guidance: Object): Promise<void>;
  getAudioGuidance(): Promise<Object>;

  // Navigation Info
  getCurrentTimeAndDistance(): Promise<Object | null>;
  getRouteSegments(): Promise<Object[]>;

  // Misc
  setTaskRemovedBehavior(behavior: number): Promise<void>;
  enableUpdateInfo(enable: boolean): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NavModule');
