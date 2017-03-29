/**
 * Created by Binh Vu (github: yobavu) on 3/27/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Twitch user's subscription.
 */
public class UserSubscription {
    @SerializedName("_id")
    private int id;

    @SerializedName("channel")
    private Channel channel;

    @SerializedName("created_at")
    private String createdAt;

    /**
     * Gets subscription id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets subscribed channel information.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Gets date subscription was created on.
     */
    public String getCreatedAt() {
        return createdAt;
    }
}
