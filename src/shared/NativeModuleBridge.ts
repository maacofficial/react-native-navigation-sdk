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

import { NativeModules } from 'react-native';

// Import TurboModule specs conditionally
let NativeNavModule: any;
let NativeNavViewModule: any;
let NativeNavAutoModule: any;

try {
  // Try to import TurboModule specs first (new architecture)
  NativeNavModule = require('../specs/NativeNavModuleSpec').default;
  NativeNavViewModule = require('../specs/NativeNavViewModuleSpec').default;
  NativeNavAutoModule = require('../specs/NativeNavAutoModuleSpec').default;
} catch (e) {
  // Fall back to old architecture NativeModules
  NativeNavModule = NativeModules.NavModule;
  NativeNavViewModule = NativeModules.NavViewModule;
  NativeNavAutoModule = NativeModules.NavAutoModule;
}

// Export the modules
export const NavModule = NativeNavModule;
export const NavViewModule = NativeNavViewModule;
export const NavAutoModule = NativeNavAutoModule;

// Event dispatchers remain the same for both architectures
export const { NavEventDispatcher, NavAutoEventDispatcher } = NativeModules;
