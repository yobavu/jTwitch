package com.yobavu.jtwitch.oauth;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;

/**
 * Created by bvu on 3/25/17.
 */
public class TwitchToken {
    private final String accessToken;
    private final String refreshToken;

    public TwitchToken(OAuthJSONAccessTokenResponse token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
