package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by YuriySokurko on 5/17/17.
 */

public class UrlInfo {

    public String descriptionText;
    public String imageUrlString;
    public String titleText;
    public String urlString;
    public String videoUrlString;

    public UrlInfo(JsonObject jsonObject) {
        if (JsonObjectHelper.hasValueFromKey("desc", jsonObject)) {
            descriptionText = jsonObject.get("desc").getAsString();
        } else if (JsonObjectHelper.hasValueFromKey("description", jsonObject)) {
            descriptionText = jsonObject.get("description").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("image", jsonObject)) {
            imageUrlString = jsonObject.get("image").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("title", jsonObject)) {
            titleText = jsonObject.get("title").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("url", jsonObject)) {
            urlString = jsonObject.get("url").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("video", jsonObject)) {
            videoUrlString = jsonObject.get("video").getAsString();
        }
    }
}
