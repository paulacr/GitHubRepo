package net.paulacr.githubrepo.network;

import net.paulacr.githubrepo.data.PullRequests;
import net.paulacr.githubrepo.data.Repositories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    String ENDPOINT_REPOSITORY = "search/repositories";
    String ENDPOINT_PULL_REQUEST = "repos/{user}/{repoName}/pulls";

    @GET(ENDPOINT_REPOSITORY)
    Call<Repositories> getRepository(
            @Query("q") String language,
            @Query("sort") String sortType,
            @Query("page") String page);

    @GET(ENDPOINT_PULL_REQUEST)
    Call<List<PullRequests>> getPullRequests(
            @Path("user") String user,
            @Path("repoName") String repoName);
}
