/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.requests;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollows;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.services.UserService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Wrapper for the Twitch user API.
 */
public class UserRequest {
    private final String API_URL = "https://api.twitch.tv/kraken/";

    private final String clientId;
    private final String accessToken;

    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
    private final UserService userService = retrofit.create(UserService.class);

    public UserRequest(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    /**
     * Gets user account associated with OAuth 2.0 token.
     *
     * Requires "user_read" scope.
     */
    public User getUser() {
        Call<User> call = userService.getUser(clientId, "OAuth " + accessToken);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets specific user account(s) by username. All user accounts containing a substring of
     * the username will be returned.
     *
     * @param username the username for specific user account.
     */
    public UserList getUserByUsername(String username) {
        Call<UserList> call = userService.getUserByName(clientId, "OAuth " + accessToken, username);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a specific user account by id.
     *
     * @param userId the id for specific user account.
     */
    public User getUserById(int userId) {
        Call<User> call = userService.getUserById(clientId, "OAuth " + accessToken, userId);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a list of the emojis and emoticons that the specified user can use in chat.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for the specific user account.
     */
    public List<UserEmoticon> getUserEmotes(int userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Check if user is subscribed to a specific channel. Will return null if user is not subscribed to channel
     * or channel does not have a subscription program.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    public UserSubscription getUserChannelSubscription(int userId, int channelId) {
        Call<UserSubscription> call = userService.getUserChannelSubscription(clientId, "OAuth " + accessToken,
                                                    userId, channelId);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets list of channels followed by specific user.
     *
     * @param userId the id for specific user account.
     * @param queryParams optional set of parameters:
     *        <ul>
     *            <li>
     *                limit: sets limit of results. Default: 25 and Max: 100.
     *            </li>
     *            <li>
     *                offset: use for pagination of results. Default: 0.
     *            </li>
     *            <li>
     *                direction: sort direction. Valid values: asc and desc. Default: desc.
     *            </li>
     *            <li>
     *                sortby: sort results. Valid values: created_at, last_broadcast, login. Default: created_at.
     *            </li>
     *        </ul>
     */
    public UserFollows getChannelsFollowedByUser(int userId, Object... queryParams) {
        Integer limit = null;
        Integer offset = null;
        String direction = null;
        String sortby = null;

        if (queryParams.length > 0) {
            if (queryParams[0] != null) {
                limit = (Integer) queryParams[0];
            }

            if (queryParams[1] != null) {
                offset = (Integer) queryParams[1];
            }

            if (queryParams[2] != null) {
                direction = (String) queryParams[2];
            }

            if (queryParams[3] != null) {
                sortby = (String) queryParams[3];
            }
        }

        try {
            Call<UserFollows> call = userService.getChannelsFollowedByUser(clientId, "OAuth " + accessToken,
                    userId, limit, offset, direction, sortby);

            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Check if user follows a specific channel. Will return null if user is not following channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    public UserFollow getChannelFollowedByUser(int userId, int channelId) {
        Call<UserFollow> call = userService.getChannelFollowedByUser(clientId, "OAuth " + accessToken, userId, channelId);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add user as a follower for a specific channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    public UserFollow followChannel(int userId, int channelId) {
        Call<UserFollow> call = userService.followChannel(clientId, "OAuth " + accessToken, userId, channelId);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
