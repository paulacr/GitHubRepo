package net.paulacr.githubrepo.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

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

    protected Item(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        forksCount = in.readLong();
        starsCount = in.readLong();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    public long getForksCount() {
        return forksCount;
    }

    public long getStarsCount() {
        return starsCount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(forksCount);
        dest.writeLong(starsCount);
    }
}
