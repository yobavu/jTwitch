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

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String errorMessage;

    @SerializedName("status")
    private int status;

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

    /**
     * Gets error status.
     */
    public String getError() {
        return error;
    }

    /**
     * Gets error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets response status code.
     */
    public int getStatus() {
        return status;
    }

    public class Channel {
        @SerializedName("_id")
        private int id;

        @SerializedName("broadcaster_language")
        private String broadcasterLanguage;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("display_name")
        private String displayName;

        @SerializedName("followers")
        private int followers;

        @SerializedName("game")
        private String game;

        @SerializedName("language")
        private String language;

        @SerializedName("logo")
        private String logo;

        @SerializedName("mature")
        private boolean mature;

        @SerializedName("name")
        private String name;

        @SerializedName("partner")
        private boolean partner;

        @SerializedName("profile_banner")
        private String profileBanner;

        @SerializedName("profile_banner_background_color")
        private String profileBannerBackgroundColor;

        @SerializedName("status")
        private String status;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("url")
        private String url;

        @SerializedName("video_banner")
        private String videoBanner;

        @SerializedName("views")
        private int views;

        /**
         * Gets channel id.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets channel broadcaster's spoken language.
         */
        public String getBroadcasterLanguage() {
            return broadcasterLanguage;
        }

        /**
         * Gets channel creation date.
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * Gets channel's display name.
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Gets channel's number of followers.
         */
        public int getFollowers() {
            return followers;
        }

        /**
         * Gets game that channel is about.
         */
        public String getGame() {
            return game;
        }

        /**
         * Gets channel's language.
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Gets channel's logo.
         */
        public String getLogo() {
            return logo;
        }

        /**
         * Check if channel is mature rating.
         */
        public boolean isMature() {
            return mature;
        }

        /**
         * Gets channel's name.
         */
        public String getName() {
            return name;
        }

        /**
         * Check if channel is Twitch partner.
         */
        public boolean isPartner() {
            return partner;
        }

        /**
         * Gets channel's banner.
         */
        public String getProfileBanner() {
            return profileBanner;
        }

        /**
         * Gets background color of channel's banner.
         */
        public String getProfileBannerBackgroundColor() {
            return profileBannerBackgroundColor;
        }

        /**
         * Gets channel's status message.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Gets date of when channel was last updated.
         */
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         * Gets channel's URL.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Gets channel's video banner.
         */
        public String getVideoBanner() {
            return videoBanner;
        }

        /**
         * Gets channel's view count.
         */
        public int getViews() {
            return views;
        }
    }
}
