package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
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

public class CheckEmailTypeRequest extends ServerRequest {

    private String email;
    private String emailType;
    private CheckEmailTypeApi service;

    /**
     * Interface.
     */

    public CheckEmailTypeRequest(String email) {
        this.email = email;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);

        Call<JsonElement> call = service.checkEmailType(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                handleSuccessResponse(response);

                if (JsonObjectHelper.hasValueFromKey("email_type", jsonObject)) {
                    emailType = jsonObject.get("email_type").getAsString();
                }

                handler.completionHandler(getCheckEmailTypeRequest());
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
        service = retrofit.create(CheckEmailTypeApi.class);
    }


    /** Private. */

    private CheckEmailTypeRequest getCheckEmailTypeRequest() {
        return this;
    }


    /** CheckEmailTypeApi interface. */

    interface CheckEmailTypeApi {
        @GET("/api/util/email_type")
        Call<JsonElement> checkEmailType(@QueryMap Map<String, String> parameters);
    }
}
