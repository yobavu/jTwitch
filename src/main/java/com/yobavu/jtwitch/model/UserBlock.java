/**
 * Created by Binh Vu (github: yobavu) on 4/29/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Block record for a Twitch user.
 */
public class UserBlock {
    @SerializedName("_id")
    private int id;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user")
    private User blockedUser;

    /**
     * Gets block record id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets updated date of block record.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Gets blocked Twitch channel.
     */
    public User getBlockedUser() {
        return blockedUser;
    }
}
