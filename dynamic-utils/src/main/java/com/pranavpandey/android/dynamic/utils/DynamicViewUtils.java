package com.pranavpandey.android.dynamic.utils;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Helper class to perform {@link View} operations.
 */
public class DynamicViewUtils {

    /**
     * Set light status bar if we are using light primary color on
     * Android M or above devices.
     *
     * @param isLight {@code true} to set the light status bar.
     */
    @TargetApi(android.os.Build.VERSION_CODES.M)
    public static void setLightStatusBar(@NonNull View view, boolean isLight){
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
     * Set light navigation bar if we are using light primary color on
     * Android O or above devices.
     *
     * @param isLight {@code true} to set the light navigation bar.
     */
    @TargetApi(android.os.Build.VERSION_CODES.O)
    public static void setLightNavigationBar(@NonNull View view, boolean isLight){
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
     * @param viewGroup View group to add the view.
     * @param view View to be added.
     * @param removePrevious {@link true} to remove all the previous
     *                       views of the view group.
     */
    public static void addView(@NonNull ViewGroup viewGroup,
                               @NonNull View view, boolean removePrevious) {
        if (removePrevious && viewGroup.getChildCount() > 0) {
            viewGroup.removeAllViews();
        }

        viewGroup.addView(view);
    }
}
