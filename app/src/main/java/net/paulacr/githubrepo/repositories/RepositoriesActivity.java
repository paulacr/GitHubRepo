package net.paulacr.githubrepo.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.repositories.model.Item;
import net.paulacr.githubrepo.repositories.model.Repositories;
import net.paulacr.githubrepo.repositories.model.Repository;
import net.paulacr.githubrepo.base.BaseActivity;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.OnListItemClick;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesActivity extends BaseActivity implements OnScrollMoreListener.OnScrollMore,
        OnListItemClick{

    private RepositoriesAdapter adapter;
    private RecyclerView listRepo;
    private FrameLayout mainContainer;
    private ArrayList<Item> items = new ArrayList<>();

    RepositoriesController controller = new RepositoriesController();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        listRepo = (RecyclerView) findViewById(R.id.listrepo);

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

    private void findViews() {

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

    public void showEmptyList() {
        //TODO COLOCAR EM UM VIEWTYPE
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

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponseRepositories repositories) {
        dismissDialog();
        setRepositoriesList(repositories.getRepositories().getItems());
    }

    @Override
    public void onListItemClicked(Item position) {

    }
}
