/**
 * Created by Binh Vu (github: yobavu) on 4/21/17.
 */

package com.yobavu.jtwitch.error;

/**
 * Represents an error response from the Twitch API.
 */
public class ApiError {
    private int statusCode;
    private String error;
    private String message;

    public ApiError(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
