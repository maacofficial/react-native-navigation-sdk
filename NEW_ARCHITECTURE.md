# React Native New Architecture Compatibility

This React Native Navigation SDK has been updated to support React Native's new architecture (Fabric + TurboModules). The library is backward compatible and works with both the old and new architectures.

## Features

### TurboModules Support
- `NavModule` - Navigation session management and guidance
- `NavAutoModule` - Android Auto functionality  
- `NavViewModule` - Map view interactions

### Fabric Components Support
- `NavView` - Custom native view component with improved performance

## Enabling New Architecture

### Android

1. Open `android/gradle.properties` in your React Native app
2. Set `newArchEnabled=true`:

```properties
# Use this property to enable support to the new architecture.
newArchEnabled=true
```

### iOS

1. Open `ios/Podfile` in your React Native app  
2. Add or modify the flag:

```ruby
ENV['RCT_NEW_ARCH_ENABLED'] = '1'
```

3. Run `cd ios && bundle exec pod install`

## Verification

To verify the new architecture is working:

1. Check that TurboModules are being used:
```javascript
import { NavModule } from '@googlemaps/react-native-navigation-sdk';
console.log('Using TurboModule:', NavModule.__turboModuleProxy != null);
```

2. For Fabric components, you should see improved performance and animations.

## Migration Notes

### No Code Changes Required
The library automatically detects which architecture is enabled and uses the appropriate implementation:

- **Old Architecture**: Uses `NativeModules` and `requireNativeComponent`
- **New Architecture**: Uses TurboModule specs and Fabric component specs

### Performance Benefits

With the new architecture enabled, you'll experience:

- Faster bridge communication
- Reduced serialization overhead  
- Improved startup time
- Better memory management
- Smoother animations

### Compatibility

- **React Native**: 0.70.0+ (recommended 0.74.0+)
- **Android**: API level 24+ (unchanged)
- **iOS**: 15.0+ (unchanged)

## Troubleshooting

### Build Issues

If you encounter build issues after enabling the new architecture:

1. Clean your project:
```bash
# Android
cd android && ./gradlew clean

# iOS  
cd ios && xcodebuild clean
```

2. Reset Metro cache:
```bash
npx react-native start --reset-cache
```

3. Reinstall dependencies:
```bash
rm -rf node_modules && yarn install
# iOS only
cd ios && bundle exec pod install
```

### Common Issues

1. **"Cannot find module" errors**: Ensure you're using React Native 0.70.0+
2. **TurboModule not found**: Verify `newArchEnabled=true` and rebuild
3. **Component rendering issues**: Clear Metro cache and rebuild

## Support

The new architecture implementation maintains full API compatibility. All existing code will continue to work without modifications.

For issues specific to the new architecture, please include:
- React Native version
- Whether new architecture is enabled
- Platform (iOS/Android)
- Error logs with stack traces
