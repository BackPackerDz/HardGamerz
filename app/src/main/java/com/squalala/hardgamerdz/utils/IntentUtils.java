package com.squalala.hardgamerdz.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by Back Packer
 * Date : 17/11/15
 */
public class IntentUtils {

    public static void viewYoutube(Context context, String url) {
        viewWithPackageName(context, url, "com.google.android.youtube");
    }

    public static void viewWithPackageName(Context context, String url, String packageName) {
        try {
            Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (isAppInstalled(context, packageName)) {
                viewIntent.setPackage(packageName);
            }
            context.startActivity(viewIntent);
        } catch (Exception e) {
            Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(viewIntent);
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
