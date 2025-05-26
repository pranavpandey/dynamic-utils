/*
 * Copyright 2017-2025 Pranav Pandey
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

import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class to perform input operations.
 */
public class DynamicInputUtils {
    
    /**
     * Set the input filter for the supplied view.
     *
     * @param view The view to be used.
     * @param filter The input filter to be set.
     */
    public static void setFilter(@Nullable View view, @Nullable InputFilter filter) {
        if (filter == null) {
            return;
        }

        if (view instanceof TextView) {
            InputFilter[] filters = ((TextView) view).getFilters();
            if (filters == null) {
                filters = new InputFilter[1];
            }

            List<InputFilter> filtersList = new ArrayList<>(Arrays.asList(filters));
            filtersList.add(filter);

            ((TextView) view).setFilters(filtersList.toArray(new InputFilter[0]));
        }
    }

    /**
     * Remove the input filter for the supplied view.
     *
     * @param view The view to be used.
     * @param filter The input filter to be removed.
     */
    public static void removeFilter(@Nullable View view, @Nullable InputFilter filter) {
        if (filter == null) {
            return;
        }

        if (view instanceof TextView) {
            InputFilter[] filters = ((TextView) view).getFilters();
            if (filters == null) {
                filters = new InputFilter[1];
            }

            List<InputFilter> filtersList = new ArrayList<>(Arrays.asList(filters));
            for (InputFilter inputFilter : filtersList) {
                if (inputFilter.getClass().isInstance(filter)) {
                    filtersList.remove(inputFilter);
                }
            }

            ((TextView) view).setFilters(filtersList.toArray(new InputFilter[0]));
        }
    }

    /**
     * Set the maximum length input filter for the supplied view.
     *
     * @param view The view to be used.
     * @param length The maximum length to be set.
     * 
     * @see #setFilter(View, InputFilter)
     */
    public static void setMaxLength(@Nullable View view, int length) {
        setFilter(view, new InputFilter.LengthFilter(length));
    }

    /**
     * Set the all caps input filter for the supplied view.
     *
     * @param view The view to be used.
     * @param allCaps {@code true} to set all caps.
     *
     * @see #setFilter(View, InputFilter)
     * @see #removeFilter(View, InputFilter)
     */
    public static void setAllCaps(@Nullable View view, boolean allCaps) {
        if (allCaps) {
            setFilter(view, new InputFilter.AllCaps());
        } else {
            removeFilter(view, new InputFilter.AllCaps());
        }
    }

    /**
     * Apply window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     * @param left {@code true} to apply the left window inset padding.
     * @param top {@code true} to apply the top window inset padding.
     * @param right {@code true} to apply the right window inset padding.
     * @param bottom {@code true} to apply the bottom window inset padding.
     * @param consume {@code true} to consume the applied window insets.
     */
    public static void applyWindowInsets(@Nullable View view, final boolean left,
            final boolean top, final boolean right, final boolean bottom, final boolean consume) {
        if (view == null) {
            return;
        }

        final int paddingLeft = view.getPaddingLeft();
        final int paddingTop = view.getPaddingTop();
        final int paddingRight = view.getPaddingRight();
        final int paddingBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
            @Override
            public @NonNull WindowInsetsCompat onApplyWindowInsets(
                    @NonNull View v, @NonNull WindowInsetsCompat insets) {
                int insetsLeft = insets.getInsets(WindowInsetsCompat.Type.ime()).left > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).left
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).left;
                int insetsTop = insets.getInsets(WindowInsetsCompat.Type.ime()).top > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).top
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                int insetsRight = insets.getInsets(WindowInsetsCompat.Type.ime()).right > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).right
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).right;
                int insetsBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;

                v.setPadding(left ? paddingLeft + insetsLeft : paddingLeft,
                        top ? paddingTop + insetsTop : paddingTop,
                        right ? paddingRight + insetsRight : paddingRight,
                        bottom ? paddingBottom + insetsBottom : paddingBottom);

                return !consume ? insets :
                        new WindowInsetsCompat.Builder(insets).setInsets(
                                WindowInsetsCompat.Type.ime(), Insets.of(
                                        left ? 0 : insetsLeft,
                                        top ? 0 : insetsTop,
                                        right ? 0 : insetsRight,
                                        bottom ? 0 : insetsBottom))
                                .build();
            }
        });

        DynamicViewUtils.requestApplyWindowInsets(view);
    }

    /**
     * Apply horizontal window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsHorizontal(@Nullable View view, boolean consume) {
        applyWindowInsets(view, true, false, true, false, consume);
    }

    /**
     * Apply horizontal window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     *
     * @see #applyWindowInsetsHorizontal(View, boolean)
     */
    public static void applyWindowInsetsHorizontal(@Nullable View view) {
        applyWindowInsetsHorizontal(view, false);
    }

    /**
     * Apply vertical window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsVertical(@Nullable View view, boolean consume) {
        applyWindowInsets(view, false, true, false, true, consume);
    }

    /**
     * Apply vertical window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     *
     * @see #applyWindowInsetsVertical(View, boolean)
     */
    public static void applyWindowInsetsVertical(@Nullable View view) {
        applyWindowInsetsVertical(view, false);
    }

    /**
     * Apply bottom window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsBottom(@Nullable View view, boolean consume) {
        applyWindowInsets(view, false, false, false, true, consume);
    }

    /**
     * Apply bottom window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsBottom(@Nullable View view) {
        applyWindowInsetsBottom(view, false);
    }

    /**
     * Apply horizontal and bottom window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsHorizontalBottom(@Nullable View view, boolean consume) {
        applyWindowInsets(view, true, false, true, true, consume);
    }

    /**
     * Apply horizontal and bottom window insets padding for the supplied view.
     *
     * @param view The view to set the insets padding.
     *
     * @see #applyWindowInsets(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsHorizontalBottom(@Nullable View view) {
        applyWindowInsetsHorizontalBottom(view, false);
    }

    /**
     * Apply window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     * @param left {@code true} to apply the left window inset margin.
     * @param top {@code true} to apply the top window inset margin.
     * @param right {@code true} to apply the right window inset margin.
     * @param bottom {@code true} to apply the bottom window inset margin.
     * @param consume {@code true} to consume the applied window margin.
     */
    public static void applyWindowInsetsMargin(final @Nullable View view, final boolean left,
            final boolean top, final boolean right, final boolean bottom, final boolean consume) {
        if (view == null || !(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }

        final ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        final int leftMargin = layoutParams.leftMargin;
        final int topMargin = layoutParams.topMargin;
        final int rightMargin = layoutParams.rightMargin;
        final int bottomMargin = layoutParams.bottomMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
            @Override
            public @NonNull WindowInsetsCompat onApplyWindowInsets(
                    @NonNull View v, @NonNull WindowInsetsCompat insets) {
                int insetsLeft = insets.getInsets(WindowInsetsCompat.Type.ime()).left > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).left
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).left;
                int insetsTop = insets.getInsets(WindowInsetsCompat.Type.ime()).top > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).top
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                int insetsRight = insets.getInsets(WindowInsetsCompat.Type.ime()).right > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).right
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).right;
                int insetsBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom > 0
                        ? insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                        : insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;

                if (left) {
                    layoutParams.leftMargin = leftMargin + insetsLeft;
                }
                if (top) {
                    layoutParams.topMargin = topMargin + insetsTop;
                }
                if (right) {
                    layoutParams.rightMargin = rightMargin + insetsRight;
                }
                if (bottom) {
                    layoutParams.bottomMargin = bottomMargin + insetsBottom;
                }

                view.setLayoutParams(layoutParams);

                return !consume ? insets :
                        new WindowInsetsCompat.Builder(insets).setInsets(
                                WindowInsetsCompat.Type.systemBars(), Insets.of(
                                        left ? 0 : insetsLeft,
                                        top ? 0 : insetsTop,
                                        right ? 0 : insetsRight,
                                        bottom ? 0 : insetsBottom))
                                .build();
            }
        });

        DynamicViewUtils.requestApplyWindowInsets(view);
    }

    /**
     * Apply horizontal window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginHorizontal(@Nullable View view, boolean consume) {
        applyWindowInsetsMargin(view, true, false, true, false, consume);
    }

    /**
     * Apply horizontal window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMarginHorizontal(View, boolean)
     */
    public static void applyWindowInsetsMarginHorizontal(@Nullable View view) {
        applyWindowInsetsMarginHorizontal(view, false);
    }

    /**
     * Apply vertical window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginVertical(@Nullable View view, boolean consume) {
        applyWindowInsetsMargin(view, false, true, false, true, consume);
    }

    /**
     * Apply vertical window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMarginVertical(View, boolean)
     */
    public static void applyWindowInsetsMarginVertical(@Nullable View view) {
        applyWindowInsetsMarginVertical(view, false);
    }

    /**
     * Apply bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginBottom(@Nullable View view, boolean consume) {
        applyWindowInsetsMargin(view, false, false, false, true, consume);
    }

    /**
     * Apply bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMarginBottom(View, boolean)
     */
    public static void applyWindowInsetsMarginBottom(@Nullable View view) {
        applyWindowInsetsMarginBottom(view, false);
    }

    /**
     * Apply horizontal and bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     * @param consume {@code true} to consume the applied window insets.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginHorizontalBottom(
            @Nullable View view, boolean consume) {
        applyWindowInsetsMargin(view, true, false, true, true, consume);
    }

    /**
     * Apply horizontal and bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMarginHorizontalBottom(View, boolean)
     */
    public static void applyWindowInsetsMarginHorizontalBottom(@Nullable View view) {
        applyWindowInsetsMarginHorizontalBottom(view, false);
    }
}
