package net.paulacr.githubrepo.pullrequests;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.PullRequests;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.utils.DividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by paularosa on 3/31/16.
 */
public class PullRequestsFragment extends Fragment implements PullRequestContract.View {

    public static final String USER_KEY = "user_key";
    public static final String REPO_KEY = "repo_key";
    public static final String TAG = "pullRequestsFragmentTag";

    private PullRequestsAdapter adapter;
    private List<PullRequests> pullRequestsItems;
    private PullRequestsPresenter presenter;
    private ProgressDialog progressBar;

    //**************************************************************************
    // Bind Views
    //**************************************************************************

    @Bind(R.id.listPullRequest)
    RecyclerView listPullRequest;


    public static PullRequestsFragment newInstance(String userName, String repositoryName) {

        PullRequestsFragment fragment = new PullRequestsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(USER_KEY, userName);
        bundle.putString(REPO_KEY, repositoryName);

        fragment.setArguments(bundle);

        return fragment;
    }

    //**************************************************************************
    // LifeCycle
    //**************************************************************************

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_pullrequest, container, false);
        ButterKnife.bind(this, view);
        presenter = new PullRequestsPresenter(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        createPullRequestsList();
        showLoadingView(true);
        presenter.searchPullRequests(getUsername(), getRepositoryName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //**************************************************************************
    // Bundle Arguments
    //**************************************************************************

    private String getBundleArguments(String key) {
        return getArguments().getString(key);
    }

    private String getUsername() {
        return getBundleArguments(USER_KEY);
    }

    private String getRepositoryName() {
        return getBundleArguments(REPO_KEY);
    }

    //**************************************************************************
    // Setup Views
    //**************************************************************************

    private void createPullRequestsList() {

        pullRequestsItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new PullRequestsAdapter(getActivity(), pullRequestsItems);

        listPullRequest.setLayoutManager(manager);
        listPullRequest.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator.VERTICAL_LIST));
        listPullRequest.setAdapter(adapter);
    }

    @Override
    public void showPullRequests(List<PullRequests> pullRequests) {
        //Populate the items
        pullRequestsItems.addAll(pullRequests);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoadingView(boolean show) {
        if(progressBar == null) {
            progressBar = new ProgressDialog(getActivity());
        }

        if(show) {
            progressBar.setTitle("Please Wait");
            progressBar.setMessage("Searching for repositories");
            progressBar.show();
        } else {
            progressBar.dismiss();
        }
    }

    @Override
    public void retry() {

    }
}
