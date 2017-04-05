package drivetag.drivetag.com.driveceos.business_layer;

import android.app.Application;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;

/**
 * Created by yuriy on 4/4/17.
 */

public class AppScope extends Application {

    private UserStorage userStorage;

//    private ColorManager colorManager;

    @Override
    public void onCreate() {
        super.onCreate();

        userStorage = new UserStorage(getApplicationContext());
//        colorManager = new ColorManager(getApplicationContext());
    }
}
