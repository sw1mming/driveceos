package drivetag.drivetag.com.driveceos.data_layer.requests.users;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.requests.TagPostRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by YuriySokurko on 5/13/17.
 */

public class CallOutRequest extends TagPostRequest {

    private CallOutApi service;

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {
        HashMap<String, Object> parameters = new HashMap<>();
        List<String> socialKyes = new ArrayList<>();

        if(user.twitterOn) {
            socialKyes.add("tw");
        }

        if (user.facebookOn) {
            socialKyes.add("fb");
        }

        if(user.linkedInOn) {
            socialKyes.add("li");
        }

        parameters.put("publish_on", socialKyes);
        parameters.put("message", message);

        if(imagesUrlStrings != null) {
            parameters.put("images", imagesUrlStrings);
        }

        String currentId;

        if(tag.currentType().equals(tag.COMPANY_TYPE)) {
            currentId = tag.leaderId.toString();
        } else {
            currentId = tag.identifier.toString();
        }

        Call<JsonElement> call = service.callOut(currentId,parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getCallOutRequest());
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
        service = retrofit.create(CallOutApi.class);
    }


    /** Private. */

    private CallOutRequest getCallOutRequest() {
        return this;
    }


    /** CallOutApi interface. */

    interface CallOutApi {
        @POST("/api/v2/tags/{currentId}/call_out")
        Call<JsonElement> callOut(@Path("currentId") String currentId, @Body HashMap<String, Object> parameters);
    }
}
