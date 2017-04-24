package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.List;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by artem on 4/18/17.
 */

public class Comment {

    public Tag writerTag;

    public String text;

    public String identifier;

    public List<String> imagesUrls;

    public Date timestamp;

    public int drivesClickCount;

    public boolean isHidden;

    public boolean isRemoved;

    public boolean isVisible;

    public Comment(JsonObject jsonObject, List<Tag> tags) {
        if (JsonObjectHelper.hasValueFromKey("content", jsonObject)) {
            text = jsonObject.get("content").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("images", jsonObject)) {
            imagesUrls.add(jsonObject.get("content").getAsString());
        }

        JsonObject prefs = null;
        if (JsonObjectHelper.hasValueFromKey("prefs", jsonObject)) {
            prefs = jsonObject.getAsJsonObject("prefs");
        }

        if (JsonObjectHelper.hasValueFromKey("hide_flag", prefs)) {
            isHidden = prefs.get("hide_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("remove_flag", prefs)) {
            isRemoved = prefs.get("remove_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("remove_flag", prefs)) {
            isRemoved = prefs.get("remove_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("uid", jsonObject)) {
            identifier = jsonObject.get("uid").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("created_at", jsonObject)) {
            long timeInMiliseconds = jsonObject.get("created_at").getAsLong();

            if (timeInMiliseconds > 0) {
                timestamp = new Date(timeInMiliseconds);
            }
        }

        JsonObject statsObjects = null;
        if (JsonObjectHelper.hasValueFromKey("stats", jsonObject)) {
            statsObjects = jsonObject.getAsJsonObject("stats");
        }

        if (JsonObjectHelper.hasValueFromKey("drive_click_count", statsObjects)) {
            drivesClickCount = statsObjects.get("drive_click_count").getAsInt();
        }

        String writerTagId = null;
        if (JsonObjectHelper.hasValueFromKey("writer_tag_uid", jsonObject)) {
            writerTagId = jsonObject.get("writer_tag_uid").getAsString();
        }

        int currentWriterTagId = Integer.parseInt(writerTagId);

        writerTag = Tag.tagById(currentWriterTagId, tags);
    }


    public void setVisible(boolean visible) {
        if (isHidden || isRemoved) {
            visible = true;
        } else {
            visible = false;
        }
    }
}