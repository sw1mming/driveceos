package drivetag.drivetag.com.driveceos.data_layer.factories;

import android.app.Activity;

import com.google.gson.JsonObject;

import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.controllers.CeoFiltersCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.CompanyFiltersCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.CurrentUserFiltersCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.FeedActionsController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.FeedCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.FeedPostController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.GlobalTagFiltersCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.PostBoxCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.SubscriptionController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.TeamCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.UserFiltersCellController;
import drivetag.drivetag.com.driveceos.data_layer.controllers.WelcomeMessageCellController;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.FeedDataSource;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Subscription;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.helpers.ColorManager;
import drivetag.drivetag.com.driveceos.helpers.Constants;

/**
 * Created by YuriySokurko on 5/11/17.
 */

public class FeedCellControllersFactory {

    public FeedCellController feedCellControllerForIdentifier(String identifier,
                                                              Object object,
                                                              ColorManager colorManager,
                                                              UserStorage userStorage,
                                                              FeedActionsController feedActionsController,
                                                              Activity activity,
                                                              FeedDataSource feedDataSource
    ) {

        FeedCellController controller = null;

        if (identifier.equals(Constants.KEY.FEED_TAG_SUBSCRIPTION_CELL_IDENTIFIER)) {
            controller = new SubscriptionController((Subscription) object, feedDataSource.tag);
        } else if (identifier.equals(Constants.KEY.FEED_WELCOME_MESSAGE_CELL_IDENTIFIER)) {
            controller = new WelcomeMessageCellController((JsonObject) object);
        } else if (identifier.equals(Constants.KEY.FEED_POST_BOX_CELL_IDENTIFIER)) {
            controller = new PostBoxCellController((Tag) object);
        } else if (identifier.equals(Constants.KEY.FEED_TEAM_CELL_IDENTIFIER)) {
            controller = new TeamCellController((List<Tag>) object);
        } else if (identifier.equals(Constants.KEY.FEED_GLOBAL_TAG_FILTERS_CELL_IDENTIFIER)) {
            controller = new GlobalTagFiltersCellController();
        } else if (identifier.equals(Constants.KEY.FEED_USER_FILTERS_CELL_IDENTIFIER)) {
            controller = new UserFiltersCellController((Tag) object);
        } else if (identifier.equals(Constants.KEY.FEED_CEO_FILTERS_CELL_IDENTIFIER)) {
            controller = new CeoFiltersCellController((Tag) object);
        } else if (identifier.equals(Constants.KEY.FEED_COMPANY_FILTERS_CELL_IDENTIFIER)) {
            controller = new CompanyFiltersCellController((Tag) object);
        } else if (identifier.equals(Constants.KEY.FEED_CURRENT_USER_FILTERS_CELL_IDENTIFIER)) {
            controller = new CurrentUserFiltersCellController();
        } else if (identifier.equals(Constants.KEY.FEED_POST_CELL_IDENTIFIER)) {
            controller = new FeedPostController((Post) object);
            // TODO: 5/12/17
        }

        if(controller != null) {
            controller.colorManager = colorManager;
            controller.userStorage = userStorage;
            controller.feedActionsController = feedActionsController;
            // TODO: 5/12/17
            controller.feedDataSource = feedDataSource;
        }

        return controller;
    }

}
