package drivetag.drivetag.com.driveceos.data_layer.requests;

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
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by YuriySokurko on 5/16/17.
 */

public class TagValidationRequest extends ServerRequest {

    public String tagName;
    private TagValidationApi service;


    /**
     * Interface.
     */

    public TagValidationRequest(String tagName) {
        this.tagName = tagName;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        String tagChain = String.valueOf("> " + tagName);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("tagchain_string", tagChain);

        Call<JsonElement> call = service.tagValidation(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                      serverResponse = objectsFromResponse(response.body().getAsJsonObject());

                    handler.completionHandler(getTagValidationRequest());
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
        service = retrofit.create(TagValidationApi.class);
    }


    /**
     * Private.
     */

    private TagValidationRequest getTagValidationRequest() {
        return this;
    }

    private List<Tag> objectsFromResponse (JsonObject response) {
        List<Tag> objects = new ArrayList<>();

            JsonObject jsonObject = response.getAsJsonObject();
            Tag tag = new Tag(jsonObject);

            if(tag != null && tag.identifier != null) {
                objects.add(tag);
            }
        return objects;
    }


    /**
     * TagValidationApi interface.
     */

    interface TagValidationApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("tags/tagchain/exists")
        Call<JsonElement> tagValidation(@QueryMap Map<String, Object> parameters);
    }
}