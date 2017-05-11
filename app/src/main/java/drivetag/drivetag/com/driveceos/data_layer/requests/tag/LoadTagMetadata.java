package drivetag.drivetag.com.driveceos.data_layer.requests.tag;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by artem on 4/27/17.
 */

public class LoadTagMetadata extends ServerRequest {

    public Integer peopleDrivenCounter;

    private Tag tag;

    private LoadTagMetadataApi service;

    public LoadTagMetadata(Tag tag) {
        this.tag = tag;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.loadTagMetadata(tag.fullTagName());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject stats = jsonObject.get("stats").getAsJsonObject();

                    if (stats != null) {
                        peopleDrivenCounter = stats.get("people_recent_drives").getAsInt();
                    }

                    completionHandler.completionHandler(getLoadTagMetadata());

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
        service = retrofit.create(LoadTagMetadataApi.class);
    }

    private LoadTagMetadata getLoadTagMetadata() {
        return this;
    }


    /** LoadTagMetadataApi interface. */

    interface LoadTagMetadataApi {

        @POST("/api/v2/tags/tagchain")
        Call<JsonElement> loadTagMetadata(@Query("tagchain_string") String fullTagName);
    }
}
