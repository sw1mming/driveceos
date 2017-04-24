package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by yuriy on 3/31/17.
 */

public class Tag {

    public String CompanyType = "url_company";
    public String DriveTagType = "driveleader";
    public String DriveTagName = "driveleader";
    public String DriveTagId = "100";

    public User user;
    public String name;
    public Boolean blacklisted;
    public String type;
    public Number identifier;
    public String leaderTagName;
    public Number parentTagId;
    public Number leaderId;
    public Number leaderUserId;
    public Integer drivesCount;
    public Integer teammatesCount;
    public Integer callOutMeCount;
    public Integer peopleDrivesTodayCount;
    public URL iconUrl;
    public Boolean isFollowing;
    public Date firstCallOutDate;
    public Boolean allowTeamup;
    public Subscription subscription;
    public Tag ceoTag;
    public Tag parentTag;


    public Tag(JsonObject jsonObject) {

        if (JsonObjectHelper.hasValueFromKey("type", jsonObject)) {
            type = jsonObject.get("type").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("blacklisted", jsonObject)) {
            blacklisted = jsonObject.get("blacklisted").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("leader_uid", jsonObject)) {
            leaderId = jsonObject.get("leader_uid").getAsNumber();
        }

        if (JsonObjectHelper.hasValueFromKey("leader_user_uid", jsonObject)) {
            leaderUserId = jsonObject.get("leader_user_uid").getAsNumber();
        }

        if (JsonObjectHelper.hasValueFromKey("leader_tag", jsonObject)) {
            leaderTagName = jsonObject.get("leader_tag").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("parent_uid", jsonObject)) {
            parentTagId = jsonObject.get("parent_uid").getAsNumber();
        }


        if (JsonObjectHelper.hasValueFromKey("tag", jsonObject)) {
            name = jsonObject.get("tag").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("stats", jsonObject)) {
            JsonObject stats = jsonObject.getAsJsonObject("stats");

            if (stats != null && stats.isJsonObject()) {
                if (JsonObjectHelper.hasValueFromKey("drives_count", jsonObject)) {
                    drivesCount = jsonObject.getAsJsonObject("drives_count").getAsInt();
                }

                if (JsonObjectHelper.hasValueFromKey("call_out_me_count", jsonObject)) {
                    callOutMeCount = jsonObject.getAsJsonObject("call_out_me_count").getAsInt();
                }

                if (JsonObjectHelper.hasValueFromKey("teammates_count", jsonObject)) {
                    teammatesCount = jsonObject.getAsJsonObject("teammates_count").getAsInt();
                }

                if (JsonObjectHelper.hasValueFromKey("people_recent_drives", jsonObject)) {
                    peopleDrivesTodayCount = jsonObject.getAsJsonObject("people_recent_drives").getAsInt();
                }

                if (JsonObjectHelper.hasValueFromKey("first_call_out_time", stats)) {
                    long timeInMiliseconds = stats.get("first_call_out_time").getAsLong();

                    if (timeInMiliseconds > 0) {
                        firstCallOutDate = new Date(timeInMiliseconds);
                    }
                }
            }
        }

        if (JsonObjectHelper.hasValueFromKey("prefs", jsonObject)) {
            JsonObject prefsInfo = jsonObject.getAsJsonObject("prefs");

            if (JsonObjectHelper.hasValueFromKey("follow_flag", prefsInfo)) {
                isFollowing = prefsInfo.get("follow_flag").getAsBoolean();
            }

            if (JsonObjectHelper.hasValueFromKey("allow_teamup", prefsInfo)) {
                String allow = prefsInfo.get("allow_teamup").getAsString();
                allowTeamup = allow.equals("false");
            }
        }

        if (JsonObjectHelper.hasValueFromKey("uid", jsonObject)) {
            identifier = jsonObject.get("uid").getAsNumber();
        }

        if (JsonObjectHelper.hasValueFromKey("icon", jsonObject)) {
            try {
                iconUrl = new URL(jsonObject.get("icon").getAsString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (JsonObjectHelper.hasValueFromKey("user", jsonObject)) {
            JsonObject userInfo = jsonObject.getAsJsonObject("user");

            if (userInfo != null) {
                user = new User(userInfo);
            }
        }
    }

    public void configureSubTagsFromTags(List<Tag> tags) {

        if (leaderId != null && leaderId.intValue() > 0) {
            ceoTag = Tag.tagById(leaderId,tags);
        }

        if (parentTagId != null && parentTagId.intValue() > 0) {
            parentTag = Tag.tagById(parentTagId,tags);
        }
    }

    public static Tag tagById (Number tagId, List<Tag> tags) {

        for (Tag tag : tags) {
            if (tag.identifier == tagId) {
                return tag;
            }
        }

        return null;
    }
}