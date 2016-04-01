package net.paulacr.githubrepo.pullrequests;

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
