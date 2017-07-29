/**
 * Created by Binh Vu (github: yobavu) on 4/22/17.
 */

package com.yobavu.jtwitch.util;

import com.yobavu.jtwitch.oauth.TwitchToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for generic actions.
 */
public final class TwitchUtil {
    private TwitchUtil() {}

    /**
     * Loads the access token for an existing user from the serialized file.
     *
     * @param id the unique id associated with an access token.
     * @return the credential token for making requests to Twitch API.
     */
    public static TwitchToken loadCredential(String id) throws ClassNotFoundException, IOException {
        File dir = new File(System.getProperty("user.home") + "/.jTwitch/");
        File serialFile = new File(System.getProperty("user.home") + "/.jTwitch/credentials.ser");

        if (!dir.exists()) {
            dir.mkdir();
        }

        boolean justCreated = serialFile.createNewFile();

        // file exists so read from it
        if (!justCreated) {
            Map<String, Object> cred = readSerializable();

            if (cred != null && cred.containsKey(id)) {
                return (TwitchToken) cred.get(id);
            }
        }

        return null;
    }

    /**
     * Reads the serialized files for existing credential tokens.
     *
     * @return map containing credential token.
     */
    public static Map<String, Object> readSerializable() throws ClassNotFoundException, IOException {
        ObjectInputStream oiStream;
        FileInputStream inStream;

        inStream = new FileInputStream(System.getProperty("user.home") + "/.jTwitch/credentials.ser");
        oiStream = new ObjectInputStream(inStream);

        Map<String, Object> credentials = (HashMap<String, Object>) oiStream.readObject();

        oiStream.close();
        inStream.close();

        return credentials;
    }

    /**
     * Stores the credential token in a serialized file.
     *
     * @param cred map containing credential token.
     */
    public static void writeSerializable(Map<String, Object> cred) throws IOException {
        ObjectOutputStream ooStream;
        FileOutputStream outStream;

        outStream = new FileOutputStream(System.getProperty("user.home") + "/.jTwitch/credentials.ser", false);
        ooStream = new ObjectOutputStream(outStream);

        ooStream.writeObject(cred);
        ooStream.close();
        outStream.close();
    }

    /**
     * Creates a comma separated string value from a list.
     *
     * @param list list of values to append together.
     */
    public static String stringifyList(List<?> list) {
        if (list != null) {
            StringBuilder sb = new StringBuilder();

            for(Object s : list) {
                sb.append(s);
                sb.append(",");
            }

            return sb.deleteCharAt(sb.length() - 1).toString();
        }

        return null;
    }
}
