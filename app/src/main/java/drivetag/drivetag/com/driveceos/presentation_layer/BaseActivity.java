package drivetag.drivetag.com.driveceos.presentation_layer;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by artem on 3/24/17.
 */

public class BaseActivity extends FragmentActivity {
    public void configureActionBar() {
        TextView actionBar = (TextView) findViewById(R.id.actionBar);
        Typeface actionBarFont = Typeface.createFromAsset(getAssets(), "fonts/Courgette_Regular.otf");
        actionBar.setTypeface(actionBarFont);
    }
}

