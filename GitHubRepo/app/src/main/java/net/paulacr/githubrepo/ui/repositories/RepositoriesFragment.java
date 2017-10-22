package net.paulacr.githubrepo.ui.repositories;

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
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.base.BaseFragment;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_repositories)
public class RepositoriesFragment extends BaseFragment implements RepositoriesContract.View,
        OnScrollMoreListener.OnScrollMore {

    private RepositoriesContract.Presenter presenter;
    private RepositoriesAdapter adapter;

    @ViewById
    RecyclerView listRepo;

    @ViewById
    FrameLayout mainContainer;

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

        presenter = new RepositoriesPresenter();
        presenter.setView(this);
        createList();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (items.isEmpty()) {
            showLoading();
            presenter.onSearchRepositoriesList();
        } else {
            refreshList();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showError(String text) {
        Snackbar.make(listRepo, "message", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void createList() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        OnScrollMoreListener endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        adapter = new RepositoriesAdapter(getActivity(), items);
        listRepo.setLayoutManager(manager);
        listRepo.setAdapter(adapter);

        listRepo.addOnScrollListener(endlessScroll);
        listRepo.addItemDecoration(new RecyclerviewDividerItemDecorator(getActivity(),
                RecyclerviewDividerItemDecorator.VERTICAL_LIST));
    }

    @Override
    public void showEmptyList() {

    }

    @Override
    public void setRepositoriesList(List<Item> itemList) {
        items.addAll(itemList);
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    @Override
    public void refreshList() {
        adapter.notifyItemRangeChanged(adapter.getItemCount(), items.size() - 1);
    }

    @Override
    public void onScrollMorePages(int page) {
        if(presenter.canRequestMorePages()) {
            showLoading();
            presenter.onSearchRepositoriesList();
            presenter.onSavePageRequest(page);
        }

    }
}
