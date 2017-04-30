package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by yuriy on 3/24/17.
 */

public class ForgotPasswordRequest extends ServerRequest {

    private String email;
    private ForgotPasswordApi service;

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);

        Call<JsonElement> call = service.forgotPassword(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                if (!isSucceedResponse(jsonObject)) {
                    isFailure = true;
                    error = errorFromResponse(jsonObject);
                }

                completionHandler.completionHandler(getForgotPasswordRequest());
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
        service = retrofit.create(ForgotPasswordApi.class);
    }

    private ForgotPasswordRequest getForgotPasswordRequest() {
        return this;
    }

    interface ForgotPasswordApi {
        @POST("/api/auth/forgot_password")
        Call<JsonElement> forgotPassword(@Body HashMap<String, String> credentials);
    }
}



