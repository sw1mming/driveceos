package drivetag.drivetag.com.driveceos.data_layer.requests.post;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by artem on 4/28/17.
 */

public class LoadPostsRequest extends ServerRequest {

    public String driveTag;

    public Integer nextTime;

    public Integer startTime;

    public Boolean feedStalled;

    public Integer count;

    public String postsFilter;

    public Integer userDriveID;

    public Post post;

    public Tag tag;

    private String ceoTitle;

    private String globalPostFilter;

    private LoadPostsApi service;


    /** Interface. */

    public LoadPostsRequest(Tag tag ,Integer startTime) {
        this.tag = tag;
        this.startTime = startTime;

        count = 10;
    }

    public void resumeWithCompletionhandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("tagchain_string", tag.fullTagName());

        if (postsFilter.equals(Constants.KEY.POST_FILTER_GLOBAL_CEO_FEED)) {
            parameters.put("filter", "ceo");
        } else if (postsFilter.equals(Constants.KEY.POST_FILTER_GLOBAL_CALL_OUTS_FEED)) {
            parameters.put("filter", "call_outs");
        } else if (postsFilter.equals(Constants.KEY.POST_FILTER_GLOBAL_POLITICIAN_FEED)) {
            parameters.put("filter", "politicians");
        }

        parameters.put("count", count.toString());
        parameters.put("start_time", startTime.toString());

        Call<JsonElement> call = service.loadPosts(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();

                    serverResponse = objectsFromResponse(jsonObject);

                    JsonObject dictionary = jsonObject.get("discusses").getAsJsonObject();

                    if (jsonObject.get("feed_stalled") != null) {
                        feedStalled = jsonObject.get("feed_stalled").getAsBoolean();
                    }

                    nextTime = dictionary.get("next_time").getAsInt();

                    completionHandler.completionHandler(getLoadPostsRequest());
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
        service = retrofit.create(LoadPostsApi.class);
    }


    /** Private. */

    private List<Post> objectsFromResponse (JsonObject response) {
        JsonObject responseObjects = response.get("discusses").getAsJsonObject();
        JsonArray listObjects;

        if (responseObjects != null) {
            listObjects = responseObjects.get("list").getAsJsonArray();
        } else {
            listObjects = response.get("list").getAsJsonArray();
        }

        JsonArray tagsObjects = response.get("tags").getAsJsonArray();
        List<Tag> tags = new ArrayList<>();

        for (JsonElement tagsDictionaryElement: tagsObjects) {
            Tag tag = new Tag(tagsDictionaryElement.getAsJsonObject());

            tag.configureSubTagsFromTags(tags);
            tags.add(tag);
        }

        List<Post> posts = new ArrayList<>();

        for (JsonElement responseElement: listObjects) {
            if (Post.isHiddenPost(responseElement.getAsJsonObject())) {
                continue;
            }

            Post post = new Post(responseElement.getAsJsonObject(), tags);
            posts.add(post);
        }

        return posts;
    }

    private LoadPostsRequest getLoadPostsRequest() {
        return this;
    }


    /** LoadPostsApi class. */

    interface LoadPostsApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/tags/tagchain/feed")
        Call<JsonElement> loadPosts(HashMap<String, String> parameters);
    }
}
