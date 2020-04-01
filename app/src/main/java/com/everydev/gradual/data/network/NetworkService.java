package com.everydev.gradual.data.network;

import com.everydev.gradual.data.network.pojo.FeedItem;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.UserProfile;
import com.everydev.gradual.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public interface NetworkService {
    /**
     * @return Observable feed response
     */
    @GET("feed.json")
    Single<WrapperResponse<List<FeedItem>>> getFeedList();


    @POST("login")
    Single<WrapperResponse<UserProfile>> doLoginApiCall(@Body LoginRequest mRequest);
}
