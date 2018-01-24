/*
 * Copyright 2017 Pranav Pandey
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

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Helper class to perform {@link android.view.Window} operations and to
 * detect system configurations at runtime.
 */
public class DynamicWindowUtils {

    /**
     * Get the app usable screen size.
     *
     * @param context Context to get the resources and window service.
     *
     * @return App usable screen size in point.
     *
     * @see Context#WINDOW_SERVICE
     * @see Point
     */
    public static @NonNull Point getAppUsableScreenSize(@NonNull Context context) {
        Point size = new Point();
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();

            if (DynamicVersionUtils.isHoneycombMR2()) {
                display.getSize(size);
            } else {
                size.x = display.getWidth();
                size.y = display.getHeight();
            }
        }

        return size;
    }

    /**
     * Get the real screen size.
     *
     * @param context Context to get the resources and window service.
     *
     * @return Real screen size in point.
     *
     * @see Context#WINDOW_SERVICE
     * @see Point
     */
    public static @NonNull Point getRealScreenSize(@NonNull Context context) {
        Point size = new Point();
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();

            if (DynamicVersionUtils.isJellyBeanMR1()) {
                display.getRealSize(size);
            } else if (DynamicVersionUtils.isIceCreamSandwich()) {
                try {
                    size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                    size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                } catch (IllegalAccessException ignored) {
                } catch (InvocationTargetException ignored) {
                } catch (NoSuchMethodException ignored) {
                }
            }
        }

        return size;
    }

    /**
     * Get the on-screen navigation bar size.
     *
     * @param context Context to get the resources and window service.
     *
     * @return On-screen navigation bar in point.
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
     * Detect if on-screen navigation bar is present or not.
     *
     * @param context Context to get the resources. Usually your
     *                {@link android.app.Application} or
     *                {@link android.app.Activity}
     *                object.
     *
     * @return {@code true} if on-screen navigation bar is present.
     */
    public static boolean isNavigationBarPresent(@NonNull Context context) {
        return !getNavigationBarSize(context).equals(0, 0);
    }

    /**
     * Detect support for navigation bar theme.
     *
     * @param context Context to get the resources. Usually your
     *                {@link android.app.Application} or
     *                {@link android.app.Activity}
     *                object.
     *
     * @return {@code true} if navigation bar theme is supported.
     */
    public static boolean isNavigationBarThemeSupported(@NonNull Context context) {
        return DynamicVersionUtils.isLollipop() && isNavigationBarPresent(context);
    }

    /**
     * Get the current device orientation.
     *
     * @param context Context to get the resources and window service.
     *
     * @return Current activity orientation info.
     *
     * @see ActivityInfo#SCREEN_ORIENTATION_PORTRAIT
     * @see ActivityInfo#SCREEN_ORIENTATION_REVERSE_PORTRAIT
     * @see ActivityInfo#SCREEN_ORIENTATION_LANDSCAPE
     * @see ActivityInfo#SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     */
    public static int getScreenOrientation(@NonNull Context context) {
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager == null) {
            return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }

        Display display = windowManager.getDefaultDisplay();
        int rotation = display.getRotation();
        DisplayMetrics displayMatrix = context.getResources().getDisplayMetrics();
        float scale = displayMatrix.density;
        int width = (int) (displayMatrix.widthPixels * scale + 0.5f);
        int height = (int) (displayMatrix.heightPixels * scale + 0.5f);

        int orientation;
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        } else {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    /**
     * @return The {@link WindowManager} overlay flag according to the
     *         android version.
     *
     * @param alert {@code true} to return alert flag on below Android O
     *              devices.
     *
     * @see WindowManager.LayoutParams#TYPE_APPLICATION_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_OVERLAY
     * @see WindowManager.LayoutParams#TYPE_SYSTEM_ALERT
     */
    public static int getOverlayFlag(boolean alert) {
        return DynamicVersionUtils.isOreo()
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : alert ? WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                : WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
    }
}
