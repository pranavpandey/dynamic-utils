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

package com.pranavpandey.android.dynamic.utils.cache;

import androidx.annotation.NonNull;

/**
 * A {@link DynamicLruCache} for the {@link Integer}.
 */
public final class IntegerLruCache<T> extends DynamicLruCache<T, Integer> {

    /**
     * Constructor to initialize an object of this class.
     */
    public IntegerLruCache() {
        super();
    }

    @Override
    protected int sizeOf(@NonNull T key, @NonNull Integer value) {
        return Integer.toString(value).getBytes().length / getByteMultiplier();
    }
}
