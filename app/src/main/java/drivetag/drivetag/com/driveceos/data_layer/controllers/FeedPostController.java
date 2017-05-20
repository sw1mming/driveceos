package drivetag.drivetag.com.driveceos.data_layer.controllers;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class FeedPostController extends FeedCellController {

    private Post post;

    public FeedPostController(Post post) {
        this.post = post;
    }
}
