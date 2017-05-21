/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.model.VideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Represents the Twitch videos API.
 */
public interface VideoService {
    /**
     * Get specific video.
     *
     * @param videoId the id for video.
     */
    @GET("videos/{videoId}")
    Call<Video> getVideoById(@Path("videoId") int videoId);

    /**
     * Gets the top videos based on view count.
     *
     * @param limit optional - sets limit of results.
     *              Default: 10.
     *              Max: 100.
     * @param offset optional - use for pagination of results.
     *               Default: 0.
     * @param game optional - constraint videos by games.
     * @param period optional - specifies the window of time to search.
     *               Valid values: week, month, all.
     *               Default: week.
     * @param broadcastType optional - constraint videos by type.
     *                       Valid values: archive, highlight, upload.
     *                       Default: all types (no filter)
     * @param language optional - constraint videos by language.
     *                 Example values: en, es.
     * @param sort optional - sort videos returned.
     *             Valid values:
     *                  time: videos are sorted by publication time, most recent first.
     *                  views: videos are sorted by view count, in descending order.
     */
    @GET("videos/top")
    Call<VideoList> getTopVideos(@Query("limit") int limit, @Query("offset") int offset, @Query("game") String game,
                                 @Query("period") String period, @Query("broadcast_type") String broadcastType,
                                 @Query("language") String language, @Query("sort") String sort);
}
