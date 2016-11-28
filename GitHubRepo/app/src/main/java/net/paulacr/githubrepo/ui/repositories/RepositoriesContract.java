package net.paulacr.githubrepo.ui.repositories;


import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.data.Repositories;
import net.paulacr.githubrepo.data.Repository;

import java.util.List;

public interface RepositoriesContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showError(String text);

        void createList();

        void showEmptyList();

        void setRepositoriesList(List<Item> itemList);

        void refreshList();
    }

    interface Presenter {

        void onSearchRepositoriesList();

        void onSaveRepositories(Repositories items);

        void setView(View view);

        String getLanguagePreference();

        String getSearchTypePreference();

        String getPage();

        Repository getRepository();

        boolean canRequestMorePages();

        void onSavePageRequest(long page);

    }
}
