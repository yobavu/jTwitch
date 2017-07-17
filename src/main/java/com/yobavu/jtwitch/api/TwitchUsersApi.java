/**
 * Created by Binh Vu (github: yobavu) on 3/25/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.User;
import com.yobavu.jtwitch.model.UserBlock;
import com.yobavu.jtwitch.model.UserEmoticon;
import com.yobavu.jtwitch.model.UserFollow;
import com.yobavu.jtwitch.model.UserSubscription;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
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
        response = webTarget.path("user").request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, User.class);
    }

    /**
     * Gets a specific user account by id.
     *
     * @param userId the id for specific user account.
     */
    public User getUserById(int userId) throws TwitchApiException {
        response = webTarget.path("users/" + userId).request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, User.class);
    }

    /**
     * Gets specific user account(s) by username. All user accounts containing a substring of
     * the username will be returned.
     *
     * Requires "user_read" scope.
     *
     * @param username the username for specific user account. E.g "user1" or "user1,user2".
     */
    public List<User> getUsersByUsername(String username) throws TwitchApiException {
        response = webTarget.path("users").queryParam("login", username).request().get();
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

    /**
     * Gets a list of the emojis and emoticons that the specified user can use in chat.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for the specific user account.
     */
    public List<UserEmoticon> getUserEmotes(int userId) {
        // todo
        throw new UnsupportedOperationException();
    }

    /**
     * Check if user is subscribed to a specific channel. Will throw TwitchApiException if user is not subscribed
     * to channel or channel does not have a subscription program.
     *
     * Requires "user_subscriptions" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    public UserSubscription getUserChannelSubscription(int userId, int channelId) throws TwitchApiException {
        response = webTarget.path("users/" + userId).path("subscriptions/" + channelId).request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, UserSubscription.class);
    }

    /**
     * Gets list of channels followed by specific user.
     *
     * @param userId the id for specific user account.
     * @param queryParams optional set of parameters:
     *        <ul>
     *            <li>
     *                limit: sets limit of results. Default: 25 and Max: 100.
     *            </li>
     *            <li>
     *                offset: use for pagination of results. Default: 0.
     *            </li>
     *            <li>
     *                direction: sort direction. Valid values: asc and desc. Default: desc.
     *            </li>
     *            <li>
     *                sortby: sort results. Valid values: created_at, last_broadcast, login. Default: created_at.
     *            </li>
     *        </ul>
     */
    public List<UserFollow> getChannelsFollowedByUser(int userId, Object... queryParams) throws TwitchApiException {
        Integer limit = null;
        Integer offset = null;
        String direction = null;
        String sortby = null;

        if (queryParams.length > 4) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

        if (queryParams.length > 0) {
            if (queryParams[0] != null) {
                limit = (Integer) queryParams[0];
            }

            if (queryParams[1] != null) {
                offset = (Integer) queryParams[1];
            }

            if (queryParams[2] != null) {
                direction = (String) queryParams[2];
            }

            if (queryParams[3] != null) {
                sortby = (String) queryParams[3];
            }
        }

        response = webTarget.path("users/" + userId).path("follows/channels").queryParam("limit", limit)
                .queryParam("offset", offset).queryParam("direction", direction).queryParam("sortby", sortby)
                .request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

        JsonArray channels = jsonObject.get("follows").getAsJsonArray();
        List<UserFollow> followList = new ArrayList<>();

        for (int i = 0; i < channels.size(); i++) {
            followList.add(super.getGson().fromJson(channels.get(i), UserFollow.class));
        }

        return followList;
    }

    /**
     * Check if user follows a specific channel. Will throw TwitchApiException if user is not following channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id of channel to check.
     */
    public UserFollow checkUserFollowsChannel(int userId, int channelId) throws TwitchApiException {
        response = webTarget.path("users/" + userId).path("follows/channels/" + channelId).request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, UserFollow.class);
    }

    /**
     * Add user as a follower for a specific channel.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel to follow.
     */
    public UserFollow followChannel(int userId, int channelId, boolean notifications) throws TwitchApiException {
        response = webTarget.path("users/" + userId).path("follows/channels/" + channelId)
                .queryParam("notifications", notifications).request().put(Entity.text(""));
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, UserFollow.class);
    }

    /**
     * Deletes a specified user from the followers list of a specified channel.
     *
     * Requires "user_follows_edit" scope.
     *
     * @param userId the id for specific user account.
     * @param channelId the id for specific channel.
     */
    public void unfollowChannel(int userId, int channelId) {
        webTarget.path("users/" + userId).path("follows/channels/" + channelId).request().delete();
    }

    /**
     * Gets a user's block list. List is sorted by recency; newest first.
     *
     * Requires "user_blocks_read" scope.
     *
     * @param userId the id of specific user account.
     * @param queryParams optional set of parameters:
     *        <ul>
     *            <li>
     *                limit: sets limit of results. Default: 25 and Max: 100.
     *            </li>
     *            <li>
     *                offset: use for pagination of results. Default: 0.
     *            </li>
     *        </ul>
     */
    public List<UserBlock> getUserBlockList(int userId, Object... queryParams) throws TwitchApiException {
        Integer limit = null;
        Integer offset = null;

        if (queryParams.length > 2) {
            throw new IllegalArgumentException("Invalid number of optional parameters");
        }

        if (queryParams.length > 0) {
            if (queryParams[0] != null) {
                limit = (Integer) queryParams[0];
            }

            if (queryParams[1] != null) {
                offset = (Integer) queryParams[1];
            }
        }

        response = webTarget.path("users/" + userId).path("blocks").queryParam("limit", limit).queryParam("offset", offset)
                .request().get();
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(json).getAsJsonObject();

        JsonArray channels = jsonObject.get("blocks").getAsJsonArray();
        List<UserBlock> blockList = new ArrayList<>();

        for (int i = 0; i < channels.size(); i++) {
            blockList.add(super.getGson().fromJson(channels.get(i), UserBlock.class));
        }

        return blockList;
    }

    /**
     * Blocks a user; blocked user will be added to specific user's block list.
     *
     * Requires "user_blocks_edit" scope.
     *
     * @param userId the id of specific user account.
     * @param blockId the id of account to block.
     */
    public UserBlock blockUser(int userId, int blockId) throws TwitchApiException {
        response = webTarget.path("users/" + userId).path("blocks/" + blockId).request().put(Entity.text(""));
        ErrorParser.checkForErrors(response);
        String json = response.readEntity(String.class);

        return super.getGson().fromJson(json, UserBlock.class);
    }

    /**
     * Unblocks a user; user will be removed from specific user's block list.
     *
     * Requires "user_blocks_edit" scope.
     *
     * @param userId the id of specific user account.
     * @param unblockId the id of account to unblock.
     */
    public void unblockUser(int userId, int unblockId) {
        webTarget.path("users/" + userId).path("blocks/" + unblockId).request().delete();
    }
}
