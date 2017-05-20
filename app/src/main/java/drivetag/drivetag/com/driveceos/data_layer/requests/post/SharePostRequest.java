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
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class SharePostRequest extends ServerRequest {

    private Post post;
    private SharePostApi service;


    /**
     * Interface.
     */

    public SharePostRequest(Post post) {
        this.post = post;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        Call<JsonElement> call = null;
        if(post.postPreferences.isSharedPost) {
            call = service.deletePost(post.identifier);
        } else {
            call = service.sharePost(post.identifier);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getSharePostRequest());
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
        service = retrofit.create(SharePostApi.class);
    }


    /** Private. */

    private SharePostRequest getSharePostRequest() {
        return this;
    }


    /** SharePostApi interface. */

    interface SharePostApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("api/v2/tags/posts/{postId}/share")
        Call<JsonElement> sharePost(@Path("postId") String postId);

        @DELETE("api/v2/tags/posts/{postId}/share")
        Call<JsonElement> deletePost(@Path("postId") String postId);
    }
}