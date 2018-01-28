package net.paulacr.githubrepo.pullrequests;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import net.paulacr.githubrepo.repositories.model.User;

public class PullRequests implements Parcelable{

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

    protected PullRequests(Parcel in) {
        id = in.readLong();
        state = in.readString();
        title = in.readString();
        body = in.readString();
        date = in.readString();
    }

    public static final Creator<PullRequests> CREATOR = new Creator<PullRequests>() {
        @Override
        public PullRequests createFromParcel(Parcel in) {
            return new PullRequests(in);
        }

        @Override
        public PullRequests[] newArray(int size) {
            return new PullRequests[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(state);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(date);
    }
}
