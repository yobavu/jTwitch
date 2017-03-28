/**
 * Created by Binh Vu (github: yobavu) on 3/26/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.requests.UserRequest;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Sample showing how to make a request to retrieve a user.
 */
public class UserRequestSample {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("samples/src/main/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost/";
        final String clientId = prop.getProperty("twitch.clientId");
        final String clientSecret = prop.getProperty("twitch.clientSecret");

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);

        TwitchToken twitchToken = oaa.authenticate("sampleUse");
        UserRequest request = new UserRequest(clientId, twitchToken.getAccessToken());

        User user = request.getUser();
        System.out.println("User id: " + user.getId());
        System.out.println("Username: " + user.getDisplayName());
        System.out.println("User email: " + user.getEmail());
        System.out.println("Created at: " + user.getCreatedAt());
    }
}
