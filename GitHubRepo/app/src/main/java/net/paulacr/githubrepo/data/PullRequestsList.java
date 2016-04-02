package net.paulacr.githubrepo.data;

/**
 * Created by paularosa on 4/2/16.
 */
public class PullRequestsList {

    private static PullRequestsList instance;

    public static PullRequestsList getInstance() {
        if(instance  == null) {
            instance = new PullRequestsList();
        }
        return instance;
    }

    private PullRequests pullRequests;

    public static void setInstance(PullRequestsList instance) {
        PullRequestsList.instance = instance;
    }

    public PullRequests getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(PullRequests pullRequests) {
        this.pullRequests = pullRequests;
    }
}
