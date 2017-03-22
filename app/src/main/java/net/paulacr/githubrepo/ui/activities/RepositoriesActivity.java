package net.paulacr.githubrepo.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.adapters.RepositoriesAdapter;
import net.paulacr.githubrepo.controller.RepositoriesController;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.data.Repository;
import net.paulacr.githubrepo.ui.base.BaseActivity;
import net.paulacr.githubrepo.utils.Constants;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.OnListItemClick;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_repositories)
public class RepositoriesActivity extends BaseActivity implements OnScrollMoreListener.OnScrollMore,
        OnListItemClick{

    private ArrayList<Item> items;
    private RepositoriesAdapter adapter;

    private int currentPage;
    private int currentScrollPosition;

    @ViewById
    RecyclerView listRepo;

    @ViewById
    FrameLayout mainContainer;

    @Bean
    RepositoriesController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            items = new ArrayList<>();
        } else {
            items = savedInstanceState
                    .getParcelableArrayList(Constants.SAVED_INSTANCE_STATE_REPOSITORIES);
            currentScrollPosition = savedInstanceState.getInt("listPosition");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.SAVED_INSTANCE_STATE_REPOSITORIES, items);
        outState.putInt("listPosition", adapter.getItemCount());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (items.isEmpty()) {
            showLoading();
            controller.searchRepositoriesList(getLanguagePreference(), getSearchTypePreference(), getPage());
        } else {
            restoreList(currentScrollPosition);
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
        LinearLayoutManager manager = new LinearLayoutManager(this);

        OnScrollMoreListener endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        adapter = new RepositoriesAdapter(items, this, this);
        listRepo.setLayoutManager(manager);
        listRepo.setAdapter(adapter);

        listRepo.addOnScrollListener(endlessScroll);
        listRepo.addItemDecoration(new RecyclerviewDividerItemDecorator(this,
                RecyclerviewDividerItemDecorator.VERTICAL_LIST));
    }

    private void setRepositoriesList(List<Item> itemList) {
        items.addAll(itemList);
        Repository.getInstance().getRepositories().getItems().addAll(itemList);
        refreshList();
    }

    private void refreshList() {
        createAdapterIfNeeded();
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
        hideLoading();
    }

    private void createAdapterIfNeeded() {
        if(adapter == null) {
            createList();
        }
    }

    private void restoreList(int position) {
        createAdapterIfNeeded();
        listRepo.scrollToPosition(position);
    }

    @Override
    public void onScrollMorePages(int page) {
        if(canRequestMorePages()) {
            showLoading();
            currentPage = page;
            controller.searchRepositoriesList(getLanguagePreference(), getSearchTypePreference(), getPage());
        }

    }

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
        return String.valueOf(currentPage);
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponseRepositories repositories) {
        Repository.getInstance().setRepositories(repositories.getRepositories());
        setRepositoriesList(repositories.getRepositories().getItems());
    }

    @Override
    public void onListItemClicked(Item item) {
        PullRequestsActivity_.intent(this)
                .user(item.getOwner().getLogin())
                .repoName(item.getName())
                .start();
    }
}
