package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Created by yuriy on 4/25/17.
 */

public class SearchTagsRequest extends ServerRequest {

    public String keyword;

    private SearchTagsRequestApi service;

    /**
     * Interface.
     */


    public SearchTagsRequest (String keyword) {
        this.keyword = keyword;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("tagchain_string", keyword);

        Call<JsonElement> call = service.searchTags(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    final JsonObject jsonObject = response.body().getAsJsonObject();

                    serverResponse = objectsFromResponse(jsonObject);

                    completionHandler.completionHandler(getSearchTagsRequest());
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
        service = retrofit.create(SearchTagsRequestApi.class);
    }


    /** Private. */

    private SearchTagsRequest getSearchTagsRequest() {
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

        for (JsonElement tagArray : listObjects) {
            JsonArray jsonArray = tagArray.getAsJsonArray();
            if (jsonArray.size() > 0) {

                Number tagId = jsonArray.get(0).getAsNumber();

                Tag tag = Tag.tagById(tagId, tags);

                if (tag != null) {
                    tag.configureSubTagsFromTags(tags);
                    result.add(tag);
                }
            }
        }
        return result;
    }


    /** SearchTagsRequestApi interface. */

    interface SearchTagsRequestApi {

        @POST("/api/v2/tags/tagchain/autocomplete-noshortcut")
        Call<JsonElement> searchTags(HashMap<String, String> parameters);
    }
}
