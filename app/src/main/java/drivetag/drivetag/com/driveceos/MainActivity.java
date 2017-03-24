package drivetag.drivetag.com.driveceos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;

import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SignInRequest;

/**
 * Created by yuriy on 3/22/17.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInRequest signInRequest = new SignInRequest("rayban@mailinator.com", "qwe123456");
        signInRequest.resumeWithCompletionHandler(new SignInRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                System.out.println();
            }

            @Override
            public void completionHandlerWithError(String error) {
                System.out.println();
            }
        });
    }
}
