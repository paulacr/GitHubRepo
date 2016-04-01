package net.paulacr.githubrepo.repositories;

import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.network.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<Repositories> service = RestApi.getClient().getRepository("language:java", "stars", "1");
        service.enqueue(new Callback<Repositories>() {
            @Override
            public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                view.showRepositories(response.body().getItems());
                view.showLoadingView(false);
            }

            @Override
            public void onFailure(Call<Repositories> call, Throwable t) {
                view.showLoadingView(false);
                view.showError(t.getMessage());

            }
        });
        //onsuccess call
        //view.showRepositories(repo);
        //view.showError(error);
    }

    @Override
    public boolean verifyNetworkConnection() {
        return false;
    }


}
