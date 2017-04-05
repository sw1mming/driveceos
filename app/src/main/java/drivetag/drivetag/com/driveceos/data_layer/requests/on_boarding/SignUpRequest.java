package drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding;

import android.icu.text.SymbolTable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by artem on 3/24/17.
 */

public class SignUpRequest extends ServerRequest {

    public String imageUrlString;

    public User user;

    private String accessToken;

    private SignUpApi service;


    /** Interface */

    public SignUpRequest(User user) {
        super();
        this.user = user;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("firstname", user.firstName);
        parameters.put("lastname", user.lastName);
        parameters.put("email", user.email);
        parameters.put("password", user.password);
        parameters.put("uniquifier", user.driveTag);
//        parameters.put("verification_code", user.verification);
//        parameters.put("avatar", user.avatarUrl.toString());

        Call<JsonElement> call = service.signUp(parameters);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonElement jsonElement = handleSuccessResponse(response);

                if (jsonElement != null) {
                    final JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject != null) {
                        if (!isSucceedResponse(jsonObject)) {
                            isFailure = true;

                            JsonArray errors = jsonObject.getAsJsonArray("fields_errors");

                            if (errors != null && errors.size() > 0) {
                                JsonElement errorInfos = errors.get(0);

                                if (errorInfos != null && errorInfos.isJsonArray()) {
                                    JsonArray errorInfo = errorInfos.getAsJsonArray();

                                    if (errorInfo != null && errorInfo.size() > 1) {
                                        error = errorInfo.get(1).getAsString();
                                    }
                                }
                            }
                        } else {
                            accessToken = jsonObject.get("dt_access_token").getAsString();
                            User loginUser = new User(null);
                            /** TODO: User's init. */
                            user = loginUser;
                        }
                    }
                }

                completionHandler.completionHandler(getSignUpRequest());
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
        service = retrofit.create(SignUpRequest.SignUpApi.class);
    }


    /** Private */

    private SignUpRequest getSignUpRequest() {
        return this;
    }

    private interface SignUpApi {
        @POST("/api/auth/dt_signup")
        Call<JsonElement> signUp(@Body HashMap<String, String> credentials);
    }
}
