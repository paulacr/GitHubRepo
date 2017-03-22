package net.paulacr.githubrepo.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.adapters.PullRequestsAdapter;
import net.paulacr.githubrepo.controller.PullRequestsController;
import net.paulacr.githubrepo.data.PullRequests;
import net.paulacr.githubrepo.ui.base.BaseActivity;
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

@EActivity(R.layout.activity_pullrequest)
public class PullRequestsActivity extends BaseActivity {

    private PullRequestsAdapter adapter;
    private List<PullRequests> pullRequestsItems;

    //**************************************************************************
    // Find Views
    //**************************************************************************

    @ViewById(R.id.listPullRequest)
    RecyclerView listPullRequest;

    @Bean
    PullRequestsController controller;

    @Extra
    String user, repoName;

    //**************************************************************************
    // LifeCycle
    //**************************************************************************

    @AfterViews void afterViewsCreated() {
        showProgressDialog();
        createPullRequestsList();
        controller.searchPullRequestsList(user, repoName);
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

        dismissProgressDialog();
    }
}
