package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;


/**
 * Created by yuriy on 3/29/17.
 */

public class LoadCurrentUserRequest extends ServerRequest {

    private Number updatedPostsCount;
    private Number myPostsCount;
    private Number myTeamPostsCount;
    private Number publicPostsCount;
    private LoadCurrentUserApi service;

    public LoadCurrentUserRequest() {
    }

    public void resumeWithCompletionHandler(final ServerRequest.ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.loadCurrentUser();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null && jsonElement.isJsonObject()) {
                    serverResponse = objectsFromResponse(jsonElement.getAsJsonObject());
                }

                if (JsonObjectHelper.hasValueFromKey("updated_posts_count", jsonObject)) {
                    updatedPostsCount = jsonObject.get("updated_posts_count").getAsNumber();
                }

                if (JsonObjectHelper.hasValueFromKey("unread_myposts_count", jsonObject)) {
                    myPostsCount = jsonObject.get("unread_myposts_count").getAsNumber();
                }

                if (JsonObjectHelper.hasValueFromKey("unread_teamposts_count", jsonObject)) {
                    myTeamPostsCount = jsonObject.get("unread_teamposts_count").getAsNumber();
                }

                if (JsonObjectHelper.hasValueFromKey("unread_publicposts_count", jsonObject)) {
                    publicPostsCount = jsonObject.get("unread_publicposts_count").getAsNumber();
                }

                completionHandler.completionHandler(getLoadCurrentUserRequest());
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
        service = retrofit.create(LoadCurrentUserApi.class);
    }

    private LoadCurrentUserRequest getLoadCurrentUserRequest() {
        return this;
    }


    private User objectsFromResponse (JsonObject response) {
        return new User(response);
    }

    interface LoadCurrentUserApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("api/v2/users/current_user")
        Call<JsonElement> loadCurrentUser();
    }
}
