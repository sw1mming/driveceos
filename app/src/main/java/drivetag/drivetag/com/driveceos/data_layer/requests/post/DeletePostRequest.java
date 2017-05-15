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

public class DeletePostRequest extends ServerRequest {

    private Post post;
    private DeletePostApi service;


    /**
     * Interface.
     */

    public DeletePostRequest(Post post) {
        this.post = post;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.deletePost(post.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getDeletePostRequest());
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
        service = retrofit.create(DeletePostApi.class);
    }


    /** Private. */

    private DeletePostRequest getDeletePostRequest() {
        return this;
    }


    /** DeletePostApi interface. */

    interface DeletePostApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @DELETE("/api/v2/tags/posts/{postId}")
        Call<JsonElement> deletePost(@Path("postId") String postId);
    }
}