package net.paulacr.githubrepo.pullrequests;

import net.paulacr.githubrepo.base.BaseController;
import net.paulacr.githubrepo.network.RestApi;
import net.paulacr.githubrepo.utils.MessageEvents;

import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean
public class PullRequestsController extends BaseController {

    public void searchPullRequestsList(String user, String repoName) {

        Call<List<PullRequests>> response = RestApi
                .getClient()
                .getPullRequests(user, repoName);

        response.enqueue(new Callback<List<PullRequests>>() {
            @Override
            public void onResponse(Call<List<PullRequests>> call, Response<List<PullRequests>> response) {
                post(new MessageEvents.ResponsePullRequests(response.body()));
            }

            @Override
            public void onFailure(Call<List<PullRequests>> call, Throwable t) {

            }
        });

    }

}
