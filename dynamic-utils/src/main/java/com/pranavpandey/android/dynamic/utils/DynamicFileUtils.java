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

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Helper class to perform various file operations.
 *
 * <p><p>A {@link FileProvider} in the form of {@code ${applicationId}.FileProvider} must be
 * added in the {@code manifest} to perform some operations automatically like saving the
 * bitmap or file in cache directory.
 */
public class DynamicFileUtils {

    /**
     * Default suffix for the file provider.
     */
    private static final String FILE_PROVIDER = ".FileProvider";

    /**
     * Constant ot match the content uri.
     */
    private static final String URI_MATCHER_CONTENT = "content:";

    /**
     * Constant ot match the file uri.
     */
    private static final String URI_MATCHER_FILE = "file:";

    /**
     * Returns the base name without extension of given file name.
     * <p>e.g. getBaseName("file.txt") will return "file".
     *
     * @param fileName The full name of the file with extension.
     *
     * @return The base name of the file without extension.
     */
    public static @NonNull String getBaseName(@NonNull String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

    /**
     * Returns the extension of a file.
     *
     * @param file The file to retrieve its extension.
     *
     * @return The extension of the file.
     */
    public static @Nullable String getExtension(@NonNull File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return ext;
    }

    /**
     * Verifies a file if it exist or not.
     *
     * @param file The file to be verified.
     *
     * @return {@code true} if a file can be accessed by automatically creating the
     *         sub directories.
     */
    public static boolean verifyFile(@NonNull File file) {
        boolean fileExists = true;

        if (!file.exists()) {
            fileExists = file.mkdirs();
        }

        return fileExists;
    }

    /**
     * Delete a directory.
     *
     * @param dir The directory to be deleted.
     *
     * @return {@code true} if the directory has been deleted successfully.
     */
    public static boolean deleteDirectory(@NonNull File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            for (String aChildren : children) {
                boolean success = deleteDirectory(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    /**
     * Creates a zip archive from the directory.
     *
     * @param dir The directory to be archived.
     * @param zip The output zip archive.
     *
     * @throws IOException Throws IO exception.
     */
    public static void zipDirectory(@NonNull File dir, @NonNull File zip) throws IOException {
        if (verifyFile(new File(zip.getParent()))) {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            zip(dir, dir, zos);
            zos.close();
        }
    }

    /**
     * Creates a zip archive from the zip output stream.
     *
     * @param dir The directory to be archived.
     * @param zip The output zip archive.
     * @param zos The zip output stream.
     */
    private static void zip(@NonNull File dir, @NonNull File zip, 
            @NonNull ZipOutputStream zos) throws IOException {
        File[] files = dir.listFiles();
        byte[] buffer = new byte[8192];
        int read;

        for (File file : files) {
            if (file.isDirectory()) {
                zip(file, zip, zos);
            } else {
                FileInputStream in = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(file.getPath().substring(
                        zip.getPath().length() + 1));
                zos.putNextEntry(entry);

                while (-1 != (read = in.read(buffer))) {
                    zos.write(buffer, 0, read);
                }

                in.close();
            }
        }
    }

    /**
     * Extracts a zip archive.
     *
     * @param zip The zip archive to be extracted.
     * @param extractTo The unzip destination.
     *
     * @throws IOException Throws IO exception.
     * @throws ZipException Throws Zip exception.
     */
    public static void unzip(@NonNull File zip, @NonNull File extractTo) throws IOException {
        ZipFile archive = new ZipFile(zip);
        Enumeration e = archive.entries();

        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            File file = new File(extractTo, entry.getName());

            if (entry.isDirectory() && !file.exists()) {
                verifyFile(file);
            } else {
                if (verifyFile(file.getParentFile())) {
                    InputStream in = archive.getInputStream(entry);
                    BufferedOutputStream out =
                            new BufferedOutputStream(new FileOutputStream(file));

                    byte[] buffer = new byte[8192];
                    int read;

                    while (-1 != (read = in.read(buffer))) {
                        out.write(buffer, 0, read);
                    }

                    in.close();
                    out.close();
                }
            }
        }
    }

    /**
     * Returns uri from the file. 
     * <p>It will automatically use the @link FileProvider} on Android N and above devices.
     *
     * @param context The context to get the file provider.
     * @param file The file to get the uri.
     *
     * @return The uri from the file.
     *
     * @see Uri
     */
    public static Uri getUriFromFile(@NonNull Context context, @NonNull File file) {
        if (DynamicVersionUtils.isNougat()) {
            return FileProvider.getUriForFile(context.getApplicationContext(),
                    context.getPackageName() + FILE_PROVIDER, file);
        } else {
            return (Uri.fromFile(file));
        }
    }

    /**
     * Returns file name from the uri.
     *
     * @param context The context to get content resolver.
     * @param uri The uri to get the file name.
     *
     * @return The file name from the uri.
     *
     * @see Context#getContentResolver()
     */
    public static String getFileNameFromUri(@NonNull Context context, @NonNull Uri uri) {
        String fileName = null;
        Cursor cursor = null;

        if (uri.toString().contains(URI_MATCHER_CONTENT)) {
            try {
                cursor = context.getContentResolver().query(
                        uri, null, null, null, null);
                if (cursor != null) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    cursor.moveToFirst();
                    fileName = cursor.getString(nameIndex);
                }
            } catch (Exception ignored) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (uri.toString().contains(URI_MATCHER_FILE)) {
            try {
                fileName = new File(new URI(uri.toString())).getName();
            } catch (Exception ignored) {
            }
        }

        return fileName;
    }

    /**
     * Writes a file from the source to destination.
     *
     * @param source The source file.
     * @param destination The destination file.
     * @param outputFileName The output files name.
     *
     * @return {@code true} if the file has been written successfully.
     */
    public static boolean writeToFile(@NonNull File source, 
            @NonNull File destination, @NonNull String outputFileName) {
        boolean success = false;

        try {
            if (DynamicFileUtils.verifyFile(destination)) {
                FileInputStream input = new FileInputStream(source);
                OutputStream output = new FileOutputStream(destination
                        + File.separator + outputFileName);
                byte[] buffer = new byte[1024];
                int length;

                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                input.close();

                success = true;
            }
        } catch (Exception ignored) {
        }

        return success;
    }

    /**
     * Writes a file uri from the source to destination.
     *
     * @param context The context to get content resolver.
     * @param sourceUri The source file uri.
     * @param destinationUri The destination file uri.
     *
     * @return {@code true} if the file has been written successfully.
     */
    public static boolean writeToFile(@NonNull Context context, 
            @NonNull Uri sourceUri, @NonNull Uri destinationUri) {
        boolean success = false;

        try {
            InputStream input = context.getContentResolver().openInputStream(sourceUri);
            ParcelFileDescriptor pfdDestination = context.getContentResolver().
                    openFileDescriptor(destinationUri, "w");
            if (input != null && pfdDestination != null) {
                OutputStream output = new FileOutputStream(pfdDestination.getFileDescriptor());
                byte[] buffer = new byte[1024];
                int length;

                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                input.close();
                pfdDestination.close();

                success = true;
            }
        } catch (Exception ignored) {
        }

        return success;
    }

    /**
     * Save and returns uri from the bitmap.
     * <p>It will automatically use the @link FileProvider} on Android N and above devices.
     *
     * @param context The context to get the file provider.
     * @param bitmap The bitmap to get the uri.
     *
     * @return The uri from the bitmap.
     *
     * @see Uri
     */
    public static Uri getBitmapUri(@NonNull Context context,
            @Nullable Bitmap bitmap, @NonNull String name) {
        Uri bitmapUri = null;

        if (bitmap != null) {
            try {
                File cachePath = new File(DynamicVersionUtils.isLollipop()
                        ? context.getCacheDir().getPath()
                        : Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getPath(), name);
                String image = cachePath + File.separator + name + ".png";
                cachePath.mkdirs();

                FileOutputStream out = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
                bitmapUri = getUriFromFile(context, new File(image));
            } catch (Exception ignored) {
            }
        }

        return bitmapUri;
    }
}
