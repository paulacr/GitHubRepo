package net.paulacr.githubrepo.repositories;

import android.support.v4.app.Fragment;

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
        void changeFragment(Fragment fragment, String TAG);

    }

    interface Presenter {
        void searchRepositories(int page);
        boolean verifyNetworkConnection();
    }
}
