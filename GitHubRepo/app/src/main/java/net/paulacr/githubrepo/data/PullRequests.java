package net.paulacr.githubrepo.data;

import com.google.gson.annotations.SerializedName;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
