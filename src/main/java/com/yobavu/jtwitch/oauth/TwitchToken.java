/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;

import java.io.Serializable;

/**
 * A credential token for making requests to the Twitch API.
 */
public class TwitchToken implements Serializable {
    private static final long serialVersionUID = -6348493889812757606L;

    private final String accessToken;
    private final String refreshToken;

    public TwitchToken(OAuthJSONAccessTokenResponse token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }

    /**
     * Get access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Get refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
