/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Represents the Twitch user API.
 */
public interface UserService {
    /**
     * Gets user account associated with OAuth 2.0 token.
     * @param clientId user's client id.
     * @param accessToken the access token associated with user's account.
     */
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("user")
    Call<User> getUser(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken);
}
