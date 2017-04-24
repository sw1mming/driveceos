package drivetag.drivetag.com.driveceos.data_layer.requests.notice;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drivetag.drivetag.com.driveceos.data_layer.models.Notice;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

/**
 * Created by artem on 4/24/17.
 */

public class NoticeReadStatusRequest extends ServerRequest {

    private List<Notice> notices;

    private boolean markAsRead;

    private NoticeReadStatusApi service;


    /** Interface. */

    public NoticeReadStatusRequest(List<Notice> notices, boolean markAsRead) {
        this.notices = notices;
        this.markAsRead = markAsRead;
    }

    public void resumeWithCompletionHandler(final ServerCompletionHandler handler) {

        List<String> result = new ArrayList<>();

        for (Notice notice: notices) {
            result.add(notice.noticeId);
        }

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("event_uids", result);

        Call<JsonElement> call;

        if (markAsRead) {
            call = service.noticeReadStatus(parameters);
        } else {
            call = service.noticeUnreadStatus(parameters);
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                handler.completionHandler(getNoticeReadStatusRequest());
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
        service = retrofit.create(NoticeReadStatusApi.class);
    }


    /** Private. */

    private NoticeReadStatusRequest getNoticeReadStatusRequest() {
        return this;
    }


    /** NoticeReadStatusApi interface. */

    interface NoticeReadStatusApi {

        @DELETE("/api/v2/users/current_user/notices_mark_read")
        Call<JsonElement> noticeUnreadStatus(@QueryMap Map<String, Object> parameters);

        @PUT("/api/v2/users/current_user/notices_mark_read")
        Call<JsonElement> noticeReadStatus(@QueryMap Map<String, Object> parameters);


    }
}
