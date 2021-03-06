/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.util.TwitchScope;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Sample showing how to authenticate and get an access token.
 */
public class TwitchAuthenticateSample {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("samples/src/main/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost:8000/";
        final String clientId = prop.getProperty("twitch.clientId");
        final String clientSecret = prop.getProperty("twitch.clientSecret");

        List<TwitchScope.SCOPES> scopes = new ArrayList<>();
        scopes.add(TwitchScope.SCOPES.USER_READ);
        scopes.add(TwitchScope.SCOPES.USER_SUBSCRIPTION);
        scopes.add(TwitchScope.SCOPES.USER_FOLLOWS_EDIT);

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri, scopes);

        TwitchToken twitchToken = oaa.authenticate("sampleUser");
        System.out.println("Twitch access token: " + twitchToken.getAccessToken());
        System.out.println("Twitch refresh token: " + twitchToken.getRefreshToken());
    }
}
