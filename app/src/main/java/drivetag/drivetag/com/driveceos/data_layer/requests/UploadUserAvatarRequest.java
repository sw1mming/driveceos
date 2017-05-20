package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/17/17.
 */

public class UploadUserAvatarRequest extends ServerRequest {

    private String urlString;
    private UploadUserAvatarApi service;


    /**
     * Interface.
     */

    public UploadUserAvatarRequest(String urlString) {
        this.urlString = urlString;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("avatar_url", urlString);

        Call<JsonElement> call = service.uploadUserAvatar(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getUploadUserAvatarRequest());
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
        service = retrofit.create(UploadUserAvatarApi.class);
    }


    /**
     * Private.
     */

    private UploadUserAvatarRequest getUploadUserAvatarRequest() {
        return this;
    }


    /**
     * UploadUserAvatarApi interface.
     */

    interface UploadUserAvatarApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/users/current_user/avatar")
        Call<JsonElement> uploadUserAvatar(@Body HashMap<String, String> parameters);
    }
}