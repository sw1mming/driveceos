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

public class SocialAutomaticTeammatesRequest extends ServerRequest  {

    public enum SocialOption {
        SocialOptionFacebook,
        SocialOptionTwitter,
        SocialOptionLinkedIn
    }

    public SocialOption socialOption;

    private SocialAutomaticTeammatesApi service;
    private Boolean onOff;


    /**
     * Interface.
     */

    public SocialAutomaticTeammatesRequest(SocialOption socialOption, Boolean onOff) {
        this.socialOption = socialOption;
        this.onOff = onOff;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        String social = "";
        String enabled;

        if (socialOption == SocialOption.SocialOptionFacebook) {
            social = "fb";
        } else if (socialOption == SocialOption.SocialOptionTwitter) {
            social = "tw";
        } else if (socialOption == SocialOption.SocialOptionLinkedIn) {
            social = "li";
        }

        if (onOff) {
            enabled = "true";
        } else {
            enabled = "false";
        }

        Call<JsonElement> call = service.socialAutomaticTeammates(social,enabled);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);

                completionHandler.completionHandler(getSocialAutomaticTeammatesRequest());
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
        service = retrofit.create(SocialAutomaticTeammatesApi.class);
    }


    /** Private. */

    private SocialAutomaticTeammatesRequest getSocialAutomaticTeammatesRequest() {
        return this;
    }


    /** SocialAutomaticTeammatesApi interface. */

    interface SocialAutomaticTeammatesApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @PUT("/api/v2/users/current_user/settings/social_automatic_teammates")
        Call<JsonElement> socialAutomaticTeammates(@Query("social") String social, @Query("enabled") String enabled);
    }
}