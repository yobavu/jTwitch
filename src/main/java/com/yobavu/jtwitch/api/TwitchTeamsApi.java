/**
 * Created by Binh Vu (github: yobavu) on 7/21/17.
 */

package com.yobavu.jtwitch.api;

import com.yobavu.jtwitch.error.ErrorParser;
import com.yobavu.jtwitch.exceptions.TwitchApiException;
import com.yobavu.jtwitch.model.Team;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for the Twitch teams API.
 */
public class TwitchTeamsApi extends TwitchApi {
    private WebTarget webTarget;
    private Response response;
    private JsonParser parser;
    private JsonObject jsonObject;

    public TwitchTeamsApi() {
        super();
    }

    public TwitchTeamsApi build(Client client) {
        super.setClient(client);
        webTarget = client.target(super.getApiUrl());
        return this;
    }

    /**
     * Gets a specific team.
     *
     * @param name name of team.
     */
    public Team getTeamByName(String name) throws TwitchApiException {
        response = webTarget.path("teams/" + name).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        return super.getGson().fromJson(responseJson, Team.class);
    }

    /**
     * Gets all active teams.
     *
     * @param limit optional maximum number of teams to return, sorted by creation date. Default: 25, max: 100.
     * @param offset optional used for pagination of results. Default: 0.
     */
    public List<Team> getAllTeams(Integer limit, Integer offset) throws TwitchApiException {
        response = webTarget.path("teams").queryParam("limit", limit).queryParam("offset", offset).request().get();
        ErrorParser.checkForErrors(response);
        String responseJson = response.readEntity(String.class);

        parser = new JsonParser();
        jsonObject = parser.parse(responseJson).getAsJsonObject();

        JsonArray teams = jsonObject.getAsJsonArray("teams");
        List<Team> teamList = new ArrayList<>();

        for(int i = 0; i < teams.size(); i++) {
            teamList.add(super.getGson().fromJson(teams.get(i), Team.class));
        }

        return teamList;
    }
}
