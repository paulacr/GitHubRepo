package net.paulacr.githubrepo.repositories;

import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;

import java.util.List;

/**
 * Created by paularosa on 3/31/16.
 */
public interface RepositoriesContract {

    /**
     * The contract for view and presenter
     */

    interface View {

        void showRepositories(Repositories repositories);
        void showError(String error);
        void showLoadingView(boolean show);
        void retry();

    }

    interface Presenter {
        void searchRepositories(int page);
        boolean verifyNetworkConnection();
    }
}
