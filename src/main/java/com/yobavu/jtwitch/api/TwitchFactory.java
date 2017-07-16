/**
 * Created by Binh Vu (github: yobavu) on 4/22/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.oauth.OAuth2Authenticator;

import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Twitch API.
 */
public final class TwitchFactory {
    public enum API { Users, Videos }

    private Map<API, Object> factory;

    private static final String TWITCH_API_VERSION = "application/vnd.twitchtv.v5+json";

    /**
     * Builder for {@link TwitchFactory}.
     */
    public static class Builder {
        String clientId;
        String accessToken;
        Map<API, Object> factory;
        Client client;

        public Builder() {
            this.factory = new HashMap<>();
            this.client = ClientBuilder.newClient(new ClientConfig());
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
         * Set headers to each request.
         */
        public TwitchFactory build() {
            client.register(new OAuth2Authenticator(HttpHeaders.ACCEPT, TWITCH_API_VERSION))
                  .register(new OAuth2Authenticator("Client-ID", clientId))
                  .register(new OAuth2Authenticator(HttpHeaders.AUTHORIZATION, "OAuth " + accessToken));

            factory.put(API.Users, new TwitchUsersApi(client));
//            factory.put(API.Videos, new TwitchVideosApi(webTarget));

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
