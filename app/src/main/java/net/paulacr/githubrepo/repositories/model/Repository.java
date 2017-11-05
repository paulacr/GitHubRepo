package net.paulacr.githubrepo.repositories.model;

import net.paulacr.githubrepo.repositories.model.Repositories;

public class Repository {

    private static Repository instance;
    private Repositories repositories;
    private long currentPage = 1;

    public static Repository getInstance() {
        if(instance == null) {
          instance = new Repository();
        }
        return instance;
    }

    private Repository(){

    }

    public Repositories getRepositories() {
        return repositories;
    }

    public void setRepositories(Repositories repositories) {
        this.repositories = repositories;
        repositories.setItems(repositories.getItems());
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
}
