package com.yobavu.jtwitch.model;

/**
 * Created by bvu on 3/25/17.
 */
public class User {
    private int _id;
    private String bio;
    private String created_at;
    private String display_name;
    private String email;
//    private boolean email_verified;
    private String name;
    private String type;
    private String updated_at;

    public int getId() {
        return _id;
    }

    public String getBio() {
        return bio;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getEmail() {
        return email;
    }

//    public boolean isEmailVerified() {
//        return email_verified;
//    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
}
