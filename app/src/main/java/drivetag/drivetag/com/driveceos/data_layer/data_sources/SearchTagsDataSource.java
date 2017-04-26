package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.RemoveRecentTagsRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SearchTagsHistoryRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SearchTagsRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;


/**
 * Created by yuriy on 4/26/17.
 */

public class SearchTagsDataSource extends DataSource {

    public enum SearchTagsType {
        SEARCH_TAGS_TYPE_RECENT,
        SEARCH_TAGS_TYPE_RESULTS
    }

    public SearchTagsType type;

    public String text;

    public Boolean hasObjects;

    private SearchTagsRequest request;

    private SearchTagsHistoryRequest historyRequest;

    private RemoveRecentTagsRequest removeRecentTagsRequest;


    /**
     * Interface.
     */

    public void reloadData() {
        notifyListenersWillLoadItems();
        removeAllSections();

        if (type == SearchTagsType.SEARCH_TAGS_TYPE_RECENT) {
            historyRequest = new SearchTagsHistoryRequest();

            historyRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Tag>>() {
                @Override
                public void completionHandler(ServerRequest<List<Tag>> request) {
                    handleResponseWithObjects(request.serverResponse);
                }

                @Override
                public void completionHandlerWithError(String error) {
                    completionHandlerWithError(error);
                }
            });
        } else {
            request = new SearchTagsRequest(text);

            request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Tag>>() {
                @Override
                public void completionHandler(ServerRequest<List<Tag>> request) {
                    handleResponseWithObjects(request.serverResponse);
                }

                @Override
                public void completionHandlerWithError(String error) {
                    completionHandlerWithError(error);
                }
            });
        }
    }

    public void clearRecentResults() {
        removeRecentTagsRequest = new RemoveRecentTagsRequest();
        removeRecentTagsRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                handleResponseWithObjects(null);
            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandlerWithError(error);
            }
        });
    }

    private void handleResponseWithObjects (List<Tag> tags) {
        if (tags.size() > 0) {
            sections.add(tags);
        }

        if (sections.size() > 0) {
            hasObjects = true;
        } else {
            hasObjects = false;
        }

        notifyListenersDidLoadItems();
    }
}