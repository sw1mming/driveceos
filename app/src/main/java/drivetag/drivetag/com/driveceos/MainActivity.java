package drivetag.drivetag.com.driveceos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding.SignInRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.UpdateCoverPhotoRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.UpdateMyPageRequest;

/**
 * Created by yuriy on 3/22/17.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**  SignInRequest  */
//        SignInRequest signInRequest = new SignInRequest("rayban@mailinator.com", "qwe123456");
//        signInRequest.resumeWithCompletionHandler(new SignInRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });

        /**  UpdateCoverPhotoRequest  */
//
//          UpdateCoverPhotoRequest updateCoverPhotoRequest = new UpdateCoverPhotoRequest(null);
//          updateCoverPhotoRequest.resumeWithCompletionHandler(new UpdateCoverPhotoRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });

        /**  UpdateMyPageRequest  */

//
//        List<String> array = new ArrayList<>();
//        array.add("https://www.rover.com/blog/wp-content/uploads/2015/05/dog-candy-junk-food-599x340.jpg");
//
//
//          UpdateMyPageRequest updateMyPageRequest = new UpdateMyPageRequest(array);
//          updateMyPageRequest.resumeWithCompletionHandler(new UpdateMyPageRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });
        

    }
}
