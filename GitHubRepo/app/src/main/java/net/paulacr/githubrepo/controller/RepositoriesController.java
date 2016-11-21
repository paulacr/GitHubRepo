package net.paulacr.githubrepo.controller;

import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.network.RestApi;
import net.paulacr.githubrepo.utils.MessageEvents;

import org.androidannotations.annotations.EBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean
public class RepositoriesController extends BaseController {

    public void searchRepositoriesList(String language, String sortType, String page) {

        Call<Repositories> response = RestApi
                .getClient()
                .getRepository(language, sortType, page);

        response.enqueue(new Callback<Repositories>() {
            @Override
            public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                Repositories repositories = response.body();
                post(new MessageEvents.ResponseRepositories(repositories));
            }

            @Override
            public void onFailure(Call<Repositories> call, Throwable t) {

            }
        });
    }
}
