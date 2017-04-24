package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by artem on 4/18/17.
 */

public class PostStats {

    int drivesCount;

    int drivesClickCount;

    int shareCount;

    String viewsCount;

    String teammatesCount;

    int opinionsCount;

    int leaderThoughtCount;

    int goodJobThoughtCount;

    int greedyThoughtCount;

    int sustainableThoughtCount;

    int harmfulThoughtCount;

    int wastefulThoughtCount;


    public PostStats(JsonObject jsonObject) {
        if (JsonObjectHelper.hasValueFromKey("drives_count", jsonObject)) {
            drivesCount = jsonObject.getAsJsonObject("drives_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("drive_click_count", jsonObject)) {
            drivesClickCount = jsonObject.getAsJsonObject("drive_click_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("share_click_count", jsonObject)) {
            shareCount = jsonObject.getAsJsonObject("share_click_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("post_zoomin_ct", jsonObject)) {
            viewsCount = jsonObject.getAsJsonObject("post_zoomin_ct").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("teammates_count", jsonObject)) {
            teammatesCount = jsonObject.getAsJsonObject("teammates_count").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("comments_count", jsonObject)) {
            opinionsCount = jsonObject.getAsJsonObject("comments_count").getAsInt();
        }


        /** Thoughts */

        if (JsonObjectHelper.hasValueFromKey("thoughts_leader_count", jsonObject)) {
            leaderThoughtCount = jsonObject.getAsJsonObject("thoughts_leader_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_good_job_count", jsonObject)) {
            goodJobThoughtCount = jsonObject.getAsJsonObject("thoughts_good_job_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_greedy_count", jsonObject)) {
            greedyThoughtCount = jsonObject.getAsJsonObject("thoughts_greedy_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_sustainable_count", jsonObject)) {
            sustainableThoughtCount = jsonObject.getAsJsonObject("thoughts_sustainable_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_harmful_count", jsonObject)) {
            harmfulThoughtCount = jsonObject.getAsJsonObject("thoughts_harmful_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_wasteful_count", jsonObject)) {
            wastefulThoughtCount = jsonObject.getAsJsonObject("thoughts_wasteful_count").getAsInt();
        }
    }

}
