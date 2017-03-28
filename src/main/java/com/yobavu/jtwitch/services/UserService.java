/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.User;

import com.yobavu.jtwitch.model.UserSubscription;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;

/**
 * Represents the Twitch user API.
 */
public interface UserService {
    /**
     * Gets user account associated with OAuth 2.0 token.
     *
     * Requires "user_read" scope.
     *
     * @param clientId the client id credential.
     * @param accessToken the access token associated with credential.
     */
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("user")
    Call<User> getUser(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken);

    /**
     * Gets a specific user account.
     *
     * @param clientId the client id credential.
     * @param accessToken the access token associated with credential.
     * @param userId the id for specific user account.
     */
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("users/{userId}")
    Call<User> getUserById(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken,
                           @Path("userId") int userId);

    /**
     * Gets a list of the emojis and emoticons that the specified user can use in chat.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param clientId the client id credential.
     * @param accessToken the access token associated with credential.
     * @param userId the id for specific user account.
     */
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("users/{userId}/emotes")
    Call<List<UserEmoticon>> getUserEmotes(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken,
                                           @Path("userId") int userId);

    /**
     * Check if user is subscribed to a specific channel. Will return null if channel does not have a
     * subscription program.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param clientId the client id credential.
     * @param accessToken the access token associated with credential.
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    @Headers("Accept: application/vnd.twitchtv.v5+json")
    @GET("users/{userId}/subscriptions/{channelId}")
    Call<UserSubscription> getUserChannelSubscription(@Header("Client-ID") String clientId, @Header("Authorization") String accessToken,
                                                      @Path("userId") int userId, @Path("channelId") int channelId);
}
