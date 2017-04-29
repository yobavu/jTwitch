/**
 * Created by Binh Vu (github: yobavu) on 4/29/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of block records for a Twitch user.
 */
public class UserBlockList {
    @SerializedName("_total")
    private int total;

    @SerializedName("blocks")
    private List<UserBlock> blocks;

    /**
     * Gets total number of blocked accounts.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gets list of blocked accounts.
     */
    public List<UserBlock> getBlocks() {
        return blocks;
    }
}
