package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Comment;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.comment.LoadPostCommentsRequest;
import drivetag.drivetag.com.driveceos.utils.ListUtils;

/**
 * Created by artem on 4/25/17.
 */

public class CommentsDataSource extends DataSource {

    public int startIndex;

    public int pageSize;

    private Post post;

    private LoadPostCommentsRequest loadPostCommentsRequest;


    /** Interface. */

    public CommentsDataSource(Post post) {
        this.post = post;
        this.pageSize = 3;
        this.startIndex = 0;
    }

    public void reloadData() {
        if (loading) {
            return;
        }

        lastPage = false;
        loading = true;
        page = 1;

        removeAllSections();
        notifyListenersWillLoadItems();

        loadCommentsWithCompletionHandler(new CommentDataSourceCompletionHandler() {
            @Override
            public void completionHandler() {
                notifyListenersDidLoadItems();
            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandlerWithError(error);
            }
        });
    }

    public void loadNextPage() {
        if (loading) {
            return;
        }

        notifyListenersWillLoadItems();
        loading = true;
        page++;

        startIndex = loadPostCommentsRequest.nextIndex;

        loadCommentsWithCompletionHandler(new CommentDataSourceCompletionHandler() {
            @Override
            public void completionHandler() {
                notifyListenersDidLoadItems();
            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandlerWithError(error);
            }
        });
    }


    /** Private. */

    private void loadCommentsWithCompletionHandler(CommentDataSourceCompletionHandler handler) {
        final CommentDataSourceCompletionHandler completionHandler = handler;

        loadPostCommentsRequest = new LoadPostCommentsRequest(post);
        loadPostCommentsRequest.startIndex = startIndex;

        loadPostCommentsRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Comment>>() {
            @Override
            public void completionHandler(ServerRequest<List<Comment>> serverRequest) {
                LoadPostCommentsRequest request = (LoadPostCommentsRequest) serverRequest;
                loading = false;

                if (request.error != null) {
                    lastPage = true;
                    notifyListenersDidLoadItemsWithError(request.error);
                } else {
                    if (request.nextIndex == -1) {
                        lastPage = true;
                    } else {
                        lastPage = false;
                    }

                    startIndex = request.nextIndex;

                    List<Comment> section;

                    if (sections.size() > 0) {
                        section = sections.get(0);


                        List<Comment> array = serverRequest.serverResponse;

                        ListUtils.addObjects(array, section);

//                        for (int i = 0; i > array.size(); i++) {
//                            Comment comment = array.get(i);
//                            section.add(comment);
//                        }
                    } else {
                        section = new ArrayList<>();
                        List<Comment> array = serverRequest.serverResponse;

//                        for (int i = 0; i > array.size(); i++) {
//                            Comment comment = array.get(i);
//                            section.add(comment);
//                        }

                        ListUtils.addObjects(array, section);

                        sections.add(section);
                    }

                    completionHandler.completionHandler();
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandler.completionHandlerWithError(error);
            }
        });
    }

    private interface CommentDataSourceCompletionHandler {
        void completionHandler();
        void completionHandlerWithError(String error);
    }
}