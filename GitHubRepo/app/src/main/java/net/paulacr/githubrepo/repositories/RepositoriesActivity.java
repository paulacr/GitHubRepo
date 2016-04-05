package net.paulacr.githubrepo.repositories;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.data.RepositoriesList;
import net.paulacr.githubrepo.pullrequests.PullRequestsFragment;
import net.paulacr.githubrepo.utils.NetworkConnectionReceiver;
import net.paulacr.githubrepo.utils.NetworkConnectionVerifier;
import net.paulacr.githubrepo.utils.OnListItemClick;
import net.paulacr.githubrepo.utils.OnReceiverNetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RepositoriesContract.View,
                    OnScrollMore, OnListItemClick, OnReceiverNetworkStatus {

    private List<Item> itemList;
    private RepositoriesAdapter adapter;
    private ProgressDialog progressBar;
    private int initialPage = 1;
    private boolean hasMorePages;
    private RepositoriesPresenter presenter;
    private int page;
    private NetworkConnectionReceiver receiver;
    private boolean hasRequestError = false;

    //**************************************************************************
    // Bind Views
    //**************************************************************************
    @Bind(R.id.listRepo)
    RecyclerView repositoryList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    //**************************************************************************
    // LifeCycle
    //**************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repositories_activity);

        ButterKnife.bind(this);
        setupViews();
        navigationView.setNavigationItemSelectedListener(this);

        //create the presenter or start service for request
        presenter = new RepositoriesPresenter(this);
        showLoadingView(true);
        if(NetworkConnectionVerifier.isConnected(this)) {
            presenter.searchRepositories(initialPage);
        } else {
            showLoadingView(false);
            generateSnackBar("network connection error", "try again");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //**************************************************************************
    // Setup Views
    //**************************************************************************

    private void setupViews() {
        setupToolBar();
        setupActionBar();
        setupNetworkReceiver();
        createRepositoryList();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
    }

    private void setupActionBar() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNetworkReceiver() {
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        receiver = new NetworkConnectionReceiver();
        registerReceiver(receiver, networkFilter);
        receiver.setListener(this);
    }

    private void createRepositoryList() {
        itemList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        OnScrollMoreListener endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        adapter = new RepositoriesAdapter(this, itemList);
        adapter.setClickListener(this);

        repositoryList.setLayoutManager(manager);
        repositoryList.addOnScrollListener(endlessScroll);
        repositoryList.setAdapter(adapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //**************************************************************************
    // Ui methods
    //**************************************************************************

    @Override
    public void showRepositories(Repositories repositories) {

        try {

            RepositoriesList.getInstance().getRepositories().getItems();
            RepositoriesList.getInstance().getRepositories().getItems().addAll(repositories.getItems());

        } catch (NullPointerException e) {
            e.printStackTrace();
            RepositoriesList.getInstance().setRepositories(repositories);
        }

        //Populate the items
        itemList.addAll(repositories.getItems());
        adapter.notifyItemRangeChanged(adapter.getItemCount(), itemList.size() - 1);
        setHasMorePages(repositories);
        hasRequestError = false;
    }

    @Override
    public void showError(String error) {
        generateSnackBar(error, "Retry");
        hasRequestError = true;
    }

    @Override
    public void showLoadingView(boolean show) {
        if(progressBar == null) {
            progressBar = new ProgressDialog(this);
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
        if(NetworkConnectionVerifier.isConnected(this)) {
            presenter.searchRepositories(getPage());
        } else {
            showLoadingView(false);
            generateSnackBar("network connection error", "try again");
        }

    }

    @Override
    public void changeFragment(android.support.v4.app.Fragment fragment, String TAG) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment, TAG);
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    private void generateSnackBar(String message, String action) {
        Snackbar.make(repositoryList, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        }).show();
    }


    //**************************************************************************
    // Pages Control
    //**************************************************************************

    private boolean hasMorePages() {
        return hasMorePages;
    }

    private void setHasMorePages(Repositories repositories) {
        if(repositories.getTotalCount() != repositories.getItems().size() ||
                repositories.getItems() != null) {
            hasMorePages = true;
        } else {
            hasMorePages = false;
        }

    }

    private void setPage(int page) {
        this.page = page;
    }

    private int getPage() {
        return page;
    }

    @Override
    public void onScrollMorePages(int page) {
        if(hasMorePages()) {
            setPage(page);
            presenter.searchRepositories(getPage());
        }
    }

    @Override
    public void onListItemClicked(int position) {

        Item item = RepositoriesList.getInstance().getRepositories().getItems().get(position);

        String userName = item.getOwner().getLogin();
        String repoName = item.getName();

        PullRequestsFragment fragment = PullRequestsFragment.newInstance(userName, repoName);
        String tag = PullRequestsFragment.TAG;

        changeFragment(fragment, tag);
    }

    @Override
    public void onReceive(boolean isConnected) {
        if(hasRequestError) {
            presenter.searchRepositories(getPage());
        }

    }
}
