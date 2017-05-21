/**
 * Created by Binh Vu (github: yobavu) on 5/20/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A Twitch video.
 */
public class Video {
    @SerializedName("_id")
    private String id;

    @SerializedName("broadcast_id")
    private int broadcastId;

    @SerializedName("broadcast_type")
    private String broadcastType;

    @SerializedName("channel")
    private Channel channel;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("description")
    private String description;

    @SerializedName("description_html")
    private String descriptionHTML;

    @SerializedName("fps")
    private Map<String, Double> fps;

    @SerializedName("game")
    private String game;

    @SerializedName("language")
    private String language;

    @SerializedName("length")
    private int length;

    @SerializedName("muted_segments")
    private List<Map<String, Integer>> mutedSegments;

    @SerializedName("preview")
    private Map<String, String> preview;

    @SerializedName("published_at")
    private Date publishedAt;

    @SerializedName("resolutions")
    private Map<String, String> resolutions;

    @SerializedName("status")
    private String status;

    @SerializedName("tag_list")
    private String tagList;

    @SerializedName("thumbnails")
    private Map<String, List<Map<String, String>>> thumbnails;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("viewable")
    private String viewable;

    @SerializedName("viewable_at")
    private String viewableAt;

    @SerializedName("views")
    private int views;

    /**
     * Gets video's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets video's broadcast id.
     */
    public int getBroadcastId() {
        return broadcastId;
    }

    /**
     * Gets video's broadcasting type.
     */
    public String getBroadcastType() {
        return broadcastType;
    }

    /**
     * Gets channel video belongs to.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Gets date video was created on.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Get video's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get video's description as HTML.
     */
    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    /**
     * Get video's frames per seconds.
     */
    public Map<String, Double> getFps() {
        return fps;
    }

    /**
     * Get the game video is about.
     */
    public String getGame() {
        return game;
    }

    /**
     * Get video's language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Get length of video.
     */
    public int getLength() {
        return length;
    }

    /**
     * Get list of section that are muted.
     */
    public List<Map<String, Integer>> getMutedSegments() {
        return mutedSegments;
    }

    /**
     * Get video preview.
     */
    public Map<String, String> getPreview() {
        return preview;
    }

    /**
     * Get date video was published.
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Get available video resolutions.
     */
    public Map<String, String> getResolutions() {
        return resolutions;
    }

    /**
     * Get video status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get list of tags associated with video.
     */
    public String getTagList() {
        return tagList;
    }

    /**
     * Get video thumbnails of varying sizes.
     */
    public Map<String, List<Map<String, String>>> getThumbnails() {
        return thumbnails;
    }

    /**
     * Get video title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get video url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get viewable status of video.
     */
    public String getViewable() {
        return viewable;
    }

    /**
     * Get location of where video can be viewed.
     */
    public String getViewableAt() {
        return viewableAt;
    }

    /**
     * Get video's view count.
     */
    public int getViews() {
        return views;
    }
}
