package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by yuriy on 4/2/17.
 */

public class Subscription {
    public Integer recomendetGear;
    public Boolean overSpeedLimit;
    public Integer currentGear;


    /** Interface. */

    public Subscription(JsonObject jsonObject) {

        if (JsonObjectHelper.hasValueFromKey("over_speed_limit", jsonObject)) {
            overSpeedLimit = jsonObject.get("over_speed_limit").getAsBoolean();
        }
        if (JsonObjectHelper.hasValueFromKey("recommended_gear", jsonObject)) {
            recomendetGear = jsonObject.get("recommended_gear").getAsInt();
        }
        if (JsonObjectHelper.hasValueFromKey("current_gear", jsonObject)) {
            currentGear = jsonObject.get("current_gear").getAsInt();
        }
    }
}
