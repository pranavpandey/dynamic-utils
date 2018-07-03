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

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Helper class to perform animation related operations.
 */
public class DynamicAnimUtils {

    /**
     * Play an animator animation on a view.
     *
     * @param view The view to play the animation.
     * @param animator The animator to be applied on the view.
     */
    public static void playAnimation(@NonNull View view, @AnimatorRes int animator) {
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater
                .loadAnimator(view.getContext(), animator);
        animatorSet.setTarget(view);

        animatorSet.start();
    }
}
