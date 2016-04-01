package net.paulacr.githubrepo.showrepositories;

import net.paulacr.githubrepo.data.Repositories;

import java.util.List;

/**
 * Created by paularosa on 3/31/16.
 */
public interface ShowRepositoriesContract {

    /**
     * The contract for view and presenter
     */

    interface View {

        void showRepositories(List<Repositories> repositories);
        void showError(String error);
        void showLoadingView(boolean show);
        void retry();

    }

    interface Presenter {
        void searchRepositories(String language, int page);
        boolean verifyNetworkConnection();
    }
}
