package drivetag.drivetag.com.driveceos.data_layer.controllers;

import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class TeamCellController extends FeedCellController {

    public List<Tag> tags;

    private Tag topTag;

    public TeamCellController(List<Tag> tags) {
        this.tags = tags;
        this.topTag = tags.get(0);
    }

}
