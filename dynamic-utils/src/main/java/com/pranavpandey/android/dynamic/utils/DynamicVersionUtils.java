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
 * Helper class to detect the Android SDK version at runtime so that
 * we can provide the user experience accordingly.
 *
 * @see Build.VERSION#SDK_INT
 */
public class DynamicVersionUtils {

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
     * To detect if the current Android version is Ice Cream Sandwich or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH}.
     */
    public static boolean isIceCreamSandwich(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH
                : isIceCreamSandwich();
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
     * To detect if the current Android version is Ice Cream Sandwich MR1 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH_MR1}.
     */
    public static boolean isIceCreamSandwichMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                : isIceCreamSandwichMR1();
    }
    
    /**
     * To detect if the current Android version is Jelly Bean or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN}.
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * To detect if the current Android version is Jelly Bean or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN}.
     */
    public static boolean isJellyBean(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN
                : isJellyBean();
    }

    /**
     * To detect if the current Android version is Jelly Bean MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN_MR1}.
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * To detect if the current Android version is Jelly Bean MR1 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN_MR1}.
     */
    public static boolean isJellyBeanMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                : isJellyBeanMR1();
    }

    /**
     * To detect if the current Android version is Jelly Bean MR2 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN_MR2}.
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * To detect if the current Android version is Jelly Bean MR2 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#JELLY_BEAN_MR2}.
     */
    public static boolean isJellyBeanMR2(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2
                : isJellyBeanMR2();
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
     * To detect if the current Android version is KitKat or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#KITKAT}.
     */
    public static boolean isKitKat(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                : isKitKat();
    }

    /**
     * To detect if the current Android version is KitKat Watch or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#KITKAT_WATCH}.
     */
    public static boolean isKitKatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * To detect if the current Android version is KitKat Watch or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#KITKAT_WATCH}.
     */
    public static boolean isKitKatWatch(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH
                : isKitKatWatch();
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
     * To detect if the current Android version is Lollipop or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#LOLLIPOP}.
     */
    public static boolean isLollipop(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP
                : isLollipop();
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
     * To detect if the current Android version is Lollipop MR1 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#LOLLIPOP_MR1}.
     */
    public static boolean isLollipopMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1
                : isLollipopMR1();
    }

    /**
     * To detect if the current Android version is Marshmallow or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#M}.
     */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * To detect if the current Android version is Marshmallow or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#M}.
     */
    public static boolean isMarshmallow(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.M
                : isMarshmallow();
    }

    /**
     * To detect if the current Android version is Nougat or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N}.
     */
    public static boolean isNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * To detect if the current Android version is Nougat or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N}.
     */
    public static boolean isNougat(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                : isNougat();
    }

    /**
     * To detect if the current Android version is Nougat MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N_MR1}.
     */
    public static boolean isNougatMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * To detect if the current Android version is Nougat MR1 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#N_MR1}.
     */
    public static boolean isNougatMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1
                : isNougatMR1();
    }

    /**
     * To detect if the current Android version is Oreo or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O}.
     */
    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * To detect if the current Android version is Oreo or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O}.
     */
    public static boolean isOreo(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O
                : isOreo();
    }

    /**
     * To detect if the current Android version is Oreo MR1 or above.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O_MR1}.
     */
    public static boolean isOreoMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    /**
     * To detect if the current Android version is Oreo MR1 or above.
     *
     * @param equals {@code true} to check for equality.
     *               {@code false} to match greater than or equal.
     *
     * @return {@code true} if current version is greater than or equal to
     *         {@link Build.VERSION_CODES#O_MR1}.
     */
    public static boolean isOreoMR1(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1
                : isOreoMR1();
    }
}
