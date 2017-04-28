/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.model.Channel;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserFollows;
import com.yobavu.jtwitch.model.UserList;

import com.yobavu.jtwitch.exceptions.TwitchApiException;

import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.util.TwitchUtil;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.util.Properties;

public class TwitchUsersApiTest {
    private Properties prop;
    private TwitchFactory factory;
    private TwitchUsersApi usersApi;

    private String clientId;
    private int twitchUserId;
    private TwitchToken token;

    private static final int CHANNEL_NOT_SUBSCRIBED = 5690948;
    private static final int CHANNEL_NOT_FOLLOWED = 44445592;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        clientId = prop.getProperty("twitch.clientId");
        twitchUserId = Integer.parseInt(prop.getProperty("twitch.userId"));
        token = TwitchUtil.loadCredential("sampleUse");

        factory = new TwitchFactory.Builder()
                .setClientId(clientId)
                .setAccessToken(token.getAccessToken())
                .build();

        usersApi = (TwitchUsersApi) factory.getInstance(TwitchFactory.API.Users);
    }

    @Test
    public void testReturnCorrectInstance() {
        Assert.assertThat(factory.getInstance(TwitchFactory.API.Users), IsInstanceOf.instanceOf(TwitchUsersApi.class));
    }

    @Test
    public void testGetUser() throws Exception {
        final UserList userList = usersApi.getUserByUsername("saddummy");

        Assert.assertEquals(userList.getUsers().size(), 1);

        final User user = userList.getUsers().get(0);
        Assert.assertEquals("saddummy", user.getName());
        Assert.assertEquals("user", user.getType());
    }

    @Test
    public void testNotSubscribedException() throws Exception {
        exception.expect(TwitchApiException.class);
        exception.expectMessage("has no subscriptions to");

        usersApi.getUserChannelSubscription(twitchUserId, CHANNEL_NOT_SUBSCRIBED);
    }

    @Test
    public void testChannelsFollowed() throws Exception {
        final UserFollows userFollows = usersApi.getChannelsFollowedByUser(twitchUserId);

        Assert.assertEquals(userFollows.getFollows().size(), 2);

        final Channel channel = userFollows.getFollows().get(0).getChannel();
        Assert.assertEquals("zeenigami", channel.getName());
        Assert.assertEquals(24078, channel.getFollowers());
    }

    @Test
    public void testChannelFollowedByUserException() throws Exception {
        exception.expect(TwitchApiException.class);
        exception.expectMessage("Follow not found");

        usersApi.getChannelFollowedByUser(twitchUserId, CHANNEL_NOT_FOLLOWED);
    }
}
