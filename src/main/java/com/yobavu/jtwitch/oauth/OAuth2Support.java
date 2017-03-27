/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

/**
 * Utility for managing and creating OAuth 2.0 credentials.
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

    /**
     * Get client id.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Get client secret.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Get redirection URI.
     */
    public String getRedirectUri() {
        return redirectUri;
    }
}
