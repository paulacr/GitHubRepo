package net.paulacr.githubrepo.pullrequests;

import net.paulacr.githubrepo.data.PullRequests;
import net.paulacr.githubrepo.network.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paularosa on 3/31/16.
 */
public class PullRequestsPresenter implements PullRequestContract.Presenter {

    private PullRequestContract.View view;

    public PullRequestsPresenter(PullRequestContract.View view) {
        this.view = view;
    }

    @Override
    public void searchPullRequests(String user, String repositoryName) {
        view.showLoadingView(true);
        Call<List<PullRequests>> service = RestApi.getClient().getPullRequests(user, repositoryName);
        service.enqueue(new Callback<List<PullRequests>>() {
            @Override
            public void onResponse(Call<List<PullRequests>> call, Response<List<PullRequests>> response) {
                view.showPullRequests(response.body());
                view.showLoadingView(false);
            }

            @Override
            public void onFailure(Call<List<PullRequests>> call, Throwable t) {
                view.showLoadingView(false);
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public boolean verifyNetworkConnection() {
        return false;
    }
}
