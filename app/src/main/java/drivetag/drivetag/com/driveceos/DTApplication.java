package drivetag.drivetag.com.driveceos;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import io.fabric.sdk.android.Fabric;


/**
 * Created by yuriy on 4/4/17.
 */

public class DTApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "oLSyPYJvv2JyZ1GtlsM5fEULq";
    private static final String TWITTER_SECRET = "69OyDQlunYfxJEGJ89ID6HiT5nyy9XlgYZVsK1A4ZiwaeIicTQ";


    private UserStorage userStorage;

//    private ColorManager colorManager;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

//        colorManager = new ColorManager(getApplicationContext());
    }

    public UserStorage getUserStorage() {
        if(userStorage == null) {
            userStorage = new UserStorage(this);
        }
        return userStorage;
    }
}
