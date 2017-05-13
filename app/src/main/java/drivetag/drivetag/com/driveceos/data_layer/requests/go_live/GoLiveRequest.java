package drivetag.drivetag.com.driveceos.data_layer.requests.go_live;

import com.google.gson.JsonElement;

import java.util.HashMap;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Created by YuriySokurko on 5/12/17.
 */

public class GoLiveRequest extends ServerRequest {

    public Boolean isOutsideHq;
    public Tag tag;
    private GoLiveApi service;

    public GoLiveRequest(Tag tag) {
        this.tag = tag;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("to_tag_uid", tag.identifier);
        parameters.put("outside_hq", isOutsideHq);

        Call<JsonElement> call = service.goLive(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    handleSuccessResponse(response);
                    handler.completionHandler(getGoLiveRequest());
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
        service = retrofit.create(GoLiveApi.class);
    }


    /** Private. */

    private GoLiveRequest getGoLiveRequest() {
        return this;
    }


    /** GoLiveApi interface. */

    interface GoLiveApi {
        @POST("/api/v2/users/current_user/live_video")
        Call<JsonElement> goLive(HashMap<String, Object> parameters);
    }
}