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

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/**
 * Base class to receive the callback from an asynchronous work.
 *
 * @param <T> The type of the params.
 * @param <P> The type of the progress.
 * @param <R> The type of the result.
 */
public abstract class DynamicRunnable<T, P, R> implements Runnable {

    /**
     * This method will be called before doing the background work.
     */
    @MainThread
    protected void onPreExecute() { }

    /**
     * This method will be called for doing the background work.
     *
     * @param params The optional parameters required for the work.
     *
     * @return The optional result object.
     */
    @WorkerThread
    protected abstract @Nullable R doInBackground(@Nullable T params);

    /**
     * This method will be called on publishing the progress.
     *
     * @param progress The progress returned by the work.
     */
    @MainThread
    protected void onProgressUpdate(@Nullable DynamicResult<P> progress) { }

    /**
     * This method will be called after completing the work.
     *
     * @param result The result returned by the work.
     */
    @MainThread
    protected void onPostExecute(@Nullable DynamicResult<R> result) { }

    /**
     * This method can be invoked from {@link #doInBackground} to
     * publish updates on the UI thread while the background computation is
     * still running. Each call to this method will trigger the execution of
     * {@link #onProgressUpdate} on the UI thread.
     *
     * {@link #onProgressUpdate} will not be called if the task has been
     * canceled.
     *
     * @param progress The progress result to update the UI with.
     *
     * @return The published result.
     *
     * @see #onProgressUpdate
     * @see #doInBackground
     */
    @WorkerThread
    public abstract @Nullable DynamicResult<P> publishProgress(
            final @Nullable DynamicResult<P> progress);

    /**
     * This method will be called to handle the result returned by the task.
     *
     * @param result The result returned by the work.
     */
    @MainThread
    public abstract void finish(@Nullable DynamicResult<R> result);

    /**
     * Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object)} has finished.
     *
     * <p>The default implementation simply invokes {@link #onCancelled()} and
     * ignores the result. If you write your own implementation, do not call
     * <code>super.onCancelled(result)</code>.
     *
     * @param result The result, if any, computed in
     *               {@link #doInBackground(Object)}, can be null.
     *
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @SuppressWarnings({"UnusedParameters"})
    @MainThread
    protected void onCancelled(DynamicResult<R> result) {
        onCancelled();
    }

    /**
     * Applications should preferably override {@link #onCancelled(DynamicResult)}.
     * This method is invoked by the default implementation of {@link #onCancelled(DynamicResult)}.
     * <p>The default version does nothing.
     *
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object)} has finished.
     *
     * @see #onCancelled(DynamicResult)
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @MainThread
    protected void onCancelled() { }

    /**
     * Returns {@code true} if this task was cancelled before it completed normally.
     * If you are calling {@link #cancel(boolean)} on the task, the value returned by this
     * method should be checked periodically from {@link #doInBackground(Object)} to end the
     * task as soon as possible.
     *
     * @return {@code true} if task was cancelled before it completed
     *
     * @see #cancel(boolean)
     */
    public abstract boolean isCancelled();

    /**
     * Attempts to cancel execution of this task.  This attempt will fail if the task has
     * already completed, already been cancelled, or could not be cancelled for some other
     * reason. If successful, and this task has not started when {@code cancel} is called,
     * this task should never run. If the task has already started, then the
     * {@code mayInterruptIfRunning} parameter determines whether the thread executing this
     * task should be interrupted in an attempt to stop the task.
     *
     * <p>Calling this method will result in {@link #onCancelled(DynamicResult)} being invoked
     * on the UI thread after {@link #doInBackground(Object)} returns. Calling this method
     * guarantees that onPostExecute(Object) is never subsequently invoked, even if
     * {@code cancel} returns false, but {@link #onPostExecute} has not yet run.
     * To finish the task as early as possible, check {@link #isCancelled()} periodically
     * from {@link #doInBackground(Object)}.
     *
     * <p>This only requests cancellation. It never waits for a running background task to
     * terminate, even if {@code mayInterruptIfRunning} is true.
     *
     * @param mayInterruptIfRunning {@code true} if the thread executing this
     *        task should be interrupted; otherwise, in-progress tasks are allowed
     *        to complete.
     *
     * @return {@code false} if the task could not be cancelled,
     *         typically because it has already completed normally;
     *         {@code true} otherwise
     *
     * @see #isCancelled()
     * @see #onCancelled(DynamicResult)
     */
    public abstract boolean cancel(boolean mayInterruptIfRunning);
}
