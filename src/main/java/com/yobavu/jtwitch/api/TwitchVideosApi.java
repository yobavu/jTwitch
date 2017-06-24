/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.FollowedVideos;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.model.TopVideos;
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
    public TopVideos getTopVideos(Object... queryParams) throws IOException, TwitchApiException {
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

        Call<TopVideos> call = videoService.getTopVideos(limit, offset, game, period, broadcastType, language, sort);

        Response<TopVideos> response = call.execute();
        ErrorParser.checkForErrors(response);

        return response.body();
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
    public FollowedVideos getFollowedVideos(Object... queryParams) throws IOException, TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String broadcastType = null;
        String languages = null;
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
                languages = sb.toString();

                sb.setLength(0);
            }

            if (queryParams[4] != null) {
                sort = (String) queryParams[4];
            }
        }

        Call<FollowedVideos> call = videoService.getFollowedVideos(limit, offset, broadcastType, languages, sort);

        Response<FollowedVideos> response = call.execute();
        ErrorParser.checkForErrors(response);

        return response.body();
    }
}
