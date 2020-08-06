/*
 * Copyright 2017-2020 Pranav Pandey
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

import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The executor interface to provide concurrent functionality.
 */
public interface DynamicExecutor {

    /**
     * Gets the number of available cores.
     * (not always the same as the maximum number of cores)
     */
    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /**
     * Instantiates the queue of Runnables as a LinkedBlockingQueue.
     */
    BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    /**
     * The amount of time an idle thread waits before terminating.
     */
    long KEEP_ALIVE = 1000;

    /**
     * The keep alive time unit.
     */
    TimeUnit KEEP_ALIVE_UNIT = TimeUnit.MILLISECONDS;

    /**
     * Returns the default executor service.
     *
     * @return The default executor service.
     */
    @NonNull ExecutorService getDefaultExecutor();
}
