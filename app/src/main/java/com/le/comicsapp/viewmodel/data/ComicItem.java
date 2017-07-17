package com.le.comicsapp.viewmodel.data;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ComicItem implements Parcelable {

    private String thumbnail;
    private String title;
    private String description;
    private int numPages;
    private double price;
    private List<String> authors;

    public ComicItem() {
    }

    private ComicItem(Parcel in) {
        title = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        numPages = in.readInt();
        price = in.readDouble();
        authors = new ArrayList<>();
        in.readStringList(authors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(description);
        out.writeString(thumbnail);
        out.writeInt(numPages);
        out.writeDouble(price);
        if (authors != null) {
            out.writeStringList(authors);
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ComicItem createFromParcel(Parcel in) {
            return new ComicItem(in);
        }

        public ComicItem[] newArray(int size) {
            return new ComicItem[size];
        }
    };


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void addAuthor(@NonNull String author) {

        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


}
