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

package com.pranavpandey.android.dynamic.utils;

import android.app.ApplicationErrorReport;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.net.MailTo;

/**
 * Helper class to perform URL, email and feedback related operations.
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
        ClipboardManager clipboard = ContextCompat.getSystemService(
                context, ClipboardManager.class);
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
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param uri The optional content uri to be shared.
     * @param mimeType The optional mime type for the file.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri uri, @Nullable String mimeType) {
        if (context == null) {
            return false;
        }

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
            intent.setType(mimeType != null ? mimeType : DynamicFileUtils.FILE_MIME);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        if (DynamicIntentUtils.isActivityResolved(context, intent)) {
            context.startActivity(Intent.createChooser(intent, title));
            return true;
        }

        return false;
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param image The optional image bitmap uri to be shared.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri image) {
        return share(context, title, message, image, "image/*");
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to retrieve the resources.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context,
            @Nullable String title, @Nullable String message) {
        return share(context, title, message, null);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to retrieve the resources.
     *
     * @return {@code true} on successful operation.
     *
     * @see #share(Context, String, String)
     */
    public static boolean shareApp(@Nullable Context context) {
        return share(context, null, null);
    }

    /**
     * View any URL in the available app or browser. Some URLs will automatically open in their
     * respective apps if installed on the device. Special treatment is applied for the
     * Facebook URLs to open them directly in the app.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to retrieve the resources.
     * @param url The web or app link to open.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    public static boolean viewUrl(@Nullable Context context, @NonNull String url) {
        return DynamicIntentUtils.viewIntent(context,
                new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to retrieve the resources.
     * @param packageName Application package name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    public static boolean viewInGooglePlay(@Nullable Context context,
            @NonNull String packageName) {
        if (viewUrl(context, URL_MARKET + packageName)) {
            return true;
        }

        return viewUrl(context, URL_PLAY_STORE + packageName);
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Can be used for the quick feedback or rating from the user.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to retrieve the resources.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewInGooglePlay(Context, String)
     */
    public static boolean rateApp(@Nullable Context context) {
        if (context == null) {
            return false;
        }

        return viewInGooglePlay(context, context.getPackageName());
    }

    /**
     * View other apps of a Publisher on Google Play or Android Market.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to retrieve the resources.
     * @param publisher The publisher name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    public static boolean moreApps(@Nullable Context context, @NonNull String publisher) {
        if (viewUrl(context, URL_MARKET_SEARCH_PUB + publisher)) {
            return true;
        }

        return viewUrl(context, URL_GOOGLE_PLAY_SEARCH_PUB + publisher);
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * @param context The context to retrieve the resources.
     * @param emails The email ids of the developer.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean email(@Nullable Context context, @NonNull String[] emails,
            @Nullable String subject, @Nullable String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        if (DynamicIntentUtils.isActivityResolved(context, intent)) {
            context.startActivity(intent);
            return true;
        }

        return false;
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * @param context The context to retrieve the resources.
     * @param email The email id of the developer.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean email(@Nullable Context context, @NonNull String email,
            @Nullable String subject, @Nullable String text) {
        return email(context, new String[] { email }, subject, text);
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * @param context The context to retrieve the resources.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean report(@Nullable Context context,
            @Nullable String appName, @NonNull String email) {
        if (context == null) {
            return false;
        }

        String version = null;
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).versionName;
        } catch (Exception ignored) {
        }

        if (appName == null) {
            appName = context.getApplicationInfo().loadLabel(
                    context.getPackageManager()).toString();
        }

        return email(context, email, String.format(
                context.getResources().getString(R.string.adu_bug_title), appName,
                version, Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE),
                context.getResources().getString(R.string.adu_bug_desc));
    }

    /**
     * Checks whether the email client exists on the device.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_SENDTO} with scheme
     * {@link MailTo#MAILTO_SCHEME} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the email client exists on the device.
     */
    public static boolean isEmailExists(@Nullable Context context) {
        return DynamicIntentUtils.isActivityResolved(context,
                new Intent(Intent.ACTION_SENDTO, Uri.parse(MailTo.MAILTO_SCHEME)));
    }

    /**
     * Checks whether the GMS (Google Mobile Services) package exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the GMS (Google Mobile Services) package exists on the device.
     */
    public static boolean isGMSExists(@Nullable Context context) {
        return DynamicPackageUtils.isPackageExists(context, PACKAGE_GMS);
    }

    /**
     * Checks whether the Google feedback package exists on the device.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the Google feedback package exists on the device.
     */
    public static boolean isGoogleFeedbackExists(@Nullable Context context) {
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
    public static boolean isFeedbackExists(@Nullable Context context) {
        return isGMSExists(context) || isGoogleFeedbackExists(context);
    }

    /**
     * Ask questions or submit bug report to the developer via Google feedback.
     *
     * <p>It will redirect to {@link #report(Context, String, String)} method if feedback
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
     * @return {@code true} on successful operation.
     *
     * @see ApplicationErrorReport
     */
    public static boolean feedback(@Nullable Context context,
            @Nullable String appName, @NonNull String email, int reportType,
            @Nullable ApplicationErrorReport.CrashInfo crashInfo) {
        if (context == null) {
            return false;
        }

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

        if (DynamicIntentUtils.viewIntent(context, intent)) {
            return true;
        }

        return report(context, appName, email);
    }

    /**
     * Ask questions or submit bug report to the developer via Google feedback.
     *
     * <p>It will redirect to {@link #report(Context, String, String)} method if feedback
     * package is not available on the device.
     *
     * @param context The context to retrieve the resources.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     *
     * @return {@code true} on successful operation.
     *
     * @see ApplicationErrorReport
     * @see ApplicationErrorReport#TYPE_NONE
     */
    public static boolean feedback(@Nullable Context context,
            @Nullable String appName, @NonNull String email) {
        return feedback(context, appName, email, ApplicationErrorReport.TYPE_NONE, null);
    }
}
