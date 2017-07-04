<img src="https://raw.githubusercontent.com/pranavpandey/dynamic-utils/master/graphics/dynamic-utils_512x512.png" width="160" height="160" align="right" hspace="20">

# Dynamic Utils

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/pranavpandey/dynamic-utils.svg?branch=master)](https://travis-ci.org/pranavpandey/dynamic-utils)
[![Download](https://api.bintray.com/packages/pranavpandey/android/dynamic-utils/images/download.svg)](https://bintray.com/pranavpandey/android/dynamic-utils/_latestVersion)

A collection of static methods to perform various operations including color, device, drawable, 
package, tasks and sdk on Android 9+ (Gingerbread or above) devices.

---

## Table of Contents

- [Installation](https://github.com/pranavpandey/dynamic-utils#installation)
- [Usage](https://github.com/pranavpandey/dynamic-utils#usage)
    - [DynamicColorUtils](https://github.com/pranavpandey/dynamic-utils#dynamiccolorutils)
    - [DynamicDeviceUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdeviceutils)
    - [DynamicDrawableUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdrawableutils)
    - [DynamicLinkUtils](https://github.com/pranavpandey/dynamic-utils#dynamiclinkutils)
    - [DynamicPackageUtils](https://github.com/pranavpandey/dynamic-utils#dynamicpackageutils)
    - [DynamicTaskUtils](https://github.com/pranavpandey/dynamic-utils#dynamictaskutils)
    - [DynamicVersionUtils](https://github.com/pranavpandey/dynamic-utils#dynamicversionutils)
    - [DynamicWindowUtils](https://github.com/pranavpandey/dynamic-utils#dynamicwindowutils)
    - [DynamicUnitUtils](https://github.com/pranavpandey/dynamic-utils#dynamicwindowutils)
    - [Dependency](https://github.com/pranavpandey/dynamic-utils#dynamicunitutils)
- [License](https://github.com/pranavpandey/dynamic-utils#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    compile 'com.pranavpandey.android:dynamic-utils:0.3.0'
}
```

---

## Usage

It is divided into different classes according to their category for easy understanding and usage.
This library is fully commented so I am highlighting some of the functions below, keep exploring 
for more hidden features.

### DynamicColorUtils

Helper class to change colors dynamically.

1. `@ColorInt int adjustAlpha(color, factor)` - Adjust alpha of a color according to the given 
parameter.

2. `@ColorInt int calculateContrast(color1, color2) ` - Calculate color contrast difference 
between two colors based on luma value according to XYZ color space.

3. `@ColorInt int getAccentColor(color)` - Calculate accent color based on the given color for 
dynamic theme generation.

4. `@ColorInt int getColorDarkness(color) ` - Calculate darkness of a color.

5. `@ColorInt int getContrastColor(color, contrastWithColor)` - Calculate contrast of a color 
based on the given base color so that it will be visible always on top of the base color.

6. `@ColorInt int getLessVisibleColor(color)` - Calculate less visible color of a given color.

7. `@ColorInt int getTintColor(color)` - Calculate tint based on a given color for better 
readability.

8. `boolean isColorDark(color)` - Detect light or dark color.

9. `@ColorInt int shiftColor(color, by)` - Shift a color according to the given parameter.

### DynamicDeviceUtils

Helper class to detect device specific features like Telephony, etc.

1. `boolean hasTelephony(context)` - To detect if device has telephony feature or not by using 
PackageManager.

### DynamicDrawableUtils

Helper class to perform Drawable operations.

1. `void colorizeDrawable(drawable, wrap, color, mode)` - Colorize and return the mutated drawable 
so that, all other references do not change.

2. `Drawable setBackground(view, drawable)` - Set background of a given view in an efficient way 
by detecting the Android SDK at runtime.

### DynamicLinkUtils

A collection of functions to perform various operations on the URL or to generate intents.

1. `void shareApp(context, title, message)` - Share application via system default share intent 
so that user can select from the available apps if more than one apps are available.

2. `void viewInGooglePlay(context, packageName)` - View app in the Google Play or Android Market.

3. `void rateApp(context)` - View app in the Google Play or Android Market.

4. `void moreApps(context, publisher)` - View other apps of a Publisher in the Google Play 
or Android Market.

5. `void viewUrl(context, url)` - View any URL in the available app or browser.

6. `void report(context, appName, email)` - Ask questions or submit bug report to the developer 
via email.

### DynamicPackageUtils

Helper class to get package or app related information.

1. `ComponentName getComponentName(context)` - Get component name from the given context.

2. `CharSequence getAppLabel(context)` - Get application label from the given context.

3. `String getAppVersion(context)` - Get application version name from the given context.

4. `Drawable getAppIcon(context)` - Load application icon from the given context.

5. `booelan isSystemApp(applicationInfo)` - To detect the given ApplicationInfo is a system app 
or not.

### DynamicTaskUtils

Helper class to easily execute or cancel an AsyncTask by handling all the exceptions.

1. `void cancelTask(asyncTask)` - Try to cancel the supplied AsyncTask.

2. `void executeTask(asyncTask)` - Try to execute the supplied AsyncTask.

### DynamicVersionUtils

Helper class to detect the Android SDK version at runtime so that we can provide the user 
experience accordingly.

1. `boolean isGingerbread()` - To detect if the current Android version is Gingerbread or above.

2. `boolean isIceCreamSandwich()` - To detect if the current Android version is Ice Cream Sandwich 
or above.

...

10. `boolean isNougat()` - To detect if the current Android version is N or above.

11. `boolean isNougatMR1()` - To detect if the current Android version is N_MR1 or above.

### DynamicWindowUtils

Helper class to perform Window operations and to detect system configurations at runtime.

1. `Point getAppUsableScreenSize(context)` - Get the app usable screen size.

2. `Point getRealScreenSize(context)` - Get the real screen size.

3. `Point getNavigationBarSize(context)` - Get the on-screen navigation bar size.

4. `boolean isNavigationBarThemeSupported(context)` - Detect support for navigation bar theme.

### DynamicUnitUtils

Helper class to perform unit conversions.

1. `int convertDpToPixels(dp)` - To convert DP into pixels.

2. `int convertPixelsToDp(pixels)` - To convert pixels into DP.

3. `int convertSpToPixels(sp)` - To convert SP into pixels.

4. `int convertPixelsToSp(pixels)` - To convert pixels into SP.

### Dependency

This library depends on the [support-compat](https://developer.android.com/topic/libraries/support-library/packages.html#v4-compat) so, its functions
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
