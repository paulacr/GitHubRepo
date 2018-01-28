package net.paulacr.githubrepo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by paularosa on 28/01/18.
 */

public class Logging {

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    public OkHttpClient log() {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(httpLoggingInterceptor);

        return okHttpClient.build();
    }
}
