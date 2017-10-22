package net.paulacr.githubrepo.utils;

import net.paulacr.githubrepo.pullrequests.PullRequests;
import net.paulacr.githubrepo.repositories.model.Repositories;

import java.util.List;

public class MessageEvents {

    public static class ResponseRepositories {
        private Repositories repositories;

        public ResponseRepositories(Repositories repositories) {
            this.repositories = repositories;
        }

        public Repositories getRepositories() {
            return repositories;
        }
    }

    public static class ResponsePullRequests {
        private List<PullRequests> pullRequests;

        public ResponsePullRequests(List<PullRequests> pullRequests) {
            this.pullRequests = pullRequests;
        }

        public List<PullRequests> getPullRequests() {
            return pullRequests;
        }
    }


}
