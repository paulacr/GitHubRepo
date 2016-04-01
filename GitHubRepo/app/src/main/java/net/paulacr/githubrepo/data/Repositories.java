package net.paulacr.githubrepo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paularosa on 3/31/16.
 */
public class Repositories {

    @SerializedName("total_count")
    private long totalCount;
    @SerializedName("incomplete_results")
    boolean isIncompleteResults;
    @SerializedName("items")
    private Items items;


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

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
