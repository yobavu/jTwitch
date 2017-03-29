/**
 * Created by Binh Vu (github: yobavu) on 3/28/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of channels Twitch user follows.
 */
public class UserFollows {
    @SerializedName("_total")
    private int total;

    @SerializedName("follows")
    private List<Follows> follows;

    /**
     * Gets total number of channels followed.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gets list of followed channels.
     */
    public List<Follows> getFollows() {
        return follows;
    }

    public class Follows {
        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("notifications")
        private boolean notifications;

        @SerializedName("channel")
        private Channel channel;

        /**
         * Gets date channel was followed.
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * Check if notification is set.
         */
        public boolean notifications() {
            return notifications;
        }

        /**
         * Gets the followed channel.
         */
        public Channel getChannel() {
            return channel;
        }
    }
}
