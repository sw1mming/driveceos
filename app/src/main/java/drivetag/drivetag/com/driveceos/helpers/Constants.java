package drivetag.drivetag.com.driveceos.helpers;

/**
 * Created by yuriy on 4/4/17.
 */

public abstract class Constants {

    public static class KEY {

        public static final String TWITTER_DID_AUTHENTICATE_NOTIFICATION = "TwitterDidAuthenticateNotification";
        public static final String TWITTER_URL_KEY = "TwitterUrlKey";


        /* Filters of Feed */

        public static final String POSTS_FILTER_CURRENT_USER_UPDATED = "PostsFilterCurrentUserUpdated";
        public static final String POSTS_FILTER_CURRENT_USER_MY = "PostsFilterCurrentUserMy";
        public static final String POSTS_FILTER_CURRENT_USER_MY_TEAM = "PostsFilterCurrentUserMyTeam";
        public static final String POSTS_FILTER_CURRENT_USER_PUBLIC = "PostsFilterCurrentUserPublic";
        public static final String POST_FILTER_USER_PUBLIC = "UserFeedFilterPublic";
        public static final String POST_FILTER_USER_OWNER = "UserFeedFilterOwner";
        public static final String POST_FILTER_GLOBAL = "driveleader";


        // Global filters
        public static final String POST_FILTER_GLOBAL_HOME_TEAM_FEED = "PostFilterGlobalHomeTeamFeed";
        public static final String POST_FILTER_GLOBAL_CEO_FEED = "PostFilterGlobalCeoFeed";
        public static final String POST_FILTER_GLOBAL_POLITICIAN_FEED = "PostFilterGlobalPoliticianFeed";
        public static final String POST_FILTER_GLOBAL_CALL_OUTS_FEED = "PostFilterGlobalCallOutsFeed";

        // FilterCategory
        public static final String FILTER_CATEGORY_ALL = "FilterCategoryAll";
        public static final String FILTER_CATEGORY_CEO = "FilterCategoryCeo";
        public static final String FILTER_CATEGORY_POLITICIAN = "FilterCategoryPolitician";

        // Tag team
        public static final String TAG_TEAM_ORDER_MARKET = "market";
        public static final String TAG_TEAM_ORDER_TITLE = "title";
        public static final String TAG_TEAM_ORDER_DRIVEN = "driven";
        public static final String TAG_TEAM_ORDER_CALLED = "called";
        public static final String TAG_TEAM_ORDER_MARKET_CEOS = "market_ceos";
        public static final String TAG_TEAM_ORDER_MARKET_POLITICIANS = "gdp_politicians";

        // Filters of Tag team
        public static final String MY_TEAMMATES_FILTER = "teammates";
        public static final String PROFESSIONAL_FILTER = "teammates_pro";
        public static final String SOCIAL_FILTER = "teammates_social";

        // Feed cell controllers identifiers
        public static final String FEED_WELCOME_MESSAGE_CELL_IDENTIFIER = "FeedWelcomeMessageCellIdentifier";
        public static final String FEED_TAG_SUBSCRIPTION_CELL_IDENTIFIER = "FeedTagSubscriptionCellIdentifier";
        public static final String FEED_POST_BOX_CELL_IDENTIFIER = "FeedPostBoxCellIdentifier";
        public static final String FEED_TEAM_CELL_IDENTIFIER = "FeedTeamCellIdentifier";
        public static final String FEED_GLOBAL_TAG_FILTERS_CELL_IDENTIFIER = "FeedGlobalTagFiltersCellIdentifier";
        public static final String FEED_USER_FILTERS_CELL_IDENTIFIER = "FeedUserFiltersCellIdentifier";
        public static final String FEED_CEO_FILTERS_CELL_IDENTIFIER = "FeedCeoFiltersCellIdentifier";
        public static final String FEED_COMPANY_FILTERS_CELL_IDENTIFIER = "FeedCompanyFiltersCellIdentifier";
        public static final String FEED_CURRENT_USER_FILTERS_CELL_IDENTIFIER = "FeedCurrentUserFiltersCellIdentifier";
        public static final String FEED_POST_CELL_IDENTIFIER = "FeedPostCellIdentifier";

        // Post type
        public static final String POST_TYPE_USER_CALL_OUT = "user_call_out";
        public static final String POST_TYPE_WEB = "web";
        public static final String POST_TYPE_TAG_LINE = "tagline";
        public static final String POST_TYPE_AUTO_TEAMUP = "auto_teamup";
        public static final String POST_TYPE_AUTO_WELCOME = "auto_welcome";
        public static final String POST_TYPE_AUTO_WDU = "auto_wdu";
        public static final String POST_TYPE_AUTO_PAGE_TEAMUP = "auto_page_teamup";
        public static final String POST_TYPE_AUTO_LAUNCH = "auto_launch";
        public static final String POST_TYPE_AUTO_USER_SIGNUP = "auto_user_signup";
        public static final String POST_TYPE_AUTO_SUBSCRIBE = "auto_subscribe";
       // public static final String POST_TYPE_AUTO_OPEN_TEAMUP = "auto_open_teamup";
       // public static  final String POST_TYPE_AUTO_TAG_DRIVE = "auto_tag_drive";
       // public static final String POST_TYPE_AUTO_TAG_SHARE = "auto_tag_share";
        public static final String POST_TYPE_CALL_OUT_LIVE = "user_call_out_live";
        public static final String POST_TYPE_CALL_OUT_LIVE_HQ = "user_call_out_livehq";
        public static final String POST_TYPE_AUTO_GO_LIVE = "auto_golive";

        // Thought
        public static final String THOUGHT_TYPE_NONE = "none";
        public static final String THOUGHT_TYPE_DRIVE = "drive";
        public static final String THOUGHT_TYPE_ALL = "all";
        public static final String THOUGHT_TYPE_LEADER = "leader";
        public static final String THOUGHT_TYPE_GOOD_JOB = "good_job";
        public static final String THOUGHT_TYPE_SUSTAINABLE = "sustainable";
        public static final String THOUGHT_TYPE_GREEDY = "greedy";
        public static final String THOUGHT_TYPE_HARMFUL = "harmful";
        public static final String THOUGHT_TYPE_WASTEFUL = "wasteful";

        // Thought long
        public static final String THOUGHT_LONG_TYPE_LEADER = "thought_leader";
        public static final String THOUGHT_LONG_TYPE_GOOD_JOB = "thought_good_job";
        public static final String THOUGHT_LONG_TYPE_SUSTAINABLE = "thought_sustainable";
        public static final String THOUGHT_LONG_TYPE_GREEDY = "thought_greedy";
        public static final String THOUGHT_LONG_TYPE_HARMFUL = "thought_harmful";
        public static final String THOUGHT_LONG_TYPE_WASTEFUL = "thought_wasteful";

        //Notices
        public static final String NOTICES_FILTER_ALL = "people";
        public static final String NOTICES_FILTER_ONLY_MY_TEAM = "team";
        public static final String NOTICE_STATUS_ON = "on";
        public static final String NOTICE_STATUS_OFF = "none";

        // Notice types NOTICE_TYPE
        public static final String NOTICE_TYPE_WRITE_OPINION = "write_opinion";
        public static final String NOTICE_TYPE_WRITE_POST = "write_post";
        public static final String NOTICE_TYPE_EDIT_POST = "edit_post";
        public static final String NOTICE_TYPE_DRIVE_POST = "drive_post";
        public static final String NOTICE_TYPE_SHARE_POST = "share_post";
        public static final String NOTICE_TYPE_EDIT_OPINION = "edit_opinion";
        public static final String NOTICE_TYPE_DRIVE_OPINION = "drive_opinion";

        public static final String KEY_DL_SHARED_PREFERENCE = "KEY_DL_SHARED_PREFERENCE";
    }

    public enum PeopleDrivingType {
        PEOPLE_DRIVING_TYPE_POST,
        PEOPLE_DRIVING_TYPE_TODAY
    }
}
