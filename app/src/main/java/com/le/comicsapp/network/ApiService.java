package com.le.comicsapp.network;


import com.le.comicsapp.network.data.GetComicsResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("/v1/public/comics")
    public Observable<Response<GetComicsResponse>> getComics(@Query("apikey") String apikey,
                                                             @Query("ts") String ts, @Query("hash") String hash,
                                                             @Query("limit") int limit);

}
