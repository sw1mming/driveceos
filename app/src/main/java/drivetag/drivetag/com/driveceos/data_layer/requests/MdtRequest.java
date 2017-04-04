package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


/**
 * Created by yuriy on 3/30/17.
 */

public class MdtRequest extends  ServerRequest {

    private String thoughtType;
    private String dataType;
    private Number startIndex;
    private Number nextIndex;
    private Integer count;
    private MdtApi service;


    /** Interface. */

    public Number getNextIndex() {
        return nextIndex;
    }

    public String getThoughtType() {
        return thoughtType;
    }

    public Number getStartIndex() {
        return startIndex;
    }

    public Integer getCount() {
        return count;
    }

    public MdtRequest(String thoughtType, String dataType, Number startIndex) {

        this.thoughtType = thoughtType;
        this.dataType = dataType;
        this.startIndex = startIndex;
        count = 10;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("data", dataType);
        parameters.put("thought", thoughtType);

        if (startIndex != null) {
            parameters.put("start_index", startIndex);
        }

        parameters.put("count", count);

        Call<JsonElement> call = service.mdt(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null && jsonElement.isJsonObject()) {
                    serverResponse = objectsFromResponse(jsonElement.getAsJsonObject());
                }

                if (JsonObjectHelper.hasValueFromKey("next_index", jsonObject)) {
                    nextIndex = jsonObject.get("next_index").getAsNumber();
                }

                completionHandler.completionHandler(getMDTRequest());
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
        service = retrofit.create(MdtApi.class);
    }


    /**
     * Private.
     */

    private MdtRequest getMDTRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse(JsonObject response) {

        List<Tag> result = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        JsonArray resposeObjects = null;
        if (JsonObjectHelper.hasValueFromKey("tags", response)) {
            resposeObjects = response.get("tags").getAsJsonArray();
        }

        JsonArray listObjects = null;
        if (JsonObjectHelper.hasValueFromKey("list", response)) {
            listObjects = response.get("list").getAsJsonArray();
        }

        if (resposeObjects != null) {
            for (JsonElement element : resposeObjects) {
                JsonObject jsonObject = element.getAsJsonObject();
                Tag tag = new Tag(jsonObject);
                tags.add(tag);
            }
        }

        if (listObjects != null) {
            for (JsonElement tagIdElement : listObjects) {
                Number tagId = tagIdElement.getAsNumber();

                Tag tag = Tag.tagById(tagId,tags);

                if (tag != null) {
                    tag.configureSubTagsFromTags(tags);
                    result.add(tag);
                }
            }
        }
        return result;
    }

    /** MdtApi interface. */

    interface MdtApi {
        @GET("/api/v2/tags/mdt")
        Call<JsonElement> mdt(@QueryMap Map<String, Object> parameters);
    }
}

