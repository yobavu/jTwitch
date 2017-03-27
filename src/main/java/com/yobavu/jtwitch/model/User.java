/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.model;

/**
 * The Twitch user.
 */
public class User {
    private int _id;
    private String bio;
    private String created_at;
    private String display_name;
    private String email;
    private boolean email_verified;
    private String name;
    private String type;
    private String updated_at;

    /**
     * Gets user's id.
     */
    public int getId() {
        return _id;
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
        return created_at;
    }

    /**
     * Gets user's display name.
     */
    public String getDisplayName() {
        return display_name;
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
        return email_verified;
    }

    /**
     * Gets user's name.
     */
    public String getName() {
        return name;
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
        return updated_at;
    }
}
