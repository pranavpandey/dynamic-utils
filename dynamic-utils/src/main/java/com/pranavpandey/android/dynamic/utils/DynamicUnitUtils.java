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

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Helper class to perform unit conversions.
 */
public class DynamicUnitUtils {

    /**
     * To convert DP into pixels.
     *
     * @param dp Value in DP to be converted into pixels.
     *
     * @return The converted value in pixels.
     */
    public static int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * To convert pixels into DP.
     *
     * @param pixels Value in pixels to be converted into DP.
     *
     * @return The converted value in DP.
     */
    public static int convertPixelsToDp(float pixels) {
        return (int) (pixels / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * To convert SP into pixels.
     *
     * @param sp Value in SP to be converted into pixels.
     *
     * @return The converted value in pixels.
     */
    public static int convertSpToPixels(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * To convert pixels into SP.
     *
     * @param pixels Value in pixels to be converted into SP.
     *
     * @return The converted value in SP.
     */
    public static int convertPixelsToSp(float pixels) {
        return (int) (pixels / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * To convert DP into SP.
     *
     * @param dp Value in DP to be converted into SP.
     *
     * @return The converted value in SP.
     */
    public static int convertDpToSp(float dp) {
        return (int) (convertDpToPixels(dp) / (float) convertSpToPixels(dp));
    }
}
