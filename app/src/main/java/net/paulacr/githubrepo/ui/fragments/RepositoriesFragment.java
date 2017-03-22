package net.paulacr.githubrepo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.adapters.RepositoriesAdapter;
import net.paulacr.githubrepo.controller.RepositoriesController;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.data.Repository;
import net.paulacr.githubrepo.network.RestApi;
import net.paulacr.githubrepo.ui.base.BaseFragment;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_repositories)
public class RepositoriesFragment extends BaseFragment implements OnScrollMoreListener.OnScrollMore {

    private RepositoriesAdapter adapter;

    @ViewById
    RecyclerView listRepo;

    @ViewById
    FrameLayout mainContainer;

    @Bean
    RepositoriesController controller;

    @FragmentArg
    ArrayList<Item> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repositories, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createList();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (items.isEmpty()) {
            showLoading();
            controller.searchRepositoriesList("java", "stars", "1");
        } else {
            refreshList();
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }

    public void showError(String text) {
        Snackbar.make(listRepo, "message", Snackbar.LENGTH_LONG).show();
    }

    public void createList() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        OnScrollMoreListener endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        //adapter = new RepositoriesAdapter(items, this, this);
        listRepo.setLayoutManager(manager);
        listRepo.setAdapter(adapter);

        listRepo.addOnScrollListener(endlessScroll);
        listRepo.addItemDecoration(new RecyclerviewDividerItemDecorator(getActivity(),
                RecyclerviewDividerItemDecorator.VERTICAL_LIST));
    }

    public void showEmptyList() {

    }

    public void setRepositoriesList(List<Item> itemList) {
        items.addAll(itemList);
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    public void refreshList() {
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    @Override
    public void onScrollMorePages(int page) {
        if(canRequestMorePages()) {
            showLoading();
        }

    }

    //TODO botar em método externo
    public boolean canRequestMorePages() {
        long totalCount = Repository.getInstance().getRepositories().getTotalCount();
        long listSize = Repository.getInstance().getRepositories().getItems().size();

        return totalCount != listSize;
    }

    public String getLanguagePreference() {
        return "Java";
    }

    public String getSearchTypePreference() {
        return "stars";
    }

    public String getPage() {
        return "1";
    }

    private void showError() {

    }

    private void saveRepositories(Repositories repositories) {

    }

    public void onEventMainThread(MessageEvents.ResponseRepositories repositories) {
        setRepositoriesList(repositories.getRepositories().getItems());
    }

    @Override
    public void getResponse(Object message) {

    }
}
