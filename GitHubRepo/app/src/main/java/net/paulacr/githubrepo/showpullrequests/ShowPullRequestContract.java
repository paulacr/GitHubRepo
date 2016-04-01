package net.paulacr.githubrepo.showpullrequests;

import net.paulacr.githubrepo.data.Repositories;

import java.util.List;

/**
 * Created by paularosa on 3/31/16.
 */
public interface ShowPullRequestContract {

    interface View {
        void showPullRequests(List<Repositories> repositories);
        void showError(String error);
        void showLoadingView(boolean show);
        void retry();
    }

    interface Presenter {
        void searchPullRequests(String user, String repositoryName);
        boolean verifyNetworkConnection();
    }
}
