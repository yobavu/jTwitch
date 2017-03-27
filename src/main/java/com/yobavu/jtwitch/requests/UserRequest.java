/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.requests;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.services.UserService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Wrapper for the Twitch user API.
 */
public class UserRequest {
    private final String API_URL = "https://api.twitch.tv/kraken/";

    private final String clientId;
    private final String accessToken;

    public UserRequest(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    /**
     * Gets user account associated with OAuth 2.0 token.
     */
    public User getUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.getUser(clientId, accessToken);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
