/**
 * Created by Binh Vu (github: yobavu) on 4/21/17.
 */

package com.yobavu.jtwitch.exceptions;

/**
 * Exception handling for the Twitch API.
 */
public class TwitchApiException extends Exception {
    private static final long serialVersionUID = 271897899914646456L;

    public TwitchApiException(String message) {
        super(message);
    }
}
