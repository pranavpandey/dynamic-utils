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

import androidx.collection.LruCache;

/**
 * An {@link LruCache} to provide base for the other caches.
 */
public abstract class DynamicLruCache<T, V> extends LruCache<T, V> {

    /**
     * Default maximum size for this cache.
     */
    public static final int MAX_SIZE = 512;

    /**
     * Default byte multiplier for this cache.
     */
    public static final int BYTE_MULTIPLIER = 1024;

    /**
     * Maximum size for this cache.
     */
    private final int mMaxSize;

    /**
     * Byte multiplier for this cache.
     */
    private final int mByteMultiplier;

    /**
     * Constructor to initialize an object of this class.
     */
    public DynamicLruCache() {
        this(MAX_SIZE, BYTE_MULTIPLIER);
    }

    /**
     * Constructor to initialize an object of this class.
     *
     * @param maxSize The maximum size to be used.
     * @param byteMultiplier The byte multiplier to be used.
     */
    public DynamicLruCache(int maxSize, int byteMultiplier) {
        super(maxSize * byteMultiplier);

        this.mMaxSize = maxSize;
        this.mByteMultiplier = byteMultiplier;
    }

    /**
     * Returns the maximum size for this cache.
     *
     * @return The maximum size for this cache.
     */
    public int getMaxSize() {
        return mMaxSize;
    }

    /**
     * Returns the byte multiplier for this cache.
     *
     * @return The byte multiplier for this cache.
     */
    public int getByteMultiplier() {
        return mByteMultiplier;
    }
}
