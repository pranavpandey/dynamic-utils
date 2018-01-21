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

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

/**
 * A collection of functions to perform various operations on the URL
 * or to generate intents.
 */
public class DynamicLinkUtils {

    /**
     * Facebook web url.
     */
    private static final String URL_FACEBOOK = "https://www.facebook";

    /**
     * Facebook app url template to open link in the app.
     */
    private static final String URL_FACEBOOK_APP = "fb://facewebmodal/f?href=";

    /**
     * Android Market app url template to open app details on older devices.
     */
    private static final String URL_MARKET = "market://details?id=";

    /**
     * Play Store app url template to open app details on newer devices.
     */
    private static final String URL_PLAY_STORE =
            "http://play.google.com/store/apps/details?id=";

    /**
     * Android Market app search query template to search apps of a publisher.
     */
    private static final String URL_MARKET_SEARCH_PUB = "market://search?q=pub:";

    /**
     * Play Store app search query template to search apps of a publisher.
     */
    private static final String URL_GOOGLE_PLAY_SEARCH_PUB =
            "http://play.google.com/store/search?q=pub:";

    /**
     * Send intent URI to open the compose mail screen.
     */
    private static final String MAIL_TO = "mailto:";

    /**
     * Share application via system default share intent so that user can
     * select from the available apps if more than one apps are available.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to retrieve the resources. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     * @param title Application chooser title if more than one apps are available.
     * @param message Default share message which user can modify.
     *                If it is <code>null</code>; app and package name will be
     *                supplied.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  android.content.Intent#ACTION_SEND
     */
    public static void shareApp(@NonNull Context context,
                                @Nullable String title, @Nullable String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        if (title == null) {
            title = context.getResources().getString(R.string.adu_share_to);
        }

        if (message == null) {
            message = String.format(Locale.getDefault(),
                    context.getString(R.string.adu_share_desc),
                    context.getApplicationInfo().loadLabel(context.getPackageManager()),
                    context.getPackageName());
        }

        sendIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(sendIntent, title));
        } catch (Exception ignored) { }
    }

    /**
     * Share application via system default share intent so that user can
     * select from the available apps if more than one apps are available.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to retrieve the resources. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  #shareApp(Context, String, String)
     */
    public static void shareApp(@NonNull Context context) {
        shareApp(context, null, null);
    }

    /**
     * View app in the Play Store or Android Market.
     *
     * @param context Context to start the activity. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     * @param packageName Application package name to build the search query.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  android.content.Intent#ACTION_VIEW
     */
    public static void viewInGooglePlay(
            @NonNull Context context, @NonNull String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(URL_MARKET + packageName)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(URL_PLAY_STORE + packageName)));
        }
    }

    /**
     * View app in the Play Store or Android Market. Can be used for the
     * quick feedback or rating from the user.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to get the package name. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  #viewInGooglePlay(Context, String)
     */
    public static void rateApp(@NonNull Context context) {
        viewInGooglePlay(context, context.getPackageName());
    }

    /**
     * View other apps of a Publisher in the Play Store or Android Market.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to start the activity. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     * @param publisher Publisher name to build the search query.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  android.content.Intent#ACTION_VIEW
     */
    public static void moreApps(@NonNull Context context, @NonNull String publisher) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(URL_MARKET_SEARCH_PUB + publisher)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(URL_GOOGLE_PLAY_SEARCH_PUB + publisher)));
        }
    }

    /**
     * View any URL in the available app or browser. Some URLs will automatically
     * open in their respective apps if installed on the the device. Special
     * treatment is applied for the Facebook URLs to open them directly in the app.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to start the activity. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     * @param url Web or app link to open.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  android.content.Intent#ACTION_VIEW
     * @see #URL_FACEBOOK_APP
     */
    public static void viewUrl(@NonNull Context context, @NonNull String url) {
        if (url.contains(URL_FACEBOOK)) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(URL_FACEBOOK_APP + url)));
            } catch (Exception e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    /**
     * Ask questions or submit bug report to the developer via email.
     * Subject of the email will be generated automatically by detecting the
     * manufacturer, device, Android version and the app version along with
     * the supplied app name.
     *
     * <p>This method throws {@link ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.</p>
     *
     * @param context Context to retrieve the resources. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     * @param appName App name in the email subject. If it is <code>null</code>;
     *                it will try to get it from the supplied context.
     * @param email Email id of the developer.
     *
     * @throws ActivityNotFoundException If no activity is found.
     *
     * @see  android.content.Intent#ACTION_SENDTO
     * @see  #MAIL_TO
     */
    public static void report(@NonNull Context context,
                              @Nullable String appName, @NonNull String email) {
        String version = null;

        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).versionName;

            if (appName == null) {
                appName = context.getApplicationInfo().loadLabel(
                        context.getPackageManager()).toString();
            }
        } catch (PackageManager.NameNotFoundException ignored) { }

        String subject = String.format(
                context.getResources().getString(R.string.adu_bug_title), appName,
                version, Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(MAIL_TO + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,
                context.getResources().getString(R.string.adu_bug_desc));

        try {
            context.startActivity(intent);
        } catch (Exception ignored) { }
    }
}
