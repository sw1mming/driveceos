package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by yuriy on 4/3/17.
 */

public class PublicPostToMeRequest extends ServerRequest {

    private Boolean state;
    private PublicPostToMeApi service;


    /**
     * Interface.
     */

    public PublicPostToMeRequest (Boolean state, String token) {
        this.token = token;
        this.state = state;
    }

    public void resumeWithCompletionHandler(final ServerRequest.ServerCompletionHandler handler) {

        HashMap<String, Boolean> parameters = new HashMap<>();
        parameters.put("posting_on_my_profile_allowed", state);


        Call<JsonElement> call = service.publicPostToMe(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                handleSuccessResponse(response);
                handler.completionHandler(getPublicPostToMeRequest());
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
        service = retrofit.create(PublicPostToMeApi.class);
    }

    /** Private. */

    private PublicPostToMeRequest getPublicPostToMeRequest() {
        return this;
    }


    /** PublicPostToMeApi interface. */

    interface PublicPostToMeApi {

//        @Headers({
//                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
//        })

        @PUT("/api/v2/users/current_user/settings")
        Call<JsonElement> publicPostToMe(@Body HashMap<String, Boolean> parameters);
    }
}