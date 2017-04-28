/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollows;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Represents the Twitch user API.
 */
public interface UserService {
    /**
     * Gets user account associated with OAuth 2.0 token.
     *
     * Requires "user_read" scope.
     */
    @GET("user")
    Call<User> getUser();

    /**
     * Gets specific user account(s) by username. All user accounts containing a substring of
     * the username will be returned.
     *
     * @param username the username for specific user account.
     */
    @GET("users")
    Call<UserList> getUserByUsername(@Query("login") String username);

    /**
     * Gets a specific user account by id.
     *
     * @param userId the id for specific user account.
     */
    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") int userId);

    /**
     * Gets a list of the emojis and emoticons that the specified user can use in chat.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for specific user account.
     */
    @GET("users/{userId}/emotes")
    // todo need to rethink the model here.. maybe EmoticonList to hold Emoticon?
    Call<List<UserEmoticon>> getUserEmotes(@Path("userId") int userId);

    /**
     * Check if user is subscribed to a specific channel. Will return null if user is not subscribed to channel
     * or channel does not have a subscription program.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    @GET("users/{userId}/subscriptions/{channelId}")
    Call<UserSubscription> getUserChannelSubscription(@Path("userId") int userId, @Path("channelId") int channelId);

    /**
     * Gets list of channels followed by specific user.
     *
     * @param userId the id for specific user account.
     * @param limit optional parameter - sets limit of results.
     *              Default: 25.
     *              Max: 100.
     * @param offset optional parameter - use for pagination of results.
     *               Default: 0.
     * @param direction optional parameter - sort direction.
     *                  Valid values: asc and desc.
     *                  Default: desc.
     * @param sortby optional parameter - sort results.
     *               Valid values: created_at, last_broadcast, login.
     *               Default: created_at.
     */
    @GET("users/{userId}/follows/channels")
    Call<UserFollows> getChannelsFollowedByUser(@Path("userId") int userId, @Query("limit") Integer limit, @Query("offset") Integer offset,
                                                @Query("direction") String direction, @Query("sortby") String sortby);

    /**
     * Check if user follows a specific channel. Will return null if user is not following channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    @GET("users/{userId}/follows/channels/{channelId}")
    Call<UserFollow> checkUserFollowsChannel(@Path("userId") int userId, @Path("channelId") int channelId);

    /**
     * Add user as a follower for a specific channel.
     *
     * Requires "user_follows_edit" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    @PUT("users/{userId}/follows/channels/{channelId}")
    Call<UserFollow> followChannel(@Path("userId") int userId, @Path("channelId") int channelId);

    /**
     * Deletes a specified user from the followers of a specified channel.
     *
     * Requires "user_follows_edit" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    @DELETE("users/{userId}/follows/channels/{channelId}")
    Call<Void> unFollowChannel(@Path("userId") int userId, @Path("channelId") int channelId);
}
