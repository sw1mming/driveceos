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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by YuriySokurko on 5/16/17.
 */

public class PeopleDrivenTodayRequest extends ServerRequest {

    public Integer nextTime;
    public Integer startTime;
    public Integer count;
    private Post post;
    private PeopleDrivenTodayApi service;


    /**
     * Interface.
     */

    public PeopleDrivenTodayRequest(Post post, Integer startTime) {
        this.post = post;
        this.startTime = startTime;
        count = 10;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("count", count);
        parameters.put("start_time", startTime);

        Call<JsonElement> call = service.peopleDrivenToday(post.identifier,parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = objectsFromResponse(response.body().getAsJsonObject());

                    handler.completionHandler(getPeopleDrivenTodayRequest());
                } else {
                    handler.completionHandlerWithError(defaultErrorMessage());
                }
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
        service = retrofit.create(PeopleDrivenTodayApi.class);
    }


    /**
     * Private.
     */

    private PeopleDrivenTodayRequest getPeopleDrivenTodayRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse (JsonObject response) {
        JsonArray tagsInfo = response.getAsJsonArray("list");
        List<Tag> objects = new ArrayList<>();

        for (JsonElement element : tagsInfo) {
            JsonObject jsonObject = response.getAsJsonObject();
            Tag tag = new Tag(jsonObject);
            objects.add(tag);
        }

        return objects;
    }


    /**
     * PeopleDrivenTodayApi interface.
     */

    interface PeopleDrivenTodayApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/tags/posts/{postId}/users_drive")
        Call<JsonElement> peopleDrivenToday(@Path("postId") String postId, @Body HashMap<String, Integer> parameters);
    }
}
