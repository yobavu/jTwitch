/**
 * Created by Binh Vu (github: yobavu) on 4/22/17.
 */

package com.yobavu.jtwitch.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Twitch API.
 */
public final class TwitchFactory {
    public enum API { Users }

    private Map<API, Object> factory;

    private static final String TWITCH_API_VERSION = "application/vnd.twitchtv.v5+json";

    /**
     * Builder for {@link TwitchFactory}.
     */
    public static class Builder {
        String clientId;
        String accessToken;
        Map<API, Object> factory;
        OkHttpClient.Builder clientBuilder;

        public Builder() {
            this.factory = new HashMap<>();
            this.clientBuilder = new OkHttpClient().newBuilder();
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * Application interceptor is used for adding request headers to each response.
         */
        public TwitchFactory build() {
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", TWITCH_API_VERSION)
                            .addHeader("Client-ID", clientId)
                            .addHeader("Authorization", "OAuth " + accessToken)
                            .build();
                    return chain.proceed(request);
                }
            });

            factory.put(API.Users, new TwitchUsersApi(clientBuilder));

            return new TwitchFactory(this.factory);
        }
    }

    private TwitchFactory(Map<API, Object> factory) {
        this.factory = factory;
    }

    public Object getInstance(API type) {
        return factory.get(type);
    }
}
