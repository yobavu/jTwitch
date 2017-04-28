/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.oauth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.FileInputStream;
import java.util.Properties;

public class OAuth2AuthenticateTest {
    private Properties prop;
    private OAuth2Authenticate oaa;

    private String redirectUri;
    private String clientId;
    private String clientSecret;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/jtwitch.properties"));

        redirectUri = "http://localhost/";
        clientId = prop.getProperty("twitch.clientId");
        clientSecret = prop.getProperty("twitch.clientSecret");

        oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUserIdIsNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        oaa.authenticate(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUserIdIsEmptyString() throws Exception {
        exception.expect(IllegalArgumentException.class);
        oaa.authenticate("");
    }
}
