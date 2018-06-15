package net.paulacr.githubrepo.network;

import android.os.Build;

import net.paulacr.githubrepo.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paularosa on 3/31/16.
 */
public class RestApi {

    private static Retrofit retrofit;
    private static GitHubService gitHubService;

    public static GitHubService getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpConfig.getInstance().getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new  Logging().log())
                    .build();

            gitHubService = retrofit.create(GitHubService.class);
        }
        return gitHubService;
    }

}
