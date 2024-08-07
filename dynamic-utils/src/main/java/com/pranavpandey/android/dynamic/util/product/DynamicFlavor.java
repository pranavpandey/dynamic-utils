/*
 * Copyright 2017-2024 Pranav Pandey
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

package com.pranavpandey.android.dynamic.util.product;

/**
 * Product flavors to support external app stores.
 */
public @interface DynamicFlavor {

    /**
     * Constant for the Google Play Store.
     */
    String GOOGLE = "google";

    /**
     * Constant for the external app store.
     */
    String EXTERNAL = "external";

    /**
     * Constant for the default flavor.
     */
    String DEFAULT = GOOGLE;
}
