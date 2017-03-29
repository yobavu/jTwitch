/**
 * Created by Binh Vu (github: yobavu) on 3/28/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of user accounts.
 */
public class UserList {
    @SerializedName("_total")
    private int total;

    @SerializedName("users")
    private List<User> users;

    /**
     * Gets total number of user accounts.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gets list of user accounts.
     */
    public List<User> getUsers() {
        return users;
    }
}
