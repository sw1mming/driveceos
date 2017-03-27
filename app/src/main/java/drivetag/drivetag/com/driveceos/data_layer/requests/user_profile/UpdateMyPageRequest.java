package drivetag.drivetag.com.driveceos.data_layer.requests.user_profile;

import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.List;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by yuriy on 3/27/17.
 */

public class UpdateMyPageRequest extends ServerRequest {

     private List<String> imageUrlStringsArray;
     private UpdateMyPageApi service;


    /** Interface. */

    public UpdateMyPageRequest(List<String> imageUrlsArray) {
        this.imageUrlStringsArray = imageUrlsArray;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("my_page", imageUrlStringsArray);

        Call<JsonElement> call = service.updateMyPage(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                handler.completionHandler(getUpdateMyPageRequest());
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
        service = retrofit.create(UpdateMyPageApi.class);
    }

    /** Private. */

    private UpdateMyPageRequest getUpdateMyPageRequest() {
        return this;
    }


    /** UploadMyPageApi interface. */

    interface UpdateMyPageApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/users/current_user/my_page")
        Call<JsonElement> updateMyPage(@Body HashMap<String, List<String>> credentials);
    }
}