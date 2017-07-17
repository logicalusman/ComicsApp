package com.le.comicsapp.viewmodel.data;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ComicDetails {

    private String thumbnail;
    private String title;
    private String description;
    private int numPages;
    private double price;
    private List<String> authors;

    public ComicDetails() {
    }

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
