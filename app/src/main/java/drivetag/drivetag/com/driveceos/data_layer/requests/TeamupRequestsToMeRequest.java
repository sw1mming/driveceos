package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by yuriy on 4/3/17.
 */

public class TeamupRequestsToMeRequest extends ServerRequest {

    private Boolean state;

    private TeamupRequestsToMeApi service;

    /**
     * Interface.
     */

    public TeamupRequestsToMeRequest(Boolean state) {
        this.state = state;
    }


    public void resumeWithCompletionHandler(final ServerRequest.ServerCompletionHandler handler) {

        Call<JsonElement> call = service.teamupRequestsToMe(state);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                handleSuccessResponse(response);
                handler.completionHandler(getTeamupRequestsToMeRequest());
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
        service = retrofit.create(TeamupRequestsToMeApi.class);
    }

    /**
     * Private.
     */

    private TeamupRequestsToMeRequest getTeamupRequestsToMeRequest() {
        return this;
    }


    /**
     * PublicPostToMeApi interface.
     */

    interface TeamupRequestsToMeApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("/api/v2/users/current_user/settings/allow_teamup_requests_to_me")
        Call<JsonElement> teamupRequestsToMe(@Query("enabled") Boolean state);
    }
}