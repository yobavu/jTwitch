/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.model.VideoList;
import com.yobavu.jtwitch.services.VideoService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Wrapper for the Twitch videos API.
 */
public class TwitchVideosApi extends TwitchApi {
    private VideoService videoService;

    public TwitchVideosApi(OkHttpClient.Builder clientBuilder) {
        super();
        videoService = new Retrofit.Builder()
                .baseUrl(super.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create(super.gson))
                .client(clientBuilder.build())
                .build()
                .create(VideoService.class);
    }

    /**
     * Get specific video.
     *
     * @param videoId the id for video.
     */
    public Video getVideoById(int videoId) throws IOException, TwitchApiException {
        Call<Video> call = videoService.getVideoById(videoId);

        Response<Video> response = call.execute();
        ErrorParser.checkForErrors(response);

        return response.body();
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
    public VideoList getTopVideos(Object... queryParams) throws IOException, TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String game = null;
        String period = null;
        String broadcastType = null;
        List<String> broadcastTypeList = null;
        String language = null;
        List<String> languageList = null;
        String sort = null;

        if (queryParams.length > 7) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

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
            StringBuilder sb = new StringBuilder();

            for (String s : broadcastTypeList) {
                sb.append(s);
                sb.append(",");
            }

            // cut out last comma
            sb.deleteCharAt(sb.length() - 1);
            broadcastType = sb.toString();
        }

        if (queryParams[5] != null) {
            languageList = (List<String>) queryParams[5];
            StringBuilder sb = new StringBuilder();

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

        Call<VideoList> call = videoService.getTopVideos(limit, offset, game, period, broadcastType, language, sort);

        Response<VideoList> response = call.execute();
        ErrorParser.checkForErrors(response);

        return response.body();
    }
}
