package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class RecentTagsRequest extends ServerRequest {

    private RecentTagsApi service;


    /**
     * Interface.
     */

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("start_node_index", 0);
        parameters.put("count", 20);

        Call<JsonElement> call = service.recentTags(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = objectsFromResponse(response.body().getAsJsonObject());

                    handler.completionHandler(getRecentTagsRequest());
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
        service = retrofit.create(RecentTagsApi.class);
    }


    /** Private. */

    private RecentTagsRequest getRecentTagsRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse(JsonObject response) {
        JsonArray tagsInfo = response.getAsJsonArray("tags");
        List<Tag> objects = new ArrayList<>();

        for (JsonElement element : tagsInfo) {
            if(tagsInfo.size() > 1) {
                JsonObject jsonObject = element.getAsJsonObject();
                Tag tag = new Tag(jsonObject);

                if(tag != null) {
                objects.add(tag);
                }
            }
        }
        return objects;
    }


    /** RecentTagsApi interface. */

    interface RecentTagsApi {
        @Headers({"Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("/api/v2/users/current_user/visited/recently")
        Call<JsonElement> recentTags(@QueryMap Map<String, Integer> parameters);
    }
}