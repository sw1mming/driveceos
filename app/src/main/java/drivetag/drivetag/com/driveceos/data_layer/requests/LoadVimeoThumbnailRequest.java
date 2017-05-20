package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/17/17.
 */

public class LoadVimeoThumbnailRequest extends ServerRequest {

    private String videoId;
    private LoadVimeoThumbnailApi service;


    /**
     * Interface.
     */

    public LoadVimeoThumbnailRequest(String videoId) {
        this.videoId = videoId;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        Call<JsonElement> call = service.loadVimeoThumbnail(videoId);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = imageUrlStringFromResponse(response.body().getAsJsonArray());

                    handler.completionHandler(getLoadVimeoThumbnailRequest());
                } else {
                    handler.completionHandlerWithError(defaultErrorMessage());
                }
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
        service = retrofit.create(LoadVimeoThumbnailApi.class);
    }


    /**
     * Private.
     */

    private LoadVimeoThumbnailRequest getLoadVimeoThumbnailRequest() {
        return this;
    }

    private String imageUrlStringFromResponse(JsonArray response) {
        String urlString = "";

        for(JsonElement element: response) {

            JsonObject jsonObject = element.getAsJsonObject();

            if (JsonObjectHelper.hasValueFromKey("thumbnail_medium", jsonObject)) {
                urlString = jsonObject.get("thumbnail_medium").getAsString();
            }
        }

        return urlString;
    }


    /**
     * LoadUrlMetadataApi interface.
     */

    interface LoadVimeoThumbnailApi {

//        @Headers({
//                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
//        })

        @GET("https://vimeo.com/api/v2/video/{videoId}.json")
        Call<JsonElement> loadVimeoThumbnail(@Path("videoId") String videoId);
    }
}
