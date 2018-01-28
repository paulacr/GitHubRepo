package net.paulacr.githubrepo.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.base.BaseActivity;
import net.paulacr.githubrepo.pullrequests.PullRequestsActivity;
import net.paulacr.githubrepo.repositories.model.Item;
import net.paulacr.githubrepo.repositories.model.Repository;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.OnListItemClick;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesActivity extends BaseActivity implements OnScrollMoreListener.OnScrollMore,
        OnListItemClick {

    private static final String EXTRA_LIST_ITEMS = "extra_list_items";

    private RepositoriesAdapter adapter;
    private RecyclerView listRepo;
    LinearLayoutManager manager;

    private RepositoriesController controller = new RepositoriesController();
    private OnScrollMoreListener endlessScroll;
    private String currentPage;
    private ArrayList<Item> items;
    private int listPositionToRestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        findViews();

        if (hasExtraListItems(savedInstanceState))
            getRestoredItemsList(savedInstanceState);
            //todo must return to the previews position list (before the rotation)
        else
            createNewItemsList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_LIST_ITEMS, items);
        outState.putInt("list_position", getLastVisiblePosition());
    }

    @Override
    public void onResume() {
        super.onResume();

        createList();
        if (items.isEmpty()) {
            showLoading();
            controller.searchRepositoriesList("java", "stars", "1");
        } else {
            refreshList();
        }
    }

    private int getLastVisiblePosition() {
        return ((LinearLayoutManager) listRepo.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
    }

    private void createNewItemsList() {
        items = new ArrayList<>();
    }

    private void findViews() {
        listRepo = (RecyclerView) findViewById(R.id.listrepo);
    }

    private void getRestoredItemsList(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        items = savedInstanceState.getParcelableArrayList(EXTRA_LIST_ITEMS);
        listPositionToRestore = savedInstanceState.getInt("list_position");

        moveCursorToPosition(listPositionToRestore);
    }

    private void moveCursorToPosition(int listPositionToRestore) {
        createList();
        manager.scrollToPositionWithOffset(listPositionToRestore, 0 );
    }

    private boolean hasExtraListItems(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }
        return !savedInstanceState.isEmpty() &&
                !savedInstanceState.getParcelableArrayList(EXTRA_LIST_ITEMS).isEmpty() &&
                savedInstanceState.getParcelableArrayList(EXTRA_LIST_ITEMS).size() != 0;
    }

    public void showError(String message) {
        Snackbar.make(listRepo, "message", Snackbar.LENGTH_LONG)
                .setAction(R.string.action_try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo retry request
                    }
                }).show();
    }

    private void performRetry() {
        controller.searchRepositoriesList("java", "stars", getCurrentPage());
    }

    public void createList() {
        manager = new LinearLayoutManager(this);

        endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        adapter = new RepositoriesAdapter(items, this, this);
        listRepo.setLayoutManager(manager);
        listRepo.setAdapter(adapter);

        listRepo.addOnScrollListener(endlessScroll);
        listRepo.addItemDecoration(new RecyclerviewDividerItemDecorator(this,
                RecyclerviewDividerItemDecorator.VERTICAL_LIST));
    }

    public void showEmptyList() {
        //TODO COLOCAR EM UM VIEWTYPE
    }

    public void setRepositoriesList(List<Item> itemList) {
        items.addAll(Repository.getInstance().getRepositories().getItems());
        Log.i("log items", "->" + Repository.getInstance().getRepositories().getItems());
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    public void refreshList() {
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    @Override
    public void onScrollMorePages(int page) {
        if (canRequestMorePages()) {
            showLoading();
            controller.searchRepositoriesList("java", "stars", String.valueOf(page));
            setCurrentPage(String.valueOf(page));
        }
    }

    public boolean canRequestMorePages() {
        long totalCount = Repository.getInstance().getRepositories().getTotalCount();
        long listSize = Repository.getInstance().getRepositories().getItems().size();

        return totalCount != listSize;
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponseRepositories repositories) {
        dismissDialog();
        Repository.getInstance().setRepositories(repositories.getRepositories());
        setRepositoriesList(repositories.getRepositories().getItems());
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.OnRequestError error) {
        dismissDialog();
        showError(error.getMessage());
        endlessScroll.invalidatePageCount();
    }

    @Override
    public void onListItemClicked(Item item) {
        //open the pull request for each item list
        PullRequestsActivity.newIntent(this,
                item.getOwner().getLogin(),
                item.getName());

    }

    private void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    private String getCurrentPage() {
        return currentPage;
    }
}
