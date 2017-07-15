/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.FollowedVideos;
import com.yobavu.jtwitch.model.Video;
import com.yobavu.jtwitch.model.TopVideos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
     *                       Default: all types (no filter).
     * @param language optional - constraint videos by language.
     *                 Example values: en, es.
     * @param sort optional - sort videos returned.
     *             Valid values:
     *                  time: videos are sorted by publication time, most recent first.
     *                  views: videos are sorted by view count, in descending order.
     */
    @GET("videos/top")
    Call<TopVideos> getTopVideos(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("game") String game,
                                 @Query("period") String period, @Query("broadcast_type") String broadcastType,
                                 @Query("language") String language, @Query("sort") String sort);

    /**
     * Gets the videos from channels followed by user.
     *
     * Requires "user_read" scope.
     *
     * @param limit optional - sets limit of results. Sorted by creation date.
     *              Default: 10.
     *              Max: 100.
     * @param offset optional - use for pagination of results.
     *               Default: 0.
     * @param broadcastType optional - constraint videos by type.
     *                       Valid values: archive, highlight, upload.
     *                       Default: all types (no filter).
     * @param language optional - constraint videos by language.
     *                 Example values: en, es.
     * @param sort optional - sort videos returned.
     *             Valid values:
     *                  time: videos are sorted by publication time, most recent first.
     *                  views: videos are sorted by view count, in descending order.
     */
    @GET("videos/followed")
    Call<FollowedVideos> getFollowedVideos(@Query("limit") Integer limit, @Query("offset") Integer offset,
                                           @Query("broadcast_type") String broadcastType, @Query("language") String language,
                                           @Query("sort") String sort);

    /**
     * Creates a new video in a channel.
     *
     * Requires "channel_editor" scope.
     *
     * @param channelId the id of channel.
     * @param videoTitle the title of video.
     * @param desc optional - description of video.
     * @param game optional - name of game in video.
     * @param language optional - language of video.
     *                 Example values: en, es.
     * @param tagList optional - tags describing the video. Max 100 char for a tag, 500 char total.
     *                Example values: walkthrough,shooter.
     * @param viewable optional - specifies who can view video.
     *                 Valid values: public, private.
     *                 Default: public.
     * @param viewableAt optional - date when video becomes public. Only applies if viewable=private.
     */
    @POST("videos")
    Call<Void> createVideo(@Query("channel_id") int channelId, @Query("title") String videoTitle, @Query("description") String desc,
                           @Query("game") String game, @Query("language") String language, @Query("tag_list") String tagList,
                           @Query("viewable") String viewable, @Query("viewable_at") String viewableAt);
}
