package com.le.comicsapp.network.data;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetComicsResponse {

    public int code;
    public String status;
    @SerializedName("data")
    public ResponseData responseData;


    public class ResponseData {

        @SerializedName("results")
        public List<ComicData> comicDataList;
    }

}


