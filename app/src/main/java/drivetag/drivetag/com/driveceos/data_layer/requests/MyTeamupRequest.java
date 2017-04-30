package drivetag.drivetag.com.driveceos.data_layer.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import drivetag.drivetag.com.driveceos.data_layer.models.Teamup;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by sergeymelnik
 */

public class MyTeamupRequest extends ServerRequest {
    private MyTeamupApi service;

    public void resumeWithCompletionHandler(MyTeamupCompletionHandler handler) {
        final MyTeamupCompletionHandler completionHandler = handler;

        Call<JsonElement> call = service.getMyTeamup();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject jsonObject = response.body().getAsJsonObject();
                List<User> users = parseResponse(jsonObject);

                List<Teamup> historyUsers = historyTeamupRequestsFromUsers(users, jsonObject);

                List<Teamup> incomingUsers = incomingTeamupRequestsFromUsers(users, jsonObject);

                completionHandler.completionHandler(historyUsers, incomingUsers, getMyTeamupCompletionHandler());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                completionHandler.completionHandlerWithError(t.toString());
            }
        });
    }

    private List<User> parseResponse(JsonObject response) {
        JsonArray tagsArray = response.getAsJsonArray("tags");

        List<User> result = new ArrayList<>();

        for (JsonElement element: tagsArray) {
            User user = new User(null);
            JsonObject object = element.getAsJsonObject();

            if (JsonObjectHelper.hasValueFromKey("uid", object)) {
                user.tagId = object.get("uid").getAsString();
            }

            if (JsonObjectHelper.hasValueFromKey("tag", object)) {
                user.driveTag = object.get("tag").getAsString();
            }

            if (JsonObjectHelper.hasValueFromKey("firstname", object)) {
                user.firstName = object.get("firstname").getAsString();
            }

            if (JsonObjectHelper.hasValueFromKey("lastName", object)) {
                user.lastName = object.get("lastName").getAsString();
            }

            if (JsonObjectHelper.hasValueFromKey("is_employee", object)) {
                user.title = object.get("is_employee").getAsString();
            }

            if (JsonObjectHelper.hasValueFromKey("stats", object)) {
                JsonObject statsObject = object.get("stats").getAsJsonObject();

                if (JsonObjectHelper.hasValueFromKey("teammates_count", statsObject)) {
                    user.teammatesCount = statsObject.get("teammates_count").getAsInt();
                }
            }

            if (JsonObjectHelper.hasValueFromKey("teammates_count", object)) {
                JsonObject userObject = object.get("user").getAsJsonObject();

                if (JsonObjectHelper.hasValueFromKey("avatar", userObject)) {
                    String urlString = userObject.get("avatar").getAsString();

                    try {
                        user.avatarUrl = new URL(urlString);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                if (JsonObjectHelper.hasValueFromKey("uid", userObject)) {
                    user.driveID = userObject.get("").getAsNumber();
                }
            }

            result.add(user);
        }

        return result;
    }

    private List<Teamup> historyTeamupRequestsFromUsers(List<User> users, JsonObject response) {
        List<Teamup> result = new ArrayList<>();

        if (JsonObjectHelper.hasValueFromKey("history", response)) {
            JsonArray historyArray = response.getAsJsonArray("history");

            for (JsonElement jsonElement: historyArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (JsonObjectHelper.hasValueFromKey("tag_uid", jsonObject)) {
                    String tagId = jsonObject.get("tag_uid").getAsString();

                    for (User user: users) {
                        if (user.tagId.equals(tagId)) {
                            Teamup teamup = new Teamup();

                            teamup.user = user;

                            if (JsonObjectHelper.hasValueFromKey("time", jsonObject)) {
                                long timeInMiliseconds = jsonObject.get("time").getAsLong();

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(timeInMiliseconds);

                                try {
                                    teamup.timestamp = formatter.parse(calendar.getTime().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            result.add(teamup);
                        }
                    }
                }
            }
        }

        return result;
    }

    private List<Teamup> incomingTeamupRequestsFromUsers(List<User> users, JsonObject response) {
        List<Teamup> result = new ArrayList<>();

        if (JsonObjectHelper.hasValueFromKey("incoming", response)) {
            JsonArray incomingJsonArray = response.get("incoming").getAsJsonArray();

            for (JsonElement jsonElement : incomingJsonArray) {
                String tagId = response.get("tag_uid").getAsString();
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                for (User user: users) {
                    if (user.tagId.equals(tagId)) {
                        Teamup teamup = new Teamup();
                        teamup.user = user;

                        if (JsonObjectHelper.hasValueFromKey("time", jsonObject)) {
                            long timeInMiliseconds = jsonObject.get("time").getAsLong();

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(timeInMiliseconds);

                            try {
                                teamup.timestamp = formatter.parse(calendar.getTime().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private MyTeamupRequest getMyTeamupCompletionHandler() {
        return this;
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(MyTeamupApi.class);
    }

    interface MyTeamupApi {
        @Headers({
                "Authorization: Basic MTQ3NTE2NzMyMDM5NS04OTQ4MDI4ODU5NDUxNDE3MDc3Og==",
        })

        @GET("/api/v2/users/current_user/requests")
        Call<JsonElement> getMyTeamup();
    }

    /**
     *  Completion handler interface for request.
     */

    public interface MyTeamupCompletionHandler<T, Y> {
        void completionHandler(T historyUsers, Y incomingUsers, ServerRequest request);
        void completionHandlerWithError(String error);
    }
}
