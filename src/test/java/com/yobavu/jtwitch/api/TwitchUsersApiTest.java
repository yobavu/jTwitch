/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.model.Channel;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserBlock;
import com.yobavu.jtwitch.model.UserBlockList;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollowList;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.UserSubscription;

import com.google.gson.Gson;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import retrofit2.Response;

import java.util.List;

public class TwitchUsersApiTest {
    private Gson gson;
    private TwitchUsersApi usersApi;

    private static final int USER_ID = 1111111;
    private static final int CHANNEL_SUBSCRIBED = 19571752;
    private static final int CHANNEL_TO_FOLLOW = 129454141;
    private static final int CHANNEL_NOT_SUBSCRIBED = 5690948;
    private static final int CHANNEL_NOT_FOLLOWED = 44445592;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        gson = new Gson();
        usersApi = Mockito.mock(TwitchUsersApi.class);
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        final String json = "{" +
                "\"_total\": 1," +
                "\"users\": [" +
                "       {" +
                "           \"_id\": 1," +
                "           \"bio\": \"Just a gamer playing games and chatting.\"," +
                "           \"created_at\": \"2013-06-03T19:12:02Z\"," +
                "           \"display_name\": \"tester\"," +
                "           \"name\": \"tester\"," +
                "           \"type\": \"staff\"" +
                "       }" +
                " ]" +
                "}";
        Response<UserList> response = Response.success(gson.fromJson(json, UserList.class));

        Mockito.when(usersApi.getUserByUsername("tester")).thenReturn(response.body());

        final UserList userList = usersApi.getUserByUsername("tester");
        Assert.assertEquals(userList.getUsers().size(), 1);
        Assert.assertEquals(1, userList.getTotal());

        final User user = userList.getUsers().get(0);
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Just a gamer playing games and chatting.", user.getBio());
        Assert.assertEquals("2013-06-03T19:12:02Z", user.getCreatedAt());
        Assert.assertEquals("tester", user.getDisplayName());
        Assert.assertEquals("tester", user.getName());
        Assert.assertEquals("staff", user.getType());

    }

    @Test
    public void testGetUserById() throws Exception {
        final String json = "{" +
                    "\"_id\": 1111111," +
                    "\"bio\": \"Just a gamer playing games and chatting.\"," +
                    "\"created_at\": \"2013-06-03T19:12:02Z\"," +
                    "\"display_name\": \"tester_display\"," +
                    "\"name\": \"tester\"," +
                    "\"type\": \"user\"" +
                "}";

        Response<User> response = Response.success(gson.fromJson(json, User.class));

        Mockito.when(usersApi.getUserById(USER_ID)).thenReturn(response.body());

        final User user = usersApi.getUserById(USER_ID);
        Assert.assertEquals(USER_ID, user.getId());
        Assert.assertEquals("Just a gamer playing games and chatting.", user.getBio());
        Assert.assertEquals("2013-06-03T19:12:02Z", user.getCreatedAt());
        Assert.assertEquals("tester_display", user.getDisplayName());
        Assert.assertEquals("tester", user.getName());
        Assert.assertEquals("user", user.getType());
    }

    @Test
    public void testUserSubscriptionException() throws Exception {
        exception.expect(TwitchApiException.class);
        exception.expectMessage("has no subscriptions to");

        Mockito.when(usersApi.getUserChannelSubscription(USER_ID, CHANNEL_NOT_SUBSCRIBED))
                .thenThrow(new TwitchApiException("tester has no subscriptions to twitch"));

        usersApi.getUserChannelSubscription(USER_ID, CHANNEL_NOT_SUBSCRIBED);
    }

    @Test
    public void testUserSubscription() throws Exception {
        final String json = "{" +
                "    \"_id\": \"ac2f1248993eaf97e71721458bd88aae66c92330\"," +
                "    \"sub_plan\": \"3000\"," +
                "    \"sub_plan_name\": \"Channel Subscription (forstycup) - $24.99 Sub\"," +
                "    \"channel\": {" +
                "        \"_id\": \"19571752\"," +
                "        \"broadcaster_language\": \"en\"," +
                "        \"created_at\": \"2011-01-16T04:35:51Z\"," +
                "        \"display_name\": \"forstycup\"," +
                "        \"followers\": 397," +
                "        \"game\": \"Final Fantasy XV\"," +
                "        \"language\": \"en\"," +
                "        \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/forstycup-profile_image-940fb4ca1e5949c0-300x300.png\"," +
                "        \"mature\": true," +
                "        \"name\": \"forstycup\"," +
                "        \"partner\": true," +
                "        \"profile_banner\": null," +
                "        \"profile_banner_background_color\": null," +
                "        \"status\": \"[Blind] Moar Sidequests! Let's explore.\"," +
                "        \"updated_at\": \"2017-04-06T09:00:41Z\"," +
                "        \"url\": \"http://localhost:3000/forstycup\"," +
                "        \"video_banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/forstycup-channel_offline_image-f7274322063da225-1920x1080.png\"," +
                "        \"views\": 5705" +
                "    }," +
                "    \"created_at\": \"2017-04-08T19:54:24Z\"" +
                "}";

        Response<UserSubscription> response = Response.success(gson.fromJson(json, UserSubscription.class));

        Mockito.when(usersApi.getUserChannelSubscription(USER_ID, CHANNEL_SUBSCRIBED)).thenReturn(response.body());

        final UserSubscription subscription = usersApi.getUserChannelSubscription(USER_ID, CHANNEL_SUBSCRIBED);
        Assert.assertEquals("ac2f1248993eaf97e71721458bd88aae66c92330", subscription.getId());
        Assert.assertThat(subscription.getChannel(), IsInstanceOf.instanceOf(Channel.class));

        final Channel channel = subscription.getChannel();
        Assert.assertEquals(19571752, channel.getId());
        Assert.assertEquals("en", channel.getBroadcasterLanguage());
        Assert.assertEquals("2011-01-16T04:35:51Z", channel.getCreatedAt());
        Assert.assertEquals("forstycup", channel.getDisplayName());
        Assert.assertEquals(397, channel.getFollowers());
        Assert.assertEquals("Final Fantasy XV", channel.getGame());
        Assert.assertTrue(channel.isPartner());
    }

    @Test
    public void testChannelsFollowedByUser() throws Exception {
        final String json = "{" +
                "   \"_total\": 2," +
                "   \"follows\": [" +
                "      {" +
                "         \"created_at\": \"2016-09-16T20:37:39Z\"," +
                "         \"notifications\": false," +
                "         \"channel\": {" +
                "            \"_id\": 12826," +
                "            \"background\": null," +
                "            \"banner\": null," +
                "            \"broadcaster_language\": \"en\"," +
                "            \"created_at\": \"2007-05-22T10:39:54Z\"," +
                "            \"delay\": null," +
                "            \"display_name\": \"Twitch\"," +
                "            \"followers\": 530641," +
                "            \"game\": \"Gaming Talk Shows\"," +
                "            \"language\": \"en\"," +
                "            \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/twitch-profile_image-bd6df6672afc7497-300x300.png\"," +
                "            \"mature\": false," +
                "            \"name\": \"twitch\"," +
                "            \"partner\": true," +
                "            \"profile_banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/twitch-profile_banner-6936c61353e4aeed-480.png\"," +
                "            \"profile_banner_background_color\": null," +
                "            \"status\": \"Twitch Weekly\"," +
                "            \"updated_at\": \"2016-12-13T18:35:28Z\"," +
                "            \"url\": \"https://www.twitch.tv/twitch\"," +
                "            \"video_banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/twitch-channel_offline_image-d687d9e22677a1b6-640x360.png\"," +
                "            \"views\": 109064987" +
                "         }" +
                "      }," +
                "      {" +
                "         \"created_at\": \"2016-10-11T20:37:39Z\"," +
                "         \"notifications\": true," +
                "         \"channel\": {" +
                "            \"_id\": 13826," +
                "            \"background\": null," +
                "            \"banner\": null," +
                "            \"broadcaster_language\": \"ko\"," +
                "            \"created_at\": \"2008-05-02T10:39:54Z\"," +
                "            \"delay\": null," +
                "            \"display_name\": \"Another_Twitch\"," +
                "            \"followers\": 641," +
                "            \"game\": \"Gaming Talk Shows\"," +
                "            \"language\": \"ko\"," +
                "            \"logo\": null," +
                "            \"mature\": false," +
                "            \"name\": \"another_twitch\"," +
                "            \"partner\": false," +
                "            \"profile_banner\": null," +
                "            \"profile_banner_background_color\": null," +
                "            \"status\": \"Twitch Weekly\"," +
                "            \"updated_at\": \"2016-12-13T18:35:28Z\"," +
                "            \"url\": \"https://www.twitch.tv/twitch\"," +
                "            \"video_banner\": null," +
                "            \"views\": 164987" +
                "         }" +
                "      }" +
                "   ]" +
                "}";

        Response<UserFollowList> response = Response.success(gson.fromJson(json, UserFollowList.class));

        Mockito.when(usersApi.getChannelsFollowedByUser(USER_ID)).thenReturn(response.body());

        final UserFollowList follows = usersApi.getChannelsFollowedByUser(USER_ID);
        Assert.assertEquals(2, follows.getTotal());

        final List<UserFollow> followList = follows.getFollows();
        Assert.assertEquals(2, followList.size());

        final UserFollow follow1 = followList.get(0);
        Assert.assertEquals("2016-09-16T20:37:39Z", follow1.getCreatedAt());
        Assert.assertFalse(follow1.getNotifications());
        Assert.assertThat(follow1.getChannel(), IsInstanceOf.instanceOf(Channel.class));

        final Channel channel1 = follow1.getChannel();
        Assert.assertEquals(12826, channel1.getId());
        Assert.assertEquals("en", channel1.getBroadcasterLanguage());
        Assert.assertEquals("2007-05-22T10:39:54Z", channel1.getCreatedAt());

        final UserFollow follow2 = followList.get(1);
        final Channel channel2 = follow2.getChannel();
        Assert.assertNotEquals(channel1.getId(), channel2.getId());
    }

    @Test
    public void testCheckUserFollowsChannel() throws Exception {
        exception.expect(TwitchApiException.class);
        exception.expectMessage("Follow not found");

        Mockito.when(usersApi.checkUserFollowsChannel(USER_ID, CHANNEL_NOT_FOLLOWED)).thenThrow(new TwitchApiException("Follow not found"));

        usersApi.checkUserFollowsChannel(USER_ID, CHANNEL_NOT_FOLLOWED);
    }

    @Test
    public void testFollowChannel() throws Exception {
        final String json = "{" +
                "   \"channel\": {" +
                "      \"_id\": 129454141," +
                "      \"broadcaster_language\": null," +
                "      \"created_at\": \"2016-07-13T14:40:42Z\"," +
                "      \"display_name\": \"dallasnchains\"," +
                "      \"followers\": 2," +
                "      \"game\": null," +
                "      \"language\": \"en\"," +
                "      \"logo\": null," +
                "      \"mature\": null," +
                "      \"name\": \"dallasnchains\"," +
                "      \"partner\": false," +
                "      \"profile_banner\": null," +
                "      \"profile_banner_background_color\": null," +
                "      \"status\": null," +
                "      \"updated_at\": \"2016-12-14T00:32:17Z\"," +
                "      \"url\": \"https://www.twitch.tv/dallasnchains\"," +
                "      \"video_banner\": null," +
                "      \"views\": 6" +
                "   }," +
                "   \"created_at\": \"2016-12-14T10:28:32-08:00\"," +
                "   \"notifications\": false" +
                "}";

        Response<UserFollow> response = Response.success(gson.fromJson(json, UserFollow.class));

        Mockito.when(usersApi.followChannel(USER_ID, CHANNEL_TO_FOLLOW)).thenReturn(response.body());

        final UserFollow follow = usersApi.followChannel(USER_ID, CHANNEL_TO_FOLLOW);
        Assert.assertEquals("2016-12-14T10:28:32-08:00", follow.getCreatedAt());
        Assert.assertFalse(follow.getNotifications());

        final Channel channel = follow.getChannel();
        Assert.assertEquals(CHANNEL_TO_FOLLOW, channel.getId());
    }

    @Test
    public void testGetUserBlockList() throws Exception {
        final String json = "{" +
                "   \"_total\": 1," +
                "   \"blocks\":" +
                "   [{" +
                "      \"_id\": 34105660," +
                "      \"updated_at\": \"2016-12-15T18:58:11Z\"," +
                "      \"user\": {" +
                "         \"_id\": 129454141," +
                "         \"bio\": null," +
                "         \"created_at\": \"2016-07-13T14:40:42Z\"," +
                "         \"display_name\": \"dallasnchains\"," +
                "         \"logo\": null," +
                "         \"name\": \"dallasnchains\"," +
                "         \"type\": \"user\"," +
                "         \"updated_at\": \"2016-12-14T00:32:17Z\"" +
                "      }" +
                "     }" +
                "   ]" +
                "}";

        Response<UserBlockList> response = Response.success(gson.fromJson(json, UserBlockList.class));

        Mockito.when(usersApi.getUserBlockList(USER_ID)).thenReturn(response.body());

        final UserBlockList userBlockList = usersApi.getUserBlockList(USER_ID);
        Assert.assertEquals(1, userBlockList.getTotal());
        Assert.assertEquals(1, userBlockList.getBlocks().size());

        final UserBlock blocked = userBlockList.getBlocks().get(0);
        Assert.assertEquals(34105660, blocked.getId());

        final User blockedUser = blocked.getBlockedUser();
        Assert.assertEquals(129454141, blockedUser.getId());
        Assert.assertEquals("dallasnchains", blockedUser.getDisplayName());
    }
}
