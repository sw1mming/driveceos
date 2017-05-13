package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonElement;
import java.util.HashMap;

import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class SideBySideAvatarRequest extends ServerRequest {

    private String fromUserId;
    private String toUserId;
    private String colorName;
    private SideBySideAvatarApi service;

    public SideBySideAvatarRequest(String fromUserId, String toUserId, String colorName) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.colorName = colorName;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("from_user_uid", fromUserId);
        parameters.put("to_user_uid", toUserId);
        parameters.put("color", colorName);
        parameters.put("arrow", "true");

        Call<JsonElement> call = service.sideBySideAvatar(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getSideBySideAvatarRequest());
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
        service = retrofit.create(SideBySideAvatarApi.class);
    }


    /** Private. */

    private SideBySideAvatarRequest getSideBySideAvatarRequest() {
        return this;
    }


    /** GoLiveApi interface. */

    interface SideBySideAvatarApi {
        @POST("/api/view/side_by_side_avatars")
        Call<JsonElement> sideBySideAvatar(HashMap<String, String> parameters);
    }
}