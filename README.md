<img src="https://raw.githubusercontent.com/pranavpandey/dynamic-utils/master/graphics/dynamic-utils.png" width="160" height="160" align="right" hspace="20">

# Dynamic Utils

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/pranavpandey/dynamic-utils.svg?branch=master)](https://travis-ci.org/pranavpandey/dynamic-utils)
[![Download](https://api.bintray.com/packages/pranavpandey/android/dynamic-utils/images/download.svg)](https://bintray.com/pranavpandey/android/dynamic-utils/_latestVersion)

A collection of static methods to perform various operations including color, device, drawable,
package, tasks and sdk on Android 2.3 (API 9) and above devices.

>Since v0.4.0, it uses [26.x.x support libraries](https://developer.android.com/topic/libraries/support-library/revisions.html#26-0-0)
so, minimum SDK will be Android 4.0 (API 14).
<br/>Since v2.0.0, it uses [AndroidX](https://developer.android.com/jetpack/androidx/) so, first
[migrate](https://developer.android.com/jetpack/androidx/migrate) your project to AndroidX.

---

## Contents

- [Installation](https://github.com/pranavpandey/dynamic-utils#installation)
- [Usage](https://github.com/pranavpandey/dynamic-utils#usage)
    - [DynamicAnimUtils](https://github.com/pranavpandey/dynamic-utils#dynamicanimutils)
    - [DynamicBitmapUtils](https://github.com/pranavpandey/dynamic-utils#dynamicbitmaputils)
    - [DynamicColorUtils](https://github.com/pranavpandey/dynamic-utils#dynamiccolorutils)
    - [DynamicDeviceUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdeviceutils)
    - [DynamicDrawableUtils](https://github.com/pranavpandey/dynamic-utils#dynamicdrawableutils)
    - [DynamicLinkUtils](https://github.com/pranavpandey/dynamic-utils#dynamiclinkutils)
    - [DynamicPackageUtils](https://github.com/pranavpandey/dynamic-utils#dynamicpackageutils)
    - [DynamicTaskUtils](https://github.com/pranavpandey/dynamic-utils#dynamictaskutils)
    - [DynamicUnitUtils](https://github.com/pranavpandey/dynamic-utils#dynamicwindowutils)
    - [DynamicSdkUtils](https://github.com/pranavpandey/dynamic-utils#dynamicsdkutils)
    - [DynamicViewUtils](https://github.com/pranavpandey/dynamic-utils#dynamicviewutils)
    - [DynamicWindowUtils](https://github.com/pranavpandey/dynamic-utils#dynamicwindowutils)
- [License](https://github.com/pranavpandey/dynamic-utils#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    // For AndroidX enabled projects.
    implementation 'com.pranavpandey.android:dynamic-utils:3.0.2'

    // For legacy projects.
    implementation 'com.pranavpandey.android:dynamic-utils:1.3.0'
}
```

---

## Usage

It is divided into different classes according to their category for easy understanding and usage.
This library is fully commented so I am highlighting some of the functions below, keep exploring
for more hidden features.

> For complete reference, please read the [documentation](https://pranavpandey.github.io/dynamic-utils).

### DynamicAnimUtils

Helper class to perform animation related operations.

- `void playAnimation(view, animator)` - Play an animator animation on a view.

### DynamicBitmapUtils

Helper class to perform bitmap operations.

- `Bitmap getBitmapFormDrawable(drawable)` - Get bitmap from the supplied drawable.

- `Bitmap resizeBitmap(bitmap, newWidth, newHeight)` - Resize bitmap to the new width and height.

- `Bitmap applyColorFilter(bitmap, colorFilter)` - Apply color filter on the supplied bitmap.

- `Bitmap applyColorFilter(bitmap, color)` - Apply monochrome color filter on the supplied bitmap.

- `@ColorInt int getDominantColor(bitmap)` - Extract the dominant color form the supplied bitmap.

### DynamicColorUtils

Helper class to change colors dynamically.

- `@ColorInt int adjustAlpha(color, factor)` - Adjust alpha of a color according to the given
parameter.

- `@ColorInt int calculateContrast(color1, color2)` - Calculate color contrast difference
between two colors based on luma value according to XYZ color space.

- `@ColorInt int getAccentColor(color)` - Calculate accent color based on the given color for
dynamic theme generation.

- `@ColorInt int getColorDarkness(color)` - Calculate darkness of a color.

- `@ColorInt int getContrastColor(color, contrastWithColor)` - Calculate contrast of a color
based on the given base color so that it will be visible always on top of the base color.

- `@ColorInt int getLessVisibleColor(color)` - Calculate less visible color of a given color.

- `@ColorInt int getTintColor(color)` - Calculate tint based on a given color for better
readability.

- `boolean isColorDark(color)` - Detect light or dark color.

- `@ColorInt int shiftColor(color, by)` - Shift a color according to the given parameter.

### DynamicDeviceUtils

Helper class to detect device specific features like Telephony, etc.

- `boolean hasTelephony(context)` - To detect if device has telephony feature or not by using
PackageManager.

### DynamicDrawableUtils

Helper class to perform Drawable operations.

- `void colorizeDrawable(drawable, wrap, color, mode)` - Colorize and return the mutated drawable
so that, all other references do not change.

- `Drawable setBackground(view, drawable)` - Set background of a given view in an efficient way
by detecting the Android SDK at runtime.

### DynamicLinkUtils

A collection of functions to perform various operations on the URL or to generate intents.

- `void shareApp(context, title, message)` - Share application via system default share intent
so that user can select from the available apps if more than one apps are available.

- `void viewInGooglePlay(context, packageName)` - View app in the Google Play or Android Market.

- `void rateApp(context)` - View app in the Google Play or Android Market.

- `void moreApps(context, publisher)` - View other apps of a Publisher in the Google Play
or Android Market.

- `void viewUrl(context, url)` - View any URL in the available app or browser.

- `void report(context, appName, email)` - Ask questions or submit bug report to the developer
via email.

### DynamicPackageUtils

Helper class to get package or app related information.

- `ComponentName getComponentName(context)` - Get component name from the given context.

- `CharSequence getAppLabel(context)` - Get application label from the given context.

- `String getAppVersion(context)` - Get application version name from the given context.

- `Drawable getAppIcon(context)` - Load application icon from the given context.

- `boolean isSystemApp(applicationInfo)` - To detect the given ApplicationInfo is a system app
or not.

### DynamicTaskUtils

Helper class to easily execute or cancel an AsyncTask by handling all the exceptions.

- `void cancelTask(asyncTask)` - Try to cancel the supplied AsyncTask.

- `void executeTask(asyncTask)` - Try to execute the supplied AsyncTask.

### DynamicUnitUtils

Helper class to perform unit conversions.

- `int convertDpToPixels(dp)` - To convert DP into pixels.

- `int convertPixelsToDp(pixels)` - To convert pixels into DP.

- `int convertSpToPixels(sp)` - To convert SP into pixels.

- `int convertPixelsToSp(pixels)` - To convert pixels into SP.

### DynamicSdkUtils

Helper class to detect the Android SDK version at runtime so that we can provide the user
experience accordingly. Pass `true` in the alternate method to check for equality.

- `boolean is14()` - To detect if the current API version is 14 (Android 4.0) or above.

- `boolean is14(equals)` - To detect if the current API version is 14 (Android 4.0) or above.

...

- `boolean is29()` - To detect if the current API version is 29 (Android 10) or above.

- `boolean is29(equals)` - To detect if the current API version is 29 (Android 10) or above.

### DynamicViewUtils

Helper class to perform `view` operations.

- `void setLightStatusBar(view, isLight)` - Set light status bar if we are using light primary
color on Android M or above devices.

- `void setLightNavigationBar(view, isLight)` - Set light navigation bar if we are using light
primary color on API 26 and above devices.

### DynamicWindowUtils

Helper class to perform Window operations and to detect system configurations at runtime.

- `Point getAppUsableScreenSize(context)` - Get the app usable screen size.

- `Point getRealScreenSize(context)` - Get the real screen size.

- `Point getNavigationBarSize(context)` - Get the on-screen navigation bar size.

- `boolean isNavigationBarPresent(context)` - Detect if on-screen navigation bar is present or not.

- `boolean isNavigationBarThemeSupported(context)` - Detect support for navigation bar theme.

---

## Translations
- German (de) - Flubberlutsch
- Hindi (hi) - Siddh Narhari
- Indonesian (in) - Gamal Kevin A
- Italian (it) - Nicola
- Spanish (es) - Dave
- Turkish (tr) - Fatih Fırıncı
- Chinese (Simplified) (zh-rCN) - John Canny

---

## Author

Pranav Pandey

[![GitHub](https://img.shields.io/github/followers/pranavpandey?label=GitHub&style=social)](https://github.com/pranavpandey)
[![Follow on Twitter](https://img.shields.io/twitter/follow/pranavpandeydev?label=Follow&style=social)](https://twitter.com/intent/follow?screen_name=pranavpandeydev)
[![Donate via PayPal](https://img.shields.io/static/v1?label=Donate&message=PayPal&color=blue)](https://paypal.me/pranavpandeydev)

---

## License

    Copyright 2019 Pranav Pandey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
