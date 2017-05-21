/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.services;

import com.yobavu.jtwitch.model.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
}
