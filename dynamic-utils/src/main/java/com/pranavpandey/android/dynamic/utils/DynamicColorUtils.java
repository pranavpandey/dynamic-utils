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

import android.graphics.Color;

import java.util.Random;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.core.graphics.ColorUtils;

/**
 * Helper class to change colors dynamically.
 */
public class DynamicColorUtils {

    /**
     * Visible contrast between the two colors.
     */
    private static final float VISIBLE_CONTRAST = 0.33f;

    /**
     * Amount to calculate the contrast color.
     */
    private static final float CONTRAST_FACTOR = 0.67f;

    /**
     * Generate a random rgb color.
     *
     * @return The randomly generated color.
     *
     * @see Random
     * @see Color#HSVToColor(float[])
     */
    public static @ColorInt int getRandomColor() {
        Random random = new Random();
        float hue = (float) random.nextInt(360);
        float saturation = random.nextFloat();
        float lightness = random.nextFloat();

        return ColorUtils.HSLToColor(new float[]{hue, saturation, lightness});
    }

    /**
     * Generate a random rgb color by comparing a given color.
     *
     * @param color The color to compare.
     *
     * @return The randomly generated color.
     */
    public static @ColorInt int getRandomColor(@ColorInt int color) {
        @ColorInt int newColor = getRandomColor();

        if (newColor != color) {
            return newColor;
        } else {
            return getRandomColor();
        }
    }

    /**
     * Adjust alpha of a color according to the given parameter.
     *
     * @param color The color whose alpha to be adjusted.
     * @param factor Factor in float by which adjust the alpha.
     *
     * @return The color with adjusted alpha.
     */
    public static @ColorInt int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.min(255, (int) (Color.alpha(color) * factor));
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Checks whether the color has alpha component.
     *
     * @param color The color to check the alpha component.
     *
     * @return {@code true} if the color has alpha component.
     */
    public static boolean isAlpha(@ColorInt int color) {
        return Color.alpha(color) != 255;
    }

    /**
     * Remove alpha from a color.
     *
     * @param color The color whose alpha to be removed.
     *
     * @return The color without alpha.
     */
    public static @ColorInt int removeAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Calculate darkness of a color.
     *
     * @param color The color whose darkness to be calculated.
     *
     * @return The darkness of color (less than or equal to 1).
     *         <p>{@code 0} for white and {@code 1} for black.
     */
    public static double getColorDarkness(@ColorInt int color) {
        return 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color)
                + 0.114 * Color.blue(color)) / 255;
    }

    /**
     * Detect light or dark color.
     *
     * @param color The color whose darkness to be calculated.
     *
     * @return {@code true} if color is dark.
     */
    public static boolean isColorDark(@ColorInt int color) {
        return getColorDarkness(color) >= 0.5;
    }

    /**
     * Calculate luma value according to XYZ color space of a color.
     *
     * @param color The color whose XyzLuma to be calculated.
     *
     * @return The luma value according to XYZ color space in the range {@code 0.0 - 1.0}.
     */
    private static float calculateXyzLuma(@ColorInt int color) {
        return (0.2126f * Color.red(color) +
                0.7152f * Color.green(color) +
                0.0722f * Color.blue(color)) / 255f;
    }

    /**
     * Lightens a color by a given amount.
     *
     * @param color The color to lighten.
     * @param amount The amount to lighten the color.
     *               <p>{@code 0} will leave the color unchanged.
     *               <p>{@code 1} will make the color completely white.
     *
     * @return The lighter color.
     */
    public static @ColorInt int getLighterColor(@ColorInt int color, float amount) {
        int red = (int) ((Color.red(color) * (1 - amount) / 255 + amount) * 255);
        int green = (int) ((Color.green(color) * (1 - amount) / 255 + amount) * 255);
        int blue = (int) ((Color.blue(color) * (1 - amount) / 255 + amount) * 255);

        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Darkens a color by a given amount.
     *
     * @param color The color to darken.
     * @param amount The amount to darken the color.
     *               <p>{@code 0} will leave the color unchanged.
     *               <p>{@code 1} will make the color completely black.
     *
     * @return The darker color.
     */
    public static @ColorInt int getDarkerColor(@ColorInt int color, float amount) {
        int red = (int) ((Color.red(color) * (1 - amount) / 255) * 255);
        int green = (int) ((Color.green(color) * (1 - amount) / 255) * 255);
        int blue = (int) ((Color.blue(color) * (1 - amount) / 255) * 255);

        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Shift a color according to the given parameter.
     * <p>Useful to create different color states.
     *
     * @param color The color to be shifted.
     * @param by The factor in float by which shift the color.
     *
     * @return The shifted color.
     */
    public static @ColorInt int shiftColor(@ColorInt int color,
            @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) {
            return color;
        }

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by;
        return Color.HSVToColor(hsv);
    }

    /**
     * Shift a color according to the supplied parameters.
     * <p>The shifted color will be lighter for a dark color and vice versa.
     *
     * @param color The color to be shifted.
     * @param shiftLightBy The factor in float by which shift the light color.
     * @param shiftDarkBy The factor in float by which shift the dark color.
     *
     * @return The shifted color.
     */
    public static @ColorInt int shiftColor(@ColorInt int color,
            @FloatRange(from = 0.0f, to = 2.0f) float shiftLightBy,
            @FloatRange(from = 0.0f, to = 2.0f) float shiftDarkBy) {
        return shiftColor(color, isColorDark(color) ? shiftDarkBy : shiftLightBy);
    }

    /**
     * Calculate accent color based on the given color for android theme generation.
     * <p>Still in beta so, sometimes may be inaccurate colors.
     *
     * @param color The color whose accent color to be calculated.
     *
     * @return The accent color based on the given color.
     */
    public static @ColorInt int getAccentColor(@ColorInt int color) {
        int finalColor;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        double Y = ((r * 299) + (g * 587) + (b * 114)) / 1000d;

        int rc = b ^ 0x55;
        int gc = g & 0xFA;
        int bc = r ^ 0x55;

        finalColor = Color.argb(a, rc, gc, bc);
        int r1 = Color.red(finalColor);
        int g1 = Color.green(finalColor);
        int b1 = Color.blue(finalColor);
        double YC = ((r1 * 299) + (g1 * 587) + (b1 * 114)) / 1000d;

        int CD = (Math.max(r, r1) - Math.min(r, r1)) + (Math.max(g, g1) - Math.min(g, g1))
                + (Math.max(b, b1) - Math.min(b, b1));
        if ((Y - YC <= 50) && CD <= 200) {
            rc = b ^ 0xFA;
            gc = g & 0x55;
            bc = r ^ 0x55;
        }

        finalColor = Color.argb(a, rc, gc, bc);
        return finalColor;
    }

    /**
     * Calculate color contrast difference between two colors based
     * on luma value according to XYZ color space.
     *
     * @param color1 The first color to calculate the contrast difference.
     * @param color2 The second color to calculate the contrast difference.
     *
     * @return The color contrast between the two colors.
     *
     * @see #calculateXyzLuma(int)
     */
    public static float calculateContrast(@ColorInt int color1, @ColorInt int color2) {
        return Math.abs(calculateXyzLuma(color1) - calculateXyzLuma(color2));
    }

    /**
     * Calculate tint based on a given color for better readability.
     *
     * @param color The color whose tint to be calculated.
     *
     * @return The tint of the given color.
     */
    public static @ColorInt int getTintColor(@ColorInt int color) {
        return getContrastColor(color, color);
    }

    /**
     * Lightens or darkens a color by a given amount.
     *
     * @param color The color to be lighten or darken.
     * @param lightenBy The amount to lighten the color.
     *                  <p>{@code 0} will leave the color unchanged.
     *                  <p>{@code 1} will make the color completely white.
     * @param darkenBy The amount to darken the color.
     *                 <p>{@code 0} will leave the color unchanged.
     *                 <p>{@code 1} will make the color completely black.
     *
     * @return The state color.
     */
    public static @ColorInt int getStateColor(@ColorInt int color,
            @FloatRange(from = 0.0f, to = 1.0f) float lightenBy,
            @FloatRange(from = 0.0f, to = 1.0f) float darkenBy) {
        return isColorDark(color) ? getLighterColor(color, lightenBy)
                : getDarkerColor(color, darkenBy);
    }

    /**
     * Calculate contrast of a color based on the given base color so that it will always
     * be visible on top of the base color.
     *
     * @param color The color whose contrast to be calculated.
     * @param contrastWith The background color to calculate the contrast.
     *
     * @return The contrast of the given color according to the base color.
     */
    public static @ColorInt int getContrastColor(@ColorInt int color, @ColorInt int contrastWith) {
        float contrast = calculateContrast(color, contrastWith);
        if (contrast < VISIBLE_CONTRAST) {
            if (isColorDark(contrastWith)) {
                return getLighterColor(color,
                        Math.max(VISIBLE_CONTRAST + contrast, CONTRAST_FACTOR));
            } else {
                return getDarkerColor(color,
                        Math.max(VISIBLE_CONTRAST + contrast, CONTRAST_FACTOR));
            }
        }

        return color;
    }

    /**
     * Calculate less visible color of a given color.
     * <p>Useful to create unselected or disabled color states.
     *
     * @param color The color whose less visible color to be calculated.
     *
     * @return The less visible color by shifting the color.
     */
    public static @ColorInt int getLessVisibleColor(@ColorInt int color) {
        return shiftColor(color, isColorDark(color) ? 0.6f : 1.6f);
    }

    /**
     * Get hexadecimal string from the color integer.
     *
     * @param color The color to get the hex code.
     * @param includeAlpha {@code true} to include alpha in the string.
     * @param includeHash {@code true} to include {@code #} in the string.
     *
     * @return The hexadecimal string equivalent of the supplied color integer.
     */
    public static String getColorString(@ColorInt int color, boolean includeAlpha,
            boolean includeHash) {
        String colorString;
        if (includeAlpha) {
            colorString = String.format("%08X", color);
        } else {
            colorString = String.format("%06X", 0xFFFFFF & color);
        }

        if (includeHash) {
            colorString = "#" + colorString;
        }

        return colorString;
    }
}
