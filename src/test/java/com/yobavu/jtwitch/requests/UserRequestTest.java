package com.yobavu.jtwitch.requests;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.OAuth2Support;
import com.yobavu.jtwitch.oauth.TwitchToken;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by bvu on 3/25/17.
 */
public class UserRequestTest {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost/";
        final String clientId = prop.getProperty("twitch.clientid");
        final String clientSecret = prop.getProperty("twitch.clientsecret");
        final String authorizationCode = prop.getProperty("twitch.authcode");

        OAuth2Support oas = new OAuth2Support(clientId, clientSecret, redirectUri);
        OAuthClient oac = new OAuthClient(new URLConnectionClient());
        OAuth2Authenticate oaa = new OAuth2Authenticate(oas, oac);

        TwitchToken twitchToken = oaa.authenticate(authorizationCode);
        UserRequest request = new UserRequest(clientId, "OAuth " + twitchToken.getAccessToken());

        User user = request.getUser();
        System.out.println("User email: " + user.getEmail());
    }
}
