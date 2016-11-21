package net.paulacr.githubrepo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.paulacr.githubrepo.R;

import java.util.Date;

/**
 * Created by paularosa on 4/6/16.
 */
public class SharedPreferencesManager {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void createDateLastRequest(Context context, String date) {

        preferences = context.getSharedPreferences(context.getString(R.string.last_service_call_key),
                Context.MODE_PRIVATE);

        editor = preferences.edit();
        editor.putString(context.getString(R.string.last_service_call_key), date);
        editor.apply();
    }

    public static String readLastDateRequest(Context context) {

        preferences = context.getSharedPreferences(context.getString(R.string.last_service_call_key),
                Context.MODE_PRIVATE);

        return preferences.getString(context.getString(R.string.last_service_call_key),
                context.getString(R.string.no_prefs));
    }
}
