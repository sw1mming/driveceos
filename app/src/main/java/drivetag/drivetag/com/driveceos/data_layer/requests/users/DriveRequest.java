package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.PostPreferences;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/13/17.
 */

public class DriveRequest extends ServerRequest {

    public String thoughtType;
    private Post post;
    private DriveApi service;

    public DriveRequest(Post post) {
    this.post = post;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Object> parameters = new HashMap<>();
        List<String> socialKyes = new ArrayList<>();

        if(post.user.twitterOn) {
            socialKyes.add("tw");
        }

        if (post.user.facebookOn) {
            socialKyes.add("fb");
        }

        if(post.user.linkedInOn) {
            socialKyes.add("li");
        }

        socialKyes.add("dt");
        parameters.put("publish_on", socialKyes);

        if (thoughtType != null) {
            if (thoughtType.equals(Constants.KEY.THOUGHT_TYPE_DRIVE)) {
                parameters.put("thought", thoughtType);
            }
        }

        Call<JsonElement> call = null;
        if(!post.postPreferences.isDrivedByMe() ||
           PostPreferences.isThoughtSelected(thoughtType) && !post.postPreferences.isSameThoughtSelected(thoughtType)) {
            call = service.drive(post.identifier,parameters);
        } else {
            call = service.driveDelete(post.identifier);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getDriveRequest());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                handler.completionHandlerWithError(t.toString());
            }
        });
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(DriveApi.class);
    }


    /** Private. */

    private DriveRequest getDriveRequest() {
        return this;
    }


    /** DriveApi interface. */

    interface DriveApi {
        @PUT("api/v2/tags/posts/{postId}/drive")
        Call<JsonElement> drive(@Path("postId") String identifier, @Body HashMap<String, Object> parameters);

        @DELETE("api/v2/tags/posts/{postId}/drive")
        Call<JsonElement> driveDelete(@Path("postId") String identifier);
    }
}