/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Twitch user.
 */
public class User {
    @SerializedName("_id")
    private int id;

    @SerializedName("bio")
    private String bio;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("email")
    private String email;

    @SerializedName("email_verified")
    private boolean emailVerified;

    @SerializedName("logo")
    private String logo;

    @SerializedName("name")
    private String name;

    @SerializedName("notifications")
    private Notifications notifications;

    @SerializedName("partnered")
    private boolean partnered;

    @SerializedName("twitter_connected")
    private boolean twitterConnected;

    @SerializedName("type")
    private String type;

    @SerializedName("updated_at")
    private String updatedAt;

    /**
     * Gets user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets user's bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Gets user's account creation date.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets user's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks if user's email is verified.
     */
    public boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Gets user's logo.
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Gets user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets user's notification settings.
     */
    public Notifications getNotifications() {
        return notifications;
    }

    /**
     * Check if user is Twitch partnered.
     */
    public boolean isPartnered() {
        return partnered;
    }

    /**
     * Check if user is connected to Twitter.
     */
    public boolean isTwitterConnected() {
        return twitterConnected;
    }

    /**
     * Gets user's account type.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets date when user's account was last updated.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    public class Notifications {
        @SerializedName("email")
        private boolean email;

        @SerializedName("push")
        private boolean push;

        /**
         * Check if user has email notifications.
         */
        public boolean emailNotification() {
            return email;
        }

        /**
         * Check if user has push notifications.
         */
        public boolean pushNotification() {
            return push;
        }
    }
}
