package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by YuriySokurko on 5/17/17.
 */

public class DrivenMessageRequest extends ServerRequest {

    private String drivenMessage;
    private DrivenMessageApi service;


    /**
     * Interface.
     */

    public DrivenMessageRequest(String drivenMessage) {
        this.drivenMessage = drivenMessage;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("driven_message", drivenMessage);

        Call<JsonElement> call = service.drivenMessage(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getDrivenMessageRequest());
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
        service = retrofit.create(DrivenMessageApi.class);
    }


    /**
     * Private.
     */

    private DrivenMessageRequest getDrivenMessageRequest() {
        return this;
    }


    /**
     * DrivenMessageApi interface.
     */

    interface DrivenMessageApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("/api/v2/users/current_user/wdu_and_post")
        Call<JsonElement> drivenMessage(@Body Map<String, String> parameters);
    }
}





