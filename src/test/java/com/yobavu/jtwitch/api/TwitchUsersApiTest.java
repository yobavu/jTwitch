/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserList;

import com.yobavu.jtwitch.exceptions.TwitchApiException;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileInputStream;
import java.util.Properties;

public class TwitchUsersApiTest {
    private Properties prop;
    private TwitchFactory factory;
    private TwitchUsersApi usersApi;

    private UserList userList;

    private int userId;

    @Before
    public void setup() throws Exception {
        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        userId = Integer.parseInt(prop.getProperty("twitch.userId"));

        factory = new TwitchFactory.Builder()
                .setClientId("id")
                .setAccessToken("token")
                .build();

        usersApi = Mockito.mock(TwitchUsersApi.class);
        userList = Mockito.mock(UserList.class);
    }

    @Test
    public void shouldReturnInstanceOfTwitchUsersApi() {
        Assert.assertThat(factory.getInstance(TwitchFactory.API.Users), IsInstanceOf.instanceOf(TwitchUsersApi.class));
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        Mockito.when(usersApi.getUserByUsername("tester")).thenReturn(userList);
        Assert.assertThat(usersApi.getUserByUsername("tester"), IsInstanceOf.instanceOf(UserList.class));
    }

    @Test
    public void shouldReturnCorrectUserInfo() throws Exception {
        Mockito.when(usersApi.getUserByUsername("saddummy")).thenReturn(userList);

        UserList userList = usersApi.getUserByUsername("saddummy");

        for (User u : userList.getUsers()) {
            Assert.assertEquals(u.getName(), "saddummy");
            Assert.assertEquals(u.getType(), "user");
        }
    }

    @Test (expected = TwitchApiException.class)
    public void shouldThrowTwitchApiExceptionIfAccountNotSubscribed() throws Exception {
        Mockito.when(usersApi.getUserChannelSubscription(userId, 5690948)).thenThrow(TwitchApiException.class);
        usersApi.getUserChannelSubscription(userId, 5690948);
    }

    @Test
    public void shouldReturnNotSubscribedMessage() throws Exception {
        try {
            usersApi.getUserChannelSubscription(userId, 5690948);
        } catch (TwitchApiException e) {
            Assert.assertThat(e.getMessage(), StringContains.containsString("has no subscriptions to"));
        }
    }
}
