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

package com.pranavpandey.android.dynamic.utils;

import android.annotation.TargetApi;
import android.app.ApplicationErrorReport;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * A collection of functions to perform various operations on the URL or to generate intents.
 */
public class DynamicLinkUtils {

    /**
     * Android Market app url template to open app details on older devices.
     */
    private static final String URL_MARKET = "market://details?id=";

    /**
     * Google Play app url template to open app details on newer devices.
     */
    private static final String URL_PLAY_STORE = "http://play.google.com/store/apps/details?id=";

    /**
     * Android Market app search query template to search apps of a publisher.
     */
    private static final String URL_MARKET_SEARCH_PUB = "market://search?q=pub:";

    /**
     * Google Play app search query template to search apps of a publisher.
     */
    private static final String URL_GOOGLE_PLAY_SEARCH_PUB =
            "http://play.google.com/store/search?q=pub:";

    /**
     * Send intent URI to open the compose mail screen.
     */
    private static final String MAIL_TO = "mailto:";

    /**
     * Package name for the Google feedback activity.
     */
    private static final String PACKAGE_FEEDBACK = "com.google.android.feedback";

    /**
     * Package name for the GMS (Google Mobile Services) activity.
     */
    private static final String PACKAGE_GMS = "com.google.android.gms";

    /**
     * Copy a plain text to the clipboard.
     *
     * @param context The context to get the clipboard manager.
     * @param label The user visible label for the clip data.
     * @param text The actual text in the clip.
     *
     * @return {@code true} if copied to clipboard successfully.
     *
     * @see ClipboardManager
     */
    public static boolean copyToClipboard(@NonNull Context context,
            @NonNull String label, @NonNull String text) {
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(label, text));

            return true;
        }

        return false;
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param uri The optional content uri to be shared.
     * @param mimeType The optional mime type for the file.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_SEND
     */
    public static void share(@NonNull Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri uri, @Nullable String mimeType) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");

        if (title == null) {
            title = context.getResources().getString(R.string.adu_share_to);
        }

        if (message == null) {
            message = String.format(context.getString(R.string.adu_share_desc),
                    context.getApplicationInfo().loadLabel(context.getPackageManager()),
                    context.getPackageName());
        }

        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType(mimeType != null ? mimeType : "*/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        try {
            context.startActivity(Intent.createChooser(intent, title));
        } catch (Exception ignored) {
        }
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param image The optional image bitmap uri to be shared.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_SEND
     */
    public static void share(@NonNull Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri image) {
        share(context, title, message, image, "image/*");
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_SEND
     */
    public static void share(@NonNull Context context,
            @Nullable String title, @Nullable String message) {
        share(context, title, message, null);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see #share(Context, String, String)
     */
    public static void shareApp(@NonNull Context context) {
        share(context, null, null);
    }

    /**
     * View any URL in the available app or browser. Some URLs will automatically open in their
     * respective apps if installed on the the device. Special treatment is applied for the
     * Facebook URLs to open them directly in the app.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param url The web or app link to open.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_VIEW
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static void viewUrl(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            if (DynamicSdkUtils.is30()) {
                intent.addFlags(Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
            }

            context.startActivity(intent);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }

    /**
     * View app on Google Play or Android Market.
     *
     * @param context The context to retrieve the resources.
     * @param packageName Application package name to build the search query.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_VIEW
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static void viewInGooglePlay(@NonNull Context context, @NonNull String packageName) {
        try {
            viewUrl(context, URL_MARKET + packageName);
        } catch (Exception e) {
            viewUrl(context, URL_PLAY_STORE + packageName);
        }
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Can be used for the quick feedback or rating from the user.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see #viewInGooglePlay(Context, String)
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static void rateApp(@NonNull Context context) {
        viewInGooglePlay(context, context.getPackageName());
    }

    /**
     * View other apps of a Publisher on Google Play or Android Market.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param publisher The publisher name to build the search query.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_VIEW
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static void moreApps(@NonNull Context context, @NonNull String publisher) {
        try {
            viewUrl(context, URL_MARKET_SEARCH_PUB + publisher);
        } catch (Exception e) {
            viewUrl(context, URL_GOOGLE_PLAY_SEARCH_PUB + publisher);
        }
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param email The email id of the developer.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_SENDTO
     * @see #MAIL_TO
     */
    public static void email(@NonNull Context context, @NonNull String email,
            @Nullable String subject, @Nullable String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(MAIL_TO + email));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);

            context.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * <p><p>This method throws {@link ActivityNotFoundException} if there was no activity found
     * to run the given intent.
     *
     * @param context The context to retrieve the resources.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see Intent#ACTION_SENDTO
     * @see #MAIL_TO
     */
    public static void report(@NonNull Context context,
            @Nullable String appName, @NonNull String email) {
        try {
            String version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).versionName;

            if (appName == null) {
                appName = context.getApplicationInfo().loadLabel(
                        context.getPackageManager()).toString();
            }

            email(context, email, String.format(
                    context.getResources().getString(R.string.adu_bug_title), appName,
                    version, Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE),
                    context.getResources().getString(R.string.adu_bug_desc));
        } catch (Exception ignored) {
        }
    }

    /**
     * Checks whether the email client exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the email client exists on the device.
     */
    public static boolean isEmailExists(@NonNull Context context) {
        List<ResolveInfo> list = null;

        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(MAIL_TO));
            PackageManager packageManager = context.getPackageManager();
            list = packageManager.queryIntentActivities(intent, 0);
        } catch (Exception ignored) {
        }

        return list != null && list.size() > 0;
    }

    /**
     * Checks whether the GMS (Google Mobile Services) package exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the GMS (Google Mobile Services) package exists on the device.
     */
    public static boolean isGMSExists(@NonNull Context context) {
        return DynamicPackageUtils.isPackageExists(context, PACKAGE_GMS);
    }

    /**
     * Checks whether the Google feedback package exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the Google feedback package exists on the device.
     */
    public static boolean isGoogleFeedbackExists(@NonNull Context context) {
        return DynamicPackageUtils.isPackageExists(context, PACKAGE_FEEDBACK);
    }

    /**
     * Checks whether the feedback functionality exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the feedback functionality exists on the device.
     *
     * @see #isGMSExists(Context)
     * @see #isGoogleFeedbackExists(Context)
     */
    public static boolean isFeedbackExists(@NonNull Context context) {
        return isGMSExists(context) || isGoogleFeedbackExists(context);
    }

    /**
     * Ask questions or submit bug report to the developer via Google feedback.
     *
     * <p><p>It will redirect to {@link #report(Context, String, String)} method if feedback
     * package is not available on the device.
     *
     * @param context The context to retrieve the resources.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     * @param reportType The crash report type.
     *                   <p>Can be one of {@link ApplicationErrorReport#TYPE_NONE},
     *                   {@link ApplicationErrorReport#TYPE_CRASH},
     *                   {@link ApplicationErrorReport#TYPE_ANR},
     *                   {@link ApplicationErrorReport#TYPE_BATTERY}, or
     *                   {@link ApplicationErrorReport#TYPE_RUNNING_SERVICE}.
     * @param crashInfo The crash info for the report.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see ApplicationErrorReport
     */
    public static void feedback(@NonNull Context context,
            @Nullable String appName, @NonNull String email, int reportType,
            @Nullable ApplicationErrorReport.CrashInfo crashInfo) {
        ApplicationErrorReport report = new ApplicationErrorReport();
        report.packageName = report.processName = context.getPackageName();
        report.time = System.currentTimeMillis();
        report.type = reportType;
        report.systemApp = DynamicPackageUtils.isSystemApp(context.getApplicationInfo());

        if (crashInfo != null) {
            report.crashInfo = crashInfo;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
        if (isGMSExists(context)) {
            intent.setClassName(PACKAGE_GMS, PACKAGE_GMS + ".feedback.FeedbackActivity");
        } else {
            intent.setClassName(PACKAGE_FEEDBACK, PACKAGE_FEEDBACK + ".FeedbackActivity");
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            report(context, appName, email);
        }
    }

    /**
     * Ask questions or submit bug report to the developer via Google feedback.
     *
     * <p><p>It will redirect to {@link #report(Context, String, String)} method if feedback
     * package is not available on the device.
     *
     * @param context The context to retrieve the resources.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see ApplicationErrorReport
     * @see ApplicationErrorReport#TYPE_NONE
     */
    public static void feedback(@NonNull Context context,
            @Nullable String appName, @NonNull String email) {
        feedback(context, appName, email, ApplicationErrorReport.TYPE_NONE, null);
    }
}
