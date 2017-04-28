/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ApiError;
import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollows;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.services.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Wrapper for the Twitch users API.
 */
public class TwitchUsersApi {
    private final String API_URL = "https://api.twitch.tv/kraken/";

    private UserService userService;

    public TwitchUsersApi(OkHttpClient.Builder clientBuilder) {
        userService = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
                .create(UserService.class);
    }

    /**
     * Gets user account associated with OAuth 2.0 token.
     *
     * Requires "user_read" scope.
     */
    public User getUser() throws IOException, TwitchApiException {
        Call<User> call = userService.getUser();

        Response<User> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
    }

    /**
     * Gets specific user account(s) by username. All user accounts containing a substring of
     * the username will be returned.
     *
     * @param username the username for specific user account.
     */
    public UserList getUserByUsername(String username) throws IOException, TwitchApiException {
        Call<UserList> call = userService.getUserByUsername(username);

        Response<UserList> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
    }

    /**
     * Gets a specific user account by id.
     *
     * @param userId the id for specific user account.
     */
    public User getUserById(int userId) throws IOException, TwitchApiException {
        Call<User> call = userService.getUserById(userId);

        Response<User> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
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
    public UserSubscription getUserChannelSubscription(int userId, int channelId) throws IOException, TwitchApiException {
        Call<UserSubscription> call = userService.getUserChannelSubscription(userId, channelId);

        Response<UserSubscription> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
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
    public UserFollows getChannelsFollowedByUser(int userId, Object... queryParams) throws IOException, TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String direction = null;
        String sortby = null;

        if (queryParams.length > 4) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

        switch (queryParams.length) {
            case 1:
                limit = (Integer) queryParams[0];
                break;
            case 2:
                limit = (Integer) queryParams[0];
                offset = (Integer) queryParams[1];
                break;
            case 3:
                limit = (Integer) queryParams[0];
                offset = (Integer) queryParams[1];
                direction = (String) queryParams[2];
                break;
            case 4:
                limit = (Integer) queryParams[0];
                offset = (Integer) queryParams[1];
                direction = (String) queryParams[2];
                sortby = (String) queryParams[3];
                break;
        }

        Call<UserFollows> call = userService.getChannelsFollowedByUser(userId, limit, offset, direction, sortby);

        Response<UserFollows> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
    }

    /**
     * Check if user follows a specific channel. Will return null if user is not following channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id of channel to check.
     */
    public UserFollow checkUserFollowsChannel(int userId, int channelId) throws IOException, TwitchApiException {
        Call<UserFollow> call = userService.checkUserFollowsChannel(userId, channelId);

        Response<UserFollow> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
    }

    /**
     * Add user as a follower for a specific channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel to follow.
     */
    public UserFollow followChannel(int userId, int channelId) throws IOException, TwitchApiException {
        Call<UserFollow> call = userService.followChannel(userId, channelId);

        Response<UserFollow> response = call.execute();
        ApiError apiError = ErrorParser.parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getMessage());
        }

        return response.body();
    }
}
