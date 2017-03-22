package net.paulacr.githubrepo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paularosa on 3/31/16.
 */
public class RestApi {

    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;
    private static GitHubService gitHubService;

    public static GitHubService getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gitHubService = retrofit.create(GitHubService.class);
        }
        return gitHubService;
    }

}
