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

public class FacebookLoginRequest extends ServerRequest {

    public String facebookAccessToken;
    public String accessToken;
    public String facebookID;
    private FacebookLoginApi service;

    /**
     * Interface.
     */

    public  FacebookLoginRequest (String facebookAccessToken, String facebookID) {
        this.facebookAccessToken = facebookAccessToken;
        this.facebookID = facebookID;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("uid", facebookID);
        parameters.put("provider", "facebook");
        parameters.put("access_token", facebookAccessToken);

        Call<JsonElement> call = service.facebookLogin(parameters);

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

                completionHandler.completionHandler(getFacebookLoginRequest());
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
        service = retrofit.create(FacebookLoginApi.class);
    }


    /** Private. */

    private FacebookLoginRequest getFacebookLoginRequest() {
        return this;
    }

    private User objectsFromResponse (JsonObject response) {
        User user = new User(response);
        user.facebookID = facebookID;

        return user;
    }


    /** EmployeeTitlesRequestApi interface. */

    interface FacebookLoginApi {

        @POST("api/auth_by_oauth")
        Call<JsonElement> facebookLogin(@Body HashMap<String, String> parameters);
    }
}

