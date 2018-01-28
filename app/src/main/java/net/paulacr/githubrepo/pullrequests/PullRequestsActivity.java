package net.paulacr.githubrepo.pullrequests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.base.BaseActivity;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PullRequestsActivity extends BaseActivity {

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_REPO_NAME = "extra_repo_name";
    private static final String EXTRA_PULL_REQUEST_ITEMS = "extra_pull_request_items";

    private String user;
    private String repoName;
    private List<PullRequests> pullRequestsList;
    private PullRequestsAdapter adapter;

    //**************************************************************************
    // Find Views
    //**************************************************************************


    private RecyclerView listPullRequest;


    private PullRequestsController controller;

    //**************************************************************************
    // LifeCycle
    //**************************************************************************


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrequest);

        listPullRequest = (RecyclerView) findViewById(R.id.listPullRequest);
        restoreExtras(savedInstanceState);



        if (hasExtraListItems(savedInstanceState)) {
            getRestoredItemsList(savedInstanceState);
        }
        else
            pullRequestsList = new ArrayList<>();

        showLoading();
        createPullRequestsList();
        populateData(pullRequestsList);
    }

    private void populateData(List<PullRequests> pullRequestsList) {
        if(pullRequestsList.isEmpty())
            fetchPullRequestList();
        else
            updateAdapter(pullRequestsList);

        dismissDialog();
    }

    private void getRestoredItemsList(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        pullRequestsList = savedInstanceState.getParcelableArrayList(EXTRA_PULL_REQUEST_ITEMS);

    }

    private boolean hasExtraListItems(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }
        return !savedInstanceState.isEmpty() &&
                !savedInstanceState.getParcelableArrayList(EXTRA_PULL_REQUEST_ITEMS).isEmpty() &&
                savedInstanceState.getParcelableArrayList(EXTRA_PULL_REQUEST_ITEMS).size() != 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<PullRequests> items = new ArrayList<>(pullRequestsList);
        outState.putParcelableArrayList(EXTRA_PULL_REQUEST_ITEMS, items);
    }


    private void fetchPullRequestList() {
        controller.searchPullRequestsList(user, repoName);
    }

    private void restoreExtras(Bundle savedInstantState) {
        user = savedInstantState.getString(EXTRA_USER);
        repoName = savedInstantState.getString(EXTRA_REPO_NAME);
    }

    //**************************************************************************
    // Setup Views
    //**************************************************************************

    private void createPullRequestsList() {

        pullRequestsList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new PullRequestsAdapter(this, pullRequestsList);

        listPullRequest.setLayoutManager(manager);
        listPullRequest.addItemDecoration(new RecyclerviewDividerItemDecorator(this, RecyclerviewDividerItemDecorator.VERTICAL_LIST));
        listPullRequest.setAdapter(adapter);
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponsePullRequests event) {

        if (event.getPullRequests() != null)
            updateAdapter(event.getPullRequests());

        dismissDialog();
    }

    private void updateAdapter(List<PullRequests> pullRequests) {
        pullRequestsList.addAll(pullRequests);
        adapter.notifyItemRangeChanged(adapter.getItemCount(), pullRequestsList.size() - 1);
    }

    public static void newIntent(Context context, String extraUser, String extraRepoName) {
        Intent intent = new Intent(context, PullRequestsActivity.class);
        intent.putExtra(EXTRA_USER, extraUser);
        intent.putExtra(EXTRA_REPO_NAME, extraRepoName);
        context.startActivity(intent);
    }
}
