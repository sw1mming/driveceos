package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.TagPostRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class SendCommentRequest extends TagPostRequest {

    public Post post;
    private User user;
    private String message;
    private SendCommentApi service;


    /**
     * Interface.
     */

    public SendCommentRequest(Post post, User user, String message) {
        this.post = post;
        this.user = user;
        this.message = message;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Object> parameters = new HashMap<>();
        List<String> socialKeys = new ArrayList<>();

        if (user.twitterOn) {
            socialKeys.add("tw");
        }

        if (user.facebookOn) {
            socialKeys.add("fb");
        }

        if (user.linkedInOn) {
            socialKeys.add("li");
        }

        parameters.put("publish_on", socialKeys);
        parameters.put("message", message);

        if(imagesUrlStrings != null) {
            parameters.put("images", imagesUrlStrings);
        }

        Call<JsonElement> call = service.sendComment(post.identifier,parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getSendCommentRequest());
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
        service = retrofit.create(SendCommentApi.class);
    }


    /** Private. */

    private SendCommentRequest getSendCommentRequest() {
        return this;
    }


    /** SendCommentApi interface. */

    interface SendCommentApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/tags/posts/{postId}/comment")
        Call<JsonElement> sendComment(@Path("postId") String postId, @Body HashMap<String, Object> parameters);
    }
}
