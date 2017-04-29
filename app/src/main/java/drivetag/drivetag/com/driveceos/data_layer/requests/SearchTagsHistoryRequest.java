package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by yuriy on 4/26/17.
 */

public class SearchTagsHistoryRequest extends SearchTagsRequest {

    private SearchTagsHistoryApi service;


    /**
     * Interface.
     */

    public SearchTagsHistoryRequest() {
        super(null);
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Integer count = 10;

        Call<JsonElement> call = service.searchTagsHistory(count.toString());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    final JsonObject jsonObject = response.body().getAsJsonObject();

                    serverResponse = objectsFromResponse(jsonObject.getAsJsonObject());

                    completionHandler.completionHandler(getSearchTagsHistoryRequest());
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
        service = retrofit.create(SearchTagsHistoryApi.class);
    }


    /**
     * Private.
     */

    private SearchTagsHistoryRequest getSearchTagsHistoryRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse (JsonObject response) {
        List<Tag> result = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        JsonArray resposeObjects = response.getAsJsonArray("tags");
        JsonArray listObjects = response.getAsJsonArray("list");

        for (JsonElement element : resposeObjects) {
            JsonObject jsonObject = element.getAsJsonObject();
            Tag tag = new Tag(jsonObject);
            tags.add(tag);
        }

        for (JsonElement tagIdElement : listObjects) {
            Integer tagId = tagIdElement.getAsInt();

            Tag tag = Tag.tagById(tagId,tags);

            if (tag != null) {
                tag.configureSubTagsFromTags(tags);
                result.add(tag);
            }
        }

        return result;
   }


    /** SearchTagsHistoryApi class. */

    interface SearchTagsHistoryApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("/api/v2/tags/tagchain/query_history")
        Call<JsonElement> searchTagsHistory(@Query("count") String count);
    }
}