package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static drivetag.drivetag.com.driveceos.data_layer.models.User.USER_TYPE;

/**
 * Created by yuriy on 4/25/17.
 */

public class LoadTagTeamRequest extends ServerRequest <List<Tag>> {

    public  Integer count;

    public  String tagTeamFilter;

    public  Integer nextIndex;

    public  Integer startIndex;

    public  Integer teammatesCount;

    public  Integer teammatesProCount;

    public  Integer teammatesSocialCount;

    public  String thoughtType;

    public  Tag callOutTag;

    private Tag tag;

    private String tagTeamOrder;

    private LoadTagTeamApi service;


    /**
     * Interface.
     */

    public LoadTagTeamRequest(Tag tag, String tagTeamOrder, Integer startIndex) {
        this.tag = tag;
        this.startIndex = startIndex;
        this.tagTeamOrder = tagTeamOrder;
        this.count = 6;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();

        String driveTagString = "";
        if (tag.name != null) {
            driveTagString = ">" + tag.name;

            if (tag.parentTag.name != null) {
                driveTagString = ">" + tag.parentTag.name + "+>" + tag.name;
            }
        }

        if (driveTagString.length() > 0) {
            parameters.put("tagchain", driveTagString);
        }

        if (tagTeamFilter != null) {
            parameters.put("filter", tagTeamFilter);
        }

        if (count != null) {
            parameters.put("count", count.toString());
        }

        if (tagTeamOrder != null) {
            parameters.put("orderby", tagTeamOrder);
        }

        if (startIndex != null) {
            parameters.put("start_index", startIndex.toString());
        }

        if (thoughtType != null) {
            parameters.put("thought", thoughtType);
        }

        Call<JsonElement> call = service.loadTagTeam(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    final JsonObject jsonObject = response.body().getAsJsonObject();

                    if (JsonObjectHelper.hasValueFromKey("teammates_count", jsonObject)) {
                        teammatesCount = jsonObject.get("teammates_count").getAsInt();
                    }

                    if (JsonObjectHelper.hasValueFromKey("teammates_pro_count", jsonObject)) {
                        teammatesProCount = jsonObject.get("teammates_pro_count").getAsInt();
                    }

                    if (JsonObjectHelper.hasValueFromKey("teammates_social_count", jsonObject)) {
                        teammatesSocialCount = jsonObject.get("teammates_social_count").getAsInt();
                    }

                    if (JsonObjectHelper.hasValueFromKey("next_index", jsonObject)) {
                        nextIndex = jsonObject.get("next_index").getAsInt();
                    }

                    serverResponse = objectsFromResponse(jsonObject.getAsJsonObject());

                    JsonArray callOutTagIds = new JsonArray();
                    if (JsonObjectHelper.hasValueFromKey("tag_call_out_btn", jsonObject)) {
                        callOutTagIds = jsonObject.get("tag_call_out_btn").getAsJsonArray();
                    }

                    Number callOutTagId;
                    if (callOutTagIds.size() > 0) {
                        callOutTagId = callOutTagIds.get(0).getAsNumber();

                        callOutTag = Tag.tagById(callOutTagId, serverResponse);
                    }

                    completionHandler.completionHandler(getLoadTagTeamRequest());
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
            service = retrofit.create(LoadTagTeamApi.class);
        }


    /**
     * Private.
     */

    private LoadTagTeamRequest getLoadTagTeamRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse (JsonObject response) {
        List<Tag> result = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        JsonArray tagsArray = response.getAsJsonArray("tags");
        JsonArray listArray = response.getAsJsonArray("list");

        for (JsonElement element: tagsArray) {

            JsonObject object = element.getAsJsonObject();
            if (JsonObjectHelper.hasValueFromKey("type", object).equals(USER_TYPE)) {
                JsonObject jsonObject = element.getAsJsonObject();
                Tag tag = new Tag(jsonObject);
                tags.add(tag);
            }
        }

        for (JsonElement tagIdElement : listArray) {
            Number tagId = tagIdElement.getAsNumber();

            Tag tag = Tag.tagById(tagId,tags);

            if (tag != null) {
                result.add(tag);
            }
        }

    return result;
    }


    /** LoadTagTeamApi class. */

    interface LoadTagTeamApi {
        
        @GET("/api/v2/tags/tagchain/tagteam")
        Call<JsonElement> loadTagTeam(@QueryMap Map<String, String> parameters);
    }
}