package net.paulacr.githubrepo.ui.repositories;

import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.data.Repository;
import net.paulacr.githubrepo.network.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepositoriesPresenter implements RepositoriesContract.Presenter {

    private RepositoriesContract.View view;


    @Override
    public void onSearchRepositoriesList() {
        Call<Repositories> service = RestApi.getClient()
                .getRepository(getLanguagePreference(), getSearchTypePreference(),
                        String.valueOf(Repository.getInstance().getCurrentPage()));

        service.enqueue(new Callback<Repositories>() {
            @Override
            public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                view.hideLoading();
                onSaveRepositories(response.body());
            }

            @Override
            public void onFailure(Call<Repositories> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public void onSaveRepositories(Repositories repositories) {
        Repository.getInstance().setRepositories(repositories);
        List<Item> items = Repository.getInstance().getRepositories().getItems();
        view.setRepositoriesList(items);
    }

    @Override
    public void setView(RepositoriesContract.View view) {
        this.view = view;
    }

    @Override
    public String getLanguagePreference() {
        return "Java";
    }

    @Override
    public String getSearchTypePreference() {
        return "stars";
    }

    @Override
    public String getPage() {
        return "1";
    }

    @Override
    public Repository getRepository() {
        return Repository.getInstance();
    }

    @Override
    public boolean canRequestMorePages() {
        long totalCount = Repository.getInstance().getRepositories().getTotalCount();
        long listSize = Repository.getInstance().getRepositories().getItems().size();

        return totalCount != listSize;
    }

    @Override
    public void onSavePageRequest(long page) {
        Repository.getInstance().setCurrentPage(page);
    }
}
