package drivetag.drivetag.com.driveceos.data_layer.controllers;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class UserFiltersCellController extends FeedCellController {

    private Tag tag;

    public UserFiltersCellController(Tag tag) {
        this.tag = tag;
    }
}
