/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of top Twitch videos.
 */
public class TopVideos {
    @SerializedName("vods")
    private List<Video> topVideos;

    /**
     * Gets list of top videos.
     */
    public List<Video> getTopVideos() {
        return topVideos;
    }
}
