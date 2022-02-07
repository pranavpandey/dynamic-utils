/*
 * Copyright 2017-2022 Pranav Pandey
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
import android.widget.TextView;

import androidx.annotation.Nullable;

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
}
