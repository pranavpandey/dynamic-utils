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

import android.os.Build;

/**
 * Helper class to detect the Android SDK version at runtime so that
 * we can provide the user experience accordingly.
 *
 * @see Build.VERSION#SDK_INT
 */
public class DynamicVersionUtils {

    /**
     * To detect if the current Android version is Gingerbread or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#GINGERBREAD}.
     *
     * @deprecated Minimum SDK is now {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * To detect if the current Android version is Gingerbread MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#GINGERBREAD_MR1}.
     *
     * @deprecated Minimum SDK is now {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * To detect if the current Android version is Honeycomb or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#HONEYCOMB}.
     *
     * @deprecated Minimum SDK is now {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * To detect if the current Android version is Honeycomb MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#HONEYCOMB_MR1}.
     *
     * @deprecated Minimum SDK is now {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * To detect if the current Android version is Honeycomb MR2 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#HONEYCOMB_MR2}.
     *
     * @deprecated Minimum SDK is now {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * To detect if the current Android version is Ice Cream Sandwich or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * To detect if the current Android version is Ice Cream Sandwich MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH_MR1}.
     */
    public static boolean isIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * To detect if the current Android version is JellyBean or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN}.
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * To detect if the current Android version is JellyBean MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN_MR1}.
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * To detect if the current Android version is KitKat or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#KITKAT}.
     */
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * To detect if the current Android version is Lollipop or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#LOLLIPOP}.
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * To detect if the current Android version is Lollipop MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#LOLLIPOP_MR1}.
     */
    public static boolean isLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * To detect if the current Android version is M or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#M}.
     */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * To detect if the current Android version is N or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N}.
     */
    public static boolean isNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * To detect if the current Android version is N_MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N_MR1}.
     */
    public static boolean isNougatMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * To detect if the current Android version is O or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O}.
     */
    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * To detect if the current Android version is O_MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O_MR1}.
     */
    public static boolean isOreoMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }
}
