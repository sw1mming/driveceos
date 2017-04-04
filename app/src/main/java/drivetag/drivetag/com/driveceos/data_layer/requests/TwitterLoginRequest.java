package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yuriy on 3/30/17.
 */

public class TwitterLoginRequest extends ServerRequest {

    private String twitterAccessToken;
    private String oauthTokenSecret;
    private String accessToken;
    private String twitterID;
    private TwitterLoginRequestApi service;

    public TwitterLoginRequest (String twitterAccessToken,
                                String oauthTokenSecret,
                                String twitterID) {

        this.twitterAccessToken = twitterAccessToken;
        this.oauthTokenSecret = oauthTokenSecret;
        this.twitterID = twitterID;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("uid", twitterID);
        parameters.put("provider", "twitter");
        parameters.put("access_token", twitterAccessToken);
        parameters.put("twitter_secret", oauthTokenSecret);

        Call<JsonElement> call = service.twitterLogin(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();
                JsonElement jsonElement = handleSuccessResponse(response);

                if (JsonObjectHelper.hasValueFromKey("dt_access_token", jsonObject)) {
                    accessToken = jsonObject.get("dt_access_token").getAsString();
                }

                if (jsonElement != null && jsonElement.isJsonObject()) {
                    serverResponse = objectsFromResponse(jsonElement.getAsJsonObject());
                }

                completionHandler.completionHandler(getTwitterLoginRequest());
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
        service = retrofit.create(TwitterLoginRequestApi.class);
    }


    /** Private. */

    private TwitterLoginRequest getTwitterLoginRequest() {
        return this;
    }

    private User objectsFromResponse (JsonObject response) {
        User user = new User(response);
        user.twitterID = twitterID;

        return user;
    }


    /** TwitterLoginRequestApi interface. */

    interface TwitterLoginRequestApi {

        @POST("api/auth_by_oauth")
        Call<JsonElement> twitterLogin(@Body HashMap<String, String> parameters);
    }
}
