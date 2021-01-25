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

import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.utils.concurrent.DynamicTask;

import java.io.File;

/**
 * A {@link DynamicTask} to delete a file.
 */
public class FileDeleteTask extends DynamicTask<Void, Void, Boolean> {

    /**
     * File used by this task.
     */
    private final File mFile;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param file The file to be deleted.
     */
    public FileDeleteTask(@Nullable File file) {
        this.mFile = file;
    }

    @Override
    protected @Nullable Boolean doInBackground(@Nullable Void params) {
        if (getFile() == null || !getFile().exists()) {
            return false;
        }

        try {
            return getFile().delete();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Get the file used by this task.
     *
     * @return The file used by this task.
     */
    public @Nullable File getFile() {
        return mFile;
    }
}
