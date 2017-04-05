package drivetag.drivetag.com.driveceos;

import android.app.Application;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;


/**
 * Created by yuriy on 4/4/17.
 */

public class DTApplication extends Application {

    private UserStorage userStorage;

//    private ColorManager colorManager;

    @Override
    public void onCreate() {
        super.onCreate();

//        colorManager = new ColorManager(getApplicationContext());
    }

    public UserStorage getUserStorage() {
        if(userStorage == null) {
            userStorage = new UserStorage(this);
        }
        return userStorage;
    }
}
