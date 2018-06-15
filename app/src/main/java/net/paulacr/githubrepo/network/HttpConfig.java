package net.paulacr.githubrepo.network;

import android.provider.CalendarContract;

import java.net.URL;

/**
 * Created by paularosa on 24/02/18.
 */

public final class HttpConfig {

    private static HttpConfig instance;
    private String url = "https://api.github.com/";

    private HttpConfig() {

    }

    public static HttpConfig getInstance() {
        if(instance == null) {
            instance = new HttpConfig();
        }
        return instance;
    }

    public String getBaseUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
