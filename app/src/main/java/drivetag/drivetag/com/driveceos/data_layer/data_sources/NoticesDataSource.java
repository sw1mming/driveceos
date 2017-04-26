package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Notice;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.notice.NoticeReadStatusRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.notice.NoticesRequest;

/**
 * Created by artem on 4/14/17.
 */

public class NoticesDataSource extends DataSource {

    public String noticesFilter;

    public int noticesCount;

    public int noticesTeamCount;

    public int noticesPeopleCount;

    private NoticesRequest request;


    /** Interface. */

    public void reloadData() {
        removeAllSections();

        loadNoticesAndAddContent(true, null);
    }

    public void loadNoticesAndAddContent (final boolean addContent, NoticesCompletionHandler handler) {
        final NoticesCompletionHandler completionHandler = handler;

        if (addContent) {
            notifyListenersWillLoadItems();
        }

        // todo: send token to parameters.
        noticesFilter = "people";
        request = new NoticesRequest(noticesFilter, "1475167320395-8948028859451417077");

        request.resumeWithCompletionHandler(new NoticesRequest.NoticesCompletionHandler() {
            @Override
            public void completionHandler(NoticesRequest request, int noticesSize, int noticesTeamSize, int noticesPeopleSize) {

                noticesCount = noticesSize;
                noticesTeamCount = noticesTeamSize;
                noticesPeopleCount = noticesPeopleSize;

                if (addContent) {
                    if (request.error != null) {
                        notifyListenersDidLoadItemsWithError(request.error);
                    }
                } else {
                    List<JsonArray> section = new ArrayList();

                    if (sections.size() > 0) {
                        section = sections.get(0);
                    }

                    sections.add(section);

                    JsonArray array = (JsonArray) request.serverResponse;

                    section.add(array);

                    notifyListenersDidLoadItems();

                    completionHandler.completionHandler();
                }

            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandler.completionHandlerWithError(error);
            }
        });
    }

    public boolean hasUnreadNotices() {
        for (List<Notice> notices: sections) {
            for (Notice notice: notices) {
                if (notice.isUnread) {
                    return true;
                }
            }
        }
        return false;
    }

    public void markAllNoticesAsRead (final NoticesCompletionHandler handler) {
        final NoticesCompletionHandler completionHandler = handler;

        List<Notice> result = new ArrayList<>();

        if (sections.size() > 0) {
            result = sections.get(0);
        } else {
            completionHandler.completionHandler();
        }

        removeAllSections();
        notifyListenersWillLoadItems();

        NoticeReadStatusRequest noticeReadStatusRequest = new NoticeReadStatusRequest(result, true);

        noticeReadStatusRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                reloadData();
            }

            @Override
            public void completionHandlerWithError(String error) {
                handler.completionHandlerWithError(error);
            }
        });
    }

    public void toggleReadStatusForNotice (final Notice notice, NoticesCompletionHandler handler) {
        final NoticesCompletionHandler completionHandler = handler;
        List<Notice> array = new ArrayList<>();
        array.add(notice);
        NoticeReadStatusRequest noticeReadStatusRequest = new NoticeReadStatusRequest(array, true);

        noticeReadStatusRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {

                if (notice.isUnread) {
                    notice.isUnread = true;
                } else {
                    notice.isUnread = null;
                }

                loadNoticesAndAddContent(false, new NoticesCompletionHandler() {
                    @Override
                    public void completionHandler() {
                        completionHandler.completionHandler();
                    }

                    @Override
                    public void completionHandlerWithError(String error) {
                        completionHandler.completionHandlerWithError(error);
                    }
                });

            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandler.completionHandlerWithError(error);
            }
        });
    }


    /**
     *  Completion handler interface for data source.
     */

    private interface NoticesCompletionHandler {
        void completionHandler();
        void completionHandlerWithError(String error);
    }
}


