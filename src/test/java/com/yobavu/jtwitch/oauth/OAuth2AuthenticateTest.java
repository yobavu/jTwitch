/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileInputStream;
import java.util.Properties;

public class OAuth2AuthenticateTest {
    private Properties prop;
    private OAuth2Authenticate oaa;

    private String redirectUri;
    private String clientId;
    private String clientSecret;

    @Before
    public void setup() throws Exception {
        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        redirectUri = "http://localhost/";
        clientId = prop.getProperty("twitch.clientId");
        clientSecret = prop.getProperty("twitch.clientSecret");

        oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUserIdIsNull() throws Exception {
        Mockito.when(oaa.authenticate(null)).thenThrow(new IllegalArgumentException());
        oaa.authenticate(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUserIdIsEmptyString() throws Exception {
        Mockito.when(oaa.authenticate("")).thenThrow(new IllegalArgumentException());
        oaa.authenticate("");
    }
}
