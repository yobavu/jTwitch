/**
 * Created by Binh Vu (github: yobavu) on 4/21/17.
 */

package com.yobavu.jtwitch.error;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import retrofit2.Response;

import java.io.IOException;

/**
 * Parses a response to check for errors.
 */
public final class ErrorParser {
    public static ApiError parseError(Response<?> response) {
        if (response.isSuccessful()) {
            return null;
        }

        try {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<ApiError> apiErrorJsonAdapter = moshi.adapter(ApiError.class);

            return apiErrorJsonAdapter.fromJson(response.errorBody().string());
        } catch (IOException io) {
            return new ApiError(response.code(), null, "Unknown error");
        }
    }
}
