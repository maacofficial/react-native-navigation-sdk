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

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DoNotStrip
public class Package extends TurboReactPackage {

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Arrays.asList(NavViewManager.getInstance(reactContext));
  }

  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
    NavViewManager viewManager = NavViewManager.getInstance(reactContext);
    
    switch (name) {
      case "NavModule":
        return NavModule.getInstance(reactContext, viewManager);
      case "NavAutoModule":
        return new NavAutoModule(reactContext);
      case "NavViewModule":
        return new NavViewModule(reactContext, viewManager);
      default:
        return null;
    }
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return () -> {
      Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
      moduleInfos.put("NavModule", new ReactModuleInfo(
        "NavModule",
        "NavModule",
        false, // canOverrideExistingModule
        false, // needsEagerInit
        true,  // hasConstants
        false, // isCxxModule
        true   // isTurboModule
      ));
      moduleInfos.put("NavAutoModule", new ReactModuleInfo(
        "NavAutoModule",
        "NavAutoModule",
        false, // canOverrideExistingModule
        false, // needsEagerInit
        true,  // hasConstants
        false, // isCxxModule
        true   // isTurboModule
      ));
      moduleInfos.put("NavViewModule", new ReactModuleInfo(
        "NavViewModule",
        "NavViewModule",
        false, // canOverrideExistingModule
        false, // needsEagerInit
        true,  // hasConstants
        false, // isCxxModule
        true   // isTurboModule
      ));
      return moduleInfos;
    };
  }

  // Legacy support for old architecture
  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();
    NavViewManager viewManager = NavViewManager.getInstance(reactContext);
    modules.add(NavModule.getInstance(reactContext, viewManager));
    modules.add(new NavAutoModule(reactContext));
    modules.add(new NavViewModule(reactContext, viewManager));

    return modules;
  }
}
