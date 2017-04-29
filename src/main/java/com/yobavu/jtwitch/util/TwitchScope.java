/**
 * Created by Binh Vu (github: yobavu) on 4/28/17.
 */

package com.yobavu.jtwitch.util;

import java.util.List;

/**
 * Enum representation of Twitch API scopes.
 */
public final class TwitchScope {
    public enum SCOPES {
        USER_READ("user_read"), USER_SUBSCRIPTION("user_subscriptions"), USER_FOLLOWS_EDIT("user_follows_edit"),
        USER_BLOCKS_READ("user_blocks_read"), USER_BLOCKS_EDIT("user_blocks_edit");

        String value;

        SCOPES(String value) {
            this.value = value;
        }
    }

    private TwitchScope() {}

    public static String constructScopes(List<SCOPES> scopes) {
        StringBuilder sb = new StringBuilder();

        for (SCOPES s : scopes) {
            sb.append(s.value);
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
