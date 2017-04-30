package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by artem on 4/26/17.
 */

public class PeopleDrivingRequest extends ServerRequest {

    public Integer nextTime;

    public Integer startTime;

    public Integer count;

    public String thoughtsType;

    public Integer sustainableCounter;

    public Integer greedyCounter;

    public Integer leaderCounter;

    public Integer goodJobCounter;

    public Integer harmfulCounter;

    public Integer wastefulCounter;

    public Integer drivesCount;

    public Constants.PeopleDrivingType peopleDrivingType;

    private Post post;

    private Tag tag;

    private PeopleDrivingApi service;

    public PeopleDrivingRequest(Post post, Integer startTime, String thoughtsType) {
        this.startTime = startTime;
        this.post = post;

        initParameters(startTime, thoughtsType);
    }

    public PeopleDrivingRequest(Tag tag, Integer startTime, String thoughtsType) {
        this.tag = tag;

        initParameters(startTime, thoughtsType);
    }

    private void initParameters (Integer startTime, String thoughtsType) {
        this.startTime = startTime;
        this.thoughtsType = thoughtsType;
        this.count = 10;
    }

    public void resumeWithCompletionHandler (ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call;
        HashMap<String, Object> parameters = new HashMap<>();
        if (peopleDrivingType.equals(Constants.PeopleDrivingType.PEOPLE_DRIVING_TYPE_POST)) {
            parameters.put("thought", thoughtsType);
            parameters.put("count", count);
            parameters.put("start_index", startTime);
            call = service.peopleDrivingTypePost(post.identifier);
        } else {
            parameters.put("thought", thoughtsType);
            parameters.put("tagchain", tag.name);
            parameters.put("count", 100);
            call = service.peopleDrivingTypeToday(parameters);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    final JsonObject jsonObject = response.body().getAsJsonObject();

                    serverResponse = objectsFromResponse(jsonObject);

                    if (peopleDrivingType.equals(Constants.PeopleDrivingType.PEOPLE_DRIVING_TYPE_POST)) {
                        drivesCount = jsonObject.get("post_drives_count").getAsInt();
                    } else {
                        drivesCount = jsonObject.get("drives_count").getAsInt();
                    }

                    sustainableCounter = jsonObject.get("thoughts_sustainable_count").getAsInt();
                    greedyCounter = jsonObject.get("thoughts_greedy_count").getAsInt();
                    leaderCounter = jsonObject.get("thoughts_leader_count").getAsInt();
                    goodJobCounter = jsonObject.get("thoughts_good_job_count").getAsInt();
                    harmfulCounter = jsonObject.get("thoughts_harmful_count").getAsInt();
                    wastefulCounter = jsonObject.get("thoughts_wasteful_count").getAsInt();

                    completionHandler.completionHandler(getPeopleDrivingRequest());
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
        service = retrofit.create(PeopleDrivingApi.class);
    }


    /** Private. */

    private List<Tag> objectsFromResponse(JsonObject response) {
        List<Tag> tags = new ArrayList<>();
        List<Tag> result = new ArrayList<>();

        JsonArray tagsInfo = response.getAsJsonArray("list");
        JsonArray tagsArray = response.getAsJsonArray("tags");

        for (JsonElement jsonElement: tagsArray) {
            Tag tag = new Tag(jsonElement.getAsJsonObject());
            tags.add(tag);
        }

        for (JsonElement tagIdElement: tagsInfo) {
            Tag tag = Tag.tagById(tagIdElement.getAsInt(), tags);

            if (tag != null) {
                result.add(tag);
            }
        }

        return result;
    }

    private PeopleDrivingRequest getPeopleDrivingRequest() {
        return this;
    }


    /** PeopleDrivingApi interface. */

    interface PeopleDrivingApi {

        @GET("/api/v2/tags/posts/{postId}/users_click_drive")
        Call<JsonElement> peopleDrivingTypePost(@Path("postId") String identifier);

        @GET("/api/v2/tags/tagchain/users_drives")
        Call<JsonElement> peopleDrivingTypeToday(@QueryMap Map<String, Object> parameters);
    }
}
