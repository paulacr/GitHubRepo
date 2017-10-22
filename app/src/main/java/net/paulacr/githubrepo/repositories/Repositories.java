package net.paulacr.githubrepo.repositories;

import com.google.gson.annotations.SerializedName;

import net.paulacr.githubrepo.repositories.model.Item;

import java.util.List;

class Repositories {

    @SerializedName("total_count")
    long totalCount;
    @SerializedName("incomplete_results")
    boolean isIncompleteResults;
    @SerializedName("items")
    private List<Item> items;


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return isIncompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        isIncompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
