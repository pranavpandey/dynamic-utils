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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Helper class to perform {@link View} operations.
 */
public class DynamicViewUtils {

    /**
     * Set light status bar if we are using light primary color on Android M or above devices.
     *
     * @param view The view to get the system ui flags.
     * @param isLight {@code true} to set the light status bar.
     */
    @TargetApi(android.os.Build.VERSION_CODES.M)
    public static void setLightStatusBar(@NonNull View view, boolean isLight) {
        if (DynamicVersionUtils.isMarshmallow()) {
            int flags = view.getSystemUiVisibility();
            if (isLight) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

            view.setSystemUiVisibility(flags);
        }
    }

    /**
     * Set light navigation bar if we are using light primary color on Android O or above devices.
     *
     * @param view The view to get the system ui flags.
     * @param isLight {@code true} to set the light navigation bar.
     */
    @TargetApi(android.os.Build.VERSION_CODES.O)
    public static void setLightNavigationBar(@NonNull View view, boolean isLight) {
        if (DynamicVersionUtils.isOreo()) {
            int flags = view.getSystemUiVisibility();
            if (isLight) {
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
