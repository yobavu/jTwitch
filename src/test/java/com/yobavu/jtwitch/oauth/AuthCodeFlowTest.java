package com.yobavu.jtwitch.oauth;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by bvu on 3/25/17.
 */
public class AuthCodeFlowTest {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost/";
        final String clientId = prop.getProperty("twitch.clientid");
        final String clientSecret = prop.getProperty("twitch.clientsecret");

        OAuth2Support oas = new OAuth2Support(clientId, clientSecret, redirectUri);
        OAuth2Authenticate oaa = new OAuth2Authenticate(oas);

        String authCodeFlowUrl = oaa.getAuthorizationCodeFlow();
        System.out.println("Please open the following URL in your browser to retrieve the authorization code:");
        System.out.println("  " + authCodeFlowUrl);
    }
}
