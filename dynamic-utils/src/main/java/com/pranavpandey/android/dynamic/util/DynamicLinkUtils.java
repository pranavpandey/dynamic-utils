/*
 * Copyright 2017-2024 Pranav Pandey
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

package com.pranavpandey.android.dynamic.util;

import android.app.ApplicationErrorReport;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.net.MailTo;

import java.util.ArrayList;

/**
 * Helper class to perform URL, email and feedback related operations.
 */
public class DynamicLinkUtils {

    /**
     * Package name for the Google feedback activity.
     */
    private static final String PACKAGE_FEEDBACK = "com.google.android.feedback";

    /**
     * Package name for the GMS (Google Mobile Services) activity.
     */
    private static final String PACKAGE_GMS = "com.google.android.gms";

    /**
     * Android Market app URL template to open app details on older devices.
     */
    private static final String URL_MARKET = "market://details?id=";

    /**
     * Google Play app URL template to open app details on newer devices.
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
     *  Samsung Galaxy Store app URL template to open app details.
     */
    private static final String URL_SAMSUNG_GALAXY_STORE =
            "https://apps.samsung.com/appquery/appDetail.as?appId=";

    /**
     * Samsung Galaxy Store app search query template to search apps of a publisher.
     */
    private static final String URL_SAMSUNG_GALAXY_STORE_SEARCH_PUB =
            "https://apps.samsung.com/appquery/sellerProductList.as?SellerID=";

    /**
     * Constant for the bookmarks URI.
     */
    private static final Uri URI_BOOKMARKS = Uri.parse("content://browser/bookmarks");

    /**
     * Phone number scheme for the call intent.
     */
    private static final String SCHEME_CALL = "tel:";

    /**
     * Phone number scheme for the SMS URI.
     */
    private static final String SCHEME_SMS = "smsto:";

    /**
     * Phone number scheme for the MMS URI.
     */
    private static final String SCHEME_MMS = "mmsto:";

    /**
     * Intent type for the plain text.
     */
    private static final String TYPE_TEXT = "text/plain";

    /**
     * Title extra for the insert intent.
     */
    private static final String EXTRA_TITLE = "title";

    /**
     * URL extra for the insert intent.
     */
    private static final String EXTRA_URL = "url";

    /**
     * Body extra for the SMS intent.
     */
    private static final String EXTRA_SMS_BODY = "sms_body";

    /**
     * Subject extra for the MMS intent.
     */
    private static final String EXTRA_MMS_SUBJECT = "subject";

    /**
     * Value splitter for the data format.
     */
    private static final String SPLIT_VALUE = ";";

    /**
     * Sub value splitter for the data format.
     */
    private static final String SPLIT_VALUE_SUB = ",";

    /**
     * Sub value alternate splitter for the data format.
     */
    private static final String SPLIT_VALUE_SUB_ALT = " ";

    /**
     * Location query for the Google Maps.
     */
    private static final String FORMAT_GEO_GOOGLE =
            "https://www.google.com/maps/search/?api=1&query="
                    + "%1$s" + SPLIT_VALUE_SUB + "%2$s";

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
     * @param context The context to be used.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param uri The optional content URI to be shared.
     * @param mimeType The optional mime type for the file.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri uri, @Nullable String mimeType,
            @DynamicSdkUtils.DynamicFlavor String flavor) {
        if (context == null) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(TYPE_TEXT);

        if (title == null) {
            title = context.getResources().getString(R.string.adu_share_to);
        } else {
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
        }

        if (message == null) {
            if (DynamicSdkUtils.DynamicFlavor.EXTERNAL.equals(flavor)
                    && DynamicDeviceUtils.isSamsungOneUI()) {
                message = String.format(context.getString(
                        R.string.adu_share_desc_samsung_galaxy_store),
                        context.getApplicationInfo().loadLabel(context.getPackageManager()),
                        context.getPackageName());
            } else {
                message = String.format(context.getString(R.string.adu_share_desc),
                        context.getApplicationInfo().loadLabel(context.getPackageManager()),
                        context.getPackageName());
            }
        }

        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType(mimeType != null ? mimeType : DynamicFileUtils.MIME_ALL);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(Intent.createChooser(intent, title)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            return true;
        }

        return false;
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param image The optional image bitmap URI to be shared.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context,
            @Nullable String title, @Nullable String message, @Nullable Uri image,
            @DynamicSdkUtils.DynamicFlavor String flavor) {
        return share(context, title, message, image, "image/*", flavor);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param image The optional image bitmap URI to be shared.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context, @Nullable String title,
            @Nullable String message, @Nullable Uri image) {
        return share(context, title, message, image, DynamicSdkUtils.DynamicFlavor.GOOGLE);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
     * @param title The application chooser title if more than one apps are available.
     * @param message The default share message which user can modify.
     *                <p>{@code null} to supply app and package name.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SEND
     */
    public static boolean share(@Nullable Context context, @Nullable String title,
            @Nullable String message, @DynamicSdkUtils.DynamicFlavor String flavor) {
        return share(context, title, message, null, flavor);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
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
        return share(context, title, message, DynamicSdkUtils.DynamicFlavor.GOOGLE);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #share(Context, String, String)
     */
    public static boolean shareApp(@Nullable Context context,
            @DynamicSdkUtils.DynamicFlavor String flavor) {
        return share(context, null, null, flavor);
    }

    /**
     * Share application via system default share intent so that user can select from the
     * available apps if more than one apps are available.
     *
     * @param context The context to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #share(Context, String, String)
     */
    public static boolean shareApp(@Nullable Context context) {
        return shareApp(context, DynamicSdkUtils.DynamicFlavor.GOOGLE);
    }

    /**
     * View any URL in the available app or browser. Some URLs will automatically open in their
     * respective apps if installed on the device. Special treatment is applied for the
     * Facebook URLs to open them directly in the app.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param url The web or app link to open.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    public static boolean viewUrl(@Nullable Context context, @Nullable String url) {
        try {
            return DynamicIntentUtils.viewIntent(context,
                    new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param packageName The app package name to build the search query.
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
     * View app on Samsung Galaxy Store.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param packageName The app package name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    public static boolean viewInSamsungGalaxyStore(@Nullable Context context,
            @NonNull String packageName) {
        return viewUrl(context, URL_SAMSUNG_GALAXY_STORE + packageName);
    }

    /**
     * View app on Google Play (or Android Market) or Samsung Galaxy Store if available.
     * <p>Can be used to view app details within the supported stores.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param packageName The app package name to build the search query.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewInGooglePlay(Context, String)
     * @see #viewInSamsungGalaxyStore(Context, String)
     * @see DynamicSdkUtils.DynamicFlavor
     */
    public static boolean viewApp(@Nullable Context context, @NonNull String packageName,
            @DynamicSdkUtils.DynamicFlavor String flavor) {
        if (DynamicSdkUtils.DynamicFlavor.EXTERNAL.equals(flavor)) {
            return viewAppExternal(context, packageName);
        } else if (context == null) {
            return false;
        }

        if (viewInGooglePlay(context, packageName)) {
            return true;
        } else if (DynamicDeviceUtils.isSamsungOneUI()) {
            return viewInSamsungGalaxyStore(context, packageName);
        }
        
        return false;
    }

    /**
     * View app on Google Play (or Android Market) or Samsung Galaxy Store if available.
     * <p>Can be used to view app details within the supported stores.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param packageName The app package name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewApp(Context, String, String)
     * @see DynamicSdkUtils.DynamicFlavor#GOOGLE
     */
    public static boolean viewApp(@Nullable Context context, @NonNull String packageName) {
        return viewApp(context, packageName, DynamicSdkUtils.DynamicFlavor.GOOGLE);
    }

    /**
     * View app on Google Play (or Android Market) or Samsung Galaxy Store if available.
     * External stores will be preferred first.
     * <p>Can be used to view app details within the supported stores.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param packageName The app package name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewInGooglePlay(Context, String)
     * @see #viewInSamsungGalaxyStore(Context, String)
     */
    public static boolean viewAppExternal(@Nullable Context context, @NonNull String packageName) {
        if (context == null) {
            return false;
        }


        if (DynamicDeviceUtils.isSamsungOneUI()
                && viewInSamsungGalaxyStore(context, packageName)) {
            return true;
        } else {
            return viewInGooglePlay(context, packageName);
        }
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Can be used for the quick feedback or rating from the user.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param flavor The product flavor to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewApp(Context, String, String)
     * @see DynamicSdkUtils.DynamicFlavor
     */
    public static boolean rateApp(@Nullable Context context,
            @DynamicSdkUtils.DynamicFlavor String flavor) {
        if (context == null) {
            return false;
        }

        return viewApp(context, context.getPackageName(), flavor);
    }

    /**
     * View app on Google Play or Android Market.
     * <p>Can be used for the quick feedback or rating from the user.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #rateApp(Context, String)
     */
    public static boolean rateApp(@Nullable Context context) {
        return rateApp(context, DynamicSdkUtils.DynamicFlavor.GOOGLE);
    }

    /**
     * View other apps of a Publisher on Google Play or Android Market.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param publisher The publisher name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewUrl(Context, String)
     */
    public static boolean moreApps(@Nullable Context context, @NonNull String publisher) {
        if (viewUrl(context, URL_MARKET_SEARCH_PUB + publisher)) {
            return true;
        }

        return viewUrl(context, URL_GOOGLE_PLAY_SEARCH_PUB + publisher);
    }

    /**
     * View other apps of a Samsung Galaxy Store.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with scheme
     * {@code https or http} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to be used.
     * @param publisher The publisher name to build the search query.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewUrl(Context, String)
     */
    public static boolean moreAppsSamsung(@Nullable Context context, @NonNull String publisher) {
        return viewUrl(context, URL_SAMSUNG_GALAXY_STORE_SEARCH_PUB + publisher);
    }

    /**
     * Send email according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param emails The email ids to be used.
     * @param ccEmails The cc email ids to be used.
     * @param bccEmails The bcc email ids to be used.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean email(@Nullable Context context, @Nullable String[] emails,
            @Nullable String[] ccEmails, @Nullable String[] bccEmails,
            @Nullable String subject, @Nullable String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        if (emails != null) {
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
        }

        if (ccEmails != null) {
            intent.putExtra(Intent.EXTRA_CC, ccEmails);
        }

        if (bccEmails != null) {
            intent.putExtra(Intent.EXTRA_BCC, bccEmails);
        }

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * Send email according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param emails The email ids to be used.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @return {@code true} on successful operation.
     *
     * @see #email(Context, String[], String[], String[], String, String)
     */
    public static boolean email(@Nullable Context context, @Nullable String[] emails,
            @Nullable String subject, @Nullable String text) {
        return email(context, emails, null, null, subject, text);
    }

    /**
     * Send email according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param email The email ids to be used.
     * @param subject The optional email subject.
     * @param text The optional email text.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean email(@Nullable Context context, @Nullable String email,
            @Nullable String subject, @Nullable String text) {
        return email(context, email != null ? new String[] { email } : null, subject, text);
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * @param context The context to be used.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     * @param license {@code true} if the license is available on the devices.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     * @see MailTo#MAILTO_SCHEME
     */
    public static boolean report(@Nullable Context context, @Nullable String appName,
            @Nullable String email, Boolean license) {
        if (context == null) {
            return false;
        }

        String versionName = null;
        int versionCode = 0;
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).versionName;
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).versionCode;
        } catch (Exception ignored) {
        }

        if (appName == null) {
            appName = context.getApplicationInfo().loadLabel(
                    context.getPackageManager()).toString();
        }

        if (license != null) {
            return email(context, email, String.format(context.getResources().getString(
                    R.string.adu_report_title_license), appName, versionName, versionCode,
                    license, Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE),
                    context.getResources().getString(R.string.adu_bug_desc));
        } else {
            return email(context, email, String.format(context.getResources().getString(
                    R.string.adu_report_title), appName, versionName, versionCode,
                    Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE),
                    context.getResources().getString(R.string.adu_bug_desc));
        }
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * <p>Subject of the email will be generated automatically by detecting the manufacturer,
     * device, Android version and the app version along with the supplied app name.
     *
     * @param context The context to be used.
     * @param appName The app name for the email subject.
     *                <p>{@code null} to get it from the supplied context.
     * @param email The email id of the developer.
     *
     * @return {@code true} on successful operation.
     *
     * @see #report(Context, String, String, Boolean)
     */
    public static boolean report(@Nullable Context context,
            @Nullable String appName, @NonNull String email) {
        return report(context, appName, email, null);
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
     * @param context The context to be used.
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
        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);

        if (isGMSExists(context)) {
            intent.setClassName(PACKAGE_GMS, PACKAGE_GMS + ".feedback.FeedbackActivity");
        } else {
            intent.setClassName(PACKAGE_FEEDBACK, PACKAGE_FEEDBACK + ".FeedbackActivity");
        }

        if (DynamicIntentUtils.viewIntent(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
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
     * @param context The context to be used.
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

    /**
     * View or bookmark URL according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param url The URL to be used.
     * @param title The optional title to launch the bookmark intent.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_INSERT
     */
    public static boolean bookmark(@Nullable Context context,
            @Nullable String url, @Nullable String title) {
        if (!DynamicSdkUtils.is23() && (url != null && title != null)) {
            Intent intent = new Intent(Intent.ACTION_INSERT, URI_BOOKMARKS);
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_URL, url);

            if (DynamicIntentUtils.isActivityResolved(context,
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
                context.startActivity(intent);

                return true;
            }
        }

        return viewUrl(context, url);
    }

    /**
     * Launch call intent for the supplied number.
     *
     * @param context The context to be used.
     * @param number The number to be called.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_DIAL
     */
    public static boolean call(@Nullable Context context, @Nullable String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);

        if (number != null) {
            intent.setData(Uri.parse(SCHEME_CALL + number));
            intent.putExtra(Intent.EXTRA_PHONE_NUMBER, number);
        }

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * Launch SMS or MMS intent according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param numbers The numbers to be used.
     * @param subject The optional subject if MMS.
     * @param text The body text to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_SENDTO
     */
    public static boolean smsOrMms(@Nullable Context context, @Nullable String[] numbers,
            @Nullable String subject, @Nullable String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(EXTRA_SMS_BODY, text);

        StringBuilder uri = new StringBuilder();
        if (subject != null) {
            uri.append(SCHEME_MMS);
            intent.putExtra(EXTRA_MMS_SUBJECT, subject);
        } else {
            uri.append(SCHEME_SMS);
        }

        if (numbers != null) {
            for (String number : numbers) {
                uri.append(number).append(SPLIT_VALUE);
            }

            intent.setData(Uri.parse(uri.toString()));
        }

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * Try to view location URL according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param latitude The latitude to be used.
     * @param longitude The longitude to be used.
     * @param altitude The optional altitude to be used.
     * @param query The optional custom query to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #viewUrl(Context, String)
     */
    public static boolean geoLocation(@Nullable Context context, @Nullable String latitude,
            @Nullable String longitude, @Nullable String altitude, @Nullable String query) {
        boolean success = false;

        if (query != null && !TextUtils.isEmpty(query)) {
            try {
                success = viewUrl(context, String.format(query, latitude, longitude, altitude));
            } catch (Exception e) {
                e.printStackTrace();

                success = viewUrl(context, query);
            }
        }

        return success || viewUrl(context, String.format(FORMAT_GEO_GOOGLE, latitude, longitude));
    }

    /**
     * Try to view location URL according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param latitude The latitude to be used.
     * @param longitude The longitude to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see #geoLocation(Context, String, String, String, String)
     */
    public static boolean geoLocation(@Nullable Context context,
            @Nullable String latitude, @Nullable String longitude) {
        return geoLocation(context, latitude, longitude, null, null);
    }

    /**
     * Launch add contact intent according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param names The names to be used.
     * @param nicknames The nicknames to be used.
     * @param pronunciation The pronunciation to be used.
     * @param phones The phones to be used.
     * @param phoneTypes The phone types to be used.
     * @param emails The emails to be used.
     * @param emailTypes The email types to be used.
     * @param addresses The addresses to be used.
     * @param addressTypes The address types to be used.
     * @param birthday The birthday to be used.
     * @param organization The organization to be used.
     * @param title The title to be used.
     * @param websites The websites to be used.
     * @param note The note to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_INSERT_OR_EDIT
     * @see ContactsContract.Contacts#CONTENT_ITEM_TYPE
     */
    public static boolean addContact(@Nullable Context context,
            @Nullable String[] names, @Nullable String[] nicknames,
            @Nullable String pronunciation, @Nullable String[] phones,
            @Nullable String[] phoneTypes, @Nullable String[] emails,
            @Nullable String[] emailTypes, @Nullable String[] addresses,
            @Nullable String[] addressTypes, @Nullable String birthday,
            @Nullable String title, @Nullable String organization,
            @Nullable String[] websites, @Nullable String note) {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        ArrayList<ContentValues> data = new ArrayList<>();

        if (names != null) {
            ContentValues nameValues = new ContentValues();
            String nameKey = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME;
            StringBuilder nameExtra = null;

            for (String name : names) {
                if (nameExtra == null) {
                    nameExtra = new StringBuilder();
                } else {
                    nameExtra.append(SPLIT_VALUE_SUB_ALT);
                }

                nameExtra = nameExtra.append(name);

                if (nameKey != null) {
                    nameValues.put(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                    nameValues.put(nameKey, name);
                }

                if (ContactsContract.CommonDataKinds
                        .StructuredName.GIVEN_NAME.equals(nameKey)) {
                    nameKey = ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME;
                } else if (ContactsContract.CommonDataKinds
                        .StructuredName.MIDDLE_NAME.equals(nameKey)) {
                    nameKey = ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME;
                } else {
                    nameKey = null;
                }
            }

            data.add(nameValues);

            if (nameExtra != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, nameExtra.toString());
            }
        }


        if (pronunciation != null) {
            ContentValues pronunciationValues = new ContentValues();
            pronunciationValues.put(ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            pronunciationValues.put(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_NAME,
                    pronunciation);

            data.add(pronunciationValues);
            intent.putExtra(ContactsContract.Intents.Insert.PHONETIC_NAME, pronunciation);
        }

        if (nicknames != null) {
            for (String nickname : nicknames) {
                ContentValues nicknameValues = new ContentValues();
                nicknameValues.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
                nicknameValues.put(ContactsContract.CommonDataKinds.Nickname.NAME, nickname);
                nicknameValues.put(ContactsContract.CommonDataKinds.Nickname.TYPE,
                        ContactsContract.CommonDataKinds.Nickname.TYPE_DEFAULT);

                data.add(nicknameValues);
            }
        }

        if (phones != null) {
            for (int i = 0; i < phones.length; i++) {
                ContentValues phoneValues = new ContentValues();
                phoneValues.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                phoneValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phones[i]);

                if (phoneTypes != null && i < phoneTypes.length) {
                    phoneValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneTypes[i]);
                } else {
                    phoneValues.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_OTHER);
                }

                data.add(phoneValues);
            }
        }

        if (emails != null) {
            for (int i = 0; i < emails.length; i++) {
                ContentValues emailValues = new ContentValues();
                emailValues.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
                emailValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS, emails[i]);

                if (emailTypes != null && i < emailTypes.length) {
                    emailValues.put(ContactsContract.CommonDataKinds.Email.TYPE, emailTypes[i]);
                } else {
                    emailValues.put(ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_OTHER);
                }

                data.add(emailValues);
            }
        }

        if (addresses != null) {
            for (int i = 0; i < addresses.length; i++) {
                ContentValues addressValues = new ContentValues();
                addressValues.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
                addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET,
                        addresses[i]);

                if (addressTypes != null && i < addressTypes.length) {
                    addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                            addressTypes[i]);
                } else {
                    addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER);
                }

                data.add(addressValues);
            }
        }

        if (birthday != null) {
            ContentValues birthdayValues = new ContentValues();
            birthdayValues.put(ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE);
            birthdayValues.put(ContactsContract.CommonDataKinds.Event.START_DATE, birthday);
            birthdayValues.put(ContactsContract.CommonDataKinds.Event.TYPE,
                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY);

            data.add(birthdayValues);
        }


        if (organization != null) {
            ContentValues organizationValues = new ContentValues();
            organizationValues.put(ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE);

            if (title != null) {
                organizationValues.put(ContactsContract.CommonDataKinds.Organization.TITLE, title);
            }

            organizationValues.put(ContactsContract.CommonDataKinds.Organization.COMPANY,
                    organization);
            organizationValues.put(ContactsContract.CommonDataKinds.Organization.TYPE,
                    ContactsContract.CommonDataKinds.Organization.TYPE_WORK);

            data.add(organizationValues);
        }

        if (websites != null) {
            for (String website : websites) {
                ContentValues websiteValues = new ContentValues();
                websiteValues.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteValues.put(ContactsContract.CommonDataKinds.Website.URL, website);
                websiteValues.put(ContactsContract.CommonDataKinds.Website.TYPE,
                        ContactsContract.CommonDataKinds.Website.TYPE_OTHER);

                data.add(websiteValues);
            }
        }

        if (note != null) {
            ContentValues noteValues = new ContentValues();
            noteValues.put(ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            noteValues.put(ContactsContract.CommonDataKinds.Note.NOTE, note);

            data.add(noteValues);
        }

        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * Launch add calendar event intent according to the supplied parameters.
     *
     * @param context The context to be used.
     * @param title The title (summary) to be used.
     * @param startTime The start time to be used.
     * @param endTime The end time to be used.
     * @param allDay {@code true} if the event is all day.
     * @param organizer The organizer tobe used.
     * @param location The location to be used.
     * @param description The description to be used.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_INSERT
     * @see CalendarContract.Events#CONTENT_URI
     */
    public static boolean addEvent(@Nullable Context context,
            @Nullable String title, long startTime, long endTime, boolean allDay,
            @Nullable String organizer, @Nullable String location, @Nullable String description) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, allDay);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(Intent.EXTRA_EMAIL, organizer);

        if (DynamicIntentUtils.isActivityResolved(context,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))) {
            context.startActivity(intent);

            return true;
        }

        return false;
    }
}
