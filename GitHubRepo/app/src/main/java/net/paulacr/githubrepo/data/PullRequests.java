package net.paulacr.githubrepo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paularosa on 3/31/16.
 */
public class PullRequests {

    @SerializedName("id")
    private long id;
    @SerializedName("state")
    private String state;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("created_at")
    private String date;
    @SerializedName("user")
    private User user;

}
