/**
 * Created by Binh Vu (github: yobavu) on 4/21/17.
 */

package com.yobavu.jtwitch.error;

import com.yobavu.jtwitch.exceptions.TwitchApiException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.readEntity(String.class)).getAsJsonObject();

            return new ApiError(jsonObject.get("status").getAsInt(), jsonObject.get("error").getAsString(),
                    jsonObject.get("message").getAsString());
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
            StringBuilder sb = new StringBuilder();

            sb.append(apiError.getStatusCode());
            sb.append(" ");
            sb.append(apiError.getError());
            sb.append(": ");
            sb.append(apiError.getMessage());

            throw new TwitchApiException(sb.toString());
        }
    }
}
