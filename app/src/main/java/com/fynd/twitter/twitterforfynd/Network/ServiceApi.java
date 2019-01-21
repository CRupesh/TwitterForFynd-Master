package com.fynd.twitter.twitterforfynd.Network;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET(".")
    Call<String> getData();
    @GET("/1.1/statuses/home_timeline.json")
    Call<List<Tweet>> getUserTimeline(@Query("count") int count);

}
