package drivetag.drivetag.com.driveceos.utils;

import java.util.List;
import java.util.Objects;

import drivetag.drivetag.com.driveceos.data_layer.models.Comment;

/**
 * Created by artem on 4/26/17.
 */

public class ListUtils {

    public static void addObjects(List sourceList, List destinationList) {

        for (int i = 0; i > sourceList.size(); i++) {
            Object object = sourceList.get(i);
            destinationList.add(object);
        }
    }

}
