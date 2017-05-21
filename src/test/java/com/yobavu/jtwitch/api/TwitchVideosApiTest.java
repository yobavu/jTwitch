/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.google.gson.Gson;
import com.yobavu.jtwitch.model.Channel;
import com.yobavu.jtwitch.model.Video;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import retrofit2.Response;

import java.util.Date;

public class TwitchVideosApiTest {
    private Gson gson;
    private TwitchVideosApi videosApi;

    private static final int VIDEO_ID = 14567223;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        gson = new Gson();
        videosApi = Mockito.mock(TwitchVideosApi.class);
    }

    @Test
    public void getVideoById() throws Exception {
        final String json = "{" +
                "   \"_id\": \"v14567223\"," +
                "   \"broadcast_id\": 1," +
                "   \"broadcast_type\": \"upload\"," +
                "   \"channel\": {" +
                "       \"_id\": \"44322889\"," +
                "       \"name\": \"dallas\"," +
                "       \"display_name\": \"dallas\"" +
                "   }," +
                "   \"created_at\": \"2016-12-10T00:46:44Z\"," +
                "   \"description\": \"Checkout Twitch Videos!\"," +
                "   \"description_html\": \"Checkout Twitch Videos!<br>\"," +
                "   \"fps\": {" +
                "      \"1080p\": 23.9767661758746," +
                "      \"480p\": 23.8767661758746," +
                "      \"720p\": 23.7767661758746" +
                "   }," +
                "   \"game\": \"\"," +
                "   \"language\": \"en\"," +
                "   \"length\": 29," +
                "   \"muted_segments\": [" +
                "        {" +
                "            \"duration\": 360," +
                "            \"offset\": 5220" +
                "        }," +
                "        {" +
                "            \"duration\": 360," +
                "            \"offset\": 7020" +
                "        }" +
                "    ]," +
                "   \"preview\": {" +
                "      \"small\": \"https://static-cdn.jtvnw.net/s3_vods/twitch/106400740/f2979575-fa80-4ad9-9665-a074d510a03a/thumb/index-0000000000-80x45.jpg\"," +
                "      \"template\": \"https://static-cdn.jtvnw.net/s3_vods/twitch/106400740/f2979575-fa80-4ad9-9665-a074d510a03a/thumb/index-0000000000-{width}x{height}.jpg\"" +
                "   }," +
                "   \"published_at\": \"2016-12-12T18:19:18Z\"," +
                "   \"resolutions\": {" +
                "      \"1080p\": \"1920x1080\"," +
                "      \"480p\": \"852x480\"," +
                "      \"720p\": \"1280x720\"" +
                "   }," +
                "   \"status\": \"recorded\"," +
                "   \"tag_list\": \"\"," +
                "   \"thumbnails\": {" +
                "      \"small\": [{" +
                "         \"type\": \"generated\"," +
                "         \"url\": \"https://static-cdn.jtvnw.net/s3_vods/twitch/106400740/f2979575-fa80-4ad9-9665-a074d510a03a/thumb/index-0000000000-80x45.jpg\"" +
                "      }]," +
                "      \"template\": [{" +
                "         \"type\": \"generated\"," +
                "         \"url\": \"https://static-cdn.jtvnw.net/s3_vods/twitch/106400740/f2979575-fa80-4ad9-9665-a074d510a03a/thumb/index-0000000000-{width}x{height}.jpg\"" +
                "      }]" +
                "   }," +
                "   \"title\": \"Introducing Twitch Video\"," +
                "   \"url\": \"https://www.twitch.tv/twitch/v/14567223\"," +
                "   \"viewable\": \"public\"," +
                "   \"viewable_at\": null," +
                "   \"views\": 7638" +
                "}";
        Response<Video> response = Response.success(gson.fromJson(json, Video.class));

        Mockito.when(videosApi.getVideoById(VIDEO_ID)).thenReturn(response.body());

        final Video video = videosApi.getVideoById(VIDEO_ID);
        Assert.assertEquals(video.getId(), "v14567223");

        Channel channel = video.getChannel();
        Assert.assertEquals(44322889, channel.getId());

        Assert.assertThat(video.getCreatedAt(), IsInstanceOf.instanceOf(Date.class));
        Assert.assertEquals("Checkout Twitch Videos!", video.getDescription());
        Assert.assertTrue(video.getFps().size() == 3);
        Assert.assertTrue(video.getFps().containsKey("480p"));
    }
}
