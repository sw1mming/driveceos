package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/18/17.
 */

public class InappropriatePostRequest extends ServerRequest{

    private Post post;
    private InappropriatePostApi service;


    /**
     * Interface.
     */

    public InappropriatePostRequest(Post post) {
        this.post = post;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.inappropriatePost(post.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getInappropriatePostRequest());
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
        service = retrofit.create(InappropriatePostApi.class);
    }


    /** Private. */

    private InappropriatePostRequest getInappropriatePostRequest() {
        return this;
    }


    /** InappropriatePostApi interface. */

    interface InappropriatePostApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/tags/posts/{postId}/flag")
        Call<JsonElement> inappropriatePost(@Path("postId") String identifier);
    }
}