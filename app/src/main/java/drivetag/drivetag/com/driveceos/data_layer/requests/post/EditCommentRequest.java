package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Comment;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class EditCommentRequest extends ServerRequest {

    public Comment comment;
    public String message;
    public Boolean shouldDeleteComment;
    public List<String> imagesUrlStrings;
    private EditCommentApi service;


    /**
     * Interface.
     */

    public EditCommentRequest(Comment comment, String message) {
       this.comment = comment;
       this.message = message;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("message", message);

        if(imagesUrlStrings != null) {
            parameters.put("images", imagesUrlStrings);
        }

        Call<JsonElement> call = null;
        if(shouldDeleteComment) {
            call = service.deleteComment(comment.identifier,parameters);
        } else {
            call = service.editComment(comment.identifier,parameters);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getEditCommentRequest());
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
        service = retrofit.create(EditCommentApi.class);
    }


    /** Private. */

    private EditCommentRequest getEditCommentRequest() {
        return this;
    }


    /** EditCommentApi interface. */

    interface EditCommentApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("/api/v2/tags/posts/comments/{commentId}")
        Call<JsonElement> editComment(@Path("commentId") String commentId, @Body HashMap<String, Object> parameters);

        @DELETE("/api/v2/tags/posts/comments/{commentId}")
        Call<JsonElement> deleteComment(@Path("commentId") String commentId, @Body HashMap<String, Object> parameters);
    }
}