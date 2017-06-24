/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.samples;

import com.yobavu.jtwitch.api.TwitchFactory;
import com.yobavu.jtwitch.api.TwitchVideosApi;
import com.yobavu.jtwitch.model.FollowedVideos;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.oauth.OAuth2Authenticate;
import com.yobavu.jtwitch.oauth.TwitchToken;
import com.yobavu.jtwitch.util.TwitchScope;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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

        List<TwitchScope.SCOPES> scopes = new ArrayList<>();
        scopes.add(TwitchScope.SCOPES.USER_READ);
        scopes.add(TwitchScope.SCOPES.USER_SUBSCRIPTION);
        scopes.add(TwitchScope.SCOPES.USER_FOLLOWS_EDIT);

        OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri, scopes);

        TwitchToken twitchToken = oaa.authenticate("sampleUse");
        TwitchFactory factory = new TwitchFactory.Builder()
                .setClientId(clientId)
                .setAccessToken(twitchToken.getAccessToken())
                .build();
        TwitchVideosApi videosApi = (TwitchVideosApi) factory.getInstance(TwitchFactory.API.Videos);

        System.out.println("========== Making VIDEOS API request ==========");

        Video video = videosApi.getVideoById(145467223);
        System.out.println("Channel video belongs to: " + video.getChannel().getDisplayName());

        System.out.println("Videos from followed channels");
        FollowedVideos vids = videosApi.getFollowedVideos();

        for (Video v: vids.getVideos()) {
            System.out.println("Title: " + v.getTitle());
        }
    }
}
