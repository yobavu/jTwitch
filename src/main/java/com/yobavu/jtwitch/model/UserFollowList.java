/**
 * Created by Binh Vu (github: yobavu) on 3/28/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of channels Twitch user follows.
 */
public class UserFollowList {
    @SerializedName("_total")
    private int total;

    @SerializedName("follows")
    private List<UserFollow> follows;

    /**
     * Gets total number of channels followed.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gets list of followed channels.
     */
    public List<UserFollow> getFollows() {
        return follows;
    }
}
