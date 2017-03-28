package drivetag.drivetag.com.driveceos.data_layer.requests;

import android.graphics.drawable.Drawable;
import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yuriy on 3/27/17.
 */

public class ImageUploadRequest extends ServerRequest {

    private Drawable image;
    private String imageUrlString;
    private String fileName;
    private Boolean canDelete;
    private Boolean uploading;

    private RegisterFileApi registerFileService;

    /**
     * Interface.
     */

    public ImageUploadRequest(Drawable image) {
        this.image = image;
        canDelete = true;
    }

    public void replaceImage(Drawable image) {
        this.image = image;
    }

    public void resumeWithCompletionHandler(ServerCompletionHandler handler) {
        registerFileOnServerWithCompletionHandler(handler);
    }

//    private void callCompletionHandler (Call<JsonElement> call, Response<JsonElement> response) {
//
//        final JsonObject jsonObject = response.body().getAsJsonObject();
//        if (!isSucceedResponse(jsonObject)) {
//            isFailure = true;
//            error = errorFromResponse(jsonObject);
//        }
//
//
//    }

    private void registerFileOnServerWithCompletionHandler(ServerCompletionHandler handler) {

        uploading = true;
        fileName = String.format("image_%d.jpg", image.hashCode());

        Call<JsonElement> call = registerFileService.registerFile(fileName);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                final JsonObject jsonObject = response.body().getAsJsonObject();

                handleSuccessResponse(response);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                System.out.println(fileName);
//                completionHandler.completionHandlerWithError(t.toString());
            }
        });
    }

    private ImageUploadRequest getImageUploadRequest() {
        return this;
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        registerFileService = retrofit.create(ImageUploadRequest.RegisterFileApi.class);


    }


    /** RegisterFileApi class. */

    interface RegisterFileApi {
        @GET("api/v2/images/file_sign/{fileName}")
        Call<JsonElement> registerFile(@Path("fileName") String fileName);
    }
}