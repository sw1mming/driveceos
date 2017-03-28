package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by yuriy on 3/28/17.
 */

public class VerifyRequest extends ServerRequest {

    private String email;
    private VerifyRequestApi service;

    /**
     * Interface.
     */

    public VerifyRequest(String email) {
        this.email = email;

    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", email);

        Call<JsonElement> call = service.verifyRequest(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                handleSuccessResponse(response);
                handler.completionHandler(getVerifyRequest());
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
            service = retrofit.create(VerifyRequestApi.class);
        }


        /** Private. */

        private VerifyRequest getVerifyRequest() {
            return this;
        }


        /** VerifyRequestApi interface. */

        interface VerifyRequestApi {
            @POST("/api/auth/verif_code")
            Call<JsonElement> verifyRequest(@Body HashMap<String, String> parameters);
        }
}
