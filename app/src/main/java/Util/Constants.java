package Util;

/**
 * Created by amalyavkin on 04/01/2018.
 */

public class Constants {
    public static String CLIENT_ID = "6310833";

    public static String OAUTH_DOMAIN = "https://oauth.vk.com";
    public static String API_DOMAIN = "https://api.vk.com";

    public static final String AUTHORIZE_PATH = "/authorize";
    public static String REDIRECT_PATH = "/blank.html";

    public static String OAUTH_URL = OAUTH_DOMAIN + AUTHORIZE_PATH;
    public static String REDIRECT_URL = OAUTH_DOMAIN + REDIRECT_PATH;

    public static String API_V = "5.69";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "VKChat";

}
