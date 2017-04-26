package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by yuriy on 4/26/17.
 */

public class MutualTeammatesRequest extends ServerRequest {

    private MutualTeammatesApi service;


    /**
     * Interface.
     */

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        final ServerCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.mutualTeammates();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                System.out.println();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                System.out.println();
            }
        });
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(MutualTeammatesApi.class);
    }


    /**
     * Private.
     */

    private MutualTeammatesRequest getMutualTeammatesRequest() {
        return this;
    }


    /** MutualTeammatesApi class. */

    interface MutualTeammatesApi {

        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("/api/v2/tags/743539/mutual_teammates")
        Call<JsonElement> mutualTeammates();
    }
}
