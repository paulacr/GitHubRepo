package net.paulacr.githubrepo.network;

import android.app.Application;
import android.content.Context;

/**
 * Created by paularosa on 24/02/18.
 */

public class BaseApplication  extends Application {

    public String getBaseUrl(Context context) {
        return "https://api.github.com/";
    }
}
