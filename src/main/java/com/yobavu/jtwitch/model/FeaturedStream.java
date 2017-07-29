/**
 * Created by Binh Vu (github: yobavu) on 7/28/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * A Twitch featured stream.
 */
public class FeaturedStream {
    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private String image;

    @SerializedName("priority")
    private int priority;

    @SerializedName("scheduled")
    private boolean scheduled;

    @SerializedName("sponsored")
    private boolean sponsored;

    @SerializedName("stream")
    private Stream stream;

    /**
     * Gets featured stream title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets featured stream description.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets preview image for featured stream.
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets featured stream priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Check if featured stream is scheduled.
     */
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * Check if featured stream is sponsored.
     */
    public boolean isSponsored() {
        return sponsored;
    }

    /**
     * Gets associated stream.
     */
    public Stream getStream() {
        return stream;
    }
}
