package drivetag.drivetag.com.driveceos.data_layer.requests.comment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Comment;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by artem on 4/25/17.
 */

public class LoadPostCommentsRequest extends ServerRequest {

    public Integer nextIndex;

    public int startIndex;

    public int count;

    private Post post;

    private LoadPostCommentApi service;


    /** Interface. */

    public LoadPostCommentsRequest(Post post) {
        this.post = post;
        this.count = 10;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("count", count);

        if (startIndex > 0) {
            parameters.put("start_index", startIndex);
        }

        Call<JsonElement> call = service.loadPostComment(post.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() == null) {
                    final JsonObject jsonObject = response.body().getAsJsonObject();
                    serverResponse = objectsFromResponse(jsonObject);

                    if (JsonObjectHelper.hasValueFromKey("next_index", jsonObject)) {
                        nextIndex = jsonObject.get("next_index").getAsInt();
                    }

                    completionHandler.completionHandler(getLoadPostCommentsRequest());
                } else {
                    completionHandler.completionHandlerWithError(defaultErrorMessage());
                }
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
        service = retrofit.create(LoadPostCommentApi.class);
    }


    /** Private. */

    private List<Comment> objectsFromResponse(JsonObject response) {

        JsonArray responseObjects = response.get("list").getAsJsonArray();
        List<Comment> comments = new ArrayList<>();
        JsonArray tagsObjects = response.get("tags").getAsJsonArray();

        List<Tag> tags = new ArrayList<>();
        for (JsonElement tagsDictionaryElement: tagsObjects) {
            Tag tag = new Tag(tagsDictionaryElement.getAsJsonObject());
            tags.add(tag);
        }

        for (JsonElement itemArrayElement: responseObjects) {
            JsonArray itemArray = itemArrayElement.getAsJsonArray();
            if (itemArray.size() > 1) {
                JsonElement commentInfoElement = itemArray.get(1);
                Comment comment = new Comment(commentInfoElement.getAsJsonObject(), tags);

                if (!comment.isVisible) {
                    comments.add(comment);
                }
            }
        }

        return comments;
    }

    private LoadPostCommentsRequest getLoadPostCommentsRequest() {
        return this;
    }


    /** LoadPostCommentApi interface. */

    interface LoadPostCommentApi {

        @GET("/api/v2/tags/posts/{postId}/comments")
        Call<JsonElement> loadPostComment(@Path("postId") String identifier);
    }
}


