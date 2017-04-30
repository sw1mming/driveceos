package drivetag.drivetag.com.driveceos.utils;

import java.util.List;

/**
 * Created by artem on 4/26/17.
 */

public class ListUtils {

    /**
     * looks like sin
     */
    public static void addObjects(List sourceList, List destinationList) {

        for (int i = 0; i < sourceList.size(); i++) {
            Object object = sourceList.get(i);
            destinationList.add(object);
        }
    }

}
