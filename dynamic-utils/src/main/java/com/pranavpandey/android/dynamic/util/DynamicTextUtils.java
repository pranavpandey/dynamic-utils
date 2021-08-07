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

package com.pranavpandey.android.dynamic.util;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * Helper class to perform text operations.
 *
 * @see String
 * @see CharSequence
 */
public class DynamicTextUtils {

    /**
     * Capitalize the first character of the supplied string.
     *
     * @param text The text to be capitalize.
     * @param locale The optional locale for better results.
     *
     * @return The string after capitalizing the first character.
     */
    public static @Nullable String capitalize(@Nullable String text, @Nullable Locale locale) {
        if (text == null || TextUtils.isEmpty(text)) {
            return text;
        }

        if (locale != null) {
            return text.substring(0, 1).toUpperCase(locale) + text.substring(1);
        }

        return text.substring(0, 1).toUpperCase(Locale.getDefault()) + text.substring(1);
    }

    /**
     * Capitalize the first character of the supplied char sequence.
     *
     * @param text The text to be capitalize.
     * @param locale The optional locale for better results.
     *
     * @return The string after capitalizing the first character.
     */
    public static @Nullable String capitalize(
            @Nullable CharSequence text, @Nullable Locale locale) {
        if (text == null || TextUtils.isEmpty(text)) {
            return String.valueOf(text);
        }

        return capitalize(text.toString(), locale);
    }
}
