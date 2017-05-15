package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class ClaimAccountRequest extends ServerRequest {

    public User user;
    private ClaimAccountApi service;


    /** Interface */

    public ClaimAccountRequest(User user) {
       this.user = user;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("precreated_account",user.driveTag);
        parameters.put("email",user.email);
        parameters.put("password",user.password);
        parameters.put("verification_code",user.verification);

        Call<JsonElement> call = service.claimAccount(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();
                if(!isSucceedResponse(jsonObject) ||
                        (JsonObjectHelper.hasValueFromKey("success", jsonObject) && jsonObject.get("success").getAsBoolean() == false)) {
                    isFailure = true;

                    JsonArray errors = jsonObject.getAsJsonArray("fields_errors");

                    if (errors != null && errors.size() > 0) {
                        JsonElement errorInfos = errors.get(0);

                        if (errorInfos != null && errorInfos.isJsonArray()) {
                            JsonArray errorInfo = errorInfos.getAsJsonArray();

                            if (errorInfo != null && errorInfo.size() > 1) {
                                error = errorInfo.get(1).getAsString();
                            }
                        }
                     }
                } else {
                    serverResponse = parseResponse(jsonObject.getAsJsonArray());
                }

                handler.completionHandler(getClaimAccountRequest());
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
        service = retrofit.create(ClaimAccountApi.class);
    }


    /** Private. */

    private ClaimAccountRequest getClaimAccountRequest() {
        return this;
    }

    private List<Object> parseResponse(JsonArray response) {
        List<Object> result = new ArrayList<>();

        return result;
    }


    /** ClaimAccountApi interface. */

    interface ClaimAccountApi {
        @POST("/api/auth/claim_account")
        Call<JsonElement> claimAccount(@Body HashMap<String, String> parameters);
    }
}
