package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;

/**
 * Created by yuriy on 3/23/17.
 */

public class User {

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

    public Number claimAccount;

    public Number precreated;

    public Integer activeSocialsCount;

    public Dictionary settings;

    public String emailType;

    public URL avatarUrl;

    public URL myPagePhotoUrl;

    public URL coverUrl;

    public String drivenMessage;

    public Boolean driveTagOn;








//    public User(JsonObject jsonObject) {
//        if (JsonObjectHelper.hasValueFromKey("first_name", jsonObject)) {
//            name = jsonObject.get("first_name").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("image", jsonObject)) {
//            String urlString = jsonObject.get("image").getAsString();
//            try {
//                userImageUrl = new URL(urlString);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("fb_auth_key", jsonObject)) {
//            fbToken = jsonObject.get("fb_auth_key").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("last_name", jsonObject)) {
//            surname = jsonObject.get("last_name").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("email", jsonObject)) {
//            email = jsonObject.get("email").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("id", jsonObject)) {
//            userId = jsonObject.get("id").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("city_location", jsonObject)) {
//            userCity = jsonObject.get("city_location").getAsString();
//        }
//
//        if (JsonObjectHelper.hasValueFromKey("facebookId", jsonObject)) {
//            facebookId = jsonObject.get("facebookId").getAsString();
//        }
//
//    }
//
//    public String fullName() {
//        String result = null;
//
//        if (name != null && !name.isEmpty()) {
//            result = name;
//        }
//
//        if (surname != null && !surname.isEmpty()) {
//            result = result + "" + surname;
//        }
//
//        return result;
//    }
}