package net.atlas.projectalpha.api;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "APP_PREFS";
    private static final String AUTH_COOKIE = "AUTH_COOKIE";
    private static final String REFRESH_COOKIE = "REFRESH_COOKIE";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public String getAuthCookie() {
        return pref.getString(AUTH_COOKIE, null);
    }

    public String getRefreshCookie() {
        return pref.getString(REFRESH_COOKIE, null);
    }

    public void setAuthCookie(String cookie) {
        editor.putString(AUTH_COOKIE, cookie);
        editor.commit();
    }

    public void setRefreshCookie(String cookie) {
        editor.putString(REFRESH_COOKIE, cookie);
        editor.commit();
    }

    public boolean isLoggedIn() {
        String refreshCookie = pref.getString(REFRESH_COOKIE, null);

        return refreshCookie != null;
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
