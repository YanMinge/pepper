package com.gzsll.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class CookiePreferencesUtil {

    private static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences("webview_cookie", Context.MODE_PRIVATE);
    }

    public static String getStorageItem(Context context, String key) {
        return getSharePreferences(context).getString(key, "");
    }

    public static void setStorageItem(Context context, String key, String value) {
        getSharePreferences(context).edit().putString(key, value).apply();
    }

    public static void removeStorageItem(Context context, String key) {
        getSharePreferences(context).edit().remove(key).apply();
    }

}
