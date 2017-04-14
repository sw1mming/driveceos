package drivetag.drivetag.com.driveceos.presentation_layer;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    /**
     * Put fragment into container. R.id.container
     * By default: without fragment name and putting into back stack
     *
     * @param fragment    Fragment support v4
     */
    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, R.id.container, false, null);
    }

    /**
     * Put fragment into container.
     * By default: without fragment name and putting into back stack
     *
     * @param fragment    Fragment support v4
     * @param containerId int id container for fragment
     */
    protected void replaceFragment(Fragment fragment, int containerId) {
        replaceFragment(fragment, containerId, false, null);
    }

    /**
     * @param isAddToBackStack if true Fragment will add to back stack
     * @param fragmentName     you can find fragment by this parameter
     */
    protected void replaceFragment(Fragment fragment, int containerId, boolean isAddToBackStack, String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, fragment);
        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }
}

