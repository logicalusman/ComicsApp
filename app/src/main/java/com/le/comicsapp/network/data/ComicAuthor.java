package com.le.comicsapp.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComicAuthor {

    @SerializedName("available")
    public int numAuthors;
    @SerializedName("items")
    public List<Author> authorList;

    public class Author {
        public String name;
    }

}
