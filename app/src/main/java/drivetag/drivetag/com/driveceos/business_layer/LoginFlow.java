package drivetag.drivetag.com.driveceos.business_layer;

import android.content.Context;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.LoadCurrentUserRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;

/**
 * Created by artem on 4/5/17.
 */

public class LoginFlow {

    UserStorage userStorage;
    public Context context;

    private LoadCurrentUserRequest loadCurrentUserRequest;


    /** Interface. */

    LoginFlow(DTApplication dtApplication) {
        this.userStorage = dtApplication.getUserStorage();
        this.context = dtApplication;
    }

    void loadUserWithCompletionHandler(final CompletionHandler<User> handler) {
        loadCurrentUserRequest = new LoadCurrentUserRequest();
        loadCurrentUserRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<User>() {
            @Override
            public void completionHandler(ServerRequest<User> request) {
                User user = null;
                if (request.serverResponse != null) {
                    user = request.serverResponse;
                }

                if (handler != null) {
                    handler.completionHandler(user, request.error);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandler(null, error);
                }
            }
        });
    }

    public interface CompletionHandler<T> {
        void completionHandler(T value, String error);
    }
}

