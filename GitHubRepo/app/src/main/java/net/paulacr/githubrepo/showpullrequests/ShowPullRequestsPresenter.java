package net.paulacr.githubrepo.showpullrequests;

/**
 * Created by paularosa on 3/31/16.
 */
public class ShowPullRequestsPresenter implements ShowPullRequestContract.Presenter {

    private ShowPullRequestContract.View view;

    public ShowPullRequestsPresenter(ShowPullRequestContract.View view) {
        this.view = view;
    }

    @Override
    public void searchPullRequests(String user, String repositoryName) {
        //call for retrofit

        //onsuccess call
        //view.showPullRequests(result);
        //view.showError(error);
    }

    @Override
    public boolean verifyNetworkConnection() {
        return false;
    }
}
