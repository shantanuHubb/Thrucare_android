package com.thrucare.healthcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferenceUtils {
    SharedPreferences prefs;
    Context context;

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static void insertData(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void insertStringSet(Context context, String key, Set<String> value) {
        getPrefs(context).edit().putStringSet(key, value).apply();
    }

    public static String retriveData(Context context, String key) {
        return getPrefs(context).getString(key, "no_data_found");
    }

    public static Set<String> getStringSet(Context context, String key) {
        return getPrefs(context).getStringSet(key, null);
    }

    public static void deleteData(Context context, String key) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean checkContains(Context ctx, String key) {
        boolean isContain = getPrefs(ctx).contains(key);
        return isContain;
    }
}
