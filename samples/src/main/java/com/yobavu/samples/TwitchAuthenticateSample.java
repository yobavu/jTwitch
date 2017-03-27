package com.yobavu.samples;

import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by bvu on 3/25/17.
 */
public class TwitchAuthenticateSample {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("samples/src/main/resources/jtwitch.properties"));
//        prop.load(TwitchAuthenticateSample.class.getClassLoader().getResourceAsStream("jtwitch.properties"));

        final String redirectUri = "http://localhost/";
        final String clientId = prop.getProperty("twitch.clientId");
        final String clientSecret = prop.getProperty("twitch.clientSecret");

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);

        TwitchToken twitchToken = oaa.authenticate("sampleUser");
        System.out.println("Twitch access token: " + twitchToken.getAccessToken());
        System.out.println("Twitch refresh token: " + twitchToken.getRefreshToken());
    }
}
