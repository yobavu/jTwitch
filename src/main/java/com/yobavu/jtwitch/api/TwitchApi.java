/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Abstraction for Twitch API.
 */
public abstract class TwitchApi {
    private final String API_URL = "https://api.twitch.tv/kraken/";

    private Gson gson;

    public TwitchApi() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public String getApiUrl() {
        return API_URL;
    }

    public Gson getGson() {
        return gson;
    }
}
