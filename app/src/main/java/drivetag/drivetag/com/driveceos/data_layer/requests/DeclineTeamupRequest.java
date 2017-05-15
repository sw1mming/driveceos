package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class DeclineTeamupRequest extends ServerRequest {

    private DeclineTeamupApi service;


    /**
     * Interface.
     */

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.declineTeamup();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null && jsonElement.isJsonArray()) {
                    serverResponse = parseResponse(jsonElement.getAsJsonArray());
                }

                handler.completionHandler(getDeclineTeamupRequest());
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
        service = retrofit.create(DeclineTeamupApi.class);
    }


    /** Private. */

    private DeclineTeamupRequest getDeclineTeamupRequest() {
        return this;
    }

    private List<Object> parseResponse(JsonArray response) {
        List<Object> result = new ArrayList<>();

        return result;
    }


    /** DeclineTeamupApi interface. */

    interface DeclineTeamupApi {
        @POST("/api/v2/users/:user_uid/decline_teamup_request")
        Call<JsonElement> declineTeamup();
    }
}