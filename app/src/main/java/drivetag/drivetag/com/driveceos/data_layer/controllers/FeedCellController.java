package drivetag.drivetag.com.driveceos.data_layer.controllers;

/**
 * Created by YuriySokurko on 5/12/17.
 */

import android.support.v7.widget.RecyclerView;

import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.FeedDataSource;
import drivetag.drivetag.com.driveceos.helpers.ColorManager;


public class FeedCellController {

    public ColorManager colorManager;
    public UserStorage userStorage;
    public FeedActionsController feedActionsController;
    public FeedDataSource feedDataSource;
}
