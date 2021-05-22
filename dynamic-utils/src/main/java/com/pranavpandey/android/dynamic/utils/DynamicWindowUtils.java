/*
 * Copyright 2017-2021 Pranav Pandey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pranavpandey.android.dynamic.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;

/**
 * Helper class to perform window operations and to detect system configurations at runtime.
 *
 * @see WindowManager
 */
public class DynamicWindowUtils {

    /**
     * Returns the correct display according to the different API levels.
     *
     * @param context The context to be used.
     *
     * @return The display according to the API level.
     *
     * @see Context#getDisplay()
     * @see WindowManager#getDefaultDisplay()
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.R)
    public static @Nullable Display getDisplay(@Nullable Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager().getDefaultDisplay();
        }

        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        return DynamicSdkUtils.is30() ? context.getDisplay()
                : windowManager != null ? windowManager.getDefaultDisplay() : null;
    }

    /**
     * Returns the display used by the supplied context.
     *
     * @param context The context to be used.
     *
     * @return The display density used by the supplied context.
     *
     * @see Context#getResources()
     * @see Resources#getDisplayMetrics()
     * @see DisplayMetrics#density
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static float getDisplayDensity(@Nullable Context context) {
        if (context == null) {
            return Resources.getSystem().getDisplayMetrics().density;
        }

        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * Get the app usable screen size.
     *
     * @param context The context to be used.
     *
     * @return The app usable screen size in point.
     *
     * @see Context#WINDOW_SERVICE
     * @see Point
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.R)
    public static @NonNull Point getAppUsableScreenSize(@Nullable Context context) {
        Point size = new Point();

        if (DynamicSdkUtils.is30() && context != null) {
            WindowMetrics windowMetrics;

            if (context instanceof Activity) {
                windowMetrics = ((Activity) context).getWindowManager().getCurrentWindowMetrics();
            } else {
                WindowManager windowManager = (WindowManager)
                        context.getSystemService(Context.WINDOW_SERVICE);
                windowMetrics = windowManager.getCurrentWindowMetrics();
            }

            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(
                    WindowInsets.Type.navigationBars());
            size.x = windowMetrics.getBounds().width() - insets.left - insets.right;
            size.y = windowMetrics.getBounds().height() - insets.top - insets.bottom;
        } else {
            Display display = getDisplay(context);

            if (display != null) {
                size.x = display.getWidth();
                size.y = display.getHeight();
            }
        }

        return size;
    }

    /**
     * Get the real screen size.
     *
     * @param context The context to be used.
     *
     * @return The real screen size in point.
     *
     * @see Context#WINDOW_SERVICE
     * @see Point
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static @NonNull Point getRealScreenSize(@Nullable Context context) {
        Point size = new Point();
        Display display = getDisplay(context);

        if (display != null) {
            try {
                if (DynamicSdkUtils.is17()) {
                    display.getRealSize(size);
                } else if (DynamicSdkUtils.is14()) {
                    Object width = Display.class.getMethod("getRawWidth").invoke(display);
                    Object height = Display.class.getMethod("getRawHeight").invoke(display);

                    if (width != null) {
                        size.x = (Integer) width;
                    }

                    if (height != null) {
                        size.y = (Integer) height;
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return size;
    }

    /**
     * Get the status bar size.
     *
     * @param context The context to get the resources.
     *
     * @return The status bar size in pixels.
     */
    public static int getStatusBarSize(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");

        if (resourceId != 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    /**
     * Get the on-screen navigation bar size.
     *
     * @param context The context to be used.
     *
     * @return The on-screen navigation bar size in point.
     *
     * @see Context#WINDOW_SERVICE
     * @see Point
     */
    public static @NonNull Point getNavigationBarSize(@NonNull Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    /**
     * Detects if on-screen navigation bar is present or not.
     *
     * @param context The context to retrieve the resources.
     *
     * @return {@code true} if on-screen navigation bar is present.
     */
    public static boolean isNavigationBarPresent(@NonNull Context context) {
        return !getNavigationBarSize(context).equals(0, 0);
    }

    /**
     * Detects support for navigation bar theme.
     *
     * @param context The context to retrieve the resources.
     *
     * @return {@code true} if navigation bar theme is supported.
     */
    public static boolean isNavigationBarThemeSupported(@NonNull Context context) {
        return DynamicSdkUtils.is21() && isNavigationBarPresent(context);
    }

    /**
     * Detects support for gesture navigation.
     *
     * @param context The context to retrieve the resources.
     *
     * @return {@code true} if gesture navigation is supported.
     */
    public static boolean isGestureNavigation(@NonNull Context context) {
        Point navigationBarSize = getNavigationBarSize(context);
        return DynamicSdkUtils.is29() && navigationBarSize.y > 0
                && navigationBarSize.y <= DynamicUnitUtils.convertDpToPixels(24);
    }

    /**
     * Set the hide navigation flag for edge-to-edge content on API 23 and above.
     *
     * @param window The window to set the system ui flags.
     * @param edgeToEdge {@code true} to hide the layout navigation.
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static void setEdgeToEdge(@Nullable Window window, boolean edgeToEdge) {
        if (window == null) {
            return;
        }

        WindowCompat.setDecorFitsSystemWindows(window, !edgeToEdge);
    }

    /**
     * Get the current device orientation.
     *
     * @param context The context to be used.
     *
     * @return The current activity orientation info.
     *
     * @see ActivityInfo#SCREEN_ORIENTATION_PORTRAIT
     * @see ActivityInfo#SCREEN_ORIENTATION_REVERSE_PORTRAIT
     * @see ActivityInfo#SCREEN_ORIENTATION_LANDSCAPE
     * @see ActivityInfo#SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     */
    public static int getScreenOrientation(@Nullable Context context) {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        Display display = getDisplay(context);

        if (display == null) {
            return orientation;
        }

        DisplayMetrics displayMatrix = context.getResources().getDisplayMetrics();
        float scale = displayMatrix.density;
        int rotation = display.getRotation();
        int width = (int) (displayMatrix.widthPixels * scale + 0.5f);
        int height = (int) (displayMatrix.heightPixels * scale + 0.5f);

        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_0:
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        } else {
            switch (rotation) {
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_0:
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    /**
     * Get the overlay flag according to the Android version.
     *
     * @param alert {@code true} to return alert flag on API 25 and below.
     * @param accessibility {@code true} to return the accessibility overlay.
     *
     * @return The {@link WindowManager} overlay flag according to the Android version.
     *
     * @see WindowManager.LayoutParams#TYPE_APPLICATION_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_ALERT
     * @see WindowManager.LayoutParams#TYPE_ACCESSIBILITY_OVERLAY
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.O)
    public static int getOverlayFlag(boolean alert, boolean accessibility) {
        if (DynamicSdkUtils.is22() && accessibility) {
            return WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        }

        return DynamicSdkUtils.is26()
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : alert ? WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                : WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
    }

    /**
     * Get the overlay flag according to the Android version.
     *
     * @param alert {@code true} to return alert flag on API 25 and below.
     *
     * @return The {@link WindowManager} overlay flag according to the Android version.
     *
     * @see #getOverlayFlag(boolean, boolean)
     * @see WindowManager.LayoutParams#TYPE_APPLICATION_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_ALERT
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static int getOverlayFlag(boolean alert) {
        return getOverlayFlag(alert, false);
    }
}
