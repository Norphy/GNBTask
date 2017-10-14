package com.example.android.gnbtask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 10/13/2017.
 */

public interface GNBApi {
    @GET("explore")
    Call<List<ExploreSight>> getExploreSights(
            @Query("count") Integer count,
            @Query("from") Integer from
    );
}
