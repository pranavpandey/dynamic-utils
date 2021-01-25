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

package com.pranavpandey.android.dynamic.utils.concurrent.task;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.utils.DynamicFileUtils;
import com.pranavpandey.android.dynamic.utils.concurrent.DynamicTask;

/**
 * A {@link DynamicTask} to write a file {@link Uri} from source to the destination.
 */
public class FileWriteTask extends ContextTask<Void, Void, Boolean> {

    /**
     * Uri for the source file.
     */
    private final Uri mSource;

    /**
     * Uri for the destination file.
     */
    private final Uri mDestination;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param context The context to get the content resolver.
     * @param source The uri of the source file.
     * @param destination The uri of the destination file.
     *
     * @see android.content.ContentResolver
     */
    public FileWriteTask(@Nullable Context context,
            @Nullable Uri source, @Nullable Uri destination) {
        super(context);

        this.mSource = source;
        this.mDestination = destination;
    }

    @Override
    protected @Nullable Boolean doInBackground(@Nullable Void params) {
        if (getContext() == null || getSource() == null || getDestination() == null) {
            return false;
        }

        return DynamicFileUtils.writeToFile(getContext(), getSource(), getDestination());
    }

    /**
     * Get the source uri used by this task.
     *
     * @return The source uri used by this task.
     */
    public @Nullable Uri getSource() {
        return mSource;
    }

    /**
     * Get the destination uri used by this task.
     *
     * @return The destination uri used by this task.
     */
    public @Nullable Uri getDestination() {
        return mDestination;
    }
}
