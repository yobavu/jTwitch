/**
 * Created by Binh Vu (github: yobavu) on 3/26/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.api.TwitchFactory;
import com.yobavu.jtwitch.api.TwitchUsersApi;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserSubscription;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.util.TwitchScope;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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

        final int dummyChannelId = 19571752;

        List<TwitchScope.SCOPES> scopes = new ArrayList<>();
        scopes.add(TwitchScope.SCOPES.USER_READ);
        scopes.add(TwitchScope.SCOPES.USER_SUBSCRIPTION);
        scopes.add(TwitchScope.SCOPES.USER_FOLLOWS_EDIT);

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri, scopes);

        TwitchToken twitchToken = oaa.authenticate("sampleUse");

        TwitchFactory factory = new TwitchFactory.Builder()
                .setClientId(clientId)
                .setAccessToken(twitchToken.getAccessToken())
                .build();
        TwitchUsersApi usersApi = factory.getInstance(TwitchUsersApi.class).build(factory.getClient());

        System.out.println("========== Making USER API request ==========");

        // get users by username and display their information
        getUsersByUsername(usersApi);

        // check if user is subscribed to a channel
        userSubscriptionToChannel(usersApi, Integer.parseInt(twitchUserId), dummyChannelId);

        // get list of channels followed by user
        channelsFollowed(usersApi, Integer.parseInt(twitchUserId));
    }

    private static void getUsersByUsername(TwitchUsersApi usersApi) throws TwitchApiException {
        // getting user accounts that contains username
        List<User> users = usersApi.getUsersByUsername("testusername");

        for (User u : users) {
            System.out.println("User id: " + u.getId());
            System.out.println("Display name: " + u.getDisplayName());
            System.out.println("User name: " + u.getName());
            System.out.println("Account type: " + u.getType());
            System.out.println("Account type: " + u.getCreatedAt());
        }
    }

    private static void userSubscriptionToChannel(TwitchUsersApi usersApi, int userId, int dummyChannelId) {
        try {
            // exception will occur if not subscribed
            UserSubscription uSub = usersApi.getUserChannelSubscription(userId, dummyChannelId);
            System.out.println("Subscribed date: " + uSub.getCreatedAt());
            System.out.println("Subscribed channel name: " + uSub.getChannel().getName());
        } catch (TwitchApiException e) {
            System.err.println(e);
        }
    }

    private static void channelsFollowed(TwitchUsersApi usersApi, int userId) throws TwitchApiException {
        List<UserFollow> userFollowList = usersApi.getChannelsFollowedByUser(userId);

        System.out.println("User is following:");

        StringBuilder sb = new StringBuilder();

        for (UserFollow f : userFollowList) {
            sb.append("Channel '").append(f.getChannel().getDisplayName())
              .append("' which has ")
              .append(f.getChannel().getFollowers()).append(" followers!\n");
        }

        System.out.println(sb.toString());
    }
}
