package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by yuriy on 4/26/17.
 */

public class RemoveRecentTagsRequest extends ServerRequest {

    private RemoveRecentTagsApi service;


    /**
     * Interface.
     */

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.removeRecentTags("all");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    completionHandler.completionHandler(getRemoveRecentTagsRequest());
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
        service = retrofit.create(RemoveRecentTagsApi.class);
    }


    /**
     * Private.
     */

    private RemoveRecentTagsRequest getRemoveRecentTagsRequest() {
        return this;
    }


    /** RemoveRecentTagsApi class. */

    interface RemoveRecentTagsApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @DELETE("/api/v2/tags/tagchain/query_history")
        Call<JsonElement> removeRecentTags(@Query("when") String all);

    }
}
