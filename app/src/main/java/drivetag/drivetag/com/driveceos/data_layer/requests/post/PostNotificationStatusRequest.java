package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class PostNotificationStatusRequest extends ServerRequest {

    private Post post;
    private PostNotificationStatusApi service;


    /**
     * Interface.
     */

    public PostNotificationStatusRequest(Post post) {
        this.post = post;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.postNotificationStatus(post.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getPostNotificationStatusRequest());
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
        service = retrofit.create(PostNotificationStatusApi.class);
    }


    /** Private. */

    private PostNotificationStatusRequest getPostNotificationStatusRequest() {
        return this;
    }


    /** PostNotificationStatusApi interface. */

    interface PostNotificationStatusApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @DELETE("api/v2/tags/posts/{postId}/notice_status")
        Call<JsonElement> postNotificationStatus(@Path("postId") String postId);
    }
}
