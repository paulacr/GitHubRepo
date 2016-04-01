package net.paulacr.githubrepo.repositories;

/**
 * Created by paularosa on 3/31/16.
 */
public class RepositoriesPresenter implements RepositoriesContract.Presenter {

    private RepositoriesContract.View view;

    public RepositoriesPresenter(RepositoriesContract.View view) {
        this.view = view;
    }


    @Override
    public void searchRepositories(String language, int page) {
        //call for retrofit

        //onsuccess call
        //view.showRepositories(repo);
        //view.showError(error);
    }

    @Override
    public boolean verifyNetworkConnection() {
        return false;
    }


}
