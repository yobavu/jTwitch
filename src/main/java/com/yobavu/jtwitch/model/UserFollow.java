/**
 * Created by Binh Vu (github: yobavu) on 4/2/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Channel Twitch user follows.
 */
public class UserFollow {
    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("notifications")
    private boolean notifications;

    @SerializedName("channel")
    private Channel channel;

    /**
     * Gets date follow was made on.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets notification status.
     */
    public boolean getNotifications() {
        return notifications;
    }

    /**
     * Gets followed channel information.
     */
    public Channel getChannel() {
        return channel;
    }


}
