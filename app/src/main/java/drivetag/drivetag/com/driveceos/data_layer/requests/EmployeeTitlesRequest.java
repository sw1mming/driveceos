package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by yuriy on 3/28/17.
 */

public class EmployeeTitlesRequest extends ServerRequest {

    private String email;
    private EmployeeTitlesApi service;

    /**
     * Interface.
     */

    public EmployeeTitlesRequest(String email) {
        this.email = email;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        String[] separated = email.split("@");

        String domain = "";
        if (separated.length > 1) {
            domain = separated[1];
        }

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("uniquifier", domain);

        Call<JsonElement> call = service.employeeTitlesRequest(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement.isJsonArray()) {
                    serverResponse = parseResponse(jsonElement.getAsJsonArray());
                }

                handler.completionHandler(getEmployeeTitlesRequest());
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
        service = retrofit.create(EmployeeTitlesApi.class);
    }


    /** Private. */

    private EmployeeTitlesRequest getEmployeeTitlesRequest() {
        return this;
    }

    private List<String> parseResponse(JsonArray response) {

        List<String> result = new ArrayList<>();

        for (JsonElement element: response) {

            JsonObject jsonObject = element.getAsJsonObject();

            if (JsonObjectHelper.hasValueFromKey("available", jsonObject)) {
                JsonElement availableElement = jsonObject.get("available");

                if (availableElement.getAsBoolean()) {

                    if (JsonObjectHelper.hasValueFromKey("title", jsonObject)) {
                        JsonElement titleElement = jsonObject.get("title");

                        result.add(titleElement.getAsString());
                    }
                }
            }
        }

        return result;
    }


    /** EmployeeTitlesRequestApi interface. */

    interface EmployeeTitlesApi {
        @GET("/api/auth/employee_titles_availability")
        Call<JsonElement> employeeTitlesRequest(@QueryMap HashMap<String, String> parameters);
    }
}
