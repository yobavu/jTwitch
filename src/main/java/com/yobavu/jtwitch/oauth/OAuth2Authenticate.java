package com.yobavu.jtwitch.oauth;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * Created by bvu on 3/25/17.
 */
public class OAuth2Authenticate {
    private final String AUTHORIZE_ENDPOINT = "https://api.twitch.tv/kraken/oauth2/authorize";
    private final String TOKEN_ENDPOINT = "https://api.twitch.tv/kraken/oauth2/token";
    private final String RESPONSE_TYPE = "code";
    private final String SCOPES = "user_read";

    private OAuthClient oAuthClient;
    private OAuth2Support oAuthSupport;

    public OAuth2Authenticate(OAuth2Support oAuthSupport) {
        this.oAuthSupport = oAuthSupport;
    }

    public OAuth2Authenticate(OAuth2Support oAuthSupport, OAuthClient oAuthClient) {
        this.oAuthSupport = oAuthSupport;
        this.oAuthClient = oAuthClient;
    }

    public String getAuthorizationCodeFlow() {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation(AUTHORIZE_ENDPOINT)
                    .setResponseType(RESPONSE_TYPE)
                    .setClientId(oAuthSupport.getClientId())
                    .setScope(SCOPES)
                    .buildQueryMessage();

            return request.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TwitchToken authenticate(String authorizationCode) {
        if (authorizationCode == null) {
            throw new IllegalArgumentException("Authorization code must not be null");
        }

        // check in case constructor without oAuthClient was called
        if (oAuthClient == null) {
            oAuthClient = new OAuthClient(new URLConnectionClient());
        }

        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(oAuthSupport.getClientId())
                    .setClientSecret(oAuthSupport.getClientSecret())
                    .setRedirectURI(oAuthSupport.getRedirectUri())
                    .setCode(authorizationCode)
                    .buildBodyMessage();

            return new TwitchToken(oAuthClient.accessToken(request));
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

        return null;
    }
}
