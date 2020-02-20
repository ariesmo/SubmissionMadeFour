package com.example.mysubmissionmadefour.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {

    private int id;
    private String photo;
    private String name;
    private String release;
    private String description;

    public TvShow() {

    }

    public TvShow(int id, String photo, String name, String release, String description) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.release = release;
        this.description = description;
    }

    private TvShow(Parcel in) {
        id = in.readInt();
        photo = in.readString();
        name = in.readString();
        release = in.readString();
        description = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(photo);
        dest.writeString(name);
        dest.writeString(release);
        dest.writeString(description);
    }
}
