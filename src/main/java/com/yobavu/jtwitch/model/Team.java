/**
 * Created by Binh Vu (github: yobavu) on 7/23/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * A Twitch team.
 */
public class Team {
    @SerializedName("_id")
    private int id;

    @SerializedName("background")
    private String background;

    @SerializedName("banner")
    private String banner;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("info")
    private String info;

    @SerializedName("logo")
    private String logo;

    @SerializedName("name")
    private String name;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("users")
    private List<User> users;

    /**
     * Gets team's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets team's background.
     */
    public String getBackground() {
        return background;
    }

    /**
     * Gets team's banner.
     */
    public String getBanner() {
        return banner;
    }

    /**
     * Get date team was created on.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets team's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets team's information.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Gets team's logo.
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Gets team's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets date team was last updated on.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Gets users belonging to team.
     */
    public List<User> getUsers() {
        return users;
    }
}
