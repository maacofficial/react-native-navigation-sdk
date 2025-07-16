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

#ifdef RCT_NEW_ARCH_ENABLED

#import "NavViewManagerComponentView.h"

@implementation NavViewManagerComponentView

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    // Minimal stub implementation
    // This component is primarily for Android, so this is just a placeholder
    self.backgroundColor = [UIColor clearColor];
  }
  return self;
}

@end

// Export the required symbol for React Native Fabric component system
extern "C" {
Class NavViewManagerCls(void)
{
  return [NavViewManagerComponentView class];
}
}

#endif // RCT_NEW_ARCH_ENABLED
