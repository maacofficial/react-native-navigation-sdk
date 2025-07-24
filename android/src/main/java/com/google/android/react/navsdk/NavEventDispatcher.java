/**
 * Copyright 2024 Google LLC
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

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableArray;
import java.util.HashMap;
import java.util.Map;

public class NavEventDispatcher extends ReactContextBaseJavaModule {
  public static final String REACT_CLASS = "NavEventDispatcher";
  private static final String TAG = "NavEventDispatcher";
  private static NavEventDispatcher instance;
  
  public NavEventDispatcher(ReactApplicationContext reactContext) {
    super(reactContext);
  }
  
  public static synchronized NavEventDispatcher getInstance(ReactApplicationContext reactContext) {
    if (instance == null) {
      instance = new NavEventDispatcher(reactContext);
    }
    return instance;
  }
  
  public static synchronized NavEventDispatcher getInstance() {
    return instance;
  }
  
  @Override
  public String getName() {
    return REACT_CLASS;
  }
  
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }
  
  public void sendEvent(String eventName, WritableArray params) {
    if (getReactApplicationContext() != null) {
      try {
        DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = 
            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        
        if (eventEmitter != null) {
          // Send event with the NavModule_ prefix that React Native side expects
          String fullEventName = "NavModule_" + eventName;
          eventEmitter.emit(fullEventName, params);
          android.util.Log.d(TAG, "Event sent successfully: " + fullEventName);
        } else {
          android.util.Log.e(TAG, "EventEmitter is null, cannot send event: " + eventName);
        }
      } catch (Exception e) {
        android.util.Log.e(TAG, "Failed to send event to React Native: " + eventName, e);
      }
    } else {
      android.util.Log.e(TAG, "ReactContext is null, cannot send event: " + eventName);
    }
  }
  
  @Override
  public boolean canOverrideExistingModule() {
    return true;
  }
}
