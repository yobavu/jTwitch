/**
 * Created by Binh Vu (github: yobavu) on 7/15/17.
 */

package com.yobavu.jtwitch.oauth;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

/**
 * OAuth 2 authentication filter to be used with Jersey client.
 */
public class OAuth2Authenticator implements ClientRequestFilter {
    private final String header;
    private final String value;

    public OAuth2Authenticator(String header, String value) {
        this.header = header;
        this.value = value;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add(header, value);
    }
}
