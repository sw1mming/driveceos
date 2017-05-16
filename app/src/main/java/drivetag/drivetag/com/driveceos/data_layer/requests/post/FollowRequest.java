package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
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

public class FollowRequest extends ServerRequest {

    public Tag tag;
    private FollowApi service;


    /**
     * Interface.
     */

    public FollowRequest(Tag tag) {
        this.tag = tag;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = null;
        if (tag.isFollowing) {
            call = service.followDelete(tag.identifier);
        } else {
            call = service.follow(tag.identifier);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getFollowRequest());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                handler.completionHandlerWithError(t.toString());
            }
        });

        if (tag.isFollowing) {
            tag.isFollowing = false;
        } else {
            tag.isFollowing = true;
        }
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(FollowApi.class);
    }


    /** Private. */

    private FollowRequest getFollowRequest() {
        return this;
    }


    /** FollowApi interface. */

    interface FollowApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("api/v2/tags/{tagId}/follow")
        Call<JsonElement> follow(@Path("tagId") Integer identifier);

        @DELETE("api/v2/tags/{tagId}/follow")
        Call<JsonElement> followDelete(@Path("tagId") Integer identifier);
    }
}