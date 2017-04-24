package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import java.util.Date;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by artem on 4/14/17.
 */

public class Notice {

    public String noticeId;

    public String noticeDescription;

    public String text;

    public Date timestamp;

    public String typeNotice;

    public Boolean isUnread;

    public Tag writerTag;

    public Tag recipientTag;

    public Post post;

    public Notice (JsonObject jsonObject) {

        if (JsonObjectHelper.hasValueFromKey("event_uid", jsonObject)) {
            noticeId = jsonObject.get("event_uid").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("event_desc", jsonObject)) {
            noticeDescription = jsonObject.get("event_desc").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("event_type", jsonObject)) {
            typeNotice = jsonObject.get("event_type").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("message", jsonObject)) {
            text = jsonObject.get("message").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("unread", jsonObject)) {
            isUnread = jsonObject.get("unread").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("first_call_out_time", jsonObject)) {
            long timeInMiliseconds = jsonObject.get("first_call_out_time").getAsLong();

            if (timeInMiliseconds > 0) {
                timestamp = new Date(timeInMiliseconds);
            }
        }
    }
}
