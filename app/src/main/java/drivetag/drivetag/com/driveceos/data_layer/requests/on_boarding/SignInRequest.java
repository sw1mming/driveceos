package drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yuriy on 3/22/17.
 */


public class SignInRequest extends ServerRequest {

    public String accessToken;

    private String driveID;

    private String email;

    private String password;

    private SignInApi service;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);

        Call<JsonElement> call = service.signIn(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                if (!isSucceedResponse(jsonObject)) {
                    isFailure = true;

                    if (!JsonObjectHelper.hasValueFromKey("fields_errors", jsonObject)) {
                        JsonArray errors = jsonObject.getAsJsonArray("fields_errors");

                        if (errors.size() > 0) {
                            JsonElement errorInfos = errors.get(0);

                            if (errorInfos.isJsonArray()) {
                                JsonArray errorInfo = errorInfos.getAsJsonArray();

                                if (errorInfo.size() > 1) {
                                    error = errorInfo.get(1).getAsString();
                                }
                            }
                        }
                    }

                    completionHandler.completionHandlerWithError(error);

                } else {
                    if (JsonObjectHelper.hasValueFromKey("dt_access_token", jsonObject)) {
                        accessToken = jsonObject.get("dt_access_token").getAsString();
                    }

                    if (JsonObjectHelper.hasValueFromKey("user_uid", jsonObject)) {
                        driveID = jsonObject.get("user_uid").getAsString();
                    }

                    completionHandler.completionHandler(getSignInRequest());
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
        service = retrofit.create(SignInApi.class);
    }

    private SignInRequest getSignInRequest() {
        return this;
    }

    interface SignInApi {
        @POST("/api/auth/dt_signon")
        Call<JsonElement> signIn(@Body HashMap<String, String> credentials);
    }
}