package net.paulacr.githubrepo.ui.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.adapters.RepositoriesAdapter;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.ui.activities.BaseActivity;
import net.paulacr.githubrepo.utils.Constants;
import net.paulacr.githubrepo.utils.OnScrollMoreListener;
import net.paulacr.githubrepo.utils.RecyclerviewDividerItemDecorator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_repositories)
public class RepositoriesActivity extends BaseActivity {

    private ArrayList<Item> items;
    private RepositoriesFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            items = new ArrayList<>();
        } else {
            items = savedInstanceState
                    .getParcelableArrayList(Constants.SAVED_INSTANCE_STATE_REPOSITORIES);
        }
    }

    @AfterViews
    void afterViewsCreated() {
        callRepositoriesFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.SAVED_INSTANCE_STATE_REPOSITORIES, items);
    }

    private void callRepositoriesFragment() {
        if(fragment == null) {
            fragment = RepositoriesFragment_.builder().build();
        }

        fragment.items = items;

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
