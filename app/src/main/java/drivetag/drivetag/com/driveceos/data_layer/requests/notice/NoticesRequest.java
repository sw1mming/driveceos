package drivetag.drivetag.com.driveceos.data_layer.requests.notice;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drivetag.drivetag.com.driveceos.data_layer.data_sources.CommentsDataSource;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.MDTDataSource;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.PeopleDrivingDataSource;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.PostDetailsDataSource;
import drivetag.drivetag.com.driveceos.data_layer.models.Notice;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by artem on 4/14/17.
 */

public class NoticesRequest extends ServerRequest {

    private Number nextIndex;

    private String noticesFilter;

    private NoticeApi service;

    public NoticesRequest(String noticesFilter, String token) {
        this.noticesFilter = noticesFilter;
        this.token = token;
    }

    public void resumeWithCompletionHandler(final NoticesCompletionHandler handler) {
        final NoticesCompletionHandler completionHandler = handler;

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("filter", noticesFilter);

        Call<JsonElement> call = service.notice(parameters);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonObject jsonObject = response.body().getAsJsonObject();

                Integer noticesCount = jsonObject.get("notices_count").getAsInt();
                Integer noticesPeopleCount = jsonObject.get("notices_people_count").getAsInt();
                Integer noticesTeamCount = jsonObject.get("notices_team_count").getAsInt();

                serverResponse = objectsFromResponse(jsonObject);

                completionHandler.completionHandler(getNoticesRequest(), noticesCount, noticesTeamCount, noticesPeopleCount);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                completionHandler.completionHandlerWithError(t.toString());
            }
        });
    }


    /** Private. */

    private List<Notice> objectsFromResponse(JsonObject response) {
        List<Notice> notices = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        List<JsonArray> noticesInfo = new ArrayList<>();
        JsonArray object = response.get("events").getAsJsonArray();
        noticesInfo.add(object);

        List<JsonArray> tagsInfo = new ArrayList<>();
        tagsInfo.add(response.get("tags").getAsJsonArray());

        List<JsonArray> discusses = new ArrayList<>();
        discusses.add(response.get("discusses").getAsJsonArray());

        if (discusses.size() > 0) {
            JsonArray discussionInfoArray = discusses.get(0).getAsJsonArray();

            JsonObject discussionInfo= discussionInfoArray.get(0).getAsJsonObject();

            JsonObject commentInfo = discussionInfo.get("comments").getAsJsonObject();
            nextIndex = commentInfo.get("next_index").getAsNumber();
        }

        for (JsonArray tagInfo: tagsInfo) {
            for (JsonElement tagObject: tagInfo) {
                Tag tag = new Tag(tagObject.getAsJsonObject());
                tags.add(tag);
            }
        }


        List<Post> posts = new ArrayList<>();
        for (JsonArray responseDictionary: discusses) {
            JsonObject responseObject = responseDictionary.get(0).getAsJsonObject();
                Post post = new Post(responseObject, tags);
                posts.add(post);

//            CommentsDataSource dataSource = new CommentsDataSource(post);
//            dataSource.reloadData();

//            PostDetailsDataSource dataSource = new PostDetailsDataSource(post);
//            dataSource.refreshData();

//            PeopleDrivingDataSource dataSource = new PeopleDrivingDataSource(post);
//            dataSource.peopleDrivingType = Constants.PeopleDrivingType.PEOPLE_DRIVING_TYPE_POST;
//            dataSource.reloadData();

//            self.dataSource = [[MDTDataSource alloc] init];
//            self.dataSource.selectedFilterIndex = 0;
//            self.dataSource.thoughtsType = ThoughtTypeAll;
//            self.dataSource.filterCategory = FilterCategoryAll;
//            self.dataSource.dataTypeString = FilterDrivenLeaders;

//            MDTDataSource dataSource = new MDTDataSource();
//            dataSource.selectedFilterIndex = 0;
//            dataSource.thoughtsType = Constants.KEY.THOUGHT_TYPE_NONE;
//            dataSource.filterCategory = Constants.KEY.FILTER_CATEGORY_ALL;
//            dataSource.dataTypeString = MDTDataSource.FILTER_DRIVEN_LEADERS;
//            dataSource.reloadData();
        }

        for (JsonArray noticeInfoArray: noticesInfo) {
            for (JsonElement noticeInfoElement: noticeInfoArray) {
                JsonObject obj = noticeInfoElement.getAsJsonObject();

                    String tagId = obj.get("tag_uid").getAsString();
                    Tag recipientTag = Tag.tagById(Integer.parseInt(tagId), tags);

                    String postId = obj.get("post_uid").getAsString();
                    Post post = Post.postById(Integer.parseInt(postId), posts);

                    JsonElement element = obj.get("writers_tag_uids");

                    JsonArray tagsIdsArray = element.getAsJsonArray();

                    Integer writerTagId = tagsIdsArray.get(0).getAsInt();

                    Tag writerTag = Tag.tagById(writerTagId, tags);

                    Notice notice = new Notice(obj);
                    notice.recipientTag = recipientTag;
                    notice.writerTag = writerTag;
                    notice.post = post;

                    notices.add(notice);
                }
            }

        return notices;
    }

    private NoticesRequest getNoticesRequest() {
        return this;
    }

    @Override
    public void setupService() {
        Retrofit retrofit = getRetrofit();
        service = retrofit.create(NoticeApi.class);
    }

    /**
     *  Completion handler interface for request.
     */

    public interface NoticesCompletionHandler {
        void completionHandler(NoticesRequest request, int noticesCount, int noticesTeamCount, int noticesPeopleCount);
        void completionHandlerWithError(String error);
    }


    /** NoticeApi interface. */

    interface NoticeApi {

        @GET("/api/v2/users/current_user/notices")
        Call<JsonElement> notice(@QueryMap Map<String, Object> parameters);
    }
}
