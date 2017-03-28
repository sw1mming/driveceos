package drivetag.drivetag.com.driveceos;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.requests.CheckEmailTypeRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ImageUploadRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SuggestedIdentifierRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding.SignInRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.UpdateCoverPhotoRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.UpdateMyPageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import java.util.List;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.MyTeamupRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.UserProfileActivity;

/**
 * Created by yuriy on 3/22/17.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**  CheckEmailTypeRequest  */
//        CheckEmailTypeRequest checkEmailTypeRequest = new CheckEmailTypeRequest("safcvafceqwcwcqqewc@gmail.com");
//        checkEmailTypeRequest.resumeWithCompletionHandler(new CheckEmailTypeRequest.ServerCompletionHandler() {
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


        /**  SuggestedIdentifierRequest  */
//        SuggestedIdentifierRequest suggestedIdentifierRequest = new SuggestedIdentifierRequest("dcefd3Secc@mailinator.com", "dcefd3Secc","Dfvcdsdfgfd");
//        suggestedIdentifierRequest.resumeWithCompletionHandler(new SuggestedIdentifierRequest.ServerCompletionHandler() {
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


        /**  ImageUploadRequest  */
//        Drawable myIcon = getResources().getDrawable( R.drawable.facebook_enabled_icon );
//
//        ImageUploadRequest imageUploadRequest = new ImageUploadRequest(myIcon);
//        imageUploadRequest.resumeWithCompletionHandler(new ImageUploadRequest.ServerCompletionHandler() {
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


//        String fileName = String.format("text_%d", 1);
//                System.out.println(fileName);
//        System.out.println(fileName);


//        log.debug( String.format("%s is %d years old, er, young", "Al", 45) );

//        Intent intent = new Intent(this, UserProfileActivity.class);
//        startActivity(intent);

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

//        MyTeamupRequest request = new MyTeamupRequest();
//        request.resumeWithCompletionHandler(new MyTeamupRequest.MyTeamupCompletionHandler<List<User>, List<User>>() {
//
//            @Override
//            public void completionHandler(List<User> historyUsers, List<User> incomingUsers, ServerRequest request) {
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
