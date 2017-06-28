/*
 * Copyright (C) 2017 Pranav Pandey
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
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Helper class to perform {@link android.view.Window} operations and to
 * detect system configurations at runtime.
 */
public class DynamicWindowUtils {

    /**
     * Get the on-screen navigation bar size.
     *
     * @param context Context to get the resources and window service.
     *
     * @return On-screen navigation bar in point.
     *
     * @see android.content.Context#WINDOW_SERVICE
     * @see android.graphics.Point
     */
    public static Point getNavigationBarSize(@NonNull Context context) {
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
     * Get the app usable screen size.
     *
     * @param context Context to get the resources and window service.
     *
     * @return App usable screen size in point.
     *
     * @see android.content.Context#WINDOW_SERVICE
     * @see android.graphics.Point
     */
    public static Point getAppUsableScreenSize(@NonNull Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (DynamicVersionUtils.isHoneycombMR2()) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
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
     * @see android.content.Context#WINDOW_SERVICE
     * @see android.graphics.Point
     */
    public static Point getRealScreenSize(@NonNull Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (DynamicVersionUtils.isJellyBeanMR1()) {
            display.getRealSize(size);
        } else if (DynamicVersionUtils.isIceCreamSandwich()) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return size;
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
        return DynamicVersionUtils.isLollipop()
                && !getNavigationBarSize(context).equals(0, 0);
    }
}
