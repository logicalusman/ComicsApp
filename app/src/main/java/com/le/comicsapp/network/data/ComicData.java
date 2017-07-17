package com.le.comicsapp.network.data;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComicData {

    public int id;
    public String title;
    public String description;
    public List<ComicPrice> prices;
    public ComicThumbnail thumbnail;
    @SerializedName("creators")
    public ComicAuthor author;


}
