/**
 * Created by Binh Vu (github: yobavu) on 7/23/17.
 */

package com.yobavu.jtwitch.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yobavu.jtwitch.model.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TwitchTeamsApiTest {
    private Gson gson;
    private JsonParser parser;
    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private TwitchTeamsApi teamsApi;

    private static final String TEAM_NAME = "staff";

    @Before
    public void setup() {
        gson = new Gson();
        parser = new JsonParser();
        teamsApi = Mockito.mock(TwitchTeamsApi.class);
    }

    @Test
    public void testGetTeamByName() throws Exception {
        String json = "{" +
                "   \"_id\": 10," +
                "   \"background\": null," +
                "   \"banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-banner_image-606ff5977f7dc36e-640x125.png\"," +
                "   \"created_at\": \"2011-10-25T23:55:47Z\"," +
                "   \"display_name\": \"Twitch Staff\"," +
                "   \"info\": \"Twitch staff stream here.\"," +
                "   \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-team_logo_image-76418c0c93a9d48b-300x300.png\"," +
                "   \"name\": \"staff\"," +
                "   \"updated_at\": \"2014-10-16T00:44:11Z\"," +
                "   \"users\": [{" +
                "         \"_id\": 5582097," +
                "         \"broadcaster_language\": \"en\"," +
                "         \"created_at\": \"2009-04-13T21:22:28Z\"," +
                "         \"display_name\": \"Sarbandia\"," +
                "         \"followers\": 1182," +
                "         \"game\": \"Hearthstone: Heroes of Warcraft\"," +
                "         \"language\": \"en\"," +
                "         \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/sarbandia-profile_image-6693b5952f31c847-300x300.jpeg\"," +
                "         \"mature\": false," +
                "         \"name\": \"sarbandia\"," +
                "         \"partner\": false," +
                "         \"profile_banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/sarbandia-profile_banner-247cdbe62dbcf4d9-480.jpeg\"," +
                "         \"profile_banner_background_color\": null," +
                "         \"status\": \"Midrange shaman laddering\"," +
                "         \"updated_at\": \"2016-12-15T19:02:40Z\"," +
                "         \"url\": \"https://www.twitch.tv/sarbandia\"," +
                "         \"video_banner\": null," +
                "         \"views\": 8168" +
                "   }]" +
                "}";

        Mockito.when(teamsApi.getTeamByName(TEAM_NAME)).thenReturn(gson.fromJson(json, Team.class));
        Team team = teamsApi.getTeamByName(TEAM_NAME);

        Assert.assertTrue(team.getId() == 10);
        Assert.assertEquals("Twitch Staff", team.getDisplayName());
    }

    @Test
    public void testGetAllTeams() throws Exception {
        String json = "{\n" +
                "   \"teams\": [{" +
                "      \"_id\": 10," +
                "      \"background\": null," +
                "      \"banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-banner_image-606ff5977f7dc36e-640x125.png\"," +
                "      \"created_at\": \"2011-10-25T23:55:47Z\"," +
                "      \"display_name\": \"Twitch Staff\"," +
                "      \"info\": \"Twitch staff stream here.\"," +
                "      \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-team_logo_image-76418c0c93a9d48b-300x300.png\"," +
                "      \"name\": \"staff\"," +
                "      \"updated_at\": \"2014-10-16T00:44:11Z\"" +
                "   }," +
                "   {" +
                "      \"_id\": 15," +
                "      \"background\": null," +
                "      \"banner\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-banner_image-606ff5977f7dc36e-640x125.png\"," +
                "      \"created_at\": \"2011-10-25T23:55:47Z\"," +
                "      \"display_name\": \"Twitch Support\"," +
                "      \"info\": \"Twitch support stream here.\"," +
                "      \"logo\": \"https://static-cdn.jtvnw.net/jtv_user_pictures/team-staff-team_logo_image-76418c0c93a9d48b-300x300.png\"," +
                "      \"name\": \"support\"," +
                "      \"updated_at\": \"2014-10-16T00:44:11Z\"" +
                "    }]" +
                "}";
        jsonObject = parser.parse(json).getAsJsonObject();
        jsonArray = jsonObject.get("teams").getAsJsonArray();

        List<Team> teams = new ArrayList<>();

        for(int i = 0; i < jsonArray.size(); i++) {
            teams.add(gson.fromJson(jsonArray.get(i), Team.class));
        }

        Assert.assertTrue(teams.size() == 2);
        Assert.assertEquals("Twitch Support", teams.get(1).getDisplayName());
    }
}
