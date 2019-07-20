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

import android.os.Build;

/**
 * Helper class to detect the Android SDK version at runtime so that we can provide the 
 * user experience accordingly.
 *
 * @see Build.VERSION#SDK_INT
 */
public class DynamicVersionUtils {

    /**
     * Detects if the current API version is 14 or above.
     *
     * @return {@code true} if the current API version is 14 or above.
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Detects if the current API version is 14 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 14 or above.
     */
    public static boolean isIceCreamSandwich(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.ICE_CREAM_SANDWICH : isIceCreamSandwich();
    }

    /**
     * Detects if the current API version is 15 or above.
     *
     * @return {@code true} if the current API version is 15 or above.
     */
    public static boolean isIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Detects if the current API version is 15 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 15 or above.
     */
    public static boolean isIceCreamSandwichMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                : isIceCreamSandwichMR1();
    }

    /**
     * Detects if the current API version is 16 or above.
     *
     * @return {@code true} if the current API version is 16 or above.
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Detects if the current API version is 16 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 16 or above.
     */
    public static boolean isJellyBean(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN : isJellyBean();
    }

    /**
     * Detects if the current API version is 17 or above.
     *
     * @return {@code true} if the current API version is 17 or above.
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Detects if the current API version is 17 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 17 or above.
     */
    public static boolean isJellyBeanMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.JELLY_BEAN_MR1 : isJellyBeanMR1();
    }

    /**
     * Detects if the current API version is 18 or above.
     *
     * @return {@code true} if the current API version is 18 or above.
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Detects if the current API version is 18 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 18 or above.
     */
    public static boolean isJellyBeanMR2(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.JELLY_BEAN_MR2 : isJellyBeanMR2();
    }

    /**
     * Detects if the current API version is 19 or above.
     *
     * @return {@code true} if the current API version is 19 or above.
     */
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Detects if the current API version is 19 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 19 or above.
     */
    public static boolean isKitKat(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT : isKitKat();
    }

    /**
     * Detects if the current API version is 20 or above.
     *
     * @return {@code true} if the current API version is 20 or above.
     */
    public static boolean isKitKatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * Detects if the current API version is 20 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 20 or above.
     */
    public static boolean isKitKatWatch(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.KITKAT_WATCH : isKitKatWatch();
    }

    /**
     * Detects if the current API version is 21 or above.
     *
     * @return {@code true} if the current API version is 21 or above.
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Detects if the current API version is 21 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 21 or above.
     */
    public static boolean isLollipop(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP : isLollipop();
    }

    /**
     * Detects if the current API version is 22 or above.
     *
     * @return {@code true} if the current API version is 22 or above.
     */
    public static boolean isLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Detects if the current API version is 22 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 22 or above.
     */
    public static boolean isLollipopMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.LOLLIPOP_MR1 : isLollipopMR1();
    }

    /**
     * Detects if the current API version is 23 or above.
     *
     * @return {@code true} if the current API version is 23 or above.
     */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Detects if the current API version is 23 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 23 or above.
     */
    public static boolean isMarshmallow(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.M : isMarshmallow();
    }

    /**
     * Detects if the current API version is 24 or above.
     *
     * @return {@code true} if the current API version is 24 or above.
     */
    public static boolean isNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Detects if the current API version is 24 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 24 or above.
     */
    public static boolean isNougat(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N : isNougat();
    }

    /**
     * Detects if the current API version is 25 or above.
     *
     * @return {@code true} if the current API version is 25 or above.
     */
    public static boolean isNougatMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * Detects if the current API version is 25 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 25 or above.
     */
    public static boolean isNougatMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 : isNougatMR1();
    }

    /**
     * Detects if the current API version is 26 or above.
     *
     * @return {@code true} if the current API version is 26 or above.
     */
    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * Detects if the current API version is 26 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 26 or above.
     */
    public static boolean isOreo(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O : isOreo();
    }

    /**
     * Detects if the current API version is 27 or above.
     *
     * @return {@code true} if the current API version is 27 or above.
     */
    public static boolean isOreoMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    /**
     * Detects if the current API version is 27 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 27 or above.
     */
    public static boolean isOreoMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1 : isOreoMR1();
    }

    /**
     * Detects if the current API version is 28 or above.
     *
     * @return {@code true} if the current API version is 28 or above.
     */
    public static boolean isPie() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * Detects if the current API version is 28 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 28 or above.
     */
    public static boolean isPie(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.P : isPie();
    }

    /**
     * Detects if the current API version is 29 or above.
     *
     * @return {@code true} if the current API version is 29 or above.
     */
    public static boolean isQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                || isPie() && Build.VERSION.PREVIEW_SDK_INT > 0;
    }

    /**
     * Detects if the current API version is 29 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 29 or above.
     */
    public static boolean isQ(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.Q : isQ();
    }
}
