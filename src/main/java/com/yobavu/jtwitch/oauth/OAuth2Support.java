package com.yobavu.jtwitch.oauth;

/**
 * Created by bvu on 3/25/17.
 */
public class OAuth2Support {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public OAuth2Support(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
