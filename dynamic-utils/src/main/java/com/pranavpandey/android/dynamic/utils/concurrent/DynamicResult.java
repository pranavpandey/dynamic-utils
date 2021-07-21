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

package com.pranavpandey.android.dynamic.utils.concurrent;

import androidx.annotation.Nullable;

/**
 * Base class to represent the result of a asynchronous work.
 *
 * @param <T> The type of the data.
 */
public abstract class DynamicResult<T> {

    /**
     * The data associated with this result.
     */
    public T data;

    /**
     * Making default constructor private to avoid instantiation.
     */
    private DynamicResult() { }

    /**
     * Get the data associated with this result.
     *
     * @return The data associated with this result.
     */
    public @Nullable T getData() {
        return data;
    }

    /**
     * Set the data associated with this result.
     *
     * @param data The data to be set.
     */
    public void setData(@Nullable T data) {
        this.data = data;
    }

    /**
     * The result class to represent the success.
     */
    public static final class Success<T> extends DynamicResult<T> {

        /**
         * Constructor to initialize an object of this class.
         *
         * @param data The data for this result.
         */
        public Success(@Nullable T data) {
            this.data = data;
        }
    }

    /**
     * The result class to represent the progress.
     */
    public static final class Progress<T> extends DynamicResult<T> {

        /**
         * Constructor to initialize an object of this class.
         *
         * @param data The data for this result.
         */
        public Progress(@Nullable T data) {
            this.data = data;
        }
    }

    /**
     * The result class to represent the error.
     */
    public static final class Error<T> extends DynamicResult<T> {

        /**
         * The exception associated with this result.
         */
        public Exception exception;

        /**
         * Constructor to initialize an object of this class.
         *
         * @param exception The exception for this error.
         */
        public Error(@Nullable Exception exception) {
            this.exception = exception;
        }

        /**
         * Get the exception associated with this result.
         *
         * @return The exception associated with this result.
         */
        public @Nullable Exception getException() {
            return exception;
        }

        /**
         * Set the data associated with this result.
         *
         * @param exception The exception to be set.
         */
        public void setException(@Nullable Exception exception) {
            this.exception = exception;
        }
    }
}
