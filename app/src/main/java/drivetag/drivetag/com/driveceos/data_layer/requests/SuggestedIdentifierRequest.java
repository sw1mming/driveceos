package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by yuriy on 3/28/17.
 */

public class SuggestedIdentifierRequest extends ServerRequest<List<String>> {
    private String email;
    private String firstName;
    private String lastName;
    private SuggestedIdentifierApi service;

    /**
     * Interface.
     */

    public SuggestedIdentifierRequest(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("first_name", firstName);
        parameters.put("last_name", lastName);

        Call<JsonElement> call = service.suggestedIdentifier(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement.isJsonObject()) {
                    serverResponse = parseResponse(jsonElement.getAsJsonObject());
                }

                completionHandler.completionHandler(getSuggestedIdentifierRequest());
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
        service = retrofit.create(SuggestedIdentifierApi.class);
    }


    /** Private. */

    private SuggestedIdentifierRequest getSuggestedIdentifierRequest() {
        return this;
    }

    private List<String> parseResponse(JsonObject response) {
        JsonArray array = response.getAsJsonArray("uniquifiers");

        List<String> result = new ArrayList<>();

        for (JsonElement element: array) {
            String suggestion = element.getAsString();

            result.add(suggestion);
        }

        return result;
    }


    /** SuggestedIdentifierApi interface. */

    interface SuggestedIdentifierApi {
        @POST("/api/v2/users/suggest_uniquifiers")
        Call<JsonElement> suggestedIdentifier(@Body HashMap<String, String> credentials);
    }
}