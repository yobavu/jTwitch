/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.model.Channel;
import com.yobavu.jtwitch.model.Video;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TwitchVideosApiTest {
    private Gson gson;
    private JsonParser parser;
    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private TwitchVideosApi videosApi;

    private static final int VIDEO_ID = 14567223;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        gson = new Gson();
        parser = new JsonParser();
        videosApi = Mockito.mock(TwitchVideosApi.class);
    }

    @Test
    public void testGetVideoById() throws Exception {
        final String json = "{" +
                "   \"_id\": \"v14567223\"," +
                "   \"broadcast_id\": 1," +
                "   \"broadcast_type\": \"upload\"," +
                "   \"channel\": {" +
                "      \"_id\": \"44322889\"," +
                "      \"name\": \"dallas\"," +
                "      \"display_name\": \"dallas\"" +
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
                "       {" +
                "           \"duration\": 360," +
                "           \"offset\": 5220" +
                "       }," +
                "       {" +
                "           \"duration\": 360," +
                "           \"offset\": 7020" +
                "       }" +
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

        Video response = gson.fromJson(json, Video.class);

        Mockito.when(videosApi.getVideoById(VIDEO_ID)).thenReturn(response);

        final Video video = videosApi.getVideoById(VIDEO_ID);
        Assert.assertEquals(video.getId(), "v14567223");

        Channel channel = video.getChannel();
        Assert.assertEquals(44322889, channel.getId());

        Assert.assertThat(video.getCreatedAt(), IsInstanceOf.instanceOf(Date.class));
        Assert.assertEquals("Checkout Twitch Videos!", video.getDescription());
        Assert.assertTrue(video.getFps().size() == 3);
        Assert.assertTrue(video.getFps().containsKey("480p"));
    }

    @Test
    public void testGetTopVideos() throws Exception {
        final String json = "{" +
                "   \"vods\": [" +
                "      {" +
                "         \"_id\": \"v14567223\"," +
                "         \"broadcast_id\": 1," +
                "         \"broadcast_type\": \"upload\"," +
                "         \"channel\": {" +
                "            \"_id\": \"44322889\"," +
                "            \"name\": \"dallas\"," +
                "            \"display_name\": \"dallas\"" +
                "         }," +
                "         \"created_at\": \"2016-12-10T00:46:44Z\"," +
                "         \"description\": \"Checkout Twitch Videos!\"," +
                "         \"fps\": {" +
                "            \"1080p\": 23.9767661758746," +
                "            \"480p\": 23.8767661758746," +
                "            \"720p\": 23.7767661758746" +
                "         }," +
                "         \"language\": \"en\"," +
                "         \"length\": 29," +
                "         \"published_at\": \"2016-12-12T18:19:18Z\"," +
                "         \"resolutions\": {" +
                "            \"1080p\": \"1920x1080\"" +
                "         }," +
                "         \"status\": \"recorded\"," +
                "         \"title\": \"Introducing Twitch Video\"," +
                "         \"url\": \"https://www.twitch.tv/twitch/v/14567223\"," +
                "         \"viewable\": \"public\"," +
                "         \"viewable_at\": null," +
                "         \"views\": 7638" +
                "      }," +
                "      {" +
                "         \"_id\": \"v24567223\"," +
                "         \"broadcast_id\": 2," +
                "         \"broadcast_type\": \"highlight\"," +
                "         \"channel\": {" +
                "            \"_id\": \"44322889\"," +
                "            \"name\": \"dallas\"," +
                "            \"display_name\": \"dallas\"" +
                "         }," +
                "         \"created_at\": \"2016-12-15T00:46:44Z\"," +
                "         \"description\": \"Checkout More Twitch Videos!\"," +
                "         \"fps\": {" +
                "            \"1080p\": 23.9767661758746," +
                "            \"480p\": 23.8767661758746," +
                "            \"720p\": 23.7767661758746" +
                "         }," +
                "         \"language\": \"en\"," +
                "         \"length\": 15," +
                "         \"published_at\": \"2016-12-15T18:19:18Z\"," +
                "         \"resolutions\": {" +
                "            \"1080p\": \"1920x1080\"" +
                "         }," +
                "         \"status\": \"recorded\"," +
                "         \"title\": \"Introducing More Twitch Video\"," +
                "         \"url\": \"https://www.twitch.tv/twitch/v/24567223\"," +
                "         \"viewable\": \"public\"," +
                "         \"viewable_at\": null," +
                "         \"views\": 638" +
                "      }" +
                "   ]" +
                "}";

        jsonObject = parser.parse(json).getAsJsonObject();
        jsonArray = jsonObject.get("vods").getAsJsonArray();

        List<Video> response = new ArrayList<>();

        for(int i = 0; i < jsonArray.size(); i++) {
            response.add(gson.fromJson(jsonArray.get(i), Video.class));
        }

        Mockito.when(videosApi.getTopVideos()).thenReturn(response);

        final List<Video> videoList = videosApi.getTopVideos();
        Assert.assertEquals(2, videoList.size());

        Video video1 = videoList.get(0);
        Video video2 = videoList.get(1);

        Assert.assertEquals(video1.getId(), "v14567223");
        Assert.assertEquals(video2.getId(), "v24567223");
        Assert.assertNotEquals(video1.getBroadcastId(), video2.getBroadcastId());
    }

    @Test
    public void testGetFollowedVideos() throws Exception {
        final String json = "{" +
                "   \"videos\": [{" +
                "      \"_id\": \"v107666453\"," +
                "      \"broadcast_id\": 23939865056," +
                "      \"broadcast_type\": \"archive\"," +
                "      \"channel\": {" +
                "         \"_id\": 14836307," +
                "         \"display_name\": \"TrumpSC\"," +
                "         \"name\": \"trumpsc\"" +
                "      }," +
                "      \"created_at\": \"2016-12-15T20:33:02Z\"," +
                "      \"description\": null," +
                "      \"description_html\": null," +
                "      \"fps\": {\n" +
                "         \"mobile\": 26.25" +
                "      }," +
                "      \"game\": \"Hearthstone: Heroes of Warcraft\"," +
                "      \"language\": \"en\"," +
                "      \"length\": 6368," +
                "      \"published_at\": \"2016-12-15T20:33:02Z\"," +
                "      \"resolutions\": {" +
                "         \"low\": \"640x360\"," +
                "         \"medium\": \"852x480\"," +
                "         \"mobile\": \"400x226\"" +
                "      },\n" +
                "      \"status\": \"recording\"," +
                "      \"thumbnails\": {" +
                "         \"large\": [{" +
                "            \"type\": \"generated\"," +
                "            \"url\": \"https://static-cdn.jtvnw.net/v1/AUTH_system/vods_631b/trumpsc_23939865056_564912068/thumb/thumb0-640x360.jpg\"" +
                "         }]," +
                "         \"medium\": [{" +
                "            \"type\": \"generated\"," +
                "            \"url\": \"https://static-cdn.jtvnw.net/v1/AUTH_system/vods_631b/trumpsc_23939865056_564912068/thumb/thumb0-320x180.jpg\"" +
                "         }]" +
                "      }," +
                "      \"title\": \"TSM Trump Renolock\"," +
                "      \"url\": \"https://www.twitch.tv/trumpsc/v/107666453\"," +
                "      \"viewable\": \"public\"," +
                "      \"views\": 10" +
                "   }]" +
                "}";

        jsonObject = parser.parse(json).getAsJsonObject();
        jsonArray = jsonObject.get("videos").getAsJsonArray();

        List<Video> response = new ArrayList<>();
        response.add(gson.fromJson(jsonArray.get(0), Video.class));

        Mockito.when(videosApi.getFollowedVideos()).thenReturn(response);

        List<Video> videoList = videosApi.getFollowedVideos();
        Assert.assertTrue(videoList.size() == 1);

        Video video = videoList.get(0);
        Assert.assertEquals("TSM Trump Renolock", video.getTitle());
    }
}
