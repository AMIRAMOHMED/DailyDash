package com.example.dailydash.authentication.data.sharedprefernce;

import android.content.Context;

public class SharedPreferences {

    private static final String PREF_NAME = "DailyDashPreferences";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_ID = "userId";
    private final android.content.SharedPreferences preferences;

    public SharedPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLoginState(boolean isLoggedIn) {
        preferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public void setUserId(String userId) {
        preferences.edit().putString(USER_ID, userId).apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserId() {
        return preferences.getString(USER_ID, "");
    }
}