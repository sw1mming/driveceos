package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;


import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by yuriy on 3/23/17.
 */

public class User {
    public static final String EmailTypePersonal = "personal";

    public static final String EmailTypeWork = "work";

    public static final String USER_TYPE = "user_profile";

    public String tagId;

    public Number driveID;

    public String driveTag;

    public String facebookID;

    public String twitterID;

    public String linkedInID;

    public String firstName;

    public String middleName;

    public String lastName;

    public String email;

    public String title;

    public String verification;

    public String password;

    public String driveEmail;

    public String colorTheme;

    public Integer teammatesCount;

    public String teammateStatus;

    public Boolean claimAccount;

    public Boolean precreated;

    public Integer activeSocialsCount;

    public JsonObject settings;

    public String emailType;

    public URL avatarUrl;

    public URL myPagePhotoUrl;

    public URL coverUrl;

    public String drivenMessage;

    public Boolean driveTagOn;

    public Boolean facebookOn;

    public Boolean twitterOn;

    public Boolean linkedInOn;

    public Boolean blockFlag;

    public Boolean isLive;

    public Boolean isEmployee;

    public User() {

    }

    public User(JsonObject jsonObject) {

        JsonObject userTagDictionary = null;
        if (JsonObjectHelper.hasValueFromKey("user_tag", jsonObject)) {
            userTagDictionary = jsonObject.getAsJsonObject("user_tag");
        }

        JsonObject userDictionary = null;
        if (userTagDictionary != null && JsonObjectHelper.hasValueFromKey("user", userTagDictionary)) {
            userDictionary = userTagDictionary.getAsJsonObject("user");
        }

        JsonObject prefsDictionary = null;
        if (userDictionary != null && JsonObjectHelper.hasValueFromKey("prefs", userDictionary)) {
            prefsDictionary = userDictionary.getAsJsonObject("prefs");
        }

        JsonObject socialDictionary = null;
        if (userDictionary != null && JsonObjectHelper.hasValueFromKey("social", userDictionary)) {
            socialDictionary = userDictionary.getAsJsonObject("social");
        }

        if (socialDictionary != null && JsonObjectHelper.hasValueFromKey("tw", socialDictionary)) {
            JsonObject twitterDictionary = socialDictionary.getAsJsonObject("tw");

            if (JsonObjectHelper.hasValueFromKey("uid", twitterDictionary)) {
                twitterID = twitterDictionary.get("uid").getAsString();
            }
        }

        if (socialDictionary != null && JsonObjectHelper.hasValueFromKey("fb", socialDictionary)) {
            JsonObject facebookDictionary = socialDictionary.getAsJsonObject("fb");

            if (JsonObjectHelper.hasValueFromKey("uid", facebookDictionary)) {
                facebookID = facebookDictionary.get("uid").getAsString();
            }
        }

        if (socialDictionary != null && JsonObjectHelper.hasValueFromKey("li", socialDictionary)) {
            JsonObject linkedInDictionary = socialDictionary.getAsJsonObject("li");

            if (JsonObjectHelper.hasValueFromKey("uid", linkedInDictionary)) {
                linkedInID = linkedInDictionary.get("uid").getAsString();
            }
        }

        JsonObject userTag = null;
        if (JsonObjectHelper.hasValueFromKey("user_tag", jsonObject)) {
            userTag = jsonObject.getAsJsonObject("user_tag");
        }

        JsonObject userInfo = null;
        if (userTag != null && JsonObjectHelper.hasValueFromKey("user", userTag)) {
            userInfo = userTag.getAsJsonObject("user");
        }

        if (userInfo != null && JsonObjectHelper.hasValueFromKey("my_page", userInfo)) {
            JsonArray myPageUrls = userInfo.get("my_page").getAsJsonArray();

            if (myPageUrls.size() > 0) {
                try {
                    myPagePhotoUrl = new URL(myPageUrls.get(0).getAsString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (JsonObjectHelper.hasValueFromKey("cover", jsonObject)) {
            try {
                coverUrl = new URL(jsonObject.get("cover").getAsString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (JsonObjectHelper.hasValueFromKey("writer_name", userDictionary)) {
            driveTag = userDictionary.get("writer_name").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("claimed", userDictionary)) {
            claimAccount = userDictionary.get("claimed").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("precreated", userDictionary)) {
            precreated = userDictionary.get("precreated").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("teammate_status", prefsDictionary)) {
            teammateStatus = prefsDictionary.get("teammate_status").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("block_flag", prefsDictionary)) {
            blockFlag = prefsDictionary.get("block_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("uid", userDictionary)) {
            driveID = userDictionary.get("uid").getAsNumber();
        }

        if (JsonObjectHelper.hasValueFromKey("drive_email", jsonObject)) {
            driveEmail = jsonObject.get("drive_email").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("settings", jsonObject)) {
            settings = jsonObject.getAsJsonObject("settings");
        }

        if (JsonObjectHelper.hasValueFromKey("color_theme", settings)) {
            colorTheme = settings.get("color_theme").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("avatar", userDictionary)) {
            try {
                avatarUrl = new URL(userDictionary.get("avatar").getAsString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (JsonObjectHelper.hasValueFromKey("tag", userTagDictionary)) {
            driveTag = userTagDictionary.get("tag").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("driven_message", userDictionary)) {
            drivenMessage = userDictionary.get("driven_message").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("is_employee", userDictionary)) {
            isEmployee = userDictionary.get("is_employee").getAsBoolean();

            if (JsonObjectHelper.hasValueFromKey("employee_title", userDictionary)) {
                title = userDictionary.get("employee_title").getAsString();
            }
        }
    }
}
