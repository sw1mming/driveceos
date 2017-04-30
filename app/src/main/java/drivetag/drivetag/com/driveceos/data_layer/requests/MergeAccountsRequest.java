package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by yuriy on 3/30/17.
 */

public class MergeAccountsRequest extends ServerRequest<User> {

    public String dtToken;
    public String userId;
    public String accessToken;
    public String provider;
    public String twitterSecret;
    private MergeAccountsRequestApi service;

    /**
     * Interface.
     */

    public MergeAccountsRequest() {
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("logged_user_dt_token", dtToken);
        parameters.put("uid", userId);
        parameters.put("access_token", accessToken);
        parameters.put("provider", provider);
        parameters.put("twitter_secret", twitterSecret);

        Call<JsonElement> call = service.mergeAccounts(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null && jsonElement.isJsonObject()) {
                    serverResponse = objectsFromResponse(jsonElement.getAsJsonObject());
                }

                completionHandler.completionHandler(getMergeAccountsRequest());
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
        service = retrofit.create(MergeAccountsRequestApi.class);
    }


    /** Private. */

    private MergeAccountsRequest getMergeAccountsRequest() {
        return this;
    }

    private User objectsFromResponse (JsonObject response) {
        return new User(response);
    }


    /** MergeAccountsRequestApi interface. */

    interface MergeAccountsRequestApi {
        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("api/merge_by_oauth")
        Call<JsonElement> mergeAccounts(@Body HashMap<String, String> parameters);
    }
}
