package drivetag.drivetag.com.driveceos.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Properties;

public class ColorManager {
    private Properties prop = new Properties();

    public void reload(Context context) {
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        final Configuration configuration = resources.getConfiguration();
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
