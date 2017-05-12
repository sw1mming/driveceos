package drivetag.drivetag.com.driveceos.data_layer.controllers;

import com.google.gson.JsonObject;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class WelcomeMessageCellController extends FeedCellController {

    public JsonObject welcomeInfo;

    public WelcomeMessageCellController(JsonObject welcomeInfo) {
        this.welcomeInfo = welcomeInfo;
    }
}
