package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.data_layer.models.UrlInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by YuriySokurko on 5/17/17.
 */

public class LoadUrlMetadataRequest extends ServerRequest {

    public UrlInfo urlInfo;
    private String urlString;
    private LoadUrlMetadataApi service;


    /**
     * Interface.
     */

    public LoadUrlMetadataRequest(String urlString) {
        this.urlString = urlString;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.loadUrlMetadata(urlString);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                JsonObject jsonObject = response.body().getAsJsonObject();

                urlInfo = new UrlInfo(jsonObject);
                handler.completionHandler(getLoadUrlMetadataRequest());
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
        service = retrofit.create(LoadUrlMetadataApi.class);
    }


    /**
     * Private.
     */

    private LoadUrlMetadataRequest getLoadUrlMetadataRequest() {
        return this;
    }


    /**
     * LoadUrlMetadataApi interface.
     */

    interface LoadUrlMetadataApi {
        @GET("/api/og/get_meta")
        Call<JsonElement> loadUrlMetadata(@Query("url") String url);
    }
}