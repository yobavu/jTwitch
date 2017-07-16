/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserBlock;
import com.yobavu.jtwitch.model.UserBlockList;
import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserFollowList;
import com.yobavu.jtwitch.model.UserList;
import com.yobavu.jtwitch.model.UserSubscription;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for the Twitch users API.
 * @// TODO: Implement wraps for Viewer Heartbeat Service
 */
public class TwitchUsersApi extends TwitchApi {
    private WebTarget webTarget;
    private Response response;
    private JsonParser parser;
    private JsonObject jsonObject;

    public TwitchUsersApi() {
        super();
    }

    public TwitchUsersApi build(Client client) {
        webTarget = client.target(super.getApiUrl());
        return this;
    }

    /**
     * Gets user account associated with OAuth 2.0 token.
     *
     * Requires "user_read" scope.
     */
    public User getUser() throws TwitchApiException {
        response = webTarget.path("user").request(MediaType.APPLICATION_JSON_TYPE).get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, User.class);
    }

    /**
     * Gets specific user account(s) by username. All user accounts containing a substring of
     * the username will be returned.
     *
     * @param username the username for specific user account.
     */
    public List<User> getUsersByUsername(String username) throws IOException, TwitchApiException {
        response = webTarget.path("users").queryParam("login", username).request(MediaType.APPLICATION_JSON_TYPE).get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

        JsonArray users = jsonObject.get("users").getAsJsonArray();
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            userList.add(super.getGson().fromJson(users.get(i), User.class));
        }

        return userList;
    }

//    /**
//     * Gets a specific user account by id.
//     *
//     * @param userId the id for specific user account.
//     */
//    public User getUserById(int userId) throws IOException, TwitchApiException {
//        Call<User> call = userService.getUserById(userId);
//
//        Response<User> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Gets a list of the emojis and emoticons that the specified user can use in chat.
//     *
//     * Requires "user_subscriptions" scope.
//     *
//     * @param userId the id for the specific user account.
//     */
//    public List<UserEmoticon> getUserEmotes(int userId) {
//        // todo
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Check if user is subscribed to a specific channel. Will return null if user is not subscribed to channel
//     * or channel does not have a subscription program.
//     *
//     * Requires "user_subscriptions" scope.
//     *
//     * @param userId the id for specific user account.
//     * @param channelId the id for specific channel.
//     */
//    public UserSubscription getUserChannelSubscription(int userId, int channelId) throws IOException, TwitchApiException {
//        Call<UserSubscription> call = userService.getUserChannelSubscription(userId, channelId);
//
//        Response<UserSubscription> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Gets list of channels followed by specific user.
//     *
//     * @param userId the id for specific user account.
//     * @param queryParams optional set of parameters:
//     *        <ul>
//     *            <li>
//     *                limit: sets limit of results. Default: 25 and Max: 100.
//     *            </li>
//     *            <li>
//     *                offset: use for pagination of results. Default: 0.
//     *            </li>
//     *            <li>
//     *                direction: sort direction. Valid values: asc and desc. Default: desc.
//     *            </li>
//     *            <li>
//     *                sortby: sort results. Valid values: created_at, last_broadcast, login. Default: created_at.
//     *            </li>
//     *        </ul>
//     */
//    public UserFollowList getChannelsFollowedByUser(int userId, Object... queryParams) throws IOException, TwitchApiException {
//        Integer limit = null;
//        Integer offset = null;
//        String direction = null;
//        String sortby = null;
//
//        if (queryParams.length > 4) {
//            throw new IllegalArgumentException("Invalid number of optional parameters");
//        }
//
//        if (queryParams.length > 0) {
//            if (queryParams[0] != null) {
//                limit = (Integer) queryParams[0];
//            }
//
//            if (queryParams[1] != null) {
//                offset = (Integer) queryParams[1];
//            }
//
//            if (queryParams[2] != null) {
//                direction = (String) queryParams[2];
//            }
//
//            if (queryParams[3] != null) {
//                sortby = (String) queryParams[3];
//            }
//        }
//
//        Call<UserFollowList> call = userService.getChannelsFollowedByUser(userId, limit, offset, direction, sortby);
//
//        Response<UserFollowList> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Check if user follows a specific channel. Will return null if user is not following channel.
//     *
//     * @param userId the id for specific user account.
//     * @param channelId the id of channel to check.
//     */
//    public UserFollow checkUserFollowsChannel(int userId, int channelId) throws IOException, TwitchApiException {
//        Call<UserFollow> call = userService.checkUserFollowsChannel(userId, channelId);
//
//        Response<UserFollow> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Add user as a follower for a specific channel.
//     *
//     * @param userId the id for specific user account.
//     * @param channelId the id for specific channel to follow.
//     */
//    public UserFollow followChannel(int userId, int channelId) throws IOException, TwitchApiException {
//        Call<UserFollow> call = userService.followChannel(userId, channelId);
//
//        Response<UserFollow> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Deletes a specified user from the followers list of a specified channel.
//     *
//     * Requires "user_follows_edit" scope.
//     *
//     * @param userId the id for specific user account.
//     * @param channelId the id for specific channel.
//     */
//    public void unfollowChannel(int userId, int channelId) throws IOException {
//        Call<Void> call = userService.unfollowChannel(userId, channelId);
//
//        call.execute();
//    }
//
//    /**
//     * Gets a user's block list. List is sorted by recency; newest first.
//     *
//     * Requires "user_blocks_read" scope.
//     *
//     * @param userId the id of specific user account.
//     * @param queryParams optional set of parameters:
//     *        <ul>
//     *            <li>
//     *                limit: sets limit of results. Default: 25 and Max: 100.
//     *            </li>
//     *            <li>
//     *                offset: use for pagination of results. Default: 0.
//     *            </li>
//     *        </ul>
//     */
//    public UserBlockList getUserBlockList(int userId, Object... queryParams) throws IOException, TwitchApiException {
//        Integer limit = null;
//        Integer offset = null;
//
//        if (queryParams.length > 2) {
//            throw new IllegalArgumentException("Invalid number of optional parameters");
//        }
//
//        switch (queryParams.length) {
//            case 1:
//                limit = (Integer) queryParams[0];
//                break;
//            case 2:
//                limit = (Integer) queryParams[0];
//                offset = (Integer) queryParams[1];
//                break;
//        }
//
//        Call<UserBlockList> call = userService.getUserBlockList(userId, limit, offset);
//
//        Response<UserBlockList> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Blocks a user; blocked user will be added to specific user's block list.
//     *
//     * Requires "user_blocks_edit" scope.
//     *
//     * @param userId the id of specific user account.
//     * @param blockId the id of account to block.
//     */
//    public UserBlock blockUser(int userId, int blockId) throws IOException, TwitchApiException {
//        Call<UserBlock> call = userService.blockUser(userId, blockId);
//
//        Response<UserBlock> response = call.execute();
//        ErrorParser.checkForErrors(response);
//
//        return response.body();
//    }
//
//    /**
//     * Unblocks a user; user will be removed from specific user's block list.
//     *
//     * Requires "user_blocks_edit" scope.
//     *
//     * @param userId the id of specific user account.
//     * @param unblockId the id of account to unblock.
//     */
//    public void unblockUser(int userId, int unblockId) throws IOException, TwitchApiException {
//        Call<Void> call = userService.unblockUser(userId, unblockId);
//
//        call.execute();
//    }
}
