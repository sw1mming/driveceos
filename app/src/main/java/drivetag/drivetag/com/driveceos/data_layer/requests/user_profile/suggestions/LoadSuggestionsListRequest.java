package drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.suggestions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by sergeymelnik on 2017-03-28.
 */

public class LoadSuggestionsListRequest extends ServerRequest<List<String>> {

    private LoadSuggestionListApi service;

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.getSuggestions();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    serverResponse = objectsFromResponse(jsonObject);
                }

                handler.completionHandler(getLoadSuggestionsListRequest());
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
        service = retrofit.create(LoadSuggestionListApi .class);
    }

    private LoadSuggestionsListRequest getLoadSuggestionsListRequest() {
        return this;
    }

    private List<String> objectsFromResponse(JsonObject responseJsonObject) {
        List<String> result = new ArrayList<>();

        for (JsonElement suggestionElement : responseJsonObject.getAsJsonArray("suggests")) {
            String text = suggestionElement.getAsJsonObject().get("text").getAsString();
            result.add(text);
        }

        return result;
    }

    interface LoadSuggestionListApi {
        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("/api/wdu_suggest")
        Call<JsonElement> getSuggestions();
    }
}
