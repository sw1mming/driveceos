package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class TeamUpRequest extends ServerRequest {

    public User user;
    private TeamUpApi service;

    public TeamUpRequest(User user) {
        this.user = user;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        Call<JsonElement> call = null;

        if (user.teammateStatus.equals(User.TEAMMATE_STATUS_NONE)) {
            call = service.teamUp(user.driveID);
            user.teammateStatus = User.TEAMMATE_STATUS_REQUEST;
        } else {
            call = service.teamUpDelete(user.driveID);
            user.teammateStatus = User.TEAMMATE_STATUS_NONE;
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                handler.completionHandler(getTeamUpRequest());
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
        service = retrofit.create(TeamUpApi.class);
    }


    /** Private. */

    private TeamUpRequest getTeamUpRequest() {
        return this;
    }


    /** TeamUpApi interface. */

    interface TeamUpApi {
        @DELETE("api/v2/users/{driveId}/as_teammate")
        Call<JsonElement> teamUpDelete(@Path("driveId") Object identifier);

        @POST("api/v2/users/{driveId}/teamup_request")
        Call<JsonElement> teamUp(@Path("driveId") Object identifier);
    }
}
