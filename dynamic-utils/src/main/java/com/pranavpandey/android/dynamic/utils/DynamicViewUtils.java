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

package com.pranavpandey.android.dynamic.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Helper class to perform {@link View} operations.
 */
public class DynamicViewUtils {

    /**
     * Set hide navigation flag for edge-to-edge content on API 23 and above devices.
     *
     * @param view The view to get the system ui flags.
     * @param edgeToEdge {@code true} to hide the layout navigation.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setEdgeToEdge(@NonNull View view, boolean edgeToEdge) {
        if (DynamicSdkUtils.is23()) {
            int flags = view.getSystemUiVisibility();
            if (edgeToEdge) {
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                flags &= ~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }

            view.setSystemUiVisibility(flags);
        }
    }

    /**
     * Checks whether the hide navigation flag is enabled for edge-to-edge content on API 23
     * and above devices.
     *
     * @param view The view to get the system ui flags.
     *
     * @return {@code true} if hide navigation flag is enabled for edge-to-edge content on
     *         API 23 and above devices.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isEdgeToEdge(@NonNull View view) {
        if (DynamicSdkUtils.is23()) {
            int flags = view.getSystemUiVisibility();

            return (flags & View.SYSTEM_UI_FLAG_LAYOUT_STABLE) == 0
                    && (flags & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) == 0;
        }

        return false;
    }

    /**
     * Set light status bar if we are using light primary color on API 23 and above devices.
     *
     * @param view The view to get the system ui flags.
     * @param light {@code true} to set the light status bar.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setLightStatusBar(@NonNull View view, boolean light) {
        if (DynamicSdkUtils.is23()) {
            int flags = view.getSystemUiVisibility();
            if (light) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

            view.setSystemUiVisibility(flags);
        }
    }

    /**
     * Set light navigation bar if we are using light primary color on API 26 and above devices.
     *
     * @param view The view to get the system ui flags.
     * @param light {@code true} to set the light navigation bar.
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void setLightNavigationBar(@NonNull View view, boolean light) {
        if (DynamicSdkUtils.is26()) {
            int flags = view.getSystemUiVisibility();
            if (light) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }

            view.setSystemUiVisibility(flags);
        }
    }

    /**
     * Add a view to the view group.
     *
     * @param viewGroup The view group to add the view.
     * @param view The view to be added.
     * @param removePrevious {@code true} to remove all the previous views of the view group.
     */
    public static void addView(@Nullable ViewGroup viewGroup,
            @Nullable View view, boolean removePrevious) {
        if (viewGroup == null) {
            return;
        }

        if (removePrevious && viewGroup.getChildCount() > 0) {
            viewGroup.removeAllViews();
        }

        if (view != null) {
            viewGroup.addView(view);
        }
    }

    /**
     * Add a view to the view group.
     *
     * @param viewGroup The view group to add the view.
     * @param layoutRes The layout resource to be added.
     * @param removePrevious {@code true} to remove all the previous views of the view group.
     */
    public static void addView(@Nullable ViewGroup viewGroup,
            @LayoutRes int layoutRes, boolean removePrevious) {
        if (viewGroup != null) {
            addView(viewGroup, LayoutInflater.from(viewGroup.getContext()).inflate(
                    layoutRes, viewGroup, false), removePrevious);
        }
    }

    /**
     * Manage scroll indicators for a view according to its current state.
     *
     * @param view The view to show or hide the scroll indicators accordingly.
     * @param upIndicator The indicator to show if the view can be scrolled upwards.
     * @param downIndicator The indicator to show if the view can be scrolled downwards.
     */
    public static void manageScrollIndicators(final @Nullable View view,
            final @Nullable View upIndicator, final @Nullable View downIndicator) {
        if (view == null || (upIndicator == null && downIndicator == null)) {
            return;
        }

        if (upIndicator != null) {
            upIndicator.setVisibility(view.canScrollVertically(-1)
                    ? View.VISIBLE : View.INVISIBLE);
        }

        if (downIndicator != null) {
            downIndicator.setVisibility(view.canScrollVertically(1)
                    ? View.VISIBLE : View.INVISIBLE);
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
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                v.setPadding(left ? paddingLeft + insets.getInsets(
                        WindowInsetsCompat.Type.systemBars()).left : paddingLeft,
                        top ? paddingTop + insets.getInsets(
                                WindowInsetsCompat.Type.systemBars()).top : paddingTop,
                        right ? paddingRight + insets.getInsets(
                                WindowInsetsCompat.Type.systemBars()).right : paddingRight,
                        bottom ? paddingBottom + insets.getInsets(
                                WindowInsetsCompat.Type.systemBars()).bottom : paddingBottom);

                return !consume ? insets :
                        new WindowInsetsCompat.Builder(insets).setInsets(
                                WindowInsetsCompat.Type.systemBars(),
                                Insets.of(left ? 0 : insets.getInsets(
                                        WindowInsetsCompat.Type.systemBars()).left,
                                        top ? 0 : insets.getInsets(
                                                WindowInsetsCompat.Type.systemBars()).top,
                                        right ? 0 : insets.getInsets(
                                                WindowInsetsCompat.Type.systemBars()).right,
                                        bottom ? 0 : insets.getInsets(
                                                WindowInsetsCompat.Type.systemBars()).bottom))
                                .build();
            }
        });

        requestApplyWindowInsets(view);
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
    public static void applyWindowInsetsMargin(@Nullable View view, final boolean left,
            final boolean top, final boolean right, final boolean bottom, final boolean consume) {
        if (view == null) {
            return;
        }

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            final int leftMargin = layoutParams.leftMargin;
            final int topMargin = layoutParams.topMargin;
            final int rightMargin = layoutParams.rightMargin;
            final int bottomMargin = layoutParams.bottomMargin;

            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    if (left) {
                        layoutParams.leftMargin = leftMargin
                                + insets.getInsets(WindowInsetsCompat.Type.systemBars()).left;
                    }
                    if (top) {
                        layoutParams.topMargin = topMargin
                                + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                    }
                    if (right) {
                        layoutParams.rightMargin = rightMargin
                                + insets.getInsets(WindowInsetsCompat.Type.systemBars()).right;
                    }
                    if (bottom) {
                        layoutParams.bottomMargin = bottomMargin
                                + insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
                    }

                    return !consume ? insets :
                            new WindowInsetsCompat.Builder(insets).setInsets(
                                    WindowInsetsCompat.Type.systemBars(),
                                    Insets.of(left ? 0 : insets.getInsets(
                                            WindowInsetsCompat.Type.systemBars()).left,
                                            top ? 0 : insets.getInsets(
                                                    WindowInsetsCompat.Type.systemBars()).top,
                                            right ? 0 : insets.getInsets(
                                                    WindowInsetsCompat.Type.systemBars()).right,
                                            bottom ? 0 : insets.getInsets(
                                                    WindowInsetsCompat.Type.systemBars()).bottom))
                                    .build();
                }
            });
        }
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
     * Apply bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginBottom(@Nullable View view) {
        applyWindowInsetsMargin(view, false, false, false, true, false);
    }

    /**
     * Apply horizontal and bottom window insets margin for the supplied view.
     *
     * @param view The view to set the insets margin.
     *
     * @see #applyWindowInsetsMargin(View, boolean, boolean, boolean, boolean, boolean)
     */
    public static void applyWindowInsetsMarginHorizontalBottom(@Nullable View view) {
        applyWindowInsetsMargin(view, true, false, true, true, false);
    }

    /**
     * Request to apply window insets for a view
     *
     * @param view The view to request the window insets.
     */
    public static void requestApplyWindowInsets(@Nullable View view) {
        if (view == null) {
            return;
        }

        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                    ViewCompat.requestApplyInsets(view);
                }

                @Override
                public void onViewDetachedFromWindow(View view) { }
            });
        }
    }

    /**
     * Set the text switcher text and animate only if there is a text change.
     *
     * @param view The view switcher to be used.
     * @param text The text to be set.
     *
     * @see TextSwitcher#setCurrentText(CharSequence)
     * @see TextSwitcher#setText(CharSequence)
     */
    public static void setTextSwitcherText(@Nullable TextSwitcher view,
            @Nullable CharSequence text) {
        if (view == null || !(view.getCurrentView() instanceof TextView)) {
            return;
        }

        if (((TextView) view.getCurrentView()).getText() != null
                && ((TextView) view.getCurrentView()).getText().equals(text)) {
            view.setCurrentText(text);
        } else {
            view.setText(text);
        }
    }

    /**
     * Equivalent to calling {@link TextView#setTextSize(int, float)}.
     *
     * @param remoteViews The remote views to set the text size.
     * @param viewId The id of the view whose text size should change.
     * @param units The units of size (e.g. COMPLEX_UNIT_SP).
     * @param size The size of the text.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setTextViewTextSize(@Nullable RemoteViews remoteViews,
            @IdRes int viewId, int units, float size) {
        if (remoteViews == null) {
            return;
        }

        if (DynamicSdkUtils.is16()) {
            remoteViews.setTextViewTextSize(viewId, units, size);
        } else {
            remoteViews.setFloat(viewId, "setTextSize", size);
        }
    }

    /**
     * Set the text view max lines for the remote views.
     *
     * @param remoteViews The remote views to set the text view max lines.
     * @param viewId The id of the text view whose max lines to be set.
     * @param lines The max lines to be set.
     *
     * @see RemoteViews#setInt(int, String, int)
     */
    public static void setTextViewMaxLines(@Nullable RemoteViews remoteViews,
            @IdRes int viewId, int lines) {
        if (remoteViews == null) {
            return;
        }

        remoteViews.setInt(viewId, "setMaxLines", lines);
    }

    /**
     * Equivalent to calling {@link RemoteViews#setTextViewText(int, CharSequence)}
     * and hide the view if the text is empty.
     *
     * @param remoteViews The remote views to set the text size.
     * @param viewId The id of the view whose text size should change.
     * @param text The text to be set.
     */
    public static void setTextViewText(@NonNull RemoteViews remoteViews,
            @IdRes int viewId, @Nullable CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            remoteViews.setTextViewText(viewId, text);
            remoteViews.setViewVisibility(viewId, View.VISIBLE);
        } else {
            remoteViews.setViewVisibility(viewId, View.GONE);
        }
    }

    /**
     * Set the alpha for the supplied view.
     *
     * @param view The view to set the alpha.
     * @param alpha The alpha value to be set.
     *
     * @see View#setAlpha(float)
     */
    public static void setAlpha(@Nullable View view,
            @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (view == null) {
            return;
        }

        view.setAlpha(alpha);
    }

    /**
     * Set the alpha for the remote views.
     *
     * @param remoteViews The remote views to set the alpha.
     * @param viewId The id of the view whose alpha to be set.
     * @param alpha The alpha value to be set.
     *
     * @see RemoteViews#setInt(int, String, int)
     */
    public static void setAlpha(@Nullable RemoteViews remoteViews, @IdRes int viewId,
            @IntRange(from = 0, to = 255) int alpha) {
        if (remoteViews == null) {
            return;
        }

        remoteViews.setInt(viewId, "setAlpha", alpha);
    }

    /**
     * Set the enabled state for the remote views.
     *
     * @param remoteViews The remote views to set the enabled state.
     * @param viewId The id of the view whose enabled state to be set.
     * @param enabled {@code true} to enable the view.
     *
     * @see RemoteViews#setBoolean(int, String, boolean)
     */
    public static void setEnabled(@Nullable RemoteViews remoteViews,
            @IdRes int viewId, boolean enabled) {
        if (remoteViews == null) {
            return;
        }

        remoteViews.setBoolean(viewId, "setEnabled", enabled);
    }

    /**
     * Set the background color for the remote views.
     *
     * @param remoteViews The remote views to set the background color.
     * @param viewId The id of the view whose background color to be set.
     * @param color The color value to be set.
     *
     * @see RemoteViews#setInt(int, String, int)
     */
    public static void setBackgroundColor(@Nullable RemoteViews remoteViews,
            @IdRes int viewId, @ColorInt int color) {
        if (remoteViews == null) {
            return;
        }

        remoteViews.setInt(viewId, "setBackgroundColor", color);
    }

    /**
     * Set the color filter for the remote views.
     *
     * @param remoteViews The remote views to set the color filter.
     * @param viewId The id of the view whose color filter to be set.
     * @param color The color value to be set.
     *
     * @see RemoteViews#setInt(int, String, int)
     */
    public static void setColorFilter(@Nullable RemoteViews remoteViews,
            @IdRes int viewId, @ColorInt int color) {
        if (remoteViews == null) {
            return;
        }

        remoteViews.setInt(viewId, "setColorFilter", color);
    }
}
