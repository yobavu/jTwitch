/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.util.TwitchUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for the Twitch videos API.
 */
public class TwitchVideosApi extends TwitchApi {
    private WebTarget webTarget;
    private Response response;
    private JsonParser parser;
    private JsonObject jsonObject;

    public enum VIEWABLE {
        PRIVATE("private"), PUBLIC("public");

        String value;

        VIEWABLE(String value) {
            this.value = value;
        }
    }

    public TwitchVideosApi() {
        super();
    }

    public TwitchVideosApi build(Client client) {
        super.setClient(client);
        webTarget = client.target(super.getApiUrl());
        return this;
    }

    /**
     * Get specific video.
     *
     * @param videoId the id for video.
     */
    public Video getVideoById(int videoId) throws TwitchApiException {
        response = webTarget.path("videos/" + videoId).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        return super.getGson().fromJson(responseJson, Video.class);
    }

    /**
     * Gets the top videos based on view count.
     *
     * @param limit optional max number of videos to return, sorted by creation date. Default: 10, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     * @param game optional constraints videos returned by games.
     * @param period optional specifies the window of time to search. Valid values: week, month, all. Default: week.
     * @param broadcastList optional constraints videos by type. Valid values: archive, highlight, upload. Default: no filters.
     * @param languageList optional constraints videos by language. Example values: en, es.
     * @param sort optional sort videos returned. Valid values: time (sorted by publication time - most recent first) and views (sorted by view count - descending).
     */
    public List<Video> getTopVideos(Integer limit, Integer offset, String game, String period, List<String> broadcastList,
                                    List<String> languageList, String sort) throws TwitchApiException {
        String broadcastTypes = TwitchUtil.stringifyList(broadcastList);
        String languages = TwitchUtil.stringifyList(languageList);

        response = webTarget.path("videos/top").queryParam("limit", limit).queryParam("offset", offset)
                    .queryParam("game", game).queryParam("period", period).queryParam("broadcast_type", broadcastTypes)
                    .queryParam("language", languages).queryParam("sort", sort).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray videos = jsonObject.getAsJsonArray("vods");
        List<Video> videoList = new ArrayList<>();

        for (int i = 0; i < videos.size(); i++) {
            videoList.add(super.getGson().fromJson(videos.get(i), Video.class));
        }

        return videoList;
    }

    /**
     * Gets the videos from channels followed by user.
     *
     * Requires "user_read" scope.
     *
     * @param limit optional max number of videos to return, sorted by creation date. Default: 10, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     * @param broadcastList optional constraints videos by type. Valid values: archive, highlight, upload. Default: no filters.
     * @param languageList optional constraints videos by language. Example values: en, es.
     * @param sort optional sort videos returned. Valid values: time (sorted by publication time - most recent first) and views (sorted by view count - descending).
     */
    public List<Video> getFollowedVideos(Integer limit, Integer offset, List<String> broadcastList, List<String> languageList,
                                         String sort) throws TwitchApiException {
        String broadcastTypes = TwitchUtil.stringifyList(broadcastList);
        String languages = TwitchUtil.stringifyList(languageList);

        response = webTarget.path("videos/followed").queryParam("limit", limit).queryParam("offset", offset)
                .queryParam("broadcast_type", broadcastTypes).queryParam("language", languages)
                .queryParam("sort", sort).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray videos = jsonObject.getAsJsonArray("videos");
        List<Video> videoList = new ArrayList<>();

        for (int i = 0; i < videos.size(); i++) {
            videoList.add(super.getGson().fromJson(videos.get(i), Video.class));
        }

        return videoList;
    }

    /**
     * Creates a new video in a channel.
     *
     * Requires "channel_editor" scope.
     *
     * @param channelId the id of channel.
     * @param videoTitle the title of video.
     *
     * @param description optional description of video.
     * @param game optional name of game in video.
     * @param languageList optional language(s) of video. Example values: en, es.
     * @param tagList optional tag(s) describing the video. Max 100 char for a tag, 500 char total. Example values: walkthrough,shooter.
     * @param viewable optional specifies who can view video. Valid values: public, private. Default: public.
     * @param viewableAt optional date when video becomes public. Only applies if viewable=private.
     */
    public Map<String, String> createVideo(int channelId, String videoTitle, String description, String game,
                                           List<String> languageList, List<String> tagList, VIEWABLE viewable,
                                           Date viewableAt) throws TwitchApiException {
        String languages = TwitchUtil.stringifyList(languageList);
        String tags = TwitchUtil.stringifyList(tagList);

        response = webTarget.path("videos").queryParam("channel_id", channelId).queryParam("title", videoTitle)
                    .queryParam("description", description).queryParam("game", game).queryParam("language", languages)
                    .queryParam("tag_list", tags).queryParam("viewable", viewable.value).queryParam("viewable_at", viewableAt)
                    .request().post(Entity.text(""));
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        Video video = super.getGson().fromJson(jsonObject.get("video"), Video.class);

        Map<String, String> data = new HashMap<>();
        data.put("token", jsonObject.get("upload").getAsJsonObject().get("token").getAsString());
        data.put("url", jsonObject.get("upload").getAsJsonObject().get("url").getAsString());
        data.put("videoId", video.getId());

        return data;
    }

    /**
     * Upload video to twitch. This method works in two parts:
     *
     *  1. Upload video in parts. Each video part except the last part must be at least 5 MB and at most 25 MB.
     *  2. Complete upload once all parts have been uploaded.
     *
     * @param videoId the id of video. Id can be obtained by calling {@link #createVideo} beforehand.
     * @param filePath the path to video file.
     * @param uploadToken the upload token to use. Token can be obtained by calling {@link #createVideo} beforehand.
     */
    public void uploadVideo(String videoId, String filePath, String uploadToken) throws IOException, TwitchApiException {
        // set upload chunksize to 10MB
        int chunkSize = 10 * 1024 * 1024;
        // index the video part
        int index = 1, bytesRead = 0;

        File videoFile = new File(filePath);
        // read in file by chunks
        FileInputStream is = new FileInputStream(videoFile);
        byte[] data = new byte[chunkSize];

        WebTarget uploadWebTarget = super.getClient().target("https://uploads.twitch.tv/");

        // read() returns -1 when end of file
        // todo make this async?
        while ((bytesRead = is.read(data)) != -1) {
            response = uploadWebTarget.path("upload/" + videoId).queryParam("part", index).queryParam("upload_token", uploadToken)
                        .request()
                        .header("Content-Length", chunkSize)
                        .put(Entity.entity(data, MediaType.APPLICATION_OCTET_STREAM));
            ErrorParser.checkForErrors(response);
            index++;
        }

        is.close();

        // complete video upload
        response = uploadWebTarget.path("upload/" + videoId).path("complete").queryParam("upload_token", uploadToken).request().post(Entity.text(""));
        ErrorParser.checkForErrors(response);
    }

    /**
     * Updates information about a specific video that was already created.
     *
     * Requires "channel_editor" scope.
     *
     * @param videoId the id of video.
     * @param videoTitle the title of video. Max of 100 char.
     *
     * @param description optional short description of video.
     * @param game optional name of game in video.
     * @param languageList optional language(s) of video. Example values: en, es.
     * @param tagList optional tag(s) describing the video. Max 100 char for a tag, 500 char total. Example values: walkthrough,shooter.
     */
    public Video updateVideo(String videoId, String videoTitle, String description, String game,
                             List<String> languageList, List<String> tagList) throws TwitchApiException {
        String languages = TwitchUtil.stringifyList(languageList);
        String tags = TwitchUtil.stringifyList(tagList);

        // substring used because videoId is in format: v########...
        // so strip out the first letter
        response = webTarget.path("videos/" + videoId.substring(1)).queryParam("title", videoTitle).queryParam("description", description)
                    .queryParam("game", game).queryParam("language", languages).queryParam("tag_list", tags)
                    .request().put(Entity.text(""));
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        return super.getGson().fromJson(responseJson, Video.class);
    }

    /**
     * Deletes a specified video.
     *
     * Requires "channel_editor" scope.
     *
     * @param videoId the id of video.
     */
    public void deleteVideo(String videoId) throws TwitchApiException {
        response = webTarget.path("videos/" + videoId.substring(1)).request().delete();
        ErrorParser.checkForErrors(response);
    }
}
