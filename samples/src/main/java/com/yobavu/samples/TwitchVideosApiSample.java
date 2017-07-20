/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.api.TwitchFactory;
import com.yobavu.jtwitch.api.TwitchVideosApi;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.util.TwitchScope;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Sample showing how to make requests to the User endpoints.
 */
public class TwitchVideosApiSample {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("samples/src/main/resources/jtwitch.properties"));

        final String redirectUri = "http://localhost:8000/";
        final String clientId = prop.getProperty("twitch.clientId");
        final String clientSecret = prop.getProperty("twitch.clientSecret");

        final int dummyVideoId = 106400740;

        List<TwitchScope.SCOPES> scopes = new ArrayList<>();
        scopes.add(TwitchScope.SCOPES.USER_READ);
        scopes.add(TwitchScope.SCOPES.USER_SUBSCRIPTION);
        scopes.add(TwitchScope.SCOPES.USER_FOLLOWS_EDIT);
        scopes.add(TwitchScope.SCOPES.CHANNEL_EDITOR);

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri, scopes);

        TwitchToken twitchToken = oaa.authenticate("sampleUse");
        TwitchFactory factory = new TwitchFactory.Builder()
                .setClientId(clientId)
                .setAccessToken(twitchToken.getAccessToken())
                .build();
        TwitchVideosApi videosApi = factory.getInstance(TwitchVideosApi.class).build(factory.getClient());

        System.out.println("========== Making VIDEOS API request ==========");

        Video video = videosApi.getVideoById(dummyVideoId);
        System.out.println("Channel video belongs to: " + video.getChannel().getDisplayName());

        System.out.println("Videos from followed channels");
        List<Video> vids = videosApi.getFollowedVideos();

        for (Video v: vids) {
            System.out.println("Title: " + v.getTitle());
        }

        System.out.println("Creating and uploading video");
        Map<String, String> response = videosApi.createVideo(151146757, "Test video", null, null, null, null,
                TwitchVideosApi.VIEWABLE.PUBLIC, null);

        videosApi.uploadVideo(response.get("videoId"), "/Users/bvu/Downloads/Volans.mp4", response.get("token"));
    }
}
