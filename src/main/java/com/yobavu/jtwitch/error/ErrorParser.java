/**
 * Created by Binh Vu (github: yobavu) on 4/21/17.
 */

package com.yobavu.jtwitch.error;

import com.yobavu.jtwitch.exceptions.TwitchApiException;

import javax.ws.rs.core.Response;

/**
 * Parses a response to check for errors.
 */
public final class ErrorParser {
    private ErrorParser() {}

    private static ApiError parseError(Response response) {
        if (response.getStatus() == 200) {
            return null;
        }

        try {
            return new ApiError(response.getStatus(), response.getStatusInfo().getReasonPhrase(),
                    response.getStatusInfo().getFamily().name());
        } catch (Exception e) {
            return new ApiError(500, e.getMessage(), null);
        }
    }

    /**
     * Parses response and handle errors if present.
     *
     * @param response the response from service.
     */
    public static void checkForErrors(Response response) throws TwitchApiException {
        ApiError apiError = parseError(response);

        if (apiError != null) {
            throw new TwitchApiException(apiError.getError() + ": " + apiError.getMessage());
        }
    }
}
