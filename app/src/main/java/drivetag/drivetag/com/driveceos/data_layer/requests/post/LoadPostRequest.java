package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by artem on 4/25/17.
 */

public class LoadPostRequest extends ServerRequest {

    private Post post;

    private LoadPostApi service;


    /** Interface. */

    public LoadPostRequest(Post post) {
        this.post = post;
    }

    public void resumeWithCompletionHandler (LoadPostCompletionHandler handler) {
        final LoadPostCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.loadPost(post.identifier);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();
                post = objectsFromResponse(jsonObject);

                completionHandler.completionHandler(getLoadPostRequest(), post);
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
        service = retrofit.create(LoadPostApi.class);
    }

    public interface LoadPostCompletionHandler {
        void completionHandler(LoadPostRequest request, Post post);
        void completionHandlerWithError(String error);
    }


    /** Private. */

    private Post objectsFromResponse (JsonObject response) {
        JsonArray itemsValue = response.getAsJsonArray("discusses");
        JsonObject discussesDictionary = (JsonObject) itemsValue.get(0);
        JsonArray tagsObjects = response.get("tags").getAsJsonArray();

        List<Tag> tags = new ArrayList<>();

        for (JsonElement tagsDictionaryElement: tagsObjects) {
            Tag tag = new Tag(tagsDictionaryElement.getAsJsonObject());
            tags.add(tag);
        }

        return new Post(discussesDictionary, tags);
    }

    private LoadPostRequest getLoadPostRequest() {
        return this;
    }


    /** LoadPostApi interface. */

    interface LoadPostApi {

        @GET("/api/v2/tags/posts/{postId}")
        Call<JsonElement> loadPost(@Path("postId") String identifier);
    }
}
