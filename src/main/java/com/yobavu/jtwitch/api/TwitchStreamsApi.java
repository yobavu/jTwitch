/**
 * Created by Binh Vu (github: yobavu) on 7/27/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.FeaturedStream;
import com.yobavu.jtwitch.model.Stream;
import com.yobavu.jtwitch.util.TwitchUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for the Twitch streams API.
 */
public class TwitchStreamsApi extends TwitchApi {
    private WebTarget webTarget;
    private Response response;
    private JsonParser parser;
    private JsonObject jsonObject;

    public TwitchStreamsApi() {
        super();
    }

    public TwitchStreamsApi build(Client client) {
        super.setClient(client);
        webTarget = client.target(super.getApiUrl());
        return this;
    }

    /**
     * Gets stream information for a specific channel. Returns null if channel is offline.
     *
     * @param channelId the channel id.
     * @param streamType optional constrains the type of streams to return. Valid values: live, playlist, all. Default: live.
     */
    public Stream getStreamByChannel(int channelId, String streamType) throws TwitchApiException {
        response = webTarget.path("streams/" + channelId).queryParam("stream_type", streamType).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        return (jsonObject.get("stream").isJsonNull()) ? null : super.getGson().fromJson(jsonObject.get("stream"), Stream.class);
    }

    /**
     * Gets a list of live streams.
     *
     * @param channelIds optional list of channel ids.
     * @param game optional name of game of the streams.
     * @param languageList optional list of languages. E.g en, es.
     * @param streamType optional constrains the type of streams to return. Valid values: live, playlist, all. Default: live.
     * @param limit optional max number of streams to return, sorted by number of views. Default: 25, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     */
    public List<Stream> getLiveStreams(List<Integer> channelIds, String game, List<String> languageList, String streamType,
                                       Integer limit, Integer offset) throws TwitchApiException {
        String channels = TwitchUtil.stringifyList(channelIds);
        String languages = TwitchUtil.stringifyList(languageList);

        response = webTarget.path("streams").queryParam("channel", channels).queryParam("game", game)
                    .queryParam("language", languages).queryParam("stream_type", streamType)
                    .queryParam("limit", limit).queryParam("offset", offset)
                    .request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray streams = jsonObject.getAsJsonArray("streams");
        List<Stream> streamList = new ArrayList<>();

        for(int i = 0; i < streams.size(); i++) {
            streamList.add(super.getGson().fromJson(streams.get(i), Stream.class));
        }

        return streamList;
    }

    /**
     * Gets a summary of live streams.
     *
     * @param game optional name of game of the streams.
     */
    public Map<String, Integer> getStreamsSummary(String game) throws TwitchApiException {
        response = webTarget.path("streams/summary").queryParam("game", game).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        Map<String, Integer> data = new HashMap<>();
        data.put("channels", jsonObject.get("channels").getAsInt());
        data.put("viewers", jsonObject.get("viewers").getAsInt());

        return data;
    }

    /**
     * Gets list of featured live streams.
     *
     * @param limit optional max number of featured streams to return, sorted by priority. Default: 25, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     */
    public List<FeaturedStream> getFeaturedStreams(Integer limit, Integer offset) throws TwitchApiException {
        response = webTarget.path("streams/featured").queryParam("limit", limit).queryParam("offset", offset)
                    .request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray featuredStreams = jsonObject.getAsJsonArray("featured");
        List<FeaturedStream> featuredStreamList = new ArrayList<>();

        for(int i = 0; i < featuredStreams.size(); i++) {
            featuredStreamList.add(super.getGson().fromJson(featuredStreams.get(i), FeaturedStream.class));
        }

        return featuredStreamList;
    }

    /**
     * Gets list of streams a user is following. User is associated with current OAuth token.
     *
     * Requires "user_read" scope.
     *
     * @param streamType optional constrains the type of streams to return. Valid values: live, playlist, all. Default: live.
     * @param limit optional max number of featured streams to return, sorted by priority. Default: 25, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     */
    public List<Stream> getFollowedStreams(String streamType, Integer limit, Integer offset) throws TwitchApiException {
        response = webTarget.path("streams/followed").queryParam("stream_type", streamType).queryParam("limit", limit)
                    .queryParam("offset", offset).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray followedStreams = jsonObject.getAsJsonArray("streams");
        List<Stream> followedStreamsList = new ArrayList<>();

        for(int i = 0; i < followedStreams.size(); i++) {
            followedStreamsList.add(super.getGson().fromJson(followedStreams.get(i), Stream.class));
        }

        return followedStreamsList;
    }
}
