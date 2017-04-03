/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    private Map<String, Object> credentials;

    public OAuth2Authenticate(String clientId, String clientSecret, String redirectUri) {
        oAuthClient = new OAuthClient(new URLConnectionClient());
        oAuthSupport = new OAuth2Support(clientId, clientSecret, redirectUri);
    }

    /**
     * Generate the authorization code flow URI for a Twitch user to authorize an application.
     *
     * @return the authorization code flow URI.
     */
    private String getAuthorizationCodeFlow() {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation(AUTHORIZE_ENDPOINT)
                    .setResponseType(RESPONSE_TYPE)
                    .setClientId(oAuthSupport.getClientId())
                    .setRedirectURI(oAuthSupport.getRedirectUri())
                    .setScope(SCOPES)
                    .buildQueryMessage();

            return request.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }

        return null;
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
    public TwitchToken authenticate(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Authorization code must not be null");
        }

        TwitchToken credential = loadCredential(userId);

        if (credential == null) {
            // authorizing for the first time

            String authCodeFlowUrl = getAuthorizationCodeFlow();

            if (authCodeFlowUrl == null) {
                throw new IllegalStateException("Authorization code flow URL must not be null");
            }

            System.out.println("Please open the following URL in your browser and enter the authorization code below when done:");
            System.out.println("  " + authCodeFlowUrl);
            System.out.println("Authorization code: ");

            Scanner scan = new Scanner(System.in);
            String authorizationCode = scan.nextLine();
            scan.close();

            try {
                OAuthClientRequest request = OAuthClientRequest
                        .tokenLocation(TOKEN_ENDPOINT)
                        .setGrantType(GrantType.AUTHORIZATION_CODE)
                        .setClientId(oAuthSupport.getClientId())
                        .setClientSecret(oAuthSupport.getClientSecret())
                        .setRedirectURI(oAuthSupport.getRedirectUri())
                        .setCode(authorizationCode)
                        .buildQueryMessage();

                credential = new TwitchToken(oAuthClient.accessToken(request));
                credentials = readSerializable();

                if (credentials == null) {
                    credentials = new HashMap<>();
                }

                credentials.put(userId, credential);
                writeSerializable(credentials);

                return credential;
            } catch (OAuthSystemException e) {
                e.printStackTrace();
            } catch (OAuthProblemException e) {
                e.printStackTrace();
            }
        }

        return credential;
    }

    /**
     * Loads the access token for an existing user from the serialized file.
     *
     * @param id the unique id associated with an access token.
     * @return the credential token for making requests to Twitch API.
     */
    private TwitchToken loadCredential(String id) {
        boolean justCreated = false;

        try {
            File dir = new File(System.getProperty("user.home") + "/.jTwitch/");
            File serialFile = new File(System.getProperty("user.home") + "/.jTwitch/credentials.ser");

            if (!dir.exists()) {
                dir.mkdir();
            }

            justCreated = serialFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!justCreated) {
            Map<String, Object> creds = readSerializable();

            if (creds != null && creds.containsKey(id)) {
                return (TwitchToken) creds.get(id);
            }
        }

        return null;
    }

    /**
     * Reads the serialized files for existing credential tokens.
     *
     * @return map containing credential token.
     */
    private Map<String, Object> readSerializable() {
        ObjectInputStream oiStream;
        FileInputStream inStream;

        try {
            inStream = new FileInputStream(System.getProperty("user.home") + "/.jTwitch/credentials.ser");
            oiStream = new ObjectInputStream(inStream);

            credentials = (HashMap<String, Object>) oiStream.readObject();

            oiStream.close();
            inStream.close();

            return credentials;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Stores the credential token in a serialized file.
     *
     * @param creds map containing credential token.
     */
    private void writeSerializable(Map<String, Object> creds) {
        ObjectOutputStream ooStream;
        FileOutputStream outStream;

        try {
            outStream = new FileOutputStream(System.getProperty("user.home") + "/.jTwitch/credentials.ser");
            ooStream = new ObjectOutputStream(outStream);

            ooStream.writeObject(creds);
            ooStream.close();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
