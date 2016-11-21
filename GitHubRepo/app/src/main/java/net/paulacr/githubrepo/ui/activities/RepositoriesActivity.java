package net.paulacr.githubrepo.ui.activities;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.controller.RepositoriesController;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.network.GitHubService;
import net.paulacr.githubrepo.repositories.OnScrollMore;
import net.paulacr.githubrepo.repositories.OnScrollMoreListener;
import net.paulacr.githubrepo.adapters.RepositoriesAdapter;
import net.paulacr.githubrepo.utils.DividerItemDecorator;
import net.paulacr.githubrepo.utils.MessageEvents;
import net.paulacr.githubrepo.utils.NetworkConnectionReceiver;
import net.paulacr.githubrepo.utils.NetworkConnectionVerifier;
import net.paulacr.githubrepo.utils.OnListItemClick;
import net.paulacr.githubrepo.utils.OnReceiverNetworkStatus;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

@EActivity(R.layout.activity_repositories)
public class RepositoriesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnScrollMore, OnListItemClick, OnReceiverNetworkStatus {

    private static final String REPOSITORIES_LIST = "repositories_list";

    private ArrayList<Item> itemList;
    private RepositoriesAdapter adapter;
    private ProgressDialog progressBar;
    private boolean hasMorePages;
    private int page;
    private NetworkConnectionReceiver receiver;
    private boolean hasRequestError = false;
    private GitHubService service;
    Call<Repositories> repositoriesResponse;

    //**************************************************************************
    // Find Views
    //**************************************************************************
    @ViewById(R.id.listRepo)
    RecyclerView repositoryList;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @Bean
    RepositoriesController controller;

    //**************************************************************************
    // LifeCycle
    //**************************************************************************


    @Override
    protected void onResume() {
        super.onResume();

        if(itemList == null || itemList.isEmpty()) {
            searchRepositoriesList();
        } else {
            createRepositoryList();
            adapter.notifyItemRangeChanged(adapter.getItemCount(), itemList.size() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            itemList = savedInstanceState.getParcelableArrayList(REPOSITORIES_LIST);
        } else {
            itemList = new ArrayList<>();
        }
        //navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupViews();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(REPOSITORIES_LIST, itemList);
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        OnScrollMoreListener endlessScroll = new OnScrollMoreListener(manager);
        endlessScroll.setListener(this);

        adapter = new RepositoriesAdapter(this, itemList);
        adapter.setClickListener(this);

        repositoryList.setLayoutManager(manager);
        repositoryList.addOnScrollListener(endlessScroll);
        repositoryList.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
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

//    @Override
//    public void retry() {
//        if(NetworkConnectionVerifier.isConnected(this)) {
//            presenter.searchRepositories(getPage());
//        } else {
//            showLoadingView(false);
//            generateSnackBar(getString(R.string.error_network_connection),getString(R.string.action_try_again));
//        }
//
//    }

    private void generateSnackBar(String message, String action) {
        Snackbar.make(repositoryList, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //retry();
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
        if (repositories.getTotalCount() != repositories.getItems().size() ||
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
        if (hasMorePages()) {
            setPage(page);
            showProgressDialog();
            controller.searchRepositoriesList("Java", "stars", String.valueOf(getPage()));
        }
    }

    @Override
    public void onListItemClicked(int position) {

        Item item = itemList.get(position);

        String userName = item.getOwner().getLogin();
        String repoName = item.getName();

        PullRequestsActivity_
                .intent(this)
                .user(userName)
                .repoName(repoName)
                .start();
    }

    @Override
    public void onReceive(boolean isConnected) {
        if (hasRequestError) {
        }
    }

    @Subscribe
    public void onEventMainThread(MessageEvents.ResponseRepositories event) {

        if (event.getRepositories().getItems() != null) {
            itemList.addAll(event.getRepositories().getItems());
            adapter.notifyItemRangeChanged(adapter.getItemCount(), itemList.size() - 1);
            setHasMorePages(event.getRepositories());
            hasRequestError = false;
        }
        dismissProgressDialog();
    }

    private void searchRepositoriesList() {
        if (NetworkConnectionVerifier.isConnected(this)) {
            showProgressDialog();
            controller.searchRepositoriesList("Java", "starts", String.valueOf(getPage()));
        }
    }


}
