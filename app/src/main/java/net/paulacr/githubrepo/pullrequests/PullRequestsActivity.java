package net.paulacr.githubrepo.pullrequests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.base.BaseActivity;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;
import net.paulacr.githubrepo.utils.MessageEvents;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PullRequestsActivity extends BaseActivity {

    private PullRequestsAdapter adapter;
    private List<PullRequests> pullRequestsItems;

    //**************************************************************************
    // Find Views
    //**************************************************************************


    private RecyclerView listPullRequest;


    private PullRequestsController controller;


    //TODO IMPLEMENTAR EXTRA
//
//    @Extra
//    String user, repoName;

    //**************************************************************************
    // LifeCycle
    //**************************************************************************


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrequest);

        listPullRequest = (RecyclerView) findViewById(R.id.listPullRequest);


        showLoading();
        createPullRequestsList();
//        controller.searchPullRequestsList(user, repoName);
    }

    //**************************************************************************
    // Setup Views
    //**************************************************************************

    private void createPullRequestsList() {

        pullRequestsItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new PullRequestsAdapter(this, pullRequestsItems);

        listPullRequest.setLayoutManager(manager);
        listPullRequest.addItemDecoration(new RecyclerviewDividerItemDecorator(this, RecyclerviewDividerItemDecorator.VERTICAL_LIST));
        listPullRequest.setAdapter(adapter);
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponsePullRequests event) {

        if (event.getPullRequests() != null) {
            pullRequestsItems.addAll(event.getPullRequests());
            adapter.notifyItemRangeChanged(adapter.getItemCount(), pullRequestsItems.size() - 1);
        }

        dismissDialog();
    }
}
