package drivetag.drivetag.com.driveceos.business_layer;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding.SignInRequest;

/**
 * Created by artem .
 */

public class SignInFlow {

    private String email;
    private String password;

    public SignInFlow(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void resumeWithComletionBlock (CompletionHandler completionHandler) {
        final CompletionHandler handler = completionHandler;
        SignInRequest request = new SignInRequest(email, password);
        request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                handler.completionHandler(request);
            }

            @Override
            public void completionHandlerWithError(String error) {
                handler.completionHandlerWithError(error);
            }
        });
    }

    public interface CompletionHandler {
        void completionHandler(ServerRequest request);
        void completionHandlerWithError(String error);
    }
}
