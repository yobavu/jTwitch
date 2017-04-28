/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class OAuth2AuthenticateTest {
    private OAuth2Authenticate oaa;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        oaa = Mockito.mock(OAuth2Authenticate.class);
    }

    @Test
    public void testNullUserIdException() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Unique user identification code must not be null or empty string");

        Mockito.when(oaa.authenticate(null))
                .thenThrow(new IllegalArgumentException("Unique user identification code must not be null or empty string"));
        oaa.authenticate(null);
    }

    @Test
    public void testEmptyStringUserIdException() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Unique user identification code must not be null or empty string");

        Mockito.when(oaa.authenticate(""))
                .thenThrow(new IllegalArgumentException("Unique user identification code must not be null or empty string"));
        oaa.authenticate("");
    }
}
