package drivetag.drivetag.com.driveceos.helpers;

/**
 * Created by yuriy on 4/4/17.
 */

public abstract class Constants {

    public static class KEY {
        public static final String KEY_DL_SHARED_PREFERENCE = "KEY_DL_SHARED_PREFERENCE";
        public static final String THOUGHT_TYPE_NONE = "none";
        public static final String THOUGHT_TYPE_ALL = "all";
        public static final String THOUGHT_TYPE_DRIVE = "drive";

        public static final String FILTER_CATEGORY_ALL = "FilterCategoryAll";
        public static final String FILTER_CATEGORY_CEO = "FilterCategoryCeo";
        public static final String FILTER_CATEGORY_POLITICIAN = "FilterCategoryPolitician";
    }

    public enum PeopleDrivingType {
        PEOPLE_DRIVING_TYPE_POST,
        PEOPLE_DRIVING_TYPE_TODAY
    }
}
