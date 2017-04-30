package drivetag.drivetag.com.driveceos;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import io.fabric.sdk.android.Fabric;


/**
 * Created by yuriy.
 */
public class DTApplication extends Application {

    private static final String TWITTER_KEY = "oLSyPYJvv2JyZ1GtlsM5fEULq";
    private static final String TWITTER_SECRET = "69OyDQlunYfxJEGJ89ID6HiT5nyy9XlgYZVsK1A4ZiwaeIicTQ";


    private UserStorage userStorage;
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
    }

    public UserStorage getUserStorage() {
        if(userStorage == null) {
            userStorage = new UserStorage(this);
        }
        return userStorage;
    }
}
