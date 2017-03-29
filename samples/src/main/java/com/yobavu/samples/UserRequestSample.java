/**
 * Created by Binh Vu (github: yobavu) on 3/26/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.requests.UserRequest;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Sample showing how to make requests to the User endpoints.
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

        System.out.println("========== Making USER API request ==========");

        // getting user account associated with token
        User user = request.getUser();

        System.out.println("User id: " + user.getId());
        System.out.println("Username: " + user.getDisplayName());
        System.out.println("User email: " + user.getEmail());
        System.out.println("Created at: " + user.getCreatedAt());

        System.out.println();

        // getting user account by username
        UserList userList = request.getUserByUsername("saddummy");

        for (User u : userList.getUsers()) {
            System.out.println("User id: " + u.getId());
            System.out.println("Username: " + u.getDisplayName());
            System.out.println("User email: " + u.getEmail());
            System.out.println("Created at: " + u.getCreatedAt());
            System.out.println();
        }

        // getting user's subscription to a channel
        // returns null if user is not subscribed to channel
        UserSubscription uSub = request.getUserChannelSubscription(00000000, 5690948);

        if (uSub != null) {
            System.out.println("Subscribed date: " + uSub.getCreatedAt());
        } else {
            System.out.println("User is not subscribed to channel");
        }
    }
}
