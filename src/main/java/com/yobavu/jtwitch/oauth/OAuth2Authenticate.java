/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import com.yobavu.jtwitch.util.TwitchUtil;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for managing and creating OAuth 2.0 authorization.
 */
public class OAuth2Authenticate {
    private final String AUTHORIZE_ENDPOINT = "https://api.twitch.tv/kraken/oauth2/authorize";
    private final String TOKEN_ENDPOINT = "https://api.twitch.tv/kraken/oauth2/token";
    private final String RESPONSE_TYPE = "code";
    private final String SCOPES = "user_read user_subscriptions user_follows_edit";   // todo need better way to construct these

    private OAuthClient oAuthClient;
    private OAuth2Support oAuthSupport;

    public OAuth2Authenticate(String clientId, String clientSecret, String redirectUri) {
        oAuthClient = new OAuthClient(new URLConnectionClient());
        oAuthSupport = new OAuth2Support(clientId, clientSecret, redirectUri);
    }

    /**
     * Generate the authorization code flow URI for a Twitch user to authorize an application.
     *
     * @return the authorization code flow URI.
     */
    private String getAuthorizationCodeFlow() throws OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(AUTHORIZE_ENDPOINT)
                .setResponseType(RESPONSE_TYPE)
                .setClientId(oAuthSupport.getClientId())
                .setRedirectURI(oAuthSupport.getRedirectUri())
                .setScope(SCOPES)
                .buildQueryMessage();

        return request.getLocationUri();
    }

    /**
     * Authenticates by calling {@link #getAuthorizationCodeFlow() getAuthorizationCodeFlow} and
     * stores the token in a serialized file to avoid having to reauthorize.
     *
     * User will need to be logged into their Twitch account in order to authorize.
     *
     * @param userId unique value to associate with token.
     * @return the credential token for making requests to Twitch API.
     */
    public TwitchToken authenticate(String userId) throws OAuthSystemException, OAuthProblemException, ClassNotFoundException, IOException, URISyntaxException {
        if (userId == null || userId.equals("")) {
            throw new IllegalArgumentException("Unique user identification code must not be null or empty string");
        }

        TwitchToken credential = TwitchUtil.loadCredential(userId);

        if (credential == null) {
            // authorizing for the first time
            Map<String, Object> credentials = new HashMap<>();

            String authCodeFlowUrl = getAuthorizationCodeFlow();

            if (authCodeFlowUrl == null) {
                throw new IllegalStateException("Authorization code flow URL must not be null");
            }

            // opens a browser and directs user to authorize page
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(authCodeFlowUrl));
            } else {
                System.err.println("Please open the following URL in your browser:  " + authCodeFlowUrl);
            }

            String authorizationCode = getAuthorizationCode();

            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(oAuthSupport.getClientId())
                    .setClientSecret(oAuthSupport.getClientSecret())
                    .setRedirectURI(oAuthSupport.getRedirectUri())
                    .setCode(authorizationCode)
                    .buildQueryMessage();

            credential = new TwitchToken(oAuthClient.accessToken(request));

            // save the credential to file for later use
            credentials.put(userId, credential);
            TwitchUtil.writeSerializable(credentials);

            return credential;
        }

        return credential;
    }

    /**
     * Generates a socket connection in order to retrieve the authorization code from Twitch.
     *
     * @return authorization code.
     */
    private String getAuthorizationCode() throws IOException {
        String code;
        ServerSocket socket = new ServerSocket(8000);

        while (true) {
            final Socket client = socket.accept();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line = reader.readLine();

                if (line == null) {
                    throw new IOException("Client closed connection");
                }

                // strip out http method and version to get only the params
                final String[] request = line.split(" ");
                final String params = request[1];

                // now strip everything except authorization code
                if (params.contains("?")) {
                    int queryStartIndex = params.indexOf("?");
                    String query = params.substring(queryStartIndex + 1, params.length());

                    // split each param up
                    String[] param = query.split("&");

                    code = param[0].split("=")[1];
                    break;
                } else {
                    throw new IllegalStateException("Invalid authorization code redirect URL");
                }
            } catch (Exception e) {
                //
            } finally {
                client.close();
            }
        }

        return code;
    }
}
