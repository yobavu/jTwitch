/**
 * Created by Binh Vu (github: yobavu) on 6/23/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of videos from channels followed by user.
 */
public class FollowedVideos {
    @SerializedName("videos")
    private List<Video> videos;

    /**
     * Gets list of videos.
     */
    public List<Video> getVideos() {
        return videos;
    }
}
