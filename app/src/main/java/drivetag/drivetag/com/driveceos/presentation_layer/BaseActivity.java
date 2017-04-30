package drivetag.drivetag.com.driveceos.presentation_layer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by artem.
 */

public class BaseActivity extends Activity {
    /**
     * Put fragment into container. R.id.content
     * By default: without fragment name and putting into back stack
     *
     * @param fragment    Fragment support v4
     */
    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, R.id.content, false, null);
    }

    /**
     * @param isAddToBackStack if true Fragment will add to back stack
     * @param fragmentName     you can find fragment by this parameter
     */
    protected void replaceFragment(Fragment fragment, int containerId, boolean isAddToBackStack, String fragmentName) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, fragment);
        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }
}

