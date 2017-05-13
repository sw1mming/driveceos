package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/13/17.
 */

public class AllowedToCallOutRequest extends ServerRequest {

    public Tag tag;
    public Boolean allowed;
    public String notAllowText;
    private AllowedToCallOutApi service;

    public AllowedToCallOutRequest(Tag tag) {
        this.tag = tag;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        String currentId;
        if (tag.currentType().equals(tag.COMPANY_TYPE)) {
            currentId = tag.leaderUserId.toString();
        } else {
            currentId = tag.user.driveID.toString();
        }

        Call<JsonElement> call = service.allowedToCallOut(currentId);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();
                JsonElement jsonElement = handleSuccessResponse(response);

                if (JsonObjectHelper.hasValueFromKey("allowed", jsonObject)) {
                    notAllowText = jsonObject.get("allowed").getAsString();
                }

                if (notAllowText.equals("true")) {
                    allowed = true;
                } else {
                    allowed = false;
                }

                handler.completionHandler(getAllowedToCallOutRequest());
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
        service = retrofit.create(AllowedToCallOutApi.class);
    }


    /** Private. */

    private AllowedToCallOutRequest getAllowedToCallOutRequest() {
        return this;
    }


    /** AllowedToCallOutApi interface. */

    interface AllowedToCallOutApi {
        @GET("api/v2/users/{currentId}/allowed_to_call_out")
        Call<JsonElement> allowedToCallOut(@Path("currentId") String currentId);
    }
}