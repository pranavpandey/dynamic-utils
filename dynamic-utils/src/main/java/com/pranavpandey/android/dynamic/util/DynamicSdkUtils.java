/*
 * Copyright 2017-2024 Pranav Pandey
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

package com.pranavpandey.android.dynamic.util;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Helper class to detect the Android SDK version at runtime so that we can provide the 
 * user experience accordingly.
 *
 * @see Build.VERSION#SDK_INT
 */
@TargetApi(Build.VERSION_CODES.M)
public class DynamicSdkUtils {

    /**
     * Product flavors to support external app stores.
     */
    public @interface DynamicFlavor {

        /**
         * Constant for the Google Play Store.
         */
        String GOOGLE = "google";

        /**
         * Constant for the external app store.
         */
        String EXTERNAL = "external";
    }

    /**
     * Detects if the current API version is a preview.
     *
     * @return {@code true} if the current API version is a preview.
     */
    public static boolean isPreview() {
        return Build.VERSION.PREVIEW_SDK_INT != 0;
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Detects if the current API version is 14 or above.
     *
     * @return {@code true} if the current API version is 14 or above.
     */
    public static boolean is14() {
        return is14(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Detects if the current API version is 15 or above.
     *
     * @return {@code true} if the current API version is 15 or above.
     */
    public static boolean is15() {
        return is15(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Detects if the current API version is 16 or above.
     *
     * @return {@code true} if the current API version is 16 or above.
     */
    public static boolean is16() {
        return is16(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Detects if the current API version is 17 or above.
     *
     * @return {@code true} if the current API version is 17 or above.
     */
    public static boolean is17() {
        return is17(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Detects if the current API version is 18 or above.
     *
     * @return {@code true} if the current API version is 18 or above.
     */
    public static boolean is18() {
        return is18(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Detects if the current API version is 19 or above.
     *
     * @return {@code true} if the current API version is 19 or above.
     */
    public static boolean is19() {
        return is19(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * Detects if the current API version is 20 or above.
     *
     * @return {@code true} if the current API version is 20 or above.
     */
    public static boolean is20() {
        return is20(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Detects if the current API version is 21 or above.
     *
     * @return {@code true} if the current API version is 21 or above.
     */
    public static boolean is21() {
        return is21(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Detects if the current API version is 22 or above.
     *
     * @return {@code true} if the current API version is 22 or above.
     */
    public static boolean is22() {
        return is22(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.M
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Detects if the current API version is 23 or above.
     *
     * @return {@code true} if the current API version is 23 or above.
     */
    public static boolean is23() {
        return is23(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Detects if the current API version is 24 or above.
     *
     * @return {@code true} if the current API version is 24 or above.
     */
    public static boolean is24() {
        return is24(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * Detects if the current API version is 25 or above.
     *
     * @return {@code true} if the current API version is 25 or above.
     */
    public static boolean is25() {
        return is25(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * Detects if the current API version is 26 or above.
     *
     * @return {@code true} if the current API version is 26 or above.
     */
    public static boolean is26() {
        return is26(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    /**
     * Detects if the current API version is 27 or above.
     *
     * @return {@code true} if the current API version is 27 or above.
     */
    public static boolean is27() {
        return is27(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.P
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * Detects if the current API version is 28 or above.
     *
     * @return {@code true} if the current API version is 28 or above.
     */
    public static boolean is28() {
        return is28(false);
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
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.Q
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * Detects if the current API version is 29 or above.
     *
     * @return {@code true} if the current API version is 29 or above.
     */
    public static boolean is29() {
        return is29(false);
    }

    /**
     * Detects if the current API version is 30 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is R or above.
     */
    public static boolean is30(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.R
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * Detects if the current API version is 30 or above.
     *
     * @return {@code true} if the current API version is R or above.
     */
    public static boolean is30() {
        return is30(false);
    }

    /**
     * Detects if the current API version is 31 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 31 or above.
     */
    public static boolean is31(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.S
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    /**
     * Detects if the current API version is 31 or above.
     *
     * @return {@code true} if the current API version is 31 or above.
     */
    public static boolean is31() {
        return is31(false);
    }

    /**
     * Detects if the current API version is 32 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 32 or above.
     */
    public static boolean is32(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.S_V2
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2;
    }

    /**
     * Detects if the current API version is 32 or above.
     *
     * @return {@code true} if the current API version is 32 or above.
     */
    public static boolean is32() {
        return is32(false);
    }

    /**
     * Detects if the current API version is 33 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 33 or above.
     */
    public static boolean is33(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    /**
     * Detects if the current API version is 33 or above.
     *
     * @return {@code true} if the current API version is 33 or above.
     */
    public static boolean is33() {
        return is33(false);
    }

    /**
     * Detects if the current API version is 34 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 34 or above.
     */
    public static boolean is34(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
    }

    /**
     * Detects if the current API version is 34 or above.
     *
     * @return {@code true} if the current API version is 34 or above.
     */
    public static boolean is34() {
        return is34(false);
    }

    /**
     * Detects if the current API version is 35 or above.
     *
     * @param equals {@code true} to check for equality.
     *               <p>{@code false} to match greater than or equal.
     *
     * @return {@code true} if the current API version is 35 or above.
     */
    public static boolean is35(boolean equals) {
        return equals ? Build.VERSION.SDK_INT == Build.VERSION_CODES.VANILLA_ICE_CREAM
                : Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM;
    }

    /**
     * Detects if the current API version is 35 or above.
     *
     * @return {@code true} if the current API version is 35 or above.
     */
    public static boolean is35() {
        return is35(false);
    }
}
