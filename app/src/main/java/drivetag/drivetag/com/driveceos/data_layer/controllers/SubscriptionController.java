package drivetag.drivetag.com.driveceos.data_layer.controllers;

import drivetag.drivetag.com.driveceos.data_layer.models.Subscription;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class SubscriptionController extends FeedCellController {

    public Subscription subscription;

    private Tag tag;

    public SubscriptionController(Subscription subscription, Tag tag) {
        this.subscription = subscription;
        this.tag = tag;
    }
}
