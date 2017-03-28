/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.requests;

import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.services.UserService;

import retrofit2.Call;
import retrofit2.Response;
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
     * Gets a specific user account.
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
}
