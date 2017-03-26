package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by bvu on 3/25/17.
 */
public interface UserService {
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("user")
    Call<User> getUser(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken);
}
