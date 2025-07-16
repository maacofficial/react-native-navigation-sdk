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

#import <UIKit/UIKit.h>

#ifdef RCT_NEW_ARCH_ENABLED

// Forward declare the interface to ensure it's available
@interface RCTNavViewComponentView : UIView
@end

@implementation RCTNavViewComponentView

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    // Minimal stub for new architecture compatibility
    // Real navigation view functionality is handled by legacy view manager
    self.backgroundColor = [UIColor clearColor];
  }
  return self;
}

@end

#endif // RCT_NEW_ARCH_ENABLED

// Export the required symbol for React Native Fabric component system
// This function is expected by RCTThirdPartyFabricComponentsProvider
extern "C" {
__attribute__((visibility("default")))
Class RCTNavViewCls(void)
{
#ifdef RCT_NEW_ARCH_ENABLED
  return [RCTNavViewComponentView class];
#else
  return nil;
#endif
}
}
