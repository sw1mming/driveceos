package drivetag.drivetag.com.driveceos.data_layer.requests.user_profile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by yuriy on 3/27/17.
 */

public class UpdateCoverPhotoRequest extends ServerRequest {

    private String urlString;
    private UpdateCoverPhotoApi service;


    /** Interface. */

    public UpdateCoverPhotoRequest(String urlString) {
        this.urlString = urlString;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        if (urlString != null) {

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("cover_url", urlString);

            Call<JsonElement> call = service.updateCoverPhoto(parameters);

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    handleSuccessResponse(response);

                    handler.completionHandler(getUpdateCoverPhotoRequest());
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    handler.completionHandlerWithError(t.toString());
                }
            });

        } else {
            Call<JsonElement> call = service.deleteCoverPhoto();

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    handleSuccessResponse(response);

                    handler.completionHandler(getUpdateCoverPhotoRequest());
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    handler.completionHandlerWithError(t.toString());
                }
            });
        }
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(UpdateCoverPhotoApi.class);
    }


    /** Private. */

    private UpdateCoverPhotoRequest getUpdateCoverPhotoRequest() {
        return this;
    }

//    private void handleSuccessResponse(Response<JsonElement> response, final ServerCompletionHandler handler) {
//        if (response.isSuccessful()) {
//            final JsonObject jsonObject = response.body().getAsJsonObject();
//
//            if (!isSucceedResponse(jsonObject)) {
//                error = errorFromResponse(jsonObject);
//            }
//        } else {
//            isFailure = true;
//
//            try {
//                error = response.errorBody().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        handler.completionHandler(getUpdateCoverPhotoRequest());
//    }

    /** UpdateCoverPhotoApi interface. */

    interface UpdateCoverPhotoApi {
        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/users/current_user/cover")
        Call<JsonElement> updateCoverPhoto(@Body HashMap<String, String> credentials);

        @DELETE("/api/v2/users/current_user/cover")
        Call<JsonElement> deleteCoverPhoto();
    }
}