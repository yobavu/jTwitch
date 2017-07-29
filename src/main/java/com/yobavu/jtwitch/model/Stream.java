/**
 * Created by Binh Vu (github: yobavu) on 7/27/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

/**
 * A Twitch stream.
 */
public class Stream {
    @SerializedName("_id")
    private long id;

    @SerializedName("game")
    private String game;

    @SerializedName("viewers")
    private int viewers;

    @SerializedName("video_height")
    private int videoHeight;

    @SerializedName("average_fps")
    private int averageFps;

    @SerializedName("delay")
    private int delay;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("is_playlist")
    private boolean playlist;

    @SerializedName("preview")
    private Map<String, String> preview;

    @SerializedName("channel")
    private Channel channel;

    /**
     * Gets stream id.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets game stream is about.
     */
    public String getGame() {
        return game;
    }

    /**
     * Gets number of viewers.
     */
    public int getViewers() {
        return viewers;
    }

    /**
     * Gets height of video.
     */
    public int getVideoHeight() {
        return videoHeight;
    }

    /**
     * Gets average frame per seconds.
     */
    public int getAverageFps() {
        return averageFps;
    }

    /**
     * Gets delay.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Gets date stream was created on.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Check if stream is a playlist.
     */
    public boolean isPlaylist() {
        return playlist;
    }

    /**
     * Gets stream preview images.
     */
    public Map<String, String> getPreview() {
        return preview;
    }

    /**
     * Gets channel stream is associated with.
     */
    public Channel getChannel() {
        return channel;
    }
}
