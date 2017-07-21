/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.Video;

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

        String json = response.readEntity(String.class);
        return super.getGson().fromJson(json, Video.class);
    }

    /**
     * Gets the top videos based on view count.
     *
     * @param queryParams optional set of parameters:
     *        <ul>
     *            <li>
     *                limit: sets limit of results. Default: 10 and max: 100.
     *            </li>
     *            <li>
     *                offset: used for pagination of results. Default: 0.
     *            </li>
     *            <li>
     *                game: constraints videos by games.
     *            </li>
     *            <li>
     *                period: specifies the window of time to search. Valid values: week, month, all. Default: week
     *            </li>
     *            <li>
     *                broadcastTypes: constraints videos by type. Valid values: archive, highlight, upload.
     *                                Default: all types (no filter).
     *            </li>
     *            <li>
     *                language: constraints videos by language. Example values: en, es.
     *            </li>
     *            <li>
     *                sort: sort videos returned: Valid values:
     *                   <ul>
     *                      <li>
     *                         time: videos are sorted by publication time, most recent first.
     *                      </li>
     *                      <li>
     *                         views: videos are sorted by view count, in descending order.
     *                      </li>
     *                   </ul>
     *            </li>
     *        </ul>
     */
    public List<Video> getTopVideos(Object... queryParams) throws TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String game = null;
        String period = null;
        String broadcastType = null;
        String language = null;
        String sort = null;

        List<String> languageList = null;
        List<String> broadcastTypeList = null;

        StringBuilder sb;

        if (queryParams.length > 7) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

        if (queryParams.length > 0) {
            sb = new StringBuilder();

            // check for optional parameters
            if (queryParams[0] != null) {
                limit = (Integer) queryParams[0];
            }

            if (queryParams[1] != null) {
                offset = (Integer) queryParams[1];
            }

            if (queryParams[2] != null) {
                game = (String) queryParams[2];
            }

            if (queryParams[3] != null) {
                period = (String) queryParams[3];
            }

            if (queryParams[4] != null) {
                broadcastTypeList = (List<String>) queryParams[4];

                for (String s : broadcastTypeList) {
                    sb.append(s);
                    sb.append(",");
                }

                // cut out last comma
                sb.deleteCharAt(sb.length() - 1);
                broadcastType = sb.toString();

                sb.setLength(0);
            }

            if (queryParams[5] != null) {
                languageList = (List<String>) queryParams[5];

                for (String s : languageList) {
                    sb.append(s);
                    sb.append(",");
                }

                sb.deleteCharAt(sb.length() - 1);
                language = sb.toString();
            }

            if (queryParams[6] != null) {
                sort = (String) queryParams[6];
            }
        }

        response = webTarget.path("videos/top").queryParam("limit", limit).queryParam("offset", offset)
                    .queryParam("game", game).queryParam("period", period).queryParam("broadcast_type", broadcastType)
                    .queryParam("language", language).queryParam("sort", sort).request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

        JsonArray videos = jsonObject.get("vods").getAsJsonArray();
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
     * @param queryParams optional set of parameters:
     *        <ul>
     *            <li>
     *                limit: sets limit of results. Default: 10 and max: 100.
     *            </li>
     *            <li>
     *                offset: used for pagination of results. Default: 0.
     *            </li>
     *            <li>
     *                broadcastTypes: constraints videos by type. Valid values: archive, highlight, upload.
     *                                Default: all types (no filter).
     *            </li>
     *            <li>
     *                language: constraints videos by language. Example values: en, es.
     *            </li>
     *            <li>
     *                sort: sort videos returned: Valid values:
     *                   <ul>
     *                      <li>
     *                         time: videos are sorted by publication time, most recent first.
     *                      </li>
     *                      <li>
     *                         views: videos are sorted by view count, in descending order.
     *                      </li>
     *                   </ul>
     *            </li>
     *        </ul>
     */
    public List<Video> getFollowedVideos(Object... queryParams) throws TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String broadcastType = null;
        String language = null;
        String sort = null;

        List<String> broadcastTypeList;
        List<String> languageList;

        StringBuilder sb;

        if (queryParams.length > 5) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

        if (queryParams.length > 0) {
            sb = new StringBuilder();

            if (queryParams[0] != null) {
                limit = (Integer) queryParams[0];
            }

            if (queryParams[1] != null) {
                offset = (Integer) queryParams[1];
            }

            if (queryParams[2] != null) {
                broadcastTypeList = (List<String>) queryParams[2];

                for (String s: broadcastTypeList) {
                    sb.append(s);
                    sb.append(",");
                }

                sb.deleteCharAt(sb.length() - 1);
                broadcastType = sb.toString();

                // clear out buffer
                sb.setLength(0);
            }

            if (queryParams[3] != null) {
                languageList = (List<String>) queryParams[3];

                for (String s: languageList) {
                    sb.append(s);
                    sb.append(",");
                }

                sb.deleteCharAt(sb.length() - 1);
                language = sb.toString();

                sb.setLength(0);
            }

            if (queryParams[4] != null) {
                sort = (String) queryParams[4];
            }
        }

        response = webTarget.path("videos/followed").queryParam("limit", limit).queryParam("offset", offset)
                .queryParam("broadcast_type", broadcastType).queryParam("language", language)
                .queryParam("sort", sort).request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

        JsonArray videos = jsonObject.get("videos").getAsJsonArray();
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
     * @param language optional language(s) of video. Example values: en, es.
     * @param tagList optional tag(s) describing the video. Max 100 char for a tag, 500 char total. Example values: walkthrough,shooter.
     * @param viewable optional specifies who can view video. Valid values: public, private. Default: public.
     * @param viewableAt optional date when video becomes public. Only applies if viewable=private.
     */
    public Map<String, String> createVideo(int channelId, String videoTitle, String description, String game,
                                           List<String> language, List<String> tagList, VIEWABLE viewable,
                                           Date viewableAt) throws TwitchApiException {
        if (viewable == null) {
            viewable = VIEWABLE.PUBLIC;
        }

        response = webTarget.path("videos").queryParam("channel_id", channelId).queryParam("title", videoTitle)
                    .queryParam("description", description).queryParam("game", game).queryParam("language", language)
                    .queryParam("tag_list", tagList).queryParam("viewable", viewable.value).queryParam("viewable_at", viewableAt)
                    .request().post(Entity.text(""));
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

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
        while ((bytesRead = is.read(data)) != -1) {
            // need to set target to uploads.api http
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
}
