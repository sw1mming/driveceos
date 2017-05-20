package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Notice;
import drivetag.drivetag.com.driveceos.data_layer.models.Subscription;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by artem on 4/27/17.
 */

public class LoadSubscriptionRequest extends ServerRequest<List<Subscription>> {
    private Tag tag;

    private Subscription subscription;

    private LoadSubscriptionApi service;

    public LoadSubscriptionRequest(Tag tag) {
        this.tag = tag;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.loadSubscription(tag.identifier.toString());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = objectsFromResponse(response.body().getAsJsonObject());

                    completionHandler.completionHandler(getLoadSubscriptionRequest());
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
        service = retrofit.create(LoadSubscriptionApi.class);
    }

    private List<Subscription> objectsFromResponse(JsonObject response) {
        List<Subscription> result = new ArrayList<>();

        Subscription subscription = new Subscription(response);
        result.add(subscription);

        return result;
    }

    private LoadSubscriptionRequest getLoadSubscriptionRequest() {
        return this;
    }


    /** LoadSubscriptionApi interface. */

    interface LoadSubscriptionApi {
        @GET("/api/v2/tags/{tagIdentifier}/subscription")
        Call<JsonElement> loadSubscription(@Query("tagIdentifier") String identifier);
    }
}