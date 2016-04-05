package net.paulacr.githubrepo.data;

/**
 * Created by paularosa on 4/2/16.
 */
public class RepositoriesList {

    private static RepositoriesList instance;

    public static RepositoriesList getInstance() {
        if(instance  == null) {
            instance = new RepositoriesList();
        }
        return instance;
    }

    private Repositories repositories;

    public static void setInstance(RepositoriesList instance) {
        RepositoriesList.instance = instance;
    }

    public Repositories getRepositories() {
        return repositories;
    }

    public void setRepositories(Repositories repositories) {
        this.repositories = repositories;
    }
}
