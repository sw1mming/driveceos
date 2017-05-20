package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
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

public class TagPostRequest extends ServerRequest {

    public Tag tag;
    public User user;
    public String message;
    public List<String> imagesUrlStrings;
    public TagPostApi service;

    public TagPostRequest(Tag tag, User user, String message) {
        this.tag = tag;
        this.user = user;
        this.message = message;
    }

    public TagPostRequest() {}

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

        Call<JsonElement> call = service.tagPost(tag.identifier,parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handleSuccessResponse(response);
                handler.completionHandler(getTagPostRequest());
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
        service = retrofit.create(TagPostApi.class);
    }


    /** Private. */

    private TagPostRequest getTagPostRequest() {
        return this;
    }


    /** TagPostApi interface. */

    interface TagPostApi {
        @POST("/api/v2/tags/{tagId}/post")
        Call<JsonElement> tagPost(@Path("tagId") Integer identifier, @Body HashMap<String, Object> parameters);
    }
}