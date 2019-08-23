/*
 * Copyright 2019 Pranav Pandey
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
public class DynamicSdkUtils {

    /**
     * Detects if the current API version is 14 or above.
     *
     * @return {@code true} if the current API version is 14 or above.
     */
    public static boolean is14() {
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
    public static boolean is14(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.ICE_CREAM_SANDWICH : is14();
    }

    /**
     * Detects if the current API version is 15 or above.
     *
     * @return {@code true} if the current API version is 15 or above.
     */
    public static boolean is15() {
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
    public static boolean is15(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                : is15();
    }

    /**
     * Detects if the current API version is 16 or above.
     *
     * @return {@code true} if the current API version is 16 or above.
     */
    public static boolean is16() {
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
    public static boolean is16(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN : is16();
    }

    /**
     * Detects if the current API version is 17 or above.
     *
     * @return {@code true} if the current API version is 17 or above.
     */
    public static boolean is17() {
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
    public static boolean is17(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.JELLY_BEAN_MR1 : is17();
    }

    /**
     * Detects if the current API version is 18 or above.
     *
     * @return {@code true} if the current API version is 18 or above.
     */
    public static boolean is18() {
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
    public static boolean is18(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.JELLY_BEAN_MR2 : is18();
    }

    /**
     * Detects if the current API version is 19 or above.
     *
     * @return {@code true} if the current API version is 19 or above.
     */
    public static boolean is19() {
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
    public static boolean is19(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT : is19();
    }

    /**
     * Detects if the current API version is 20 or above.
     *
     * @return {@code true} if the current API version is 20 or above.
     */
    public static boolean is20() {
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
    public static boolean is20(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.KITKAT_WATCH : is20();
    }

    /**
     * Detects if the current API version is 21 or above.
     *
     * @return {@code true} if the current API version is 21 or above.
     */
    public static boolean is21() {
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
    public static boolean is21(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP : is21();
    }

    /**
     * Detects if the current API version is 22 or above.
     *
     * @return {@code true} if the current API version is 22 or above.
     */
    public static boolean is22() {
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
    public static boolean is22(boolean equals) {
        return equals ? Build.VERSION.SDK_INT
                == Build.VERSION_CODES.LOLLIPOP_MR1 : is22();
    }

    /**
     * Detects if the current API version is 23 or above.
     *
     * @return {@code true} if the current API version is 23 or above.
     */
    public static boolean is23() {
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
    public static boolean is23(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.M : is23();
    }

    /**
     * Detects if the current API version is 24 or above.
     *
     * @return {@code true} if the current API version is 24 or above.
     */
    public static boolean is24() {
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
    public static boolean is24(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N : is24();
    }

    /**
     * Detects if the current API version is 25 or above.
     *
     * @return {@code true} if the current API version is 25 or above.
     */
    public static boolean is25() {
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
    public static boolean is25(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 : is25();
    }

    /**
     * Detects if the current API version is 26 or above.
     *
     * @return {@code true} if the current API version is 26 or above.
     */
    public static boolean is26() {
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
    public static boolean is26(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O : is26();
    }

    /**
     * Detects if the current API version is 27 or above.
     *
     * @return {@code true} if the current API version is 27 or above.
     */
    public static boolean is27() {
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
    public static boolean is27(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1 : is27();
    }

    /**
     * Detects if the current API version is 28 or above.
     *
     * @return {@code true} if the current API version is 28 or above.
     */
    public static boolean is28() {
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
    public static boolean is28(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.P : is28();
    }

    /**
     * Detects if the current API version is 29 or above.
     *
     * @return {@code true} if the current API version is 29 or above.
     */
    public static boolean is29() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                || is28() && Build.VERSION.PREVIEW_SDK_INT > 0;
    }

    /**
     * Detects if the current API version is 29 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 29 or above.
     */
    public static boolean is29(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.Q : is29();
    }
}
