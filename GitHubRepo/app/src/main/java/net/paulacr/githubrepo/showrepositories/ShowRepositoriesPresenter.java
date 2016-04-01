package net.paulacr.githubrepo.showrepositories;

/**
 * Created by paularosa on 3/31/16.
 */
public class ShowRepositoriesPresenter implements ShowRepositoriesContract.Presenter {

    private ShowRepositoriesContract.View view;

    public ShowRepositoriesPresenter(ShowRepositoriesContract.View view) {
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
