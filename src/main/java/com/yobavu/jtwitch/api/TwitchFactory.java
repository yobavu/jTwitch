/**
 * Created by Binh Vu (github: yobavu) on 4/22/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.oauth.OAuth2Authenticator;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Factory for Twitch API.
 */
public final class TwitchFactory {
    private Client client;

    private static final String TWITCH_API_VERSION = "application/vnd.twitchtv.v5+json";

    /**
     * Builder for {@link TwitchFactory}.
     */
    public static class Builder {
        String clientId;
        String accessToken;
        Client client;

        public Builder() {
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
         * Set headers for each request.
         */
        public TwitchFactory build() {
            client.register(new OAuth2Authenticator(TWITCH_API_VERSION, clientId, accessToken));
            return new TwitchFactory(client);
        }
    }

    private TwitchFactory(Client client) {
        this.client = client;
    }

    public <T> T getInstance(Class<T> c) throws IllegalAccessException, InstantiationException {
        return c.newInstance();
    }

    public Client getClient() {
        return client;
    }
}
