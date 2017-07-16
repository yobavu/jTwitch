/**
 * Created by Binh Vu (github: yobavu) on 7/15/17.
 */

package com.yobavu.jtwitch.oauth;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * OAuth 2 authentication filter to be used with Jersey client.
 */
public class OAuth2Authenticator implements ClientRequestFilter {
    private final String apiVersion;
    private final String clientId;
    private final String accessToken;

    public OAuth2Authenticator(String apiVersion, String clientId, String accessToken) {
        this.apiVersion = apiVersion;
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        headers.add(HttpHeaders.ACCEPT, apiVersion);
        headers.add("Client-ID", clientId);
        headers.add(HttpHeaders.AUTHORIZATION, "OAuth " + accessToken);
    }
}
