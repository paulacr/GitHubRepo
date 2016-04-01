package net.paulacr.githubrepo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paularosa on 3/31/16.
 */
public class Item {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("forks_count")
    private long forksCount;
    @SerializedName("stargazers_count")
    private long starsCount;
    @SerializedName("owner")
    private Owner owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getForksCount() {
        return forksCount;
    }

    public void setForksCount(long forksCount) {
        this.forksCount = forksCount;
    }

    public long getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(long starsCount) {
        this.starsCount = starsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
