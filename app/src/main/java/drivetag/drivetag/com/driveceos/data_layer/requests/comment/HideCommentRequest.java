package drivetag.drivetag.com.driveceos.data_layer.requests.comment;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.Comment;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class HideCommentRequest extends ServerRequest {

    private Comment comment;

    private HideCommentApi service;

    public HideCommentRequest(Comment comment) {
        this.comment = comment;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        Call<JsonElement> call = service.hideComment(comment.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                handler.completionHandler(getHideCommentRequest());
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
        service = retrofit.create(HideCommentApi.class);
    }


    /** Private. */

    private HideCommentRequest getHideCommentRequest() {
        return this;
    }


    /** HideCommentApi interface. */

    interface HideCommentApi {
        @POST("/api/v2/tags/posts/comments/{commentsId}/hide")
        Call<JsonElement> hideComment(@Path("commentsId") String identifier);
    }
}
