/**
 * Created by Binh Vu (github: yobavu) on 4/22/17.
 */

package com.yobavu.jtwitch.api;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitchFactoryTest {
    private TwitchFactory factory;

    private static final String CLIENT_ID = "unique client id";
    private static final String ACCESS_TOKEN = "token";

    @Before
    public void setup() {
        factory = new TwitchFactory.Builder().setClientId(CLIENT_ID).setAccessToken(ACCESS_TOKEN).build();
    }

    @Test
    public void testUsersApiInstance() throws InstantiationException, IllegalAccessException {
        Assert.assertThat(factory.getInstance(TwitchUsersApi.class), IsInstanceOf.instanceOf(TwitchUsersApi.class));
    }
}
