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

public class InappropriateCommentRequest extends ServerRequest {

    private Comment comment;

    private InappropriateCommentApi service;


    /**
     * Interface.
     */

    public InappropriateCommentRequest(Comment comment) {
        this.comment = comment;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.inappropriateComment(comment.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                completionHandler.completionHandler(getInappropriateCommentRequest());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                completionHandler.completionHandlerWithError(t.toString());
            }
        });
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(InappropriateCommentApi.class);
    }


    /** Private. */

    private InappropriateCommentRequest getInappropriateCommentRequest() {
        return this;
    }


    /** InappropriateCommentApi interface. */

    interface InappropriateCommentApi {
        @POST("/api/v2/tags/posts/comments/{commentsId}/flag")
        Call<JsonElement> inappropriateComment(@Path("commentsId") String identifier);
    }
}
