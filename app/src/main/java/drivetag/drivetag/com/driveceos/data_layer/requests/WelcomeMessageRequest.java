package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by artem on 4/27/17.
 */

public class WelcomeMessageRequest extends ServerRequest<List<Object>>{

    private WelcomeMessageApi service;

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.loadWelcomeMessage();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = parseResponse(response.body().getAsJsonArray());

                    completionHandler.completionHandler(getWelcomeMessageRequest());
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
        service = retrofit.create(WelcomeMessageApi.class);
    }

    private WelcomeMessageRequest getWelcomeMessageRequest() {
        return this;
    }

    private List<Object> parseResponse(JsonArray response) {
        List<Object> result = new ArrayList<>();

        if(response != null) {
            result.add(response);
        }
        return result;
    }


    /** WelcomeMessageApi interface. */

    interface WelcomeMessageApi {
        @GET("/api/welcome_message")
        Call<JsonElement> loadWelcomeMessage();
    }
}
