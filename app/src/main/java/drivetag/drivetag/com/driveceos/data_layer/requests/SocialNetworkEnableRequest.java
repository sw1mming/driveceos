package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by yuriy on 3/30/17.
 */

public class SocialNetworkEnableRequest extends ServerRequest {

    public enum SocialNetwork {
        SocialNetworkFacebook,
        SocialNetworkTwitter,
        SocialNetworkLinkedIn
    }

    private SocialNetwork socialNetwork;
    private SocialNetworkEnableApi service;

    /**
     * Interface.
     */

    public SocialNetworkEnableRequest(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = null;

        if (socialNetwork == SocialNetwork.SocialNetworkFacebook) {
            call = service.enableFacebookNetwork();
        } else if (socialNetwork == SocialNetwork.SocialNetworkTwitter) {
            call = service.enableTwitterNetwork();
        } else if (socialNetwork == SocialNetwork.SocialNetworkLinkedIn) {
            call = service.enableLinkedInNetwork();
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                completionHandler.completionHandler(getSocialNetworkEnableRequest());
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
        service = retrofit.create(SocialNetworkEnableApi.class);
    }


    /** Private. */

    private SocialNetworkEnableRequest getSocialNetworkEnableRequest() {
        return this;
    }


    /** SocialNetworkEnableApi interface. */

    interface SocialNetworkEnableApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("api/v2/users/current_user/fb_enabled")
        Call<JsonElement> enableFacebookNetwork();

        @PUT("api/v2/users/current_user/tw_enabled")
        Call<JsonElement> enableTwitterNetwork();

        @PUT("api/v2/users/current_user/li_enabled")
        Call<JsonElement> enableLinkedInNetwork();
    }
}