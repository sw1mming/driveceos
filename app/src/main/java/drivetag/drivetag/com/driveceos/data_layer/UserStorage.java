package drivetag.drivetag.com.driveceos.data_layer;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.helpers.Constants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yuriy on 4/3/17.
 */

public class UserStorage  {

    private static final String DRIVE_ID = "DRIVE_ID";

    private static final String FACEBOOK_ID = "FACEBOOK_ID";

    private static final String USER_POSITION = "USER_POSITION";

    private static final String TWITTER_ID = "TWITTER_ID";

    private static final String LINKED_IN_ID = "LINKED_IN_ID";

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static final String FACEBOOK_ON = "FACEBOOK_ON";

    private static final String TWITTER_ON = "TWITTER_ON";

    private static final String LINKED_IN_ON = "LINKED_IN_ON";

    private static final String DRIVE_EMAIL = "DriveEmail";

    private static final String COLOR_THEME = "ColorTheme";

    private static final String AVATAR_URL = "AvatarUrl";

    private static final String DRIVE_TAG = "DriveTag";

    private static final String ALLOW_PUBLIC_POSTS_TO_ME = "ALLOW_PUBLIC_POSTS_TO_ME";

    private static final String ALLOW_TEAMUP_REQUEST_TO_ME = "AllowTeamupRequestToMe";

    private static final String FACEBOOK_TEAMMATE_CONNECTIONS_ENABLED = "FacebookTeammateConnectionsEnabled";

    private static final String LINKED_IN_TEAMMATE_CONNECTIONS_ENABLED = "LinkedInTeammateConnectionsEnabled";

    private static final String TWITTER_TEAMMATE_CONNECTIONS_ENABLED = "TwitterTeammateConnectionsEnabled";

    private static final String SHOW_WELCOME_MESSAGE = "ShowWelcomeMessage";

    private Context context;

    private List<DataSourceListener> listeners = new ArrayList<>();

    public Boolean twitterTeammateConnectionsEnabled;

    public Boolean linkedInTeammateConnectionsEnabled;

    public Boolean facebookTeammateConnectionsEnabled;

    public Boolean isLoggedIn;

    public Boolean allowPublicPostsToMe;

    public Boolean isSessionUpdated;

    public String accessToken;

    public Boolean shouldShowWelcomeMessage;

    public User user;


    /** Interface. */

    public UserStorage(Context context) {
        this.context = context;

        if (shouldSetupDefaultTeammateSetting()) {
            setupDefaultTeammateSettings();
        }

        if (shouldSetupDefaultWelcomeMessageSetting()) {
            shouldShowWelcomeMessage = true;
        }
    };

    public Boolean getAllowPublicPostsToMe() {
        Boolean value = sharedPreferences().getBoolean(ALLOW_PUBLIC_POSTS_TO_ME, false);

        return value;
    }

    public void setAllowPublicPostsToMe(Boolean allowPublicPostsToMe) {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ALLOW_PUBLIC_POSTS_TO_ME, allowPublicPostsToMe);
            editor.apply();
        }
    }

    public Boolean getallowTeamupRequestToMe() {
        Boolean value = sharedPreferences().getBoolean(ALLOW_TEAMUP_REQUEST_TO_ME, false);

        return value;
    }

    public void setAllowTeamupRequestToMe (Boolean allowTeamupRequestToMe) {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ALLOW_TEAMUP_REQUEST_TO_ME, allowTeamupRequestToMe);
            editor.apply();
        }
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(accessToken, ACCESS_TOKEN);
            editor.apply();
        }
    }

    public String getAccessToken() {
        return sharedPreferences().getString(ACCESS_TOKEN, null);
    }

    public Boolean getShouldShowWelcomeMessage() {
        return sharedPreferences().getBoolean(SHOW_WELCOME_MESSAGE, false);
    }

    public void setShouldShowWelcomeMessage(Boolean shouldShowWelcomeMessage) {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(SHOW_WELCOME_MESSAGE, shouldShowWelcomeMessage);
            editor.apply();
        }
    }

    public void saveUserAfterEmailLogin (User user) {
        configureDefaultFieldsForUser(user);
        saveUser();
        notifyListenersUserDidLogin(user);
    }

    public User user() {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            Number driveID = sharedPreferences.getLong(DRIVE_ID, 0);

            if(user == null && !driveID.equals(0)) {
                user = new User();
                user.driveID = driveID;
                user.title = sharedPreferences.getString(USER_POSITION, null);
                user.facebookID = sharedPreferences.getString(FACEBOOK_ID, null);
                user.twitterID = sharedPreferences.getString(TWITTER_ID, null);
                user.linkedInID = sharedPreferences.getString(LINKED_IN_ID, null);
                user.facebookOn = sharedPreferences.getBoolean(FACEBOOK_ON, false);
                user.twitterOn = sharedPreferences.getBoolean(TWITTER_ON, false);
                user.linkedInOn = sharedPreferences.getBoolean(LINKED_IN_ON, false);
                user.driveEmail = sharedPreferences.getString(DRIVE_EMAIL, null);
                user.colorTheme = sharedPreferences.getString(COLOR_THEME, null);

                String urlString = sharedPreferences.getString(AVATAR_URL, null);

                if (urlString != null) {
                    try {
                        user.avatarUrl = new URL(urlString);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                user.driveTag = sharedPreferences.getString(DRIVE_TAG, null);
            }
        }

        return user;
    }

    public void saveUser() {
        saveUser(user);
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = sharedPreferences();

        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putLong(DRIVE_ID, user.driveID.longValue());
            editor.putString(USER_POSITION, user.title);
            editor.putString(FACEBOOK_ID, user.facebookID);
            editor.putString(TWITTER_ID, user.twitterID);
            editor.putString(LINKED_IN_ID, user.linkedInID);
            editor.putBoolean(FACEBOOK_ON, user.facebookOn);
            editor.putBoolean(TWITTER_ON, user.twitterOn);
            editor.putBoolean(LINKED_IN_ON, user.linkedInOn);
            editor.putString(DRIVE_EMAIL, user.driveEmail);
            editor.putString(COLOR_THEME, user.colorTheme);
            editor.putString(AVATAR_URL, user.avatarUrl.toString());
            editor.putString(DRIVE_TAG, user.driveTag);

            editor.apply();
        }
    }

    public void saveUserAfterUpdateSession (User user) {
        configureDefaultFieldsForUser(user);
        saveUser();
        notifyListenersUserDidLogin(user);
    }

    public void saveUserAfterTwitterLogin (User user) {
        User currentUser = configureDefaultFieldsForUser(user);
        currentUser.twitterOn = true;
        saveUser();
        notifyListenersUserDidLogin(user);
    }

    public void saveUserAfterFacebookLogin (User user) {
        User currentUser = configureDefaultFieldsForUser(user);
        currentUser.facebookOn = true;
        saveUser();
        notifyListenersUserDidLogin(user);
    }

    public void saveUserAfterLinkedInLogin (User user) {
        User currentUser = configureDefaultFieldsForUser(user);
        currentUser.linkedInOn = true;
        saveUser();
        notifyListenersUserDidLogin(user);
    }

    public Boolean isLoggedIn() {

        if (user.driveID != null) {
            return true;
        } else if (user.facebookID != null) {
            return true;
        } else if (user.twitterID != null) {
            return true;
        } else if (user.linkedInID != null) {
            return true;
        } else {
            return false;
        }
    }

    public void resetUser() {
        User deletedUser = user;
        user = null;
        accessToken = null;

        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putLong(DRIVE_ID, 0);
            editor.putString(USER_POSITION,null);
            editor.putString(FACEBOOK_ID, null);
            editor.putString(TWITTER_ID, null);
            editor.putString(LINKED_IN_ID, null);
            editor.putString(DRIVE_EMAIL, null);
            editor.putBoolean(FACEBOOK_ON, false);
            editor.putBoolean(TWITTER_ON, false);
            editor.putString(COLOR_THEME, null);
            editor.putString(AVATAR_URL, null);
            editor.putString(DRIVE_TAG, null);
            editor.putBoolean(SHOW_WELCOME_MESSAGE, false);

            editor.apply();
        }
        setupDefaultTeammateSettings();
        notifyListenersUserDidLogout(deletedUser);
    }


    /** Private. */

   private User configureDefaultFieldsForUser (User user) {

       if (this.user == null) {
           this.user = new User();
       }

       User currentUser = this.user;

       currentUser.title = user.title;
       currentUser.driveID = user.driveID;
       currentUser.facebookID = user.facebookID;
       currentUser.twitterID = user.twitterID;
       currentUser.linkedInID = user.linkedInID;
       currentUser.avatarUrl = user.avatarUrl;
       currentUser.coverUrl = user.coverUrl;
       currentUser.myPagePhotoUrl = user.myPagePhotoUrl;
       currentUser.driveTag = user.driveTag;
       currentUser.driveEmail = user.driveEmail;
       currentUser.colorTheme = user.colorTheme;

       return currentUser;
   }


    /** Listeners */

    public void addListener(Object listener) {
        if (!listeners.contains(listener)) {
            listeners.add((DataSourceListener) listener);
        }
    }

    public void removeListener(Object listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void notifyListenersUserDidLogin (User user) {
        for (DataSourceListener listener : listeners) {
            listener.notifyListenersUserDidLogin(user);
        }
    }

    private void notifyListenersUserDidLogout (User user) {
        for (DataSourceListener listener : listeners) {
            listener.notifyListenersUserDidLogout(user);
        }
    }


    /** DataSourceListener. */

    public interface DataSourceListener {
        void notifyListenersUserDidLogin(User user);
        void notifyListenersUserDidLogout(User user);
    }


    /** Teammates */

    private void setFacebookTeammateConnectionsEnabled (Boolean facebookTeammateConnectionsEnabled) {
        setupValue(facebookTeammateConnectionsEnabled, FACEBOOK_TEAMMATE_CONNECTIONS_ENABLED);
    }

    private Boolean facebookTeammateConnectionsEnabled() {
        return sharedPreferences().getBoolean(FACEBOOK_TEAMMATE_CONNECTIONS_ENABLED, true);
    }

    private void setLinkedInTeammateConnectionsEnabled (Boolean linkedInTeammateConnectionsEnabled) {
        setupValue(linkedInTeammateConnectionsEnabled, LINKED_IN_TEAMMATE_CONNECTIONS_ENABLED);
    }

    private Boolean linkedInTeammateConnectionsEnabled() {
        return sharedPreferences().getBoolean(LINKED_IN_TEAMMATE_CONNECTIONS_ENABLED, true);
    }

    private void setTwitterTeammateConnectionsEnabled(Boolean twitterTeammateConnectionsEnabled) {
        setupValue(twitterTeammateConnectionsEnabled,TWITTER_TEAMMATE_CONNECTIONS_ENABLED);
    }

    private Boolean twitterTeammateConnectionsEnabled() {
        return sharedPreferences().getBoolean(TWITTER_TEAMMATE_CONNECTIONS_ENABLED, true);
    }

    private void setupValue(Boolean value, String key) {
        if (sharedPreferences() != null) {
            SharedPreferences.Editor editor = sharedPreferences().edit();

            editor.putBoolean(key, value);

            editor.apply();
        }
    }

    private void setupDefaultTeammateSettings() {
        setFacebookTeammateConnectionsEnabled(true);
        setLinkedInTeammateConnectionsEnabled(true);
        setTwitterTeammateConnectionsEnabled(true);
    }

    private Boolean shouldSetupDefaultTeammateSetting() {
        if (sharedPreferences().getBoolean(FACEBOOK_TEAMMATE_CONNECTIONS_ENABLED, false)) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean shouldSetupDefaultWelcomeMessageSetting() {
        if (sharedPreferences().getBoolean(SHOW_WELCOME_MESSAGE, false)) {
            return false;
        } else {
            return true;
        }
    }

    private SharedPreferences sharedPreferences() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.KEY.KEY_DL_SHARED_PREFERENCE, MODE_PRIVATE);
        return sharedPreferences;
    }
}