/**
 * Created by Binh Vu (github: yobavu) on 3/26/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.api.TwitchFactory;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollows;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.api.TwitchUsersApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Sample showing how to make requests to the User endpoints.
 */
public class TwitchUsersApiSample {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("samples/src/main/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost:8000/";
        final String clientId = prop.getProperty("twitch.clientId");
        final String clientSecret = prop.getProperty("twitch.clientSecret");
        // this is id of user associated with token - in other words, your Twitch account id
        final String twitchUserId = prop.getProperty("twitch.userId");

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);

        TwitchToken twitchToken = oaa.authenticate("sampleUse");
        TwitchFactory factory = new TwitchFactory.Builder()
                .setClientId(clientId)
                .setAccessToken(twitchToken.getAccessToken())
                .build();
        TwitchUsersApi usersApi = (TwitchUsersApi) factory.getInstance(TwitchFactory.API.Users);

        System.out.println("========== Making USER API request ==========");

        // get users by username and display their information
        getUserByUsername(usersApi);

        // check if user is subscribed to a channel
        userSubscriptionToChannel(usersApi, Integer.parseInt(twitchUserId));

        // get list of channels followed by user
        channelsFollowed(usersApi, Integer.parseInt(twitchUserId));
    }

    public static void getUserByUsername(TwitchUsersApi usersApi) throws Exception {
        // getting user accounts that contains username
        UserList userList = usersApi.getUserByUsername("saddummy");

        for (User u : userList.getUsers()) {
            System.out.println("User id: " + u.getId());
            System.out.println("Display name: " + u.getDisplayName());
            System.out.println("User name: " + u.getName());
            System.out.println("Account type: " + u.getType());
        }
    }

    public static void userSubscriptionToChannel(TwitchUsersApi usersApi, int userId) throws IOException {
        try {
            // exception will occur if not subscribed
            UserSubscription uSub = usersApi.getUserChannelSubscription(userId, 5690948);
            System.out.println("Subscribed date: " + uSub.getCreatedAt());
            System.out.println("Subscribed channel name: " + uSub.getChannel().getName());
        } catch (TwitchApiException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void channelsFollowed(TwitchUsersApi usersApi, int userId) throws TwitchApiException, IOException {
        UserFollows userFollows = usersApi.getChannelsFollowedByUser(userId);

        System.out.println("User is following:");

        StringBuilder sb = new StringBuilder();

        for (UserFollow f : userFollows.getFollows()) {
            sb.append("Channel '").append(f.getChannel().getDisplayName())
                    .append("' which has ")
                    .append(f.getChannel().getFollowers()).append(" followers!\n");
        }

        String results = sb.toString();
        System.out.println(results);

        System.out.println();
    }
}
