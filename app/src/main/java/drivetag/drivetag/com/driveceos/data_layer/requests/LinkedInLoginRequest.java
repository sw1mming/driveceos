package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/18/17.
 */

public class LinkedInLoginRequest extends ServerRequest {

    public String linkedInID;
    public String linkedInAccesToken;
    public String accessToken;
    private LinkedInLoginApi service;


    /**
     * Interface.
     */

    public LinkedInLoginRequest(String linkedInID, String linkedInAccesToken) {
        this.linkedInID = linkedInID;
        this.linkedInAccesToken = linkedInAccesToken;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("uid", linkedInID);
        parameters.put("provider", "linkedin");
        parameters.put("access_token", linkedInAccesToken);

        Call<JsonElement> call = service.linkedInLogin(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    serverResponse = objectsFromResponse(response.body().getAsJsonObject());

                    handler.completionHandler(getLinkedInLoginRequest());
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
        service = retrofit.create(LinkedInLoginApi.class);
    }


    /**
     * Private.
     */

    private LinkedInLoginRequest getLinkedInLoginRequest() {
        return this;
    }

    private List<User> objectsFromResponse (JsonObject response) {
        List<User> objects = new ArrayList<>();

            JsonObject jsonObject = response.getAsJsonObject();
            User user = new User(jsonObject);

            user.linkedInID = linkedInID;

            objects.add(user);

        return objects;
    }


    /**
     * LinkedInLoginApi interface.
     */

    interface LinkedInLoginApi {

//        @Headers({
//                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
//        })

        @GET("api/auth_by_oauth")
        Call<JsonElement> linkedInLogin(@Body HashMap<String, String> parameters);
    }
}