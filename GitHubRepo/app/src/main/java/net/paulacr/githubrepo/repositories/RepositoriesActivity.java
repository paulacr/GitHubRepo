package net.paulacr.githubrepo.repositories;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RepositoriesContract.View {

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
        RepositoriesPresenter presenter = new RepositoriesPresenter(this);
        //presenter.searchRepositories();

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

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
    }

    private void setupActionBar() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void createRepositoryList(List<Item> items) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RepositoriesAdapter adapter = new RepositoriesAdapter(this, items);

        repositoryList.setLayoutManager(manager);
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
    public void showRepositories(List<Repositories> repositories) {
        //Create the adapter and pass the repositories list
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoadingView(boolean show) {

    }

    @Override
    public void retry() {

    }
}
