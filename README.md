<img src="https://raw.githubusercontent.com/pranavpandey/dynamic-utils/master/graphics/dynamic-utils_512x512.png" width="160" height="160" align="right" hspace="20">

# Dynamic Utils

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/pranavpandey/dynamic-utils.svg?branch=master)](https://travis-ci.org/pranavpandey/dynamic-utils)

A collection of static methods to perform various operations including color, device, drawable, 
package, tasks and sdk on Android 9+ (Gingerbread or above) devices.

---

## Table of Contents

1. [Installation](https://github.com/pranavpandey/dynamic-utils#installation)
2. [Usage](https://github.com/pranavpandey/dynamic-utils#usage)
    1. [DynamicColorUtils](https://github.com/pranavpandey/dynamic-utils#dynamiccolorutils)
    2. [DynamicDeviceUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdeviceutils)
    3. [DynamicDrawableUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdrawableutils)
    4. [DynamicLinkUtils](https://github.com/pranavpandey/dynamic-utils#dynamiclinkutils)
    5. [DynamicPackageUtils](https://github.com/pranavpandey/dynamic-utils#dynamicpackageutils)
    6. [DynamicTaskUtils](https://github.com/pranavpandey/dynamic-utils#dynamictaskutils)
    7. [DynamicVersionUtils](https://github.com/pranavpandey/dynamic-utils#dynamicversionutils)
    8. [DynamicWindowUtils](https://github.com/pranavpandey/dynamic-utils#dynamicwindowutils)
    9. [Dependency](https://github.com/pranavpandey/dynamic-utils#dependency)
3. [License](https://github.com/pranavpandey/dynamic-utils#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    compile 'com.pranavpandey.android:dynamic-utils:0.1.0'
}
```

---

## Usage

It is divided into different classes according to their category for easy understanding and usage.
This library is fully commented so I am highlighting some of the functions below, keep exploring 
for more hidden features.

### DynamicColorUtils

Helper class to change colors dynamically.

1. `adjustAlpha(color, factor)` - Adjust alpha of a color according to the given parameter.

2. `calculateContrast(color1, color2) ` - Calculate color contrast difference between two 
colors based on luma value according to XYZ color space.

3. `getAccentColor(color)` - Calculate accent color based on the given color for dynamic theme 
generation.

4. `getColorDarkness(color) ` - Calculate darkness of a color.

5. `getContrastColor(color, contrastWithColor)` - Calculate contrast of a color based on the given 
base color so that it will be visible always on top of the base color.

6. `getLessVisibleColor(color)` - Calculate less visible color of a given color.

7. `getTintColor(color)` - Calculate tint based on a given color for better readability.

8. `isColorDark(color)` - Detect light or dark color.

9. `shiftColor(color, by)` - Shift a color according to the given parameter.

### DynamicDeviceUtils

Helper class to detect device specific features like Telephony, etc.

1. `hasTelephony(context)` - To detect if device has telephony feature or not by using PackageManager.

### DynamicDrawableUtils

Helper class to perform Drawable operations.

1. `colorizeDrawable(drawable, wrap, color, mode)` - Colorize and return the mutated drawable so that, 
all other references do not change.

2. `setBackground(view, drawable)` - Set background of a given view in an efficient way by detecting 
the Android SDK at runtime.

### DynamicLinkUtils

A collection of functions to perform various operations on the URL or to generate intents.

1. `moreApps(context, publisher)` - View other apps of a Publisher in the Google Play or Android 
Market.

2. `rateApp(context)` - View app in the Google Play or Android Market.

3. `report(context, appName, email)` - Ask questions or submit bug report to the developer via email.

4. `shareApp(context, title, message)` - Share application via system default share intent so that 
user can select from the available apps if more than one apps are available.

5. `viewInGooglePlay(context, packageName)` - View app in the Google Play or Android Market.

6. `viewUrl(context, url)` - View any URL in the available app or browser.

### DynamicPackageUtils

Helper class to get package or app related information.

1. `getAppIcon(context)` - Load application icon from the given context.

2. `getAppLabel(context)` - Get application label from the given context.

3. `getAppVersion(context)` - Get application version name from the given context.

4. `getComponentName(context)` - Get component name from the given context.

5. `isSystemApp(applicationInfo)` - To detect the given ApplicationInfo is a system app or not.

### DynamicTaskUtils

Helper class to easily execute or cancel an AsyncTask by handling all the exceptions.

1. `cancelTask(asyncTask)` - Try to cancel the supplied AsyncTask.

2. `executeTask(asyncTask)` - Try to execute the supplied AsyncTask.

### DynamicVersionUtils

Helper class to detect the Android SDK version at runtime so that we can provide the user 
experience accordingly.

1. `isGingerbread()` - To detect if the current Android version is Gingerbread or above.

2. `isIceCreamSandwich()` - To detect if the current Android version is Ice Cream Sandwich or above.

...

10. `isNougat()` - To detect if the current Android version is N or above.

11. `isNougatMR1()` - To detect if the current Android version is N_MR1 or above.

### DynamicWindowUtils

Helper class to perform Window operations and to detect system configurations at runtime.

1. `getAppUsableScreenSize(context)` - Get the app usable screen size.

2. `getNavigationBarSize(context)` - Get the on-screen navigation bar size.

3. `getRealScreenSize(context)` - Get the real screen size.

4. `isNavigationBarThemeSupported(context)` - Detect support for navigation bar theme.

### Dependency

As it depends on the [support-compat](https://developer.android.com/topic/libraries/support-library/packages.html#v4-compat), its functions
can be used to perform other operations.

---

## License

    Copyright (c) 2017 Pranav Pandey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
