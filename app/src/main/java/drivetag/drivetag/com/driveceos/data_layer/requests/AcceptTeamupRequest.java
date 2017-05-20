package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;

import drivetag.drivetag.com.driveceos.data_layer.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/15/17.
 */

public class AcceptTeamupRequest extends ServerRequest {

    public User user;
    private AcceptTeamupApi service;


    /**
     * Interface.
     */

    public AcceptTeamupRequest(User user) {
        this.user = user;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        Call<JsonElement> call = service.acceptTeamup(user.driveID);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getAcceptTeamupRequest());
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
        service = retrofit.create(AcceptTeamupApi.class);
    }


    /** Private. */

    private AcceptTeamupRequest getAcceptTeamupRequest() {
        return this;
    }


    /** AcceptTeamupApi interface. */

    interface AcceptTeamupApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @POST("/api/v2/users/{driveId}/accept_teamup_request")
        Call<JsonElement> acceptTeamup(@Path("driveId") Integer postId);
    }
}
