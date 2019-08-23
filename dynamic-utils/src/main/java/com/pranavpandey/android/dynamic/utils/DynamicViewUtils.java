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

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
     * or above devices.
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
    public static void addView(@NonNull ViewGroup viewGroup,
            @NonNull View view, boolean removePrevious) {
        if (removePrevious && viewGroup.getChildCount() > 0) {
            viewGroup.removeAllViews();
        }

        viewGroup.addView(view);
    }


    /**
     * Manage scroll indicators for a view according to its current state.
     *
     * @param view The view to show or hide the scroll indicators accordingly.
     * @param upIndicator The indicator to show if the view can be scrolled upwards.
     * @param downIndicator The indicator to show if the view can be scrolled downwards.
     */
    public static void manageScrollIndicators(@NonNull View view,
            @Nullable View upIndicator, @Nullable View downIndicator) {
        if (upIndicator != null) {
            upIndicator.setVisibility(
                    view.canScrollVertically(-1) ? View.VISIBLE : View.INVISIBLE);
        }

        if (downIndicator != null) {
            downIndicator.setVisibility(
                    view.canScrollVertically(1) ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
